package com.example.group20_hw04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PagesRecyclerViewAdapter extends RecyclerView.Adapter<PagesRecyclerViewAdapter.PageViewHolder> {
    ArrayList<String> pages;
    IPagesRecycler mListener;

    public PagesRecyclerViewAdapter(ArrayList<String> pages, IPagesRecycler mListener) {
        this.pages = pages;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_number, parent, false);
        PageViewHolder pageViewHolder = new PageViewHolder(view, mListener);
        return pageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        String page = pages.get(position);
        holder.textViewPage.setText(page);
        holder.page = page;
    }

    @Override
    public int getItemCount() {
        return this.pages.size();
    }

    public static class PageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPage;
        View rootView;
        int position;
        String page;
        IPagesRecycler mListener;

        public PageViewHolder(@NonNull View itemView, IPagesRecycler mListener) {
            super(itemView);
            rootView = itemView;
            this.mListener = mListener;
            textViewPage = itemView.findViewById(R.id.textViewPage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.gotoNewPage(textViewPage.getText().toString());
                }
            });
        }
    }

    interface IPagesRecycler {
        void gotoNewPage(String page);
    }

}
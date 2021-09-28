package com.example.group20_hw02;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SortRecyclerViewAdapter extends RecyclerView.Adapter<SortRecyclerViewAdapter.SortAttributeViewHolder> {
    String[] attributes;
    ISortRecycler mListener;

    public SortRecyclerViewAdapter(String[] attributes, ISortRecycler mListener) {
        this.attributes = attributes;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public SortAttributeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_row_item, parent, false);
        SortAttributeViewHolder sortAttributeViewHolder = new SortAttributeViewHolder(view, mListener);
        return sortAttributeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SortAttributeViewHolder holder, int position) {
        String attribute = attributes[position];
        holder.textViewSortAttribute.setText(attribute);
        holder.sortAttribute = attribute;
    }

    @Override
    public int getItemCount() {
        return this.attributes.length;
    }

    public static class SortAttributeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSortAttribute;
        String sortAttribute;
        Boolean ascending;
        View rootView;
        int position;
        ISortRecycler mListener;

        public SortAttributeViewHolder(@NonNull View itemView, ISortRecycler mListener) {
            super(itemView);
            this.mListener = mListener;
            rootView = itemView;
            textViewSortAttribute = itemView.findViewById(R.id.textViewSortAttribute);

            itemView.findViewById(R.id.imageButtonAscending).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortAttribute = textViewSortAttribute.getText().toString();
                    ascending = true;
                    mListener.sortUsers(sortAttribute, ascending);
                }
            });

            itemView.findViewById(R.id.imageButtonDescending).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortAttribute = textViewSortAttribute.getText().toString();
                    ascending = false;
                    mListener.sortUsers(sortAttribute, ascending);
                }
            });

        }
    }


    interface ISortRecycler {
        void sortUsers(String sortAttribute, Boolean ascending);
    }

}



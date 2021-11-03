package com.example.group20_hw05;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ForumsRecyclerViewAdapter extends RecyclerView.Adapter<ForumsRecyclerViewAdapter.ForumViewHolder> {
    ArrayList<Forum> forums;
    IForumsRecycler mListener;
    FirebaseAuth mAuth;

    public ForumsRecyclerViewAdapter(ArrayList<Forum> forums, IForumsRecycler mListener) {
        this.forums = forums;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_row_item, parent, false);
        ForumViewHolder forumViewHolder = new ForumViewHolder(view, mListener);
        return forumViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        mAuth = FirebaseAuth.getInstance();

        DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy h:mm a");

        Forum forum = forums.get(position);
        holder.forum = forum;

        Timestamp time = forum.getTimeStamp();

        if(time != null) {
            holder.textViewTimeStamp.setText(dateFormat.format(time.toDate()));
        }

        holder.textViewTitle.setText(forum.getTitle());
        holder.textViewCreator.setText(forum.getCreator());
        holder.textviewDescription.setText(forum.getDescription());

        if(mAuth.getCurrentUser().getUid().equals(forum.getCreatorUid())) {
            holder.imageButtonDelete.setVisibility(View.VISIBLE);
        } else {
            holder.imageButtonDelete.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.forums.size();
    }

    public static class ForumViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewCreator;
        TextView textviewDescription;
        TextView textViewTimeStamp;
        ImageButton imageButtonDelete;

        View rootView;
        int position;
        Forum forum;
        IForumsRecycler mListener;

        public ForumViewHolder(@NonNull View itemView, IForumsRecycler mListener) {
            super(itemView);
            rootView = itemView;
            this.mListener = mListener;

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCreator = itemView.findViewById(R.id.textViewCreator);
            textviewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewTimeStamp = itemView.findViewById(R.id.textViewTimeStamp);
            imageButtonDelete = itemView.findViewById(R.id.imageButtonDelete);

            itemView.findViewById(R.id.imageButtonDelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.deleteForum(forum);
                }
            });

        }
    }

    interface IForumsRecycler {
        void deleteForum(Forum forum);
    }
}


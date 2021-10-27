package com.example.group20_inclass08;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

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

        Forum forum = forums.get(position);
        holder.forum = forum;
        holder.textViewTitle.setText(forum.getTitle());
        holder.textViewCreator.setText(forum.getCreator());
        holder.textviewDescription.setText(forum.getDescription());
        holder.textViewTimeStamp.setText(String.valueOf(forum.getTimeStamp()));

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

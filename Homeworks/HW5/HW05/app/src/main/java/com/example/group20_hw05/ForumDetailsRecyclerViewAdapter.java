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

public class ForumDetailsRecyclerViewAdapter extends RecyclerView.Adapter<ForumDetailsRecyclerViewAdapter.CommentViewHolder> {
    ArrayList<Comment> comments;
    ICommentsRecycler mListener;
    FirebaseAuth mAuth;

    public ForumDetailsRecyclerViewAdapter(ArrayList<Comment> comments, ICommentsRecycler mListener) {
        this.comments = comments;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row_item, parent, false);
        CommentViewHolder commentViewHolder = new CommentViewHolder(view, mListener);
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        mAuth = FirebaseAuth.getInstance();

        DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy h:mm a");

        Comment comment = comments.get(position);
        holder.comment = comment;

        Timestamp time = comment.getTimeStamp();

        if(time != null) {
            holder.textViewCommentTimeStamp.setText(dateFormat.format(time.toDate()));
        }

        holder.textViewCommentCreator.setText(comment.getCreator());
        holder.textviewCommentDescription.setText(comment.getDescription());

        if(mAuth.getCurrentUser().getUid().equals(comment.getCreatorUid())) {
            holder.imageButtonDelete.setVisibility(View.VISIBLE);
        } else {
            holder.imageButtonDelete.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.comments.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCommentCreator;
        TextView textviewCommentDescription;
        TextView textViewCommentTimeStamp;
        ImageButton imageButtonDelete;

        View rootView;
        int position;
        Comment comment;
        ICommentsRecycler mListener;

        public CommentViewHolder(@NonNull View itemView, ICommentsRecycler mListener) {
            super(itemView);
            rootView = itemView;
            this.mListener = mListener;

            textViewCommentCreator = itemView.findViewById(R.id.textViewCommentCreator);
            textviewCommentDescription = itemView.findViewById(R.id.textViewCommentDescription);
            textViewCommentTimeStamp = itemView.findViewById(R.id.textViewCommentTimeStamp);
            imageButtonDelete = itemView.findViewById(R.id.imageButtonDelete);

            imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.deleteComment(comment);
                }
            });
        }
    }

    interface ICommentsRecycler {
        void deleteComment(Comment comment);
    }
}

package com.example.group20_hw04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostsListRecyclerViewAdapter extends RecyclerView.Adapter<PostsListRecyclerViewAdapter.PostViewHolder> {
    ArrayList<Post> posts;
    IPostsListRecycler mListener;
    UserToken mUserToken;

    public PostsListRecyclerViewAdapter(ArrayList<Post> posts, IPostsListRecycler mListener, UserToken mUserToken) {
        this.posts = posts;
        this.mListener = mListener;
        this.mUserToken = mUserToken;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row_item, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view, mListener, mUserToken);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.post = post;
        holder.textViewPostText.setText(post.getPostText());
        holder.textViewAuthor.setText(post.getAuthor());
        holder.textViewTimeStamp.setText(post.getTimeStamp());

        if (post.getAuthorId().equals(mUserToken.getUserId())) {
            holder.imageButtonDelete.setVisibility(View.VISIBLE);
        } else {
            holder.imageButtonDelete.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPostText;
        TextView textViewAuthor;
        TextView textViewTimeStamp;
        ImageButton imageButtonDelete;

        View rootView;
        int position;
        Post post;
        IPostsListRecycler mListener;
        UserToken mUserToken;

        public PostViewHolder(@NonNull View itemView, IPostsListRecycler mListener, UserToken mUserToken) {
            super(itemView);
            rootView = itemView;
            this.mListener = mListener;
            this.mUserToken = mUserToken;

            textViewPostText = itemView.findViewById(R.id.textViewPostText);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewTimeStamp = itemView.findViewById(R.id.textViewTimeStamp);
            imageButtonDelete = itemView.findViewById(R.id.imageButtonDelete);

            itemView.findViewById(R.id.imageButtonDelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.deletePost(post);
                }
            });

        }
    }

    interface IPostsListRecycler {
        void deletePost(Post post);
    }
}
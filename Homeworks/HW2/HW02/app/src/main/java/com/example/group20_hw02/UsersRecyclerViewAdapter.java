package com.example.group20_hw02;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.UserViewHolder> {
    ArrayList<DataServices.User> users;

    public UsersRecyclerViewAdapter(ArrayList<DataServices.User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_item, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        DataServices.User user = users.get(position);
        holder.textViewName.setText(user.name);
        holder.textViewState.setText(user.state);
        holder.textViewAge.setText(user.age + " Years Old");
        holder.textViewGroup.setText(user.group);
        if(user.gender == "Male") {
            holder.imageViewGender.setImageResource(R.drawable.avatar_male);
        } else {
            holder.imageViewGender.setImageResource(R.drawable.avatar_female);
        }
        holder.user = user;
    }

    public void remove(int position) {
        users.remove(position);
        notifyItemChanged(position);
        notifyItemRangeRemoved(position, 1);
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewState;
        TextView textViewAge;
        TextView textViewGroup;
        ImageView imageViewGender;
        View rootView;
        int position;
        DataServices.User user;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewState = itemView.findViewById(R.id.textViewState);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            textViewGroup = itemView.findViewById(R.id.textViewGroup);
            imageViewGender = itemView.findViewById(R.id.imageViewGender);
        }
    }
}


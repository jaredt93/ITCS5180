package com.example.group20_hw04;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.group20_hw04.databinding.FragmentPostsListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PostsListFragment extends Fragment implements PostsListRecyclerViewAdapter.IPostsListRecycler, PagesRecyclerViewAdapter.IPagesRecycler {
    FragmentPostsListBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    private static final String ARG_USER_TOKEN = "ARG_USER_TOKEN";
    UserToken mUserToken;
    private int totalPages = 1;
    private String currentPage = "1";

    ArrayList<Post> posts = new ArrayList<>();
    ArrayList<String> pages = new ArrayList<>();

    LinearLayoutManager layoutManagerPosts;
    PostsListRecyclerViewAdapter adapterPosts;

    LinearLayoutManager layoutManagerPages;
    PagesRecyclerViewAdapter adapterPages;

    public PostsListFragment() {
        // Required empty public constructor
    }

    public static PostsListFragment newInstance(UserToken userToken) {
        PostsListFragment fragment = new PostsListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_TOKEN, userToken);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserToken = (UserToken)getArguments().getSerializable(ARG_USER_TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostsListBinding.inflate(inflater, container, false);

        layoutManagerPosts = new LinearLayoutManager(getContext());
        binding.recyclerViewPosts.setLayoutManager(layoutManagerPosts);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(binding.recyclerViewPosts.getContext(), layoutManagerPosts.getOrientation());
        binding.recyclerViewPosts.addItemDecoration(mDividerItemDecoration);

        adapterPosts = new PostsListRecyclerViewAdapter(posts, this, mUserToken);
        binding.recyclerViewPosts.setAdapter(adapterPosts);

        layoutManagerPages = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewPages.setLayoutManager(layoutManagerPages);

        adapterPages = new PagesRecyclerViewAdapter(pages, this);
        binding.recyclerViewPages.setAdapter(adapterPages);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Posts");
        getPosts(currentPage);

        binding.textViewHello.setText("Hello " + mUserToken.getFullname());

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoLogin();
            }
        });

        binding.buttonCreatePost.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoCreatePost(mUserToken);
            }
        }));
    }

    public void getPosts(String page) {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https")
                .host("www.theappsdr.com")
                .addPathSegment("posts")
                .addQueryParameter("page", page)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "BEARER " + mUserToken.getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();

                    try {
                        JSONObject json = new JSONObject(body);
                        totalPages = (int) Math.ceil(json.getDouble("totalCount") / 10);
                        pages.clear();

                        for(int i = 1; i <= totalPages; i++) {
                            pages.add(String.valueOf(i));
                        }

                        posts.clear();
                        JSONArray postsJson = json.getJSONArray("posts");
                        for(int i = 0; i < postsJson.length(); i++) {
                            JSONObject postJsonObject = postsJson.getJSONObject(i);
                            Post post = new Post();
                            post.setPostText(postJsonObject.getString("post_text"));
                            post.setPostId(postJsonObject.getString("post_id"));
                            post.setAuthor(postJsonObject.getString("created_by_name"));
                            post.setAuthorId(postJsonObject.getString("created_by_uid"));
                            post.setTimeStamp(postJsonObject.getString("created_at"));
                            posts.add(post);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterPosts.notifyDataSetChanged();
                            adapterPages.notifyDataSetChanged();

                            binding.textViewPageNumber.setText("Showing Page " + page + " out of " + totalPages);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void gotoNewPage(String page) {
        getPosts(page);
        this.currentPage = page;
    }

    @Override
    public void deletePost(Post post) {
        AlertDialog.Builder builderAlert = new AlertDialog.Builder(getContext());
        builderAlert.setMessage("Do you want to delete the selected post?");
        builderAlert.setCancelable(true);

        builderAlert.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delete(post);
                        dialog.cancel();
                    }
                });

        builderAlert.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builderAlert.create();
        alert.show();
    }

    private void delete(Post post) {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https")
                .host("www.theappsdr.com")
                .addPathSegment("posts")
                .addPathSegment("delete")
                .build();

        FormBody formBody = new FormBody.Builder()
                .add("post_id", post.getPostId())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "BEARER " + mUserToken.getToken())
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getPosts(currentPage);
                        }
                    });
                } else {
                    String body = response.body().string();
                    try {
                        JSONObject json = new JSONObject(body);
                        String message = json.getString("message");

                        Log.d("demo", "onResponse: " + message);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    // Listener
    PostsListFragment.PostsListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (PostsListFragment.PostsListListener) context;
    }

    public interface PostsListListener {
        void gotoLogin();
        void gotoCreatePost(UserToken mUserToken);
    }
}
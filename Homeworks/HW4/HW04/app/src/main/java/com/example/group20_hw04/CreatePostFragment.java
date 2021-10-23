package com.example.group20_hw04;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.group20_hw04.databinding.FragmentCreatePostBinding;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CreatePostFragment extends Fragment {

    FragmentCreatePostBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    private static final String ARG_USER_TOKEN = "ARG_USER_TOKEN";
    private UserToken mUserToken;

    public CreatePostFragment() {
        // Required empty public constructor
    }

    public static CreatePostFragment newInstance(UserToken mUserToken) {
        CreatePostFragment fragment = new CreatePostFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_TOKEN, mUserToken);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserToken = (UserToken) getArguments().getSerializable(ARG_USER_TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Post");

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goBack();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String post = binding.editTextPost.getText().toString();

                if(post.isEmpty() || post == null) {
                    Toast.makeText(view.getContext(), "Missing post input!", Toast.LENGTH_SHORT).show();
                } else {
                    createPost(post);
                }
            }
        });
    }

    private void createPost(String post) {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https")
                .host("www.theappsdr.com")
                .addPathSegment("posts")
                .addPathSegment("create")
                .build();

        FormBody formBody = new FormBody.Builder()
                .add("post_text", post)
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

            // Child thread
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mListener.goBack();
                    }
                });
            }
        });
    }


    // Listener
    CreatePostFragment.CreatePostListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreatePostFragment.CreatePostListener) context;
    }

    public interface CreatePostListener {
        void goBack();
    }
}
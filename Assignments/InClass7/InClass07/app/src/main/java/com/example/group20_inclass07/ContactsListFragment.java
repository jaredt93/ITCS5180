package com.example.group20_inclass07;

import android.content.Context;
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
import android.widget.AdapterView;

import com.example.group20_inclass07.databinding.FragmentContactsListBinding;

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

public class ContactsListFragment extends Fragment implements ContactsListRecyclerViewAdapter.IContactsListRecycler {
    FragmentContactsListBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<Contact> contacts = new ArrayList<>();
    LinearLayoutManager layoutManager;
    ContactsListRecyclerViewAdapter adapter;

    public ContactsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactsListBinding.inflate(inflater, container, false);
        getActivity().setTitle("Contacts");

        layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(binding.recyclerView.getContext(), layoutManager.getOrientation());
        binding.recyclerView.addItemDecoration(mDividerItemDecoration);

        adapter = new ContactsListRecyclerViewAdapter(contacts, this);
        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getContacts();

        binding.buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoNewContact();
            }
        });

    }

    // Retrieve list of contacts
    public void getContacts() {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https")
                .host("www.theappsdr.com")
                .addPathSegment("contacts")
                .addPathSegment("json")
                .build();

        Request request = new Request.Builder()
                .url(url)
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
                        contacts.clear();
                        JSONObject json = new JSONObject(body);
                        JSONArray contactsJson = json.getJSONArray("contacts");

                        for(int i = 0; i < contactsJson.length(); i++) {
                            JSONObject contactJsonObject = contactsJson.getJSONObject(i);
                            Contact contact = new Contact();
                            contact.setName(contactJsonObject.getString("Name"));
                            contact.setEmail(contactJsonObject.getString("Email"));
                            contact.setPhoneNumber(contactJsonObject.getString("Phone"));
                            contact.setType(contactJsonObject.getString("PhoneType"));
                            contact.setId(contactJsonObject.getString("Cid"));
                            contacts.add(contact);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }


    @Override
    public void gotoContactDetails(Contact contact) {
        mListener.gotoContactDetails(contact);
    }

    @Override
    public void deleteContact(Contact contact) {
        FormBody formBody = new FormBody.Builder()
                .add("id", contact.getId())
                .build();

        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https")
                .host("www.theappsdr.com")
                .addPathSegment("contact")
                .addPathSegment("json")
                .addPathSegment("delete")
                .build();

        Request request = new Request.Builder()
                .url(url)
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
                            adapter.notifyDataSetChanged();
                            getContacts();
                        }
                    });
                }
            }
        });
    }


    // Listener
    ContactsListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ContactsListListener) {
            mListener = (ContactsListListener) context;
        } else {
            throw new RuntimeException((context.toString() + "must implement ContactsListListener"));
        }
    }

    public interface ContactsListListener {
        void gotoContactDetails(Contact contact);
        void gotoNewContact();
    }

}
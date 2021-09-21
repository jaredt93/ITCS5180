package com.example.group20_inclass05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.group20_inclass05.databinding.AppRowItemBinding;

import org.w3c.dom.Text;

import java.util.List;

public class AppsAdapter extends ArrayAdapter<DataServices.App> {
    public AppsAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.App> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            AppRowItemBinding binding = AppRowItemBinding.inflate(LayoutInflater.from(getContext()), parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }

        DataServices.App app = getItem(position);

        AppRowItemBinding binding = (AppRowItemBinding) convertView.getTag();

        binding.textViewAppName.setText(app.name);
        binding.textViewArtistName.setText(app.artistName);
        binding.textViewReleaseDate.setText(app.releaseDate);

        return convertView;
    }

}

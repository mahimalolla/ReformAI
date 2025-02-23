package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import java.util.List;

public class PersonGridAdapter extends BaseAdapter {
    private Context context;
    private List<Person> personList;

    public PersonGridAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }

        ImageView profileImage = convertView.findViewById(R.id.profileImage);
        Button nameButton = convertView.findViewById(R.id.nameButton);

        Person person = personList.get(position);
        profileImage.setImageResource(person.getImageResource());
        nameButton.setText(person.getName());

        // ✅ Fix: Make button clickable
        nameButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, PersonDetailActivity.class);
            intent.putExtra("person", person);
            context.startActivity(intent);
        });

        return convertView;
    }

}

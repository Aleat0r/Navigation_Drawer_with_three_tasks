package com.kovalenko.aleksandr.aleat0r.navigationdrawercamerarealmcustomimageview.Realm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.kovalenko.aleksandr.aleat0r.navigationdrawercamerarealmcustomimageview.R;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

// Класс адаптера для вывода информации с базы данных Realm в список
public class MyRealmAdapter extends RealmBaseAdapter <User> implements ListAdapter {

    public MyRealmAdapter(Context context, RealmResults <User> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    private static class ViewHolder {
        TextView name;
        TextView age;
        TextView email;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            view = inflater.inflate(R.layout.realm_list_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.textViewName);
            viewHolder.age = (TextView) view.findViewById(R.id.textViewAge);
            viewHolder.email = (TextView) view.findViewById(R.id.textViewEmail);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        User user = realmResults.get(position);
        viewHolder.name.setText(user.getName());
        viewHolder.age.setText(String.valueOf(user.getAge()));
        viewHolder.email.setText(user.getEmail());
        return view;
    }
}

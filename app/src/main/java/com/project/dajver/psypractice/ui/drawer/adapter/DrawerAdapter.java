package com.project.dajver.psypractice.ui.drawer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.drawer.DrawerModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 11/7/17.
 */

public class DrawerAdapter extends ArrayAdapter<DrawerModel> {

    private Context context;
    private List<DrawerModel> drawerItemList;

    public DrawerAdapter(Context context, List<DrawerModel> listItems) {
        super(context, 0, listItems);
        this.context = context;
        this.drawerItemList = listItems;
    }

    @Override
    public int getCount() {
        return drawerItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_drawer, parent, false);

        DrawerItemHolder drawerHolder = new DrawerItemHolder(convertView);
        drawerHolder.itemName.setText(drawerItemList.get(position).getTitle());
        return convertView;
    }

    public class DrawerItemHolder {

        @BindView(R.id.itemName)
        TextView itemName;

        public DrawerItemHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
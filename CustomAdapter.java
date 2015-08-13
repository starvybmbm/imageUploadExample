package com.example.chase.imageupload;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by chase on 8/12/2015.
 */
public class CustomAdapter extends ArrayAdapter<imgData> {

    public CustomAdapter(Context context, ArrayList<imgData> imgList){
        super(context, R.layout.custom_listview, imgList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        imgData imgdata = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
            viewHolder.iView = (ImageView) convertView.findViewById(R.id.image_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.iView.setImageDrawable(imgdata.drawables.get(position));

        return convertView;
    }

    public static class ViewHolder{
        ImageView iView;
    }
}

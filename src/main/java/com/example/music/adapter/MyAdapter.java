package com.example.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.music.R;
import com.example.music.species.Music;
import com.example.music.util.MusicUtils;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<Music> list;
    private Context context;

    public MyAdapter(List<Music> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_layout, null);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.artist = convertView.findViewById(R.id.artist);
            viewHolder.duration = convertView.findViewById(R.id.duration);
            convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.artist.setText(list.get(position).getArtist());
        //用来格式化时间
        String s = MusicUtils.formatTime(new Integer(list.get(position).getDuration()));
        viewHolder.duration.setText(s);
        return convertView;
    }

    class ViewHolder{
        private TextView title;
        private TextView artist;
        private TextView duration;
    }

}

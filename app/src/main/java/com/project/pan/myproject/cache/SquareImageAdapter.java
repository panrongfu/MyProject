package com.project.pan.myproject.cache;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.project.pan.myproject.R;
import com.project.pan.myproject.cache.imageLoader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: panrongfu
 * @date: 2018/10/29 14:59
 * @describe:
 */

public class SquareImageAdapter extends BaseAdapter {

    private List<String> urlList = new ArrayList<>();
    private Context mContext;
    public boolean gridViewIdle = true;
    private ImageLoader mImageLoader;

    public boolean isGridViewIdle() {
        return gridViewIdle;
    }

    public void setGridViewIdle(boolean gridViewIdle) {
        this.gridViewIdle = gridViewIdle;
    }

    public SquareImageAdapter(List<String> list, Context context) {
        urlList = list;
        mContext = context;
        mImageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public String getItem(int position) {
        return urlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cache_gridview_item_layout,null,false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageView imageView = holder.imageView;
        final String tag = (String) imageView.getTag();
        final String url = getItem(position);
        if (!url.equals(tag)){
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }
        if (isGridViewIdle()){
            imageView.setTag(url);
            mImageLoader.bindBitmap(getItem(position),imageView);
        }
        return convertView;
    }

   public static class ViewHolder {
        ImageView imageView;
    }
}

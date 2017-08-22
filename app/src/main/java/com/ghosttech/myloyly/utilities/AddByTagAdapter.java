package com.ghosttech.myloyly.utilities;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ghosttech.myloyly.R;

import java.util.List;

/**
 * Created by Asus on 8/22/2017.
 */

public class AddByTagAdapter extends RecyclerView.Adapter<AddByTagAdapter.ViewHolder> {
    Context context;
    List<GetByTagHelper> getByTagHelpers;
    @Override
    public AddByTagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aufguss_by_tag_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tvByTagsTitle, tvByTagsTime, tvByTagsTags;
        ImageView ivByTagsImage;

        public ViewHolder(View v) {
            super(v);
            tvByTagsTags = (TextView)v.findViewById(R.id.tv_tags_tags);
            tvByTagsTitle = (TextView)v.findViewById(R.id.tv_by_tags_title);
            tvByTagsTime = (TextView)v.findViewById(R.id.tv_by_tag_time);
            ivByTagsImage = (ImageView) v.findViewById(R.id.iv_by_tags_title_image);


        }
    }
    public AddByTagAdapter( Context context, List<GetByTagHelper> getByTagHelpers){
        this.context = context;
        this.getByTagHelpers = getByTagHelpers;
    }

    @Override
    public void onBindViewHolder(AddByTagAdapter.ViewHolder holder, int position) {
        GetByTagHelper  myHelper = getByTagHelpers.get(position);
        //Glide.with()
        // holder.ivByTagsImage.setImageResource(myHelper.getByTagImageID);
        holder.tvByTagsTitle.setText(myHelper.getStrGetByTagTitle());
        holder.tvByTagsTime.setText(myHelper.getStrGetByTagTime());
        holder.tvByTagsTags.setText(myHelper.getStrGetByTagTAG());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

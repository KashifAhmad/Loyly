package com.ghosttech.myloyly.utilities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ghosttech.myloyly.R;
import com.ghosttech.myloyly.fragments.GetByTagDetailFragment;
import com.ghosttech.myloyly.fragments.GetByTagFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Asus on 8/22/2017.
 */

public class GetByTagAdapter extends RecyclerView.Adapter<GetByTagAdapter.ViewHolder> {
    Context context;
    List<GetByTagHelper> getByTagHelpers;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tvByTagsTitle, tvByTagsTime, tvByTagsTags;
        CardView cvTagItem;
        ImageView ivByTagsImage;

        public ViewHolder(View v) {
            super(v);
            tvByTagsTags = (TextView)itemView.findViewById(R.id.tv_tags_tags);
            tvByTagsTitle = (TextView)itemView.findViewById(R.id.tv_by_tags_title);
            tvByTagsTime = (TextView)itemView.findViewById(R.id.tv_by_tag_time);
            ivByTagsImage = (ImageView) itemView.findViewById(R.id.iv_by_tags_title_image);
            cvTagItem = (CardView)itemView.findViewById(R.id.cv_tag_item);


        }
    }

    public GetByTagAdapter(Context context, List<GetByTagHelper> getByTagHelpers){
        getByTagHelpers.clear();
        this.context = context;
        this.getByTagHelpers = getByTagHelpers;
    }

    @Override
    public GetByTagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aufguss_by_tag_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return getByTagHelpers.size();
    }



    @Override
    public void onBindViewHolder(GetByTagAdapter.ViewHolder holder, int position) {
        final GetByTagHelper  myHelper = getByTagHelpers.get(position);
        String url = "http://swatshawls.com/loyly/assets/uploads/"+myHelper.getByTagImageID;
        Picasso.with(this.context).load(url).into(holder.ivByTagsImage);
        holder.tvByTagsTitle.setText(myHelper.getStrGetByTagTitle());
        holder.tvByTagsTime.setText(myHelper.getStrGetByTagTime()+" minutes");
        holder.tvByTagsTags.setText(myHelper.getStrGetByTagTAG());
        final Bundle args = new Bundle();
        args.putString("instructions",myHelper.getStrInstructions());
        args.putString("ingredients",myHelper.getStrIngredients());
        args.putString("image",String.valueOf(myHelper.getByTagImageID));
        args.putString("title",myHelper.getStrGetByTagTitle());
        args.putInt("id", myHelper.getItemID());
        args.putString("steps", myHelper.getStrSteps());
        args.putString("time", myHelper.getStrGetByTagTime());
        args.putString("image", url);
        holder.cvTagItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new GetByTagDetailFragment();
                ((AppCompatActivity) context).getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
                fragment.setArguments(args);
            }
        });



    }
    private void showOptionDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(context);
        pictureDialog.setTitle("Take Image from");
        String[] pictureDialogItems = {
                "Edit", "Delete"
        };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                break;
                            case 1:
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

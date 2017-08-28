package com.ghosttech.myloyly.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghosttech.myloyly.R;
import com.ghosttech.myloyly.utilities.EducationHelper;
import com.ghosttech.myloyly.utilities.GetByTagAdapter;
import com.ghosttech.myloyly.utilities.GetByTagHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Asus on 8/27/2017.
 */

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ViewHolder> {

    Context context;
    List<EducationHelper> educationHelpers;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tvLanguage, tvDate, tvLevel;
        CardView cvTagItem;
        ImageView ivEducationImage;

        public ViewHolder(View v) {
            super(v);
            ivEducationImage = (ImageView) itemView.findViewById(R.id.iv_edu_image);
            tvDate = (TextView) itemView.findViewById(R.id.tv_edu_date);
            tvLanguage = (TextView) itemView.findViewById(R.id.tv_language);
            cvTagItem = (CardView) itemView.findViewById(R.id.cv_tag_item);
            tvLevel = (TextView) itemView.findViewById(R.id.tv_level);


        }
    }

    public EducationAdapter(Context context, List<EducationHelper> educationHelpers) {
        this.educationHelpers = educationHelpers;
        this.context = context;

    }

    @Override
    public EducationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.education_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EducationAdapter.ViewHolder holder, int position) {
        EducationHelper educationHelper = educationHelpers.get(position);
        holder.tvLanguage.setText(educationHelper.getStrLanguage());
        holder.tvDate.setText(educationHelper.getStrWearDate());
        holder.tvLevel.setText(educationHelper.getStrLevel());
        final Bundle args = new Bundle();
        args.putString("level", educationHelper.getStrLevel());
        args.putString("description", educationHelper.getStrDescription());
        args.putString("wear_date", educationHelper.getStrWearDate());
        args.putString("next_date", educationHelper.getStrNextDate());
        args.putString("flang", educationHelper.getStrLanguage());
        args.putString("steps", educationHelper.getStrTopic());
        args.putString("picture", educationHelper.getStrPicture());
        args.putString("f_lang", educationHelper.getStrfLanguage());


        String url = "http://swatshawls.com/loyly/assets/uploads/" + educationHelper.getStrPicture();
        Picasso.with(this.context).load(url).into(holder.ivEducationImage);

        holder.cvTagItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new EducationDetailFragment();
                ((AppCompatActivity) context).getFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
                fragment.setArguments(args);
            }
        });

    }

    @Override
    public int getItemCount() {
        return educationHelpers.size();
    }
}

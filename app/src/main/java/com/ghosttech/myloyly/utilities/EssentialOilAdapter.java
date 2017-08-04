package com.ghosttech.myloyly.utilities;

/**
 * Created by Asus on 2/1/2017.
 */

import java.util.ArrayList;
import java.util.List;

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
import com.ghosttech.myloyly.fragments.EssentailOilDetailFragment;

public class EssentialOilAdapter extends RecyclerView.Adapter<EssentialOilAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    Context context;
    List<EssentialOilHelper> essentialItemHelpers;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cvOilItem;
        TextView tvOilTitle;
        TextView tvBotanicalName;
        ImageView personPhoto;

        public ViewHolder(View v) {
            super(v);
            cvOilItem = (CardView)itemView.findViewById(R.id.cv_oil_item);
            tvOilTitle = (TextView)itemView.findViewById(R.id.tv_essential_oil_title);
            tvBotanicalName = (TextView)itemView.findViewById(R.id.tv_botanical_name);
            personPhoto = (ImageView)itemView.findViewById(R.id.iv_oil_title_image);
        }
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public EssentialOilAdapter(Context context, List<EssentialOilHelper> essentialItemHelpers) {
        this.essentialItemHelpers = essentialItemHelpers;;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final EssentialOilHelper oilHelper = essentialItemHelpers.get(position);
        holder.tvOilTitle.setText(oilHelper.getStrTitle());
        holder.tvBotanicalName.setText(oilHelper.getStrBotanicalName());
        holder.personPhoto.setImageResource(oilHelper.intImageID[position]);
        holder.cvOilItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                Fragment fragment = new EssentailOilDetailFragment();
                ((AppCompatActivity) context).getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_main, fragment).addToBackStack("tag").commit();
                args.putInt("row_id",position);
                args.putInt("image_id",oilHelper.intImageID[position]);
                args.putParcelable("EssentialOilObject",oilHelper);
                fragment.setArguments(args);

            }
        });


    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return essentialItemHelpers.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
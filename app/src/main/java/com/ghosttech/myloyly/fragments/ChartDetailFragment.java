package com.ghosttech.myloyly.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghosttech.myloyly.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChartDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChartDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//    private ImageViewTouch ivLargeImage;
    private Bitmap myBitmap;

    private OnFragmentInteractionListener mListener;

    public ChartDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartDetailFragment newInstance(String param1, String param2) {
        ChartDetailFragment fragment = new ChartDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart_detail, container, false);
//        ivLargeImage = (ImageViewTouch) view.findViewById(R.id.ivLargeImageView);

// if image size is too large.Then scale image.
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        customActionBar();
        Bundle args = getArguments();
        int getID = args.getInt("id");
        if (options.outWidth > 3000 || options.outHeight > 2000) {
            options.inSampleSize = 4;
        } else if (options.outWidth > 2000 || options.outHeight > 1500) {
            options.inSampleSize = 3;
        } else if (options.outWidth > 1000 || options.outHeight > 1000) {
            options.inSampleSize = 2;
        }
        options.inJustDecodeBounds = false;
        switch (getID) {
            case 1:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charts_1);
                break;
            case 2:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charts_2);
                break;
            case 3:
                myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chart_3);
                break;
        }

//        ivLargeImage.setImageBitmapReset(myBitmap, 0, true);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void customActionBar() {
        android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Chart Detail");
        ImageView mBackArrow = (ImageView) mCustomView.findViewById(R.id.iv_back_arrow);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ChartsFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container_main, fragment).commit();


            }
        });

    }
}

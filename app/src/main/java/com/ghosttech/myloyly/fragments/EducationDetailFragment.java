package com.ghosttech.myloyly.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghosttech.myloyly.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EducationDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EducationDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EducationDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tvLevel, tvNextDate, tvWearDate, tvlLanguage, tvfLanguage, tvDescription, tvSteps;
    ImageView ivEducationImage;
    private OnFragmentInteractionListener mListener;

    public EducationDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EducationDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EducationDetailFragment newInstance(String param1, String param2) {
        EducationDetailFragment fragment = new EducationDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_education_detail, container, false);
        tvDescription = (TextView) view.findViewById(R.id.tv_edu_description);
        tvfLanguage = (TextView) view.findViewById(R.id.tv_edu_second_language);
        tvLevel = (TextView) view.findViewById(R.id.tv_level);
        tvNextDate = (TextView) view.findViewById(R.id.tv_edu_detail_date);
        tvWearDate = (TextView) view.findViewById(R.id.tv_edu_second_date);
        tvlLanguage = (TextView) view.findViewById(R.id.tv_edu_second_language);
        tvSteps = (TextView) view.findViewById(R.id.tv_edu_steps);
        ivEducationImage = (ImageView) view.findViewById(R.id.iv_detail_edu_title_image);


        Picasso.with(getActivity()).load("http://swatshawls.com/loyly/assets/uploads/" +
                getArguments().getString("picture")).into(ivEducationImage);
        tvfLanguage.setText(String.valueOf(getArguments().getString("f_lang")));
        tvSteps.setText(String.valueOf(getArguments().getString("steps")));
        tvDescription.setText(String.valueOf(getArguments().getString("description")));
        tvWearDate.setText(String.valueOf(getArguments().getString("wear_date")));
        tvNextDate.setText(String.valueOf(getArguments().getString("next_date")));
        tvLevel.setText(String.valueOf(getArguments().getString("level")));


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
}

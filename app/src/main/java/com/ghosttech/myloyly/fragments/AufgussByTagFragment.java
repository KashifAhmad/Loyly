package com.ghosttech.myloyly.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ghosttech.myloyly.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AufgussByTagFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AufgussByTagFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AufgussByTagFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnTagsClassic, btnTagsModern, btnTagSteamBath, btnTagsSmoke, btnTagsShow, btnAllTags, btnMyTags, btnInTagsAll;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String strTags;
    private OnFragmentInteractionListener mListener;

    public AufgussByTagFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AufgussByTagFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AufgussByTagFragment newInstance(String param1, String param2) {
        AufgussByTagFragment fragment = new AufgussByTagFragment();
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
        View view = inflater.inflate(R.layout.fragment_aufguss_by_tag, container, false);
        btnTagsModern = (Button) view.findViewById(R.id.btn_tags_modern);
        btnTagsShow = (Button) view.findViewById(R.id.btn_tags_show);
        btnTagsSmoke = (Button) view.findViewById(R.id.btn_tags_smoke);
        btnTagSteamBath = (Button) view.findViewById(R.id.btn_tags_steambath);
        btnAllTags = (Button) view.findViewById(R.id.btn_all_tags);
        btnTagsClassic = (Button) view.findViewById(R.id.btn_tags_classic);
        btnInTagsAll = (Button) view.findViewById(R.id.btn_in_tags_all);
        btnMyTags = (Button) view.findViewById(R.id.btn_my_tags);
        btnTagsClassic.setOnClickListener(this);
        btnTagsModern.setOnClickListener(this);
        btnTagsShow.setOnClickListener(this);
        btnTagSteamBath.setOnClickListener(this);
        btnTagsSmoke.setOnClickListener(this);
        btnInTagsAll.setOnClickListener(this);
        btnAllTags.setOnClickListener(this);
        btnMyTags.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tags_classic:
                strTags = "Classic";
                btnTagsClassic.setBackgroundColor(Color.parseColor("#eacb61"));
                btnTagsClassic.setTextColor(Color.WHITE);
                btnTagsModern.setTextColor(Color.parseColor("#eacb61"));
                btnTagsShow.setTextColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setTextColor(Color.parseColor("#eacb61"));
                btnAllTags.setTextColor(Color.parseColor("#eacb61"));
                btnInTagsAll.setTextColor(Color.parseColor("#eacb61"));
                btnMyTags.setTextColor(Color.parseColor("#eacb61"));

                btnAllTags.setBackgroundResource(R.drawable.button_orange_border);
                btnInTagsAll.setBackgroundResource(R.drawable.button_orange_border);
                btnMyTags.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsShow.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsModern.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsSmoke.setBackgroundResource(R.drawable.button_orange_border);
                btnTagSteamBath.setBackgroundResource(R.drawable.button_orange_border);
                break;
            case R.id.btn_tags_modern:
                strTags = "Modern";
                btnTagsModern.setBackgroundColor(Color.parseColor("#eacb61"));
                btnTagsModern.setTextColor(Color.WHITE);


                btnTagsClassic.setTextColor(Color.parseColor("#eacb61"));
                btnTagsShow.setTextColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setTextColor(Color.parseColor("#eacb61"));

                btnAllTags.setTextColor(Color.parseColor("#eacb61"));
                btnInTagsAll.setTextColor(Color.parseColor("#eacb61"));
                btnMyTags.setTextColor(Color.parseColor("#eacb61"));

                btnAllTags.setBackgroundResource(R.drawable.button_orange_border);
                btnInTagsAll.setBackgroundResource(R.drawable.button_orange_border);
                btnMyTags.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsShow.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsClassic.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsSmoke.setBackgroundResource(R.drawable.button_orange_border);
                btnTagSteamBath.setBackgroundResource(R.drawable.button_orange_border);
                break;
            case R.id.btn_tags_show:
                strTags = "Show";
                btnTagsShow.setBackgroundColor(Color.parseColor("#eacb61"));
                btnTagsShow.setTextColor(Color.WHITE);

                btnTagsModern.setTextColor(Color.parseColor("#eacb61"));
                btnTagsClassic.setTextColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setTextColor(Color.parseColor("#eacb61"));

                btnAllTags.setTextColor(Color.parseColor("#eacb61"));
                btnInTagsAll.setTextColor(Color.parseColor("#eacb61"));
                btnMyTags.setTextColor(Color.parseColor("#eacb61"));

                btnAllTags.setBackgroundResource(R.drawable.button_orange_border);
                btnInTagsAll.setBackgroundResource(R.drawable.button_orange_border);
                btnMyTags.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsModern.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsClassic.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsSmoke.setBackgroundResource(R.drawable.button_orange_border);
                btnTagSteamBath.setBackgroundResource(R.drawable.button_orange_border);
                break;
            case R.id.btn_tags_smoke:
                strTags = "Smoke";
                btnTagsSmoke.setBackgroundColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.WHITE);

                btnTagsShow.setTextColor(Color.parseColor("#eacb61"));
                btnTagsModern.setTextColor(Color.parseColor("#eacb61"));
                btnTagsClassic.setTextColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setTextColor(Color.parseColor("#eacb61"));

                btnAllTags.setTextColor(Color.parseColor("#eacb61"));
                btnInTagsAll.setTextColor(Color.parseColor("#eacb61"));
                btnMyTags.setTextColor(Color.parseColor("#eacb61"));

                btnAllTags.setBackgroundResource(R.drawable.button_orange_border);
                btnInTagsAll.setBackgroundResource(R.drawable.button_orange_border);
                btnMyTags.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsModern.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsClassic.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsShow.setBackgroundResource(R.drawable.button_orange_border);
                btnTagSteamBath.setBackgroundResource(R.drawable.button_orange_border);
                break;
            case R.id.btn_tags_steambath:
                strTags = "SteamBath";
                btnTagSteamBath.setBackgroundColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setTextColor(Color.WHITE);

                btnTagsShow.setTextColor(Color.parseColor("#eacb61"));
                btnTagsModern.setTextColor(Color.parseColor("#eacb61"));
                btnTagsClassic.setTextColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.parseColor("#eacb61"));

                btnAllTags.setTextColor(Color.parseColor("#eacb61"));
                btnInTagsAll.setTextColor(Color.parseColor("#eacb61"));
                btnMyTags.setTextColor(Color.parseColor("#eacb61"));

                btnAllTags.setBackgroundResource(R.drawable.button_orange_border);
                btnInTagsAll.setBackgroundResource(R.drawable.button_orange_border);
                btnMyTags.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsModern.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsClassic.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsShow.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsSmoke.setBackgroundResource(R.drawable.button_orange_border);
                break;
            case R.id.btn_all_tags:
                strTags = "all";
                btnAllTags.setBackgroundColor(Color.parseColor("#eacb61"));
                btnAllTags.setTextColor(Color.WHITE);

                btnInTagsAll.setTextColor(Color.WHITE);
                btnTagSteamBath.setTextColor(Color.parseColor("#eacb61"));
                btnTagsShow.setTextColor(Color.parseColor("#eacb61"));
                btnTagsModern.setTextColor(Color.parseColor("#eacb61"));
                btnTagsClassic.setTextColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.parseColor("#eacb61"));
                btnMyTags.setTextColor(Color.parseColor("#eacb61"));

                btnInTagsAll.setBackgroundColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setBackgroundResource(R.drawable.button_orange_border);
                btnMyTags.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsModern.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsClassic.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsShow.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsSmoke.setBackgroundResource(R.drawable.button_orange_border);
                break;
            case R.id.btn_in_tags_all:
                strTags = "all";
                btnAllTags.setBackgroundColor(Color.parseColor("#eacb61"));
                btnAllTags.setTextColor(Color.WHITE);
                btnInTagsAll.setTextColor(Color.WHITE);
                btnTagSteamBath.setTextColor(Color.parseColor("#eacb61"));
                btnTagsShow.setTextColor(Color.parseColor("#eacb61"));
                btnTagsModern.setTextColor(Color.parseColor("#eacb61"));
                btnTagsClassic.setTextColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.parseColor("#eacb61"));
                btnMyTags.setTextColor(Color.parseColor("#eacb61"));

                btnMyTags.setBackgroundResource(R.drawable.button_orange_border);
                btnInTagsAll.setBackgroundColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsModern.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsClassic.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsShow.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsSmoke.setBackgroundResource(R.drawable.button_orange_border);
                break;
            case R.id.btn_my_tags:
                strTags = "my";

                btnMyTags.setBackgroundColor(Color.parseColor("#eacb61"));
                btnMyTags.setTextColor(Color.WHITE);

                btnAllTags.setTextColor(Color.parseColor("#eacb61"));
                btnInTagsAll.setTextColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setTextColor(Color.parseColor("#eacb61"));
                btnTagsShow.setTextColor(Color.parseColor("#eacb61"));
                btnTagsModern.setTextColor(Color.parseColor("#eacb61"));
                btnTagsClassic.setTextColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.parseColor("#eacb61"));
                btnAllTags.setBackgroundResource(R.drawable.button_orange_border);
                btnInTagsAll.setBackgroundResource(R.drawable.button_orange_border);
                btnTagSteamBath.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsModern.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsClassic.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsShow.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsSmoke.setBackgroundResource(R.drawable.button_orange_border);
                break;

        }
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

package com.ghosttech.myloyly.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ghosttech.myloyly.R;
import com.ghosttech.myloyly.utilities.Configuration;
import com.ghosttech.myloyly.utilities.EducationHelper;
import com.ghosttech.myloyly.utilities.GetByTagAdapter;
import com.ghosttech.myloyly.utilities.GetByTagHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EducationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EducationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EducationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    RecyclerView myRecyclerView;
    RecyclerView.Adapter addByTagAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<EducationHelper> educationHelpers;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String strTags = null;
    SweetAlertDialog pDialog;
    RequestQueue mRequestQueue;
    private OnFragmentInteractionListener mListener;

    public EducationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EducationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EducationFragment newInstance(String param1, String param2) {
        EducationFragment fragment = new EducationFragment();
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
        View view = inflater.inflate(R.layout.fragment_education, container, false);

        // Inflate the layout for this fragment
        myRecyclerView = (RecyclerView) view.findViewById(R.id.rv_by_tags);
        layoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(layoutManager);
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#179e99"));
        pDialog.setTitleText("Wait a while");
        pDialog.show();
        mRequestQueue = Volley.newRequestQueue(getActivity());
        educationHelpers = new ArrayList<>();
        myRecyclerView.setHasFixedSize(true);

        getDataFromAPI();
        addByTagAdapter = new EducationAdapter(getActivity(), educationHelpers);
        myRecyclerView.setAdapter(addByTagAdapter);
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void getDataFromAPI() {
        // tvEmptyList.setVisibility(View.GONE);
        String url = "http://swatshawls.com/loyly/Apis/education";
        Log.d("zma url -response", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    pDialog.dismiss();
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        EducationHelper jsonHelper = new EducationHelper();
                        JSONObject tempObject = jsonArray.getJSONObject(i);
                        jsonHelper.setStrDescription(tempObject.getString("description"));
                        jsonHelper.setStrLanguage(tempObject.getString("llanguage"));
                        jsonHelper.setStrLevel(tempObject.getString("level"));
                        jsonHelper.setStrTopic(tempObject.getString("topic"));
                        jsonHelper.setStrPicture(tempObject.getString("picture"));
                        jsonHelper.setStrNextDate(tempObject.getString("next_date"));
                        jsonHelper.setStrWearDate(tempObject.getString("wear_date"));
                        jsonHelper.setStrfLanguage(tempObject.getString("flanguage"));
                        educationHelpers.add(jsonHelper);
                    }
                    addByTagAdapter.notifyDataSetChanged();
//                    if (jsonArray.length() == 0) {
//                        tvEmptyList.setVisibility(View.VISIBLE);
//                        tvEmptyList.setText("No data found");
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
//                    tvEmptyList.setVisibility(View.VISIBLE);
//                    tvEmptyList.setText(String.valueOf(e));
                    Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                tvEmptyList.setVisibility(View.VISIBLE);
//                tvEmptyList.setText(String.valueOf(error));
                Toast.makeText(getActivity(), String.valueOf(error), Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        mRequestQueue.add(jsonObjectRequest);

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

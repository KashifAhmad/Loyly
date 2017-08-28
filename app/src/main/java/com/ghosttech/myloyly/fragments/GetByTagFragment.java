package com.ghosttech.myloyly.fragments;

import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ghosttech.myloyly.R;
import com.ghosttech.myloyly.utilities.GetByTagAdapter;
import com.ghosttech.myloyly.utilities.Configuration;
import com.ghosttech.myloyly.utilities.GetByTagHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GetByTagFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GetByTagFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetByTagFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnTagsClassic, btnTagsModern, btnTagSteamBath, btnTagsSmoke, btnTagsShow, btnAllTags, btnMyTags, btnInTagsAll;
    RecyclerView myRecyclerView;
    RecyclerView.Adapter addByTagAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<GetByTagHelper> byTagHelpers;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String strTags = null;
    SweetAlertDialog pDialog;
    private OnFragmentInteractionListener mListener;
    boolean dataFlag = false;
    boolean myDataFlag = false;
    RequestQueue requestQueue;
    String url = null;
    TextView tvEmptyList;
    String strMyData = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String strUserID;

    public GetByTagFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GetByTagFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GetByTagFragment newInstance(String param1, String param2) {
        GetByTagFragment fragment = new GetByTagFragment();
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

        sharedPreferences = getActivity().getSharedPreferences("com.loyly", 0);
        editor = sharedPreferences.edit();
        strUserID = sharedPreferences.getString("user_id", "");
        Log.d("zma user id", strUserID);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.rv_by_tags);
        layoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(layoutManager);
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#179e99"));
        pDialog.setTitleText("Wait a while");
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddPlantFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
            }
        });

        requestQueue = Volley.newRequestQueue(getActivity());
        byTagHelpers = new ArrayList<>();
        myRecyclerView.setHasFixedSize(true);
        tvEmptyList = (TextView) view.findViewById(R.id.tv_empty_list);
        tvEmptyList.setVisibility(View.GONE);
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
        dataFlag = true;
        if (dataFlag) {
            pDialog.show();
            url = "http://swatshawls.com/loyly/Apis/getdata/";
            getDataFromAPI(strTags);
            addByTagAdapter = new GetByTagAdapter(getActivity(), byTagHelpers);
            myRecyclerView.setAdapter(addByTagAdapter);
            btnAllTags.setBackgroundColor(Color.parseColor("#eacb61"));
            btnAllTags.setTextColor(Color.WHITE);
            btnInTagsAll.setTextColor(Color.WHITE);
            btnInTagsAll.setBackgroundColor(Color.parseColor("#eacb61"));
            dataFlag = false;
        }

        return view;
    }

    public void getDataFromAPI(String strTags) {
        tvEmptyList.setVisibility(View.GONE);

        if (dataFlag) {
            url = "http://swatshawls.com/loyly/Apis/getdata/";
            dataFlag = false;
        } else if (myDataFlag) {
            url = "http://swatshawls.com/loyly/Apis/getdata/" + strUserID;
            Log.d("zma my url", url);
            myDataFlag = false;

        } else {
            url = Configuration.GET_BY_TAGS_URL + strTags;
        }
        Log.d("zma url -response", url);
        final String finalUrl = url;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("zma response", String.valueOf(response) + "\n url :" + finalUrl);
                try {
                    pDialog.dismiss();
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        GetByTagHelper jsonHelper = new GetByTagHelper();
                        JSONObject tempObject = jsonArray.getJSONObject(i);
                        jsonHelper.setStrGetByTagTitle(tempObject.getString("title"));
                        jsonHelper.setStrGetByTagTime(tempObject.getString("time"));
                        jsonHelper.setStrGetByTagTAG(tempObject.getString("tags"));
                        jsonHelper.setGetByTagImageID(tempObject.getString("pic_renamed"));
                        jsonHelper.setStrIngredients(tempObject.getString("ingredient"));
                        jsonHelper.setStrInstructions(tempObject.getString("steps"));
                        byTagHelpers.add(jsonHelper);
                    }
                    addByTagAdapter.notifyDataSetChanged();
                    if (jsonArray.length() == 0) {
                        tvEmptyList.setVisibility(View.VISIBLE);
                        tvEmptyList.setText("No data found");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    tvEmptyList.setVisibility(View.VISIBLE);
                    tvEmptyList.setText(String.valueOf(e));
                    Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvEmptyList.setVisibility(View.VISIBLE);
                tvEmptyList.setText(String.valueOf(error));
                Toast.makeText(getActivity(), String.valueOf(error), Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        requestQueue.add(jsonObjectRequest);

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

                pDialog.show();
                getDataFromAPI(strTags);
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

                pDialog.show();
                getDataFromAPI(strTags);
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

                pDialog.show();
                getDataFromAPI(strTags);
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

                pDialog.show();
                getDataFromAPI(strTags);
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

                pDialog.show();
                getDataFromAPI(strTags);
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
                dataFlag = true;
                pDialog.show();
                getDataFromAPI(strTags);
                break;
            case R.id.btn_in_tags_all:
                strTags = "all";
                btnAllTags.setBackgroundColor(Color.parseColor("#eacb61"));
                btnAllTags.setTextColor(Color.WHITE);
                btnInTagsAll.setTextColor(Color.WHITE);
                btnInTagsAll.setBackgroundColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setTextColor(Color.parseColor("#eacb61"));
                btnTagsShow.setTextColor(Color.parseColor("#eacb61"));
                btnTagsModern.setTextColor(Color.parseColor("#eacb61"));
                btnTagsClassic.setTextColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.parseColor("#eacb61"));
                btnMyTags.setTextColor(Color.parseColor("#eacb61"));

                btnMyTags.setBackgroundResource(R.drawable.button_orange_border);

                btnTagSteamBath.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsModern.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsClassic.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsShow.setBackgroundResource(R.drawable.button_orange_border);
                btnTagsSmoke.setBackgroundResource(R.drawable.button_orange_border);
                dataFlag = true;
                pDialog.show();
                getDataFromAPI(strTags);
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
                myDataFlag = true;
                getDataFromAPI(strTags);
                pDialog.show();

                break;
        }
        Log.d("zma tag click", strTags);
        addByTagAdapter = new GetByTagAdapter(getActivity(), byTagHelpers);
        myRecyclerView.setAdapter(addByTagAdapter);


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

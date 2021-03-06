package com.ghosttech.myloyly.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ghosttech.myloyly.R;
import com.ghosttech.myloyly.utilities.Configuration;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GetByTagDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GetByTagDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetByTagDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView tvTitleName, tvIngredients, tvInstructions, tvSteps;
    ImageView ivTitleImage, ivDeleteIcon, ivEditIcon;
    public static String strEdit = "no";

    private OnFragmentInteractionListener mListener;
    Fragment fragment;
    public GetByTagDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GetByTagDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GetByTagDetailFragment newInstance(String param1, String param2) {
        GetByTagDetailFragment fragment = new GetByTagDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_get_by_tag_detail, container, false);
        tvIngredients = (TextView) view.findViewById(R.id.tv_add_by_tag_ingredients);
        tvInstructions = (TextView) view.findViewById(R.id.tv_add_by_tag_instructions);
        tvTitleName = (TextView) view.findViewById(R.id.tv_by_tag_title);
        ivTitleImage = (ImageView) view.findViewById(R.id.iv_detail_title_image);
        ivDeleteIcon = (ImageView)view.findViewById(R.id.iv_delete_icon);
        ivEditIcon = (ImageView)view.findViewById(R.id.iv_edit_btn);
        tvSteps = (TextView)view.findViewById(R.id.tv_add_by_tag_steps);
        final Bundle args = getArguments();

        if (GetByTagFragment.strTags.equals("my")) {
            Toast.makeText(getActivity(), GetByTagFragment.strTags, Toast.LENGTH_SHORT).show();
            ivEditIcon.setVisibility(View.VISIBLE);
            GetByTagFragment.strTags = "";
            ivEditIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment = new AddPlantFragment();
                    strEdit = "yes";
                    fragment.setArguments(args);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();


                }
            });
            ivDeleteIcon.setVisibility(View.VISIBLE);
            ivDeleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("You want to delete recipe?")
                            .setConfirmText("Yes, Delete")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    deleteAPICall(getArguments().getInt("id"));
                                }
                            }).setCancelText("Cancel")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();

                }
            });
        }
        String strImage = "http://swatshawls.com/loyly/assets/uploads/" + getArguments().getString("image");
        Log.d("zma image detail", strImage);

        tvInstructions.setText(args.getString("instructions"));
        tvIngredients.setText(args.getString("ingredients"));
        tvTitleName.setText(args.getString("title"));
        tvSteps.setText(args.getString("steps"));
        Picasso.with(getActivity()).load(strImage).into(ivTitleImage);
        return view;
    }
    public void deleteAPICall(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.DELETE_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("true")){
                    Toast.makeText(getActivity(), "Recipe Deleted", Toast.LENGTH_SHORT).show();
                    Fragment fragment = new GetByTagFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
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
    public void editApiCall(){

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

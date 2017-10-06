package com.ghosttech.myloyly.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ghosttech.myloyly.R;
import com.ghosttech.myloyly.utilities.Configuration;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText etName, etEmail, etPassword, etLastName, etCountry, etCity, etAddress, etPostalCode,
            etWellness, etPhoneNumber;
    Button btnSubmit;
    String strFirstName, strLastName, strEmail, strPassword, strCountry, strWellness, strAddress, strPostalCOde,
            strPhoneNumber, strCity;
    SweetAlertDialog pDialog;
    private OnFragmentInteractionListener mListener;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        etName = (EditText) view.findViewById(R.id.et_name);
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etLastName = (EditText) view.findViewById(R.id.et_last_name);
        etCountry = (EditText) view.findViewById(R.id.et_country);
        etCity = (EditText) view.findViewById(R.id.et_city);
        etWellness = (EditText) view.findViewById(R.id.et_wellness);
        etAddress = (EditText) view.findViewById(R.id.et_address);
        etPostalCode = (EditText) view.findViewById(R.id.et_postal_code);
        etPhoneNumber = (EditText) view.findViewById(R.id.et_phone_number);
        ScrollView scrollView = (ScrollView)view.findViewById(R.id.sv_main);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return false;
            }
        });

        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#179e99"));
        pDialog.setTitleText("Registering...");
        pDialog.setCancelable(false);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strFirstName = etName.getText().toString();
                strLastName = etLastName.getText().toString();
                strEmail = etEmail.getText().toString();
                strPassword = etPassword.getText().toString();
                strCountry = etCountry.getText().toString();
                strWellness = etWellness.getText().toString();
                strCity = etCity.getText().toString();
                strPostalCOde = etPostalCode.getText().toString();
                strAddress = etAddress.getText().toString();
                strPhoneNumber = etPhoneNumber.getText().toString();


                if (strFirstName.equals("")) {
                    etName.setError("Please write your name");
                } else if ((!android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches())) {
                    etEmail.setError("Please enter valid email id");
                } else if (strPassword.equals("") || strPassword.length() < 3) {
                    etPassword.setError("Password should be greater than 3 characters");
                } else if (strCountry.equals("")) {
                    etCountry.setError("Enter your country name");
                } else if (strCity.equals("")) {
                    etCity.setError("Enter your city name");
                } else if (strAddress.equals("")) {
                    etAddress.setError("Enter your address");
                } else if (strPostalCOde.equals("")) {
                    etPostalCode.setError("Enter your postal code");
                } else if (strPhoneNumber.equals("")) {
                    etPhoneNumber.setError("Enter your phone number");
                } else if (strLastName.equals("")) {
                    etLastName.setError("Enter your last name");
                } else {
                    apiCall();
                    pDialog.show();
                    Configuration.FLAG_SIGN_UP = true;
                }
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void apiCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.USER_URL + "/signup", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("User Registered Successfully")) {
                    pDialog.dismiss();
                    Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                    Fragment fragment = new LoginFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                } else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Email already registered")
                            .show();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Server Error")
                        .show();
                pDialog.dismiss();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", strEmail);
                params.put("name", strFirstName);
                params.put("lastname", strLastName);
                params.put("password", strPassword);
                params.put("country", strCountry);
                params.put("city", strCity);
                params.put("postcode", strPostalCOde);
                params.put("address", strAddress);
                params.put("wellness", strWellness);
                params.put("phoneno", strPhoneNumber);
                Log.d("zma reg name", strFirstName);
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
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

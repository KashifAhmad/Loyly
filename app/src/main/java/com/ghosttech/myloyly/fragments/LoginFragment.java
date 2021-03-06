package com.ghosttech.myloyly.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
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
import com.ghosttech.myloyly.BuildConfig;
import com.ghosttech.myloyly.R;
import com.ghosttech.myloyly.utilities.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText etName, etEmail, etPassword;
    Button btnSubmit;
    String strName, strEmail, strPassword;
    private OnFragmentInteractionListener mListener;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView tvSkip, tvForgotPassword;
    SweetAlertDialog pDialog;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        sharedPreferences = getActivity().getSharedPreferences("com.loyly", 0);
        editor = sharedPreferences.edit();
        tvSkip = (TextView) view.findViewById(R.id.tvSkip);
        tvForgotPassword = (TextView) view.findViewById(R.id.tv_forget_password);

        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#179e99"));
        pDialog.setTitleText("Getting Login");
        pDialog.setCancelable(false);
        SpannableString contentRegister = new SpannableString("New user? Register Here");
        contentRegister.setSpan(new UnderlineSpan(), 0, contentRegister.length(), 0);
        tvSkip.setText(contentRegister);

        SpannableString contentForgetPassword = new SpannableString("Forgot Password?");
        contentForgetPassword.setSpan(new UnderlineSpan(), 0, contentForgetPassword.length(), 0);
        tvForgotPassword.setText(contentForgetPassword);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ForgetPasswordFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
            }
        });
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.sv_scroll_view);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return false;
            }
        });
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RegistrationFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
            }
        });
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = etEmail.getText().toString();
                strPassword = etPassword.getText().toString();
                if ((!android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches())) {
                    etEmail.setError("Please enter valid email id");
                } else if (strPassword.equals("") || strPassword.length() < 3) {
                    etPassword.setError("Password should be greater than 3 characters");
                } else {
                    pDialog.show();
                    apiCall();
                }
            }
        });
        return view;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if (BuildConfig.DEBUG) {
//            etEmail.setText("Kashif@gmail.com");
//            etPassword.setText("Kashif");
//        }
//    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void apiCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.USER_URL + "/login"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma response login", response);
                if (response.contains("Success")) {
                    pDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String strID = jsonObject.getString("id");
                        editor.putString("user_id", strID).commit();
                        Log.d("zma put string", strID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Fragment fragment = new MainFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                    editor.putBoolean("loggedIn", true).commit();
                } else {
                    pDialog.dismiss();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Invalid credentials")
                            .show();
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
                params.put("password", strPassword);
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

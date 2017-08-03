package com.ghosttech.myloyly.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TableLayout;
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
import com.ghosttech.myloyly.utilities.GeneralUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddPlantFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddPlantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPlantFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    final int CAMERA_CAPTURE = 1;
    final int RESULT_LOAD_IMAGE = 2;
    private String mParam1;
    private String mParam2;
    private FragmentTabHost mTabHost;
    private OnFragmentInteractionListener mListener;
    ImageView ivAddEditText;
    LinearLayout.LayoutParams p;
    View view;
    File sourceFile;
    EditText etPlantName, etTime, etInstruction1, etInstruction2, etStep1, etStep2,
            etIngredient1, etIngredient2, etIngredient3, etIngredient4;

    Button btnTagsClassic, btnTagsModern, btnTagSteamBath, btnTagsSmoke, btnTagsShow, btnAddImage,
            btnSendData;
    ImageView ivImageView;
    RequestQueue mRequestQueue;
    String strPlantName, strTime, strTags, strIngredients, strSteps, strPicture;

    public AddPlantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPlantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPlantFragment newInstance(String param1, String param2) {
        AddPlantFragment fragment = new AddPlantFragment();
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
        view = inflater.inflate(R.layout.fragment_add_plant, container, false);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        etPlantName = (EditText) view.findViewById(R.id.et_plant_name);
        etIngredient1 = (EditText) view.findViewById(R.id.et_add_ing_1);
        etIngredient2 = (EditText) view.findViewById(R.id.et_add_ing_2);
        etIngredient3 = (EditText) view.findViewById(R.id.et_add_ing_3);
        etIngredient4 = (EditText) view.findViewById(R.id.et_add_ing_4);
        etInstruction1 = (EditText) view.findViewById(R.id.et_instruction_1);
        etInstruction2 = (EditText) view.findViewById(R.id.et_instruction_2);
        etStep1 = (EditText) view.findViewById(R.id.et_step_1);
        etStep2 = (EditText) view.findViewById(R.id.et_step_2);
        btnAddImage = (Button) view.findViewById(R.id.btn_add_image);
        btnSendData = (Button) view.findViewById(R.id.btn_send_data);
        btnTagsClassic = (Button) view.findViewById(R.id.btn_tags_classic);
        btnTagsModern = (Button) view.findViewById(R.id.btn_tags_modern);
        btnTagsShow = (Button) view.findViewById(R.id.btn_tags_show);
        btnTagsSmoke = (Button) view.findViewById(R.id.btn_tags_smoke);
        btnTagSteamBath = (Button) view.findViewById(R.id.btn_tags_steambath);

        btnTagsClassic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
//        btnTagsModern.setOnClickListener((View.OnClickListener) getActivity());
//        btnTagsShow.setOnClickListener((View.OnClickListener) getActivity());
//        btnTagSteamBath.setOnClickListener((View.OnClickListener) getActivity());
//        btnTagsSmoke.setOnClickListener((View.OnClickListener) getActivity());
//        btnSendData.setOnClickListener((View.OnClickListener) getActivity());
//        btnAddImage.setOnClickListener((View.OnClickListener) getActivity());

        return view;
    }

    public void takeDataFromFields() {
        strPlantName = etPlantName.getText().toString();
        strIngredients = etIngredient1.getText().toString() + "," + etIngredient2.getText().toString() +
                "," + etIngredient3.getText().toString() + "," + etIngredient4.getText().toString();
        strTime = etTime.getText().toString();
        strSteps = etStep1.getText().toString() + "," + etStep2.getText().toString();
        if (strPlantName.equals("") || strTags.equals("") ||
                strIngredients.equals("") || strSteps.equals("")) {
            apiCall();
        } else {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Something went wrong")
                    .show();
        }

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void apiCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Configuration.END_POINT_LIVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                boolean status = response.contains("true");
                if (status) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success")
                            .show();
                } else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success")
                            .show();
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
                params.put("title", strPlantName);
                params.put("tags", strTags);
                params.put("time", strTime);
                params.put("ingredients", strIngredients);
                params.put("steps", strSteps);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

//    public void Add_Line() {
//        Log.d("zma addline", "sho");
//        TableLayout ll = (TableLayout) view.findViewById(R.id.ll_main_layout);
//        // add edittext
//        EditText et = new EditText(getActivity());
//        p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        p.topMargin = 10;
//
//
//        int paddingPixel = 25;
//        et.setPadding(12, 0, 0, 0);
//        et.setLayoutParams(p);
//        et.setBackgroundResource(R.drawable.edit_text_bg);
//        et.setHint("Text");
//        ll.addView(et);
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tags_classic:
                strTags = "Classic";
                break;
            case R.id.btn_tags_modern:
                strTags = "Modern";
                break;
            case R.id.btn_tags_show:
                strTags = "Show";
                break;
            case R.id.btn_tags_smoke:
                strTags = "Smoke";
                break;
            case R.id.btn_tags_steambath:
                strTags = "SteamBath";
                break;
            case R.id.btn_send_data:
                takeDataFromFields();
                break;
            case R.id.btn_add_image:
                AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
                pictureDialog.setTitle("Choose Image from");
                String[] pictureDialogItems = {
                        "\tGallery",
                        "\tCamera"};
                pictureDialog.setItems(pictureDialogItems,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        galleryIntent();
                                        break;
                                    case 1:
                                        cameraIntent();
                                        break;
                                }
                            }
                        });


        }
    }

    public void cameraIntent() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(captureIntent, CAMERA_CAPTURE);
    }

    public void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && null != data) {
            Uri selectedImage = data.getData();
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            sourceFile = new File(picturePath);
            Log.d("zma path load image", picturePath.toString());
//            if (sourceFile != null) {
//                flag = true;
//            } else {
//                flag = false;
//            }
//            cursor.close();
            ivImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            if (picturePath.equals("")) {
                ivImageView.setBackgroundResource(R.drawable.edit_text_bg_green);
            }
//            tvTakePic.setText("Take Another");
//            tvTakePic.setTextColor(Color.RED);
//            isImage = true;
//            ivCrossImage.setVisibility(View.VISIBLE);

        } else if (resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivImageView.setImageBitmap(photo);
            Uri tempUri = GeneralUtils.getImageUri(getActivity(), photo);
            sourceFile = new File(GeneralUtils.getRealPathFromURI(getActivity(), tempUri));
//            if (sourceFile != null) {
//                flag = true;
//            } else {
//                flag = false;
//            }
//            tvTakePic.setText("Take another");
//            tvTakePic.setTextColor(Color.RED);
//            isImage = true;
//            ivCrossImage.setVisibility(View.VISIBLE);

        } else if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            sourceFile = new File(GeneralUtils.getRealPathFromURI(getActivity(), uri));
        }
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

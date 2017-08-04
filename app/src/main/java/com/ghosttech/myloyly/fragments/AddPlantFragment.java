package com.ghosttech.myloyly.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.os.Looper;
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
import com.ghosttech.myloyly.utilities.HTTPMultiPartEntity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    long totalSize = 0;
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
    SweetAlertDialog pDialog;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.INTERNET, android.Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#179e99"));
        pDialog.setTitleText("Sending complaint");
        etPlantName = (EditText) view.findViewById(R.id.et_plant_name);
        etIngredient1 = (EditText) view.findViewById(R.id.et_add_ing_1);
        etIngredient2 = (EditText) view.findViewById(R.id.et_add_ing_2);
        etIngredient3 = (EditText) view.findViewById(R.id.et_add_ing_3);
        etIngredient4 = (EditText) view.findViewById(R.id.et_add_ing_4);
        etInstruction1 = (EditText) view.findViewById(R.id.et_instruction_1);
        etInstruction2 = (EditText) view.findViewById(R.id.et_instruction_2);
        etStep1 = (EditText) view.findViewById(R.id.et_step_1);
        etStep2 = (EditText) view.findViewById(R.id.et_step_2);
        etTime = (EditText) view.findViewById(R.id.et_time);
        btnAddImage = (Button) view.findViewById(R.id.btn_add_image);
        btnSendData = (Button) view.findViewById(R.id.btn_send_data);
        btnTagsClassic = (Button) view.findViewById(R.id.btn_tags_classic);
        btnTagsModern = (Button) view.findViewById(R.id.btn_tags_modern);
        btnTagsShow = (Button) view.findViewById(R.id.btn_tags_show);
        btnTagsSmoke = (Button) view.findViewById(R.id.btn_tags_smoke);
        btnTagSteamBath = (Button) view.findViewById(R.id.btn_tags_steambath);
        ivImageView = (ImageView) view.findViewById(R.id.iv_image_view);
        btnTagsClassic.setOnClickListener(this);
        btnTagsModern.setOnClickListener(this);
        btnTagsShow.setOnClickListener(this);
        btnTagSteamBath.setOnClickListener(this);
        btnTagsSmoke.setOnClickListener(this);
        btnSendData.setOnClickListener(this);
        btnAddImage.setOnClickListener(this);

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
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Some fields are empty")
                    .show();
//            pDialog.show();
        } else {
            new UploadFileToServer().execute();

        }

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * Uploading the file to server
     * */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible

        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Configuration.END_POINT_LIVE);

            try {
                HTTPMultiPartEntity entity = new HTTPMultiPartEntity(
                        new HTTPMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File mSourceFile = new File(sourceFile.getPath());

                // Adding file data to http body
                entity.addPart("picture", new FileBody(mSourceFile));

                // Extra parameters if you want to pass to server
                entity.addPart("title", new StringBody(strPlantName));
                entity.addPart("tags", new StringBody(strTags));
                entity.addPart("time", new StringBody(strTime));
                entity.addPart("ingredients", new StringBody(strIngredients));
                entity.addPart("steps", new StringBody(strSteps));
                totalSize = entity.getContentLength();
                httppost.setEntity(entity);
                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("zma Response server: ", result);

            // showing the server response in an alert dialog
            showAlert();

            super.onPostExecute(result);
        }

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
                btnTagsClassic.setBackgroundColor(Color.parseColor("#eacb61"));
                btnTagsClassic.setTextColor(Color.WHITE);

                btnTagsModern.setTextColor(Color.parseColor("#eacb61"));
                btnTagsShow.setTextColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setTextColor(Color.parseColor("#eacb61"));

                btnTagsShow.setBackgroundColor(Color.WHITE);
                break;
            case R.id.btn_tags_modern:
                strTags = "Modern";
                btnTagsModern.setBackgroundColor(Color.parseColor("#eacb61"));
                btnTagsModern.setTextColor(Color.WHITE);


                btnTagsClassic.setTextColor(Color.parseColor("#eacb61"));
                btnTagsShow.setTextColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setTextColor(Color.parseColor("#eacb61"));
                break;
            case R.id.btn_tags_show:
                strTags = "Show";
                btnTagsShow.setBackgroundColor(Color.parseColor("#eacb61"));
                btnTagsShow.setTextColor(Color.WHITE);

                btnTagsModern.setTextColor(Color.parseColor("#eacb61"));
                btnTagsClassic.setTextColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setTextColor(Color.parseColor("#eacb61"));
                break;
            case R.id.btn_tags_smoke:
                strTags = "Smoke";
                btnTagsSmoke.setBackgroundColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.WHITE);

                btnTagsShow.setTextColor(Color.parseColor("#eacb61"));
                btnTagsModern.setTextColor(Color.parseColor("#eacb61"));
                btnTagsClassic.setTextColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setTextColor(Color.parseColor("#eacb61"));
                break;
            case R.id.btn_tags_steambath:
                strTags = "SteamBath";
                btnTagSteamBath.setBackgroundColor(Color.parseColor("#eacb61"));
                btnTagSteamBath.setTextColor(Color.WHITE);

                btnTagsShow.setTextColor(Color.parseColor("#eacb61"));
                btnTagsModern.setTextColor(Color.parseColor("#eacb61"));
                btnTagsClassic.setTextColor(Color.parseColor("#eacb61"));
                btnTagsSmoke.setTextColor(Color.parseColor("#eacb61"));
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
                pictureDialog.show();


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
            Bitmap bm = null;
            if (data != null) {
                try {
                    Uri selectedImage = data.getData();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    sourceFile = new File(picturePath);
                    cursor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ivImageView.setImageBitmap(bm);

        } else if (resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE && data != null) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
             sourceFile = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                sourceFile.createNewFile();
                fo = new FileOutputStream(sourceFile);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ivImageView.setImageBitmap(thumbnail);
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

    private void showAlert() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Recipe added successfully").show();
    }
}

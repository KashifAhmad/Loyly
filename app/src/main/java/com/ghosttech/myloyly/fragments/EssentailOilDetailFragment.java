package com.ghosttech.myloyly.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghosttech.myloyly.R;
import com.ghosttech.myloyly.utilities.EssentialOilAdapter;
import com.ghosttech.myloyly.utilities.EssentialOilHelper;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EssentailOilDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EssentailOilDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EssentailOilDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    EssentialOilAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String strTitleCol, strBotanicalCol;
    ArrayList<EssentialOilHelper> essentialItemHelperList = new ArrayList<EssentialOilHelper>();
    InputStream inputStream;
    ImageView ivTitleImage;
    TextView tvOilFamily, tvOilOrigin, tvOilStrength, tvOilNote, tvOilFragranceGroup, tvOilExtractionMethod,
            tvOilDescription, tvOilProperties, tvOilBlends, tvOilChakra, tvOilOtherLanguages, tvOilTags,
            tvTransparentBotanicalName, tvColor, tvDidYouKnow, tvElement, tvContraindications;
    Bundle args;
    int intRowId, intImageID;
    String strPlantName;
    EssentialOilHelper essentialOilHelper;
    private OnFragmentInteractionListener mListener;

    public EssentailOilDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EssentailOilDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EssentailOilDetailFragment newInstance(String param1, String param2) {
        EssentailOilDetailFragment fragment = new EssentailOilDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_essentail_oil_detail, container, false);
        ivTitleImage = (ImageView) view.findViewById(R.id.iv_detail_title_image);
        tvOilFamily = (TextView) view.findViewById(R.id.tv_oil_family);
        tvOilOrigin = (TextView) view.findViewById(R.id.tv_oil_origin);
        tvOilStrength = (TextView) view.findViewById(R.id.tv_oil_strength);
        tvOilNote = (TextView) view.findViewById(R.id.tv_oil_note);
        tvOilFragranceGroup = (TextView) view.findViewById(R.id.tv_oil_fragrance);
        tvOilExtractionMethod = (TextView) view.findViewById(R.id.tv_extraction_method);
        tvOilDescription = (TextView) view.findViewById(R.id.tv_oil_description);
        tvOilProperties = (TextView) view.findViewById(R.id.tv_oil_properties);
        tvOilChakra = (TextView) view.findViewById(R.id.tv_oil_chakra);
        tvOilOtherLanguages = (TextView) view.findViewById(R.id.tv_other_lang);
        tvTransparentBotanicalName = (TextView) view.findViewById(R.id.tv_trans_botanical_name);
        tvColor = (TextView)view.findViewById(R.id.tv_oil_color);
        tvContraindications = (TextView)view.findViewById(R.id.tv_contraidication);
        tvElement = (TextView)view.findViewById(R.id.tv_element);
        tvDidYouKnow = (TextView)view.findViewById(R.id.tv_do_you_know);
        args = getArguments();
        strPlantName = args.getString("oil_title");
//        intRowId = args.getInt("row_id");
        intImageID = args.getInt("image_id");
        essentialOilHelper = args.getParcelable("EssentialOilObject");

//        initializeData(intRowId);
        initializeData();
        customActionBar();
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

    public void initializeData() {
        tvOilDescription.setText(essentialOilHelper.getStrDescription());
        tvOilStrength.setText(essentialOilHelper.getStrStrength());
        tvTransparentBotanicalName.setText(essentialOilHelper.getStrTitle());
        tvOilOtherLanguages.setText(essentialOilHelper.getStrLanguages());
        tvOilOrigin.setText(essentialOilHelper.getStrOrigin());
        tvOilNote.setText(essentialOilHelper.getStrNote());
        tvOilExtractionMethod.setText(essentialOilHelper.getStrExtraction());
        tvOilProperties.setText(essentialOilHelper.getStrProperties());
        tvOilFragranceGroup.setText(essentialOilHelper.getStrFragrance());
        tvOilFamily.setText(essentialOilHelper.getStrPlantFamily());
        tvOilChakra.setText(essentialOilHelper.getStrChakra());
        tvDidYouKnow.setText(essentialOilHelper.getStrDidYouKnow());
        tvContraindications.setText(essentialOilHelper.getStrContraindications());
        tvElement.setText(essentialOilHelper.getStrElement());
        tvColor.setText(essentialOilHelper.getStrColor());
        ivTitleImage.setImageResource(intImageID);


    }

    public void customActionBar() {
        android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText(strPlantName);
        ImageView mBackArrow = (ImageView) mCustomView.findViewById(R.id.iv_back_arrow);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new EssesntialOilFragment();
                Bundle args = new Bundle();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container_main, fragment).commit();
                args.putString("back", "yes");
                fragment.setArguments(args);


            }
        });

    }
}
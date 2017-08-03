package com.ghosttech.myloyly.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghosttech.myloyly.activities.FullscreenActivity;
import com.ghosttech.myloyly.utilities.EssentialOilAdapter;
import com.ghosttech.myloyly.R;
import com.ghosttech.myloyly.utilities.EssentialOilHelper;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EssesntialOilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EssesntialOilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EssesntialOilFragment extends Fragment {
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
    private MaterialSearchBar searchBar;
    String[] myDataset;
    String strTitleCol, strBotanicalCol;
    ArrayList<EssentialOilHelper> essentialItemHelperList = new ArrayList<EssentialOilHelper>();
    private OnFragmentInteractionListener mListener;
    InputStream inputStream;
    String[] ids;
    int ivImagesArray[] = {R.drawable.alpepine, R.drawable.balsamfir, R.drawable.basil, R.drawable.bee_balm, R.drawable.benzoin,
            R.drawable.bergamotte, R.drawable.bergamotmint, R.drawable.black_pepper, R.drawable.bloodorange,
            R.drawable.cabrueva, R.drawable.cade, R.drawable.cajeput, R.drawable.cammomile_real, R.drawable.camphor,
            R.drawable.cardamom, R.drawable.cassia,R.drawable.cassia, R.drawable.ceder_himalaya, R.drawable.ceder_virginia,
            R.drawable.cinnamom, R.drawable.citronella, R.drawable.clove, R.drawable.combava_leaf, R.drawable.copal,
            R.drawable.coriander, R.drawable.cypress, R.drawable.douglasfir, R.drawable.elemi, R.drawable.eucalyptus_citriodora,
            R.drawable.eucalyptus_dives, R.drawable.eucalyptus_globulus, R.drawable.eucalyptus_smithii,
            R.drawable.eucalyptus_staigeriana, R.drawable.frankinscense, R.drawable.geranium,R.drawable.geranium, R.drawable.ginger,
            R.drawable.grapefruit, R.drawable.green_mint, R.drawable.ho_leaf_wood,R.drawable.ho_leaf_wood, R.drawable.jasmin, R.drawable.jatamansi,
            R.drawable.juniper, R.drawable.katafray, R.drawable.korean_fir, R.drawable.laural, R.drawable.lavandin_super,
            R.drawable.lavender, R.drawable.lemon, R.drawable.lemongrass, R.drawable.lime, R.drawable.litsea,
            R.drawable.mandarin_green_yellow_red_petitgrain_tangerine_clementine,R.drawable.mandarin_green_yellow_red_petitgrain_tangerine_clementine,
            R.drawable.mandarin_green_yellow_red_petitgrain_tangerine_clementine,R.drawable.maritim_pine, R.drawable.mountainpine,
            R.drawable.myrrh, R.drawable.myrthle, R.drawable.niaouli, R.drawable.nutmeg, R.drawable.orangeflower, R.drawable.petitgrain_orange,
            R.drawable.palmarosa, R.drawable.patchouli, R.drawable.peppermint, R.drawable.petitgrain_orange, R.drawable.ravintsara,
            R.drawable.rhodondendron, R.drawable.rose_damas, R.drawable.rosemary, R.drawable.sage, R.drawable.sandalwood_west_indian,
            R.drawable.scotch_pine, R.drawable.siam_wood, R.drawable.siberian_fir, R.drawable.silverfir, R.drawable.spearmint,
            R.drawable.staranis, R.drawable.sweet_orange, R.drawable.mandarin_green_yellow_red_petitgrain_tangerine_clementine,
            R.drawable.tea_tree, R.drawable.thuja, R.drawable.thymian, R.drawable.wild_mint_tokyo_mint, R.drawable.vetiver,
            R.drawable.wild_mint_tokyo_mint, R.drawable.wintergreen, R.drawable.ylang_ylang};

    public EssesntialOilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EssesntialOilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EssesntialOilFragment newInstance(String param1, String param2) {
        EssesntialOilFragment fragment = new EssesntialOilFragment();
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
        View view = inflater.inflate(R.layout.fragment_essesntial_oil, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //inputStream = getActivity().getResources().openRawResource(R.raw.oils_sheet);
        searchBar = (MaterialSearchBar) view.findViewById(R.id.searchBar);
        searchBar.setHint("Custom hint");
        searchBar.setSpeechMode(false);
        mRecyclerView.setHasFixedSize(true);
        // specify an adapter (see also next example)
        mAdapter = new EssentialOilAdapter(getActivity(), essentialItemHelperList);
        mRecyclerView.setAdapter(mAdapter);
        customActionBar();
        initializeData();
        searchEducationList();
        return view;

    }

    private void initializeData() {
        AssetManager assetManager = getActivity().getAssets();
        EssentialOilHelper helper = null;
        try {
            inputStream = assetManager.open("oils_data.XLS");
            Workbook workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(0);
            int row = sheet.getRows();
            int column = sheet.getColumns();
            for (int i = 1; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    Cell cellData = sheet.getCell(0, i);
                    Cell colDescirption = sheet.getCell(2, i);
                    if (j == 0) {
                        helper = new EssentialOilHelper();
                        String columnData = String.valueOf(cellData.getContents());
                        helper.setStrTitle(String.valueOf(cellData.getContents()));
                        helper.setStrDescription(String.valueOf(colDescirption.getContents()));
                        Log.d("zma first column", columnData);
                        helper.setIntImageID(ivImagesArray);
                    }
                }
                essentialItemHelperList.add(helper);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();
        searchEducationList();
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


    public void searchEducationList() {
        AssetManager assetManager = getActivity().getAssets();
        try {
            inputStream = assetManager.open("oils_data.XLS");
            Workbook workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(0);



        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence query, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                Log.d("zma changed list", getClass().getSimpleName() + " text changed " + searchBar.getText());
                final ArrayList<EssentialOilHelper> filteredList = new ArrayList<>();
                if (editable.length() > 0) {
                    for (EssentialOilHelper essentialOilHelper : essentialItemHelperList) {
                        if (essentialOilHelper.getStrTitle().toLowerCase().toString().contains(editable.toString().toLowerCase())) {
                            filteredList.add(essentialOilHelper);
                        }
                    }
                    mAdapter = new EssentialOilAdapter(getActivity(), filteredList);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter = new EssentialOilAdapter(getActivity(), essentialItemHelperList);
                    mRecyclerView.setAdapter(mAdapter);
                }


            }
        });
    }

    public void customActionBar() {
        android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Essential Oils");
        ImageView mBackArrow = (ImageView) mCustomView.findViewById(R.id.iv_back_arrow);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                intent.putExtra("frag_id",1);
                startActivity(intent);
                getActivity().finish();
//                Fragment fragment = new MainFragment();
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();


            }
        });

    }
}

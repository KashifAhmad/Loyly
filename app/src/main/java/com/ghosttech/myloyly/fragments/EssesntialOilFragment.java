package com.ghosttech.myloyly.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ghosttech.myloyly.R;
import com.ghosttech.myloyly.activities.FullscreenActivity;
import com.ghosttech.myloyly.utilities.EssentialOilAdapter;
import com.ghosttech.myloyly.utilities.EssentialOilHelper;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class EssesntialOilFragment extends Fragment {


    private RecyclerView mRecyclerView;
    EssentialOilAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MaterialSearchBar searchBar;
    String[] myDataset;
    String strTitleCol, strBotanicalCol;
    ArrayList<EssentialOilHelper> essentialItemHelperList = new ArrayList<EssentialOilHelper>();
    InputStream inputStream;
    String[] ids;
    int ivImagesArray[] = {R.drawable.alpepine, R.drawable.balsamfir, R.drawable.basil, R.drawable.bee_balm, R.drawable.benzoin,
            R.drawable.bergamotte, R.drawable.bergamotmint, R.drawable.black_pepper, R.drawable.bloodorange,
            R.drawable.cabrueva, R.drawable.cade, R.drawable.cajeput, R.drawable.cammomile_real, R.drawable.camphor,
            R.drawable.cardamom, R.drawable.cassia, R.drawable.cassia, R.drawable.ceder_himalaya, R.drawable.ceder_virginia,
            R.drawable.cinnamom, R.drawable.citronella, R.drawable.clove, R.drawable.combava_leaf, R.drawable.copal,
            R.drawable.coriander, R.drawable.cypress, R.drawable.douglasfir, R.drawable.elemi, R.drawable.eucalyptus_citriodora,
            R.drawable.eucalyptus_dives, R.drawable.eucalyptus_globulus, R.drawable.eucalyptus_smithii,
            R.drawable.eucalyptus_staigeriana, R.drawable.frankinscense, R.drawable.geranium, R.drawable.geranium, R.drawable.ginger,
            R.drawable.grapefruit, R.drawable.green_mint, R.drawable.ho_leaf_wood, R.drawable.ho_leaf_wood, R.drawable.jasmin, R.drawable.jatamansi,
            R.drawable.juniper, R.drawable.katafray, R.drawable.korean_fir, R.drawable.laural, R.drawable.lavandin_super,
            R.drawable.lavender, R.drawable.lemon, R.drawable.lemongrass, R.drawable.lime, R.drawable.litsea,
            R.drawable.mandarin_green_yellow_red_petitgrain_tangerine_clementine, R.drawable.mandarin_green_yellow_red_petitgrain_tangerine_clementine,
            R.drawable.mandarin_green_yellow_red_petitgrain_tangerine_clementine, R.drawable.maritim_pine, R.drawable.mountainpine,
            R.drawable.myrrh, R.drawable.myrthle, R.drawable.niaouli, R.drawable.nutmeg, R.drawable.orangeflower, R.drawable.petitgrain_orange,
            R.drawable.palmarosa, R.drawable.patchouli, R.drawable.peppermint, R.drawable.petitgrain_orange, R.drawable.ravintsara,
            R.drawable.rhodondendron, R.drawable.rose_damas, R.drawable.rosemary, R.drawable.sage, R.drawable.sandalwood_west_indian,
            R.drawable.scotch_pine, R.drawable.siam_wood, R.drawable.siberian_fir, R.drawable.silverfir, R.drawable.spearmint,
            R.drawable.staranis, R.drawable.sweet_orange, R.drawable.mandarin_green_yellow_red_petitgrain_tangerine_clementine,
            R.drawable.tea_tree, R.drawable.thuja, R.drawable.thymian, R.drawable.wild_mint_tokyo_mint, R.drawable.vetiver,
            R.drawable.wild_mint_tokyo_mint, R.drawable.wintergreen, R.drawable.ylang_ylang};

    public EssesntialOilFragment() {
    }

    public static EssesntialOilFragment newInstance() {
        return new EssesntialOilFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_essesntial_oil, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return false;
            }
        });
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        Bundle args = new Bundle();
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //inputStream = getActivity().getResources().openRawResource(R.raw.oils_sheet);
        searchBar = (MaterialSearchBar) view.findViewById(R.id.searchBar);
        searchBar.setVisibility(View.GONE);
        if (MainFragment.SEARCH) {
            searchBar.setVisibility(View.VISIBLE);
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) mRecyclerView.getLayoutParams();
            marginLayoutParams.setMargins(0,200,0,0);
            mRecyclerView.setLayoutParams(marginLayoutParams);

        }
        searchBar.setHint("Search here");
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
            inputStream = assetManager.open("Excel_work_Updated.xls");
            Workbook workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(0);
            int row = sheet.getRows();
            for (int i = 1; i < row; i++) {
                Cell collTitle = sheet.getCell(0, i);
                Cell colDescription = sheet.getCell(15, i);
                Cell collOilFamily = sheet.getCell(3, i);
                Cell collOilOrigin = sheet.getCell(4, i);
                Cell collOilStrength = sheet.getCell(9, i);
                Cell collOilNote = sheet.getCell(8, i);
                Cell collOilFragranceGroup = sheet.getCell(10, i);
                Cell cellOtherLangauge = sheet.getCell(1, i);
                Cell cellBotanicalName = sheet.getCell(2, i);
                Cell cellExtractionMethod = sheet.getCell(6, i);
                Cell cellProperties = sheet.getCell(16, i);
                Cell cellChakra = sheet.getCell(11, i);
                Cell cellElement = sheet.getCell(13, i);
                Cell cellColor = sheet.getCell(12, i);
                Cell cellDidYouKnow = sheet.getCell(21, i);
                Cell cellContraindications = sheet.getCell(22, i);

                helper = new EssentialOilHelper();
                helper.setStrTitle(String.valueOf(collTitle.getContents()));
                helper.setStrBotanicalName(String.valueOf(cellBotanicalName.getContents()));
                helper.setStrDescription(String.valueOf(colDescription.getContents()));
                helper.setStrPlantFamily(String.valueOf(collOilFamily.getContents()));
                helper.setStrOrigin(String.valueOf(collOilOrigin.getContents()));
                helper.setStrStrength(String.valueOf(collOilStrength.getContents()));
                helper.setStrNote(String.valueOf(collOilNote.getContents()));
                helper.setStrFragrance(String.valueOf(collOilFragranceGroup.getContents()));
                helper.setStrLanguages(String.valueOf(cellOtherLangauge.getContents()));
                helper.setStrExtraction(String.valueOf(cellExtractionMethod.getContents()));
                helper.setStrProperties(String.valueOf(cellProperties.getContents()));
                helper.setStrChakra(String.valueOf(cellChakra.getContents()));
                helper.setStrElement(String.valueOf(cellElement.getContents()));
                helper.setStrColor(String.valueOf(cellColor.getContents()));
                helper.setStrDidYouKnow(String.valueOf(cellDidYouKnow.getContents()));
                helper.setStrContraindications(String.valueOf(cellContraindications.getContents()));

                helper.setIntImageID(ivImagesArray);
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


    public void searchEducationList() {

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
                        if (essentialOilHelper.getStrTitle().toLowerCase().toString().startsWith(editable.toString().toLowerCase())) {
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
                intent.putExtra("frag_id", 1);
                startActivity(intent);
                getActivity().finish();
//                Fragment fragment = new MainFragment();
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();


            }
        });

    }
}

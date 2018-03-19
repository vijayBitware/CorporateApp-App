package com.corporateapp.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.AppUtils.AutoScrollViewPager;
import com.corporateapp.R;
import com.corporateapp.adapter.AdapterNewsHome;
import com.corporateapp.adapter.AdapterPhotoGallary;
import com.corporateapp.adapter.slidingImageAdapter;
import com.corporateapp.models.home.HomeResponce;
import com.corporateapp.webservice.APIRequest;
import com.corporateapp.webservice.AppConstants;
import com.corporateapp.webservice.BaseResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bitware on 13/10/17.
 */

public class FragmentHome extends Fragment implements View.OnClickListener, APIRequest.ResponseHandler {

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    View view;
    ViewPager bannaerPager, vp_news;
    AutoScrollViewPager autoScrollViewPager;
    RecyclerView rv_photoGallary, rv_news, rv_corporator;
    LinearLayout ll_complaints, ll_suggesions, ll_currentDevelopment, ll_emergency_contacts, ll_aboutme;
    TextView tv_coporatorViewAll, txtAllNews, tv_photogallaryViewAll;
    LinearLayoutManager layoutManager;
    List<String> arrSlider;
    CirclePageIndicator indicator;
    String[] permissionsRequired = new String[]{android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.ACCESS_FINE_LOCATION};
    ImageLoader imageLoader;
    ImageView iv_aboutMe;
    RelativeLayout rl_aboutMe;
    AdapterNewsHome adapterNewsHome;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        if (AppUtil.isConnectingToInternet(getContext())) {
            callHomeApi();
        } else {
            AppUtil.showToastMsg(getContext(), getResources().getString(R.string.no_internet));
        }
        CheckPermission();
        return view;
    }

    private void callHomeApi() {
        String homeUrl = AppConstants.BASE_URL + "home_screen";
        System.out.println("Home url > " + homeUrl);
        new APIRequest(getContext(), homeUrl, new JSONObject(), this, AppConstants.API_HOME, AppConstants.GET);
    }

    private void init() {
        permissionStatus = getContext().getSharedPreferences("permissionStatus", MODE_PRIVATE);
        iv_aboutMe = (ImageView) view.findViewById(R.id.iv_aboutMe);
        rv_photoGallary = (RecyclerView) view.findViewById(R.id.rv_photoGallary);
        rl_aboutMe = (RelativeLayout) view.findViewById(R.id.rl_aboutMe);
        layoutManager = new GridLayoutManager(getContext(), 3);
        rv_photoGallary.setLayoutManager(layoutManager);
        ll_complaints = (LinearLayout) view.findViewById(R.id.ll_complaints);
        ll_currentDevelopment = (LinearLayout) view.findViewById(R.id.ll_currentDevelopment);
        ll_suggesions = (LinearLayout) view.findViewById(R.id.ll_suggesions);
        txtAllNews = (TextView) view.findViewById(R.id.txtAllNews);
        tv_photogallaryViewAll = (TextView) view.findViewById(R.id.tv_photoGallaryViewAll);
        ll_emergency_contacts = (LinearLayout) view.findViewById(R.id.ll_emergency_contacts);

        ll_complaints.setOnClickListener(this);
        ll_suggesions.setOnClickListener(this);
        ll_currentDevelopment.setOnClickListener(this);
        txtAllNews.setOnClickListener(this);
        tv_photogallaryViewAll.setOnClickListener(this);
        ll_emergency_contacts.setOnClickListener(this);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
        ll_aboutme = (LinearLayout) view.findViewById(R.id.ll_aboutme);

        autoScrollViewPager = (AutoScrollViewPager) view.findViewById(R.id.pager);
        vp_news = (ViewPager) view.findViewById(R.id.vp_news);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_complaints:
                AppConstants.currentFragmentPositionIncomplaint = 0;
                AppUtil.replaceFragment(new FragmentComplaints(), getFragmentManager(), null);
                break;
            case R.id.ll_suggesions:
                AppConstants.curentFragmentPositionInSuggesion = 0;
                AppUtil.replaceFragment(new FragmentSuggesions(), getFragmentManager(), null);
                break;
            case R.id.ll_currentDevelopment:
                AppUtil.replaceFragment(new FragmentCurrentDevelopement(), getFragmentManager(), null);
                break;
            case R.id.txtAllNews:
                AppUtil.replaceFragment(new FragmentALlNews(), getFragmentManager(), null);
                break;
            case R.id.tv_photoGallaryViewAll:
                AppUtil.replaceFragment(new FragmentPhotoGallary(), getFragmentManager(), null);
                break;
            case R.id.ll_emergency_contacts:
                AppUtil.replaceFragment(new FragmentEmergencyContacts(), getFragmentManager(), null);
                break;

        }
    }

    @Override
    public void onSuccess(BaseResponse response) {
        final HomeResponce homeResponce = (HomeResponce) response;
        if (homeResponce.getStatus().equalsIgnoreCase("success")) {
            AppConstants.arrGallary = homeResponce.getGallery();
            rv_photoGallary.setAdapter(new AdapterPhotoGallary(getContext(), homeResponce.getGallery()));
            AppConstants.arrSlider = new ArrayList<>();
            for (int i = 0; i < homeResponce.getSlider().size(); i++) {
                AppConstants.arrSlider.add(homeResponce.getSlider().get(i).getImage());
            }
            adapterNewsHome = new AdapterNewsHome(getContext(), homeResponce.getNewsData());
            vp_news.setAdapter(adapterNewsHome);
            AppConstants.arrNews = homeResponce.getNewsData();
            AppConstants.arrDevelopment = homeResponce.getDevelopment();
            AppConstants.arrDepartment = homeResponce.getDepartment();
            AppConstants.arrAmbulance = homeResponce.getEmergencyContact().getAmbulance();
            AppConstants.arrPoliceStation = homeResponce.getEmergencyContact().getPolisStation();
            AppConstants.arrHospital = homeResponce.getEmergencyContact().getHospitals();
            AppConstants.arrBloodBank = homeResponce.getEmergencyContact().getBloodBank();
            AppConstants.arrFireBrigade = homeResponce.getEmergencyContact().getFireBrigade();

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showStubImage(R.drawable.placeholder_banner).cacheOnDisc().build();
            imageLoader.displayImage(homeResponce.getCorporatorData().getImage(), iv_aboutMe, options);

            ll_aboutme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("member_name", homeResponce.getCorporatorData().getName());
                    bundle.putString("member_email", homeResponce.getCorporatorData().getEmail());
                    bundle.putString("member_description", homeResponce.getCorporatorData().getDescription());
                    bundle.putString("member_address", homeResponce.getCorporatorData().getCity());
                    bundle.putString("member_phone", homeResponce.getCorporatorData().getMobile());
                    bundle.putString("member_image", homeResponce.getCorporatorData().getProfile());
                    AppUtil.replaceFragment(new FragmentMemberDetails(), getFragmentManager(), bundle);
                }
            });
            rl_aboutMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("member_name", homeResponce.getCorporatorData().getName());
                    bundle.putString("member_email", homeResponce.getCorporatorData().getEmail());
                    bundle.putString("member_description", homeResponce.getCorporatorData().getDescription());
                    bundle.putString("member_address", homeResponce.getCorporatorData().getCity());
                    bundle.putString("member_phone", homeResponce.getCorporatorData().getMobile());
                    bundle.putString("member_image", homeResponce.getCorporatorData().getProfile());
                    AppUtil.replaceFragment(new FragmentMemberDetails(), getFragmentManager(), bundle);
                }
            });

            autoScrollViewPager.startAutoScroll();
            autoScrollViewPager.setInterval(3000);
            autoScrollViewPager.setCycle(true);
            autoScrollViewPager.setStopScrollWhenTouch(true);
            autoScrollViewPager.setAdapter(new slidingImageAdapter(getContext(), AppConstants.arrSlider));


        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    private void CheckPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), permissionsRequired[4]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissionsRequired[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissionsRequired[4])) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions((Activity) getContext(), permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getContext(), "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(getActivity(), permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
//            proceedAfterPermission();
           /* userId = sharedPreferences.getString("user_id","");
            if (isInternetPresent){
                new getVendorProfile().execute("{\"user_id\":\"" +  userId + "\"}");
            }else {
                Toast.makeText(getContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
            }*/
        }

    }
}

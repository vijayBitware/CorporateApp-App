package com.corporateapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.corporateapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class FragmentMemberDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    View view;
    TextView txtName, txtEmail, txtPhNo, txtAddress, txtDescription;
    ImageView imgProfile;
    Button btnBack;
    FragmentManager fragmentManager;
    ImageLoader imageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_member_details, container, false);
        init();
        Bundle bundle = getArguments();
        txtName.setText(bundle.getString("member_name"));
        txtEmail.setText(bundle.getString("member_email"));
        txtDescription.setText(bundle.getString("member_description"));
        txtAddress.setText(bundle.getString("member_address"));
//        txtAddress.setText("Gayatri App., 1/7, Sceam No.1, Sector no.21, Yamuna nagar, Nigadi, Pune-411044");
        txtPhNo.setText(bundle.getString("member_phone"));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.placeholder_corporator).cacheOnDisc().build();
        imageLoader.displayImage(bundle.getString("member_image"), imgProfile, options);
        // Glide.with(getContext()).load(bundle.getString("member_image")).placeholder(R.drawable.placeholder_corporator).into(imgProfile);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStackImmediate();
            }
        });
        return view;
    }

    private void init() {
        txtName = (TextView) view.findViewById(R.id.txtName);
        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        txtPhNo = (TextView) view.findViewById(R.id.txtPhNo);
        txtAddress = (TextView) view.findViewById(R.id.txtAddress);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        fragmentManager = getFragmentManager();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
    }
}

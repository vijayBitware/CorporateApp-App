package com.corporateapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.corporateapp.AppUtils.AppSharedPreference;
import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.AppUtils.GPSTracker;
import com.corporateapp.AppUtils.WebServiceImage;
import com.corporateapp.R;
import com.corporateapp.webservice.AppConstants;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by bitware on 13/10/17.
 */

public class SubmitComplaintFormActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    private static int RESULT_LOAD_IMAGE = 3;
    final int CAMERA_CAPTURE = 1;
    final int PIC_CROP = 2;
    public Uri picUri;
    //File f;
    String network_status;
    Bitmap tempBmp;
    String path = Environment.getExternalStorageDirectory() + "/image.jpg";
    File f = null;
    ImageView imgProfile, imgCalender, imgLocation;
    TextView txtChangeImg;
    EditText edtTitle, edtPincode, edtDate, edtDescription, edtLocation;
    Spinner spDepartment;
    Button btnSubmit, btnCancel;
    UploadPhotoDialog mUploadPhotoDialog;
    TextView txt_selectedward;
    ArrayList<String> departmentList;
    FragmentManager fragmentManager;
    GPSTracker gpsTracker;
    LocationManager locationManager;
    double lattitude, longitude;
    boolean locationUpdated = false;
    ProgressDialog p;
    GPSTracker mGPS;
    //after submitting AD
    WebServiceImage.CallbackImage callback = new WebServiceImage.CallbackImage() {
        @Override
        public void onSuccessImage(int reqestcode, JSONObject rootjson) {
            System.out.println("++++++-result++++++" + rootjson);
            try {
                if (rootjson.get("status").toString().equals("success")) {
                    Toast.makeText(SubmitComplaintFormActivity.this, rootjson.get("description").toString(), Toast.LENGTH_SHORT).show();
                    finish();
                    //
                }

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("++++++-Exception++++++" + e);
            }
        }

        @Override
        public void onErrorImage(int reqestcode, String error) {
        }

    };
    private Uri fileUri;
    /**
     * This method for select profile picture to upload
     */

    UploadPhotoDialog.onMediaDialogListener mMediaDialogListener = new UploadPhotoDialog.onMediaDialogListener() {

        @Override
        public void onGalleryClick() {
            // TODO Auto-generated method stub
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            SubmitComplaintFormActivity.this.startActivityForResult(i, RESULT_LOAD_IMAGE);
        }

        @Override
        public void onDeleteClick() {
            int id = getResources().getIdentifier("pro_default", "drawable", SubmitComplaintFormActivity.this.getPackageName());
            imgProfile.setImageResource(id);

            imgProfile.setTag("no_image");
        }

        @Override
        public void onCameraClick() {
            // TODO Auto-generated method stub
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            System.out.println("File URI------" + fileUri);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_CAPTURE);
        }

    };

    /*
    * returning image / video
    */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_form);

        init();

    }

    private void init() {
        mGPS = new GPSTracker(SubmitComplaintFormActivity.this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        // imgCalender = (ImageView) findViewById(R.id.imgCalender);
        imgLocation = (ImageView) findViewById(R.id.imgLocation);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);

        txt_selectedward = (TextView) findViewById(R.id.txtWard);
        //  txt_selectedward.setText(getResources().getString(R.string.app_name));
        txtChangeImg = (TextView) findViewById(R.id.txtChangeImg);
        edtLocation = (EditText) findViewById(R.id.edtLocation);
        edtDescription = (EditText) findViewById(R.id.edtDescription);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtPincode = (EditText) findViewById(R.id.edtPincode);

        spDepartment = (Spinner) findViewById(R.id.spDepartment);
        //spWard = (Spinner) findViewById(R.id.spWard);
        //spLocation = (Spinner) findViewById(R.id.spLocation);

        departmentList = new ArrayList<>();
        for (int i = 0; i < AppConstants.arrDepartment.size(); i++) {
            departmentList.add(AppConstants.arrDepartment.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, departmentList);
        spDepartment.setAdapter(adapter);
        /*AdapterDepartment adapterDepartment = new AdapterDepartment(SubmitComplaintFormActivity.this,R.layout.row_department,departmentList);

        spDepartment.setAdapter(adapterDepartment);*/

        imgProfile.setOnClickListener(this);
//        imgCalender.setOnClickListener(this);
        txtChangeImg.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imgLocation.setOnClickListener(this);

        imgProfile.setTag("no_image");

        //show current date
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        edtDate.setText(formattedDate);


        p = new ProgressDialog(SubmitComplaintFormActivity.this);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setMessage("Getting User location");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgProfile:
                break;

//            case R.id.imgCalender:
//                /*ShowDateTimePicker objShowDateTimePicker = new ShowDateTimePicker();
//                objShowDateTimePicker.showDatePicker(this, edtDate, "date");*/
//                break;

            case R.id.txtChangeImg:
                mUploadPhotoDialog = new UploadPhotoDialog(SubmitComplaintFormActivity.this, mMediaDialogListener, imgProfile.getTag().toString());
                mUploadPhotoDialog.show();

                break;

            case R.id.btnSubmit:
                if (validation()) {
                    if (AppUtil.isConnectingToInternet(SubmitComplaintFormActivity.this)) {
                        submitData();
                    } else {
                        AppUtil.showToastMsg(SubmitComplaintFormActivity.this, getResources().getString(R.string.no_internet));
                    }
                }
                break;

            case R.id.btnCancel:
                // fragmentManager.popBackStackImmediate();
                finish();
                break;
            case R.id.imgLocation:
                getCurrentLocation();
                break;
        }
    }

    private boolean validation() {

        if (edtTitle.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(SubmitComplaintFormActivity.this, "Please enter title", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtDate.getText().toString().equals("")) {
            Toast.makeText(SubmitComplaintFormActivity.this, "Please enter date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtPincode.getText().toString().equals("")) {
            Toast.makeText(SubmitComplaintFormActivity.this, "Please enter pincode", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtDescription.getText().toString().equals("")) {
            Toast.makeText(SubmitComplaintFormActivity.this, "Please enter description", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtLocation.getText().toString().equals("")) {
            Toast.makeText(SubmitComplaintFormActivity.this, "Please enter location", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * This method for select profile picture to upload
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("Image Result code-------" + resultCode);

        if (resultCode == RESULT_OK) {
            AppConstants.isLocationEnabled = true;
            if (requestCode == CAMERA_CAPTURE) {
                try {
                    // bimatp factory
                    BitmapFactory.Options options = new BitmapFactory.Options();

                    // downsizing image as it throws OutOfMemory Exception for larger  // images
                    options.inSampleSize = 8;

                    final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
                    tempBmp = bitmap;
                    imgProfile.setImageBitmap(tempBmp);
                    System.out.println("Pic file Uri====" + fileUri.getPath() + ", Bitmap-------" + bitmap);
                    UploadPhotoDialog.profile_ = fileUri.getPath().toString();
                    getServiceResponseForPhoto(bitmap);
                } catch (Exception e) {
                    System.out.println("Exception camera click--" + e.toString());
                }

            } else if (requestCode == PIC_CROP) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                Bitmap thePic = extras.getParcelable("data");
                // retrieve a reference to the ImageView

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thePic.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                File f = null;
                try {
                    // you can create a new file name "test.jpg" in sdcard folder.
                    f = new File(Environment.getExternalStorageDirectory() + File.separator + "test.jpg");
                    f.createNewFile();
                    // write the bytes in file
                    FileOutputStream fo;
                    UploadPhotoDialog.profile_ = f.toString();
                    fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
                if (thePic != null)
                    tempBmp = UploadPhotoDialog.decodeSampledBitmapFromPath(f.toString(), 50, 50);
                imgProfile.setImageBitmap(tempBmp);
                // display the returned cropped image
                getServiceResponse();
            } else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

                System.out.println("Picture path in RESULT_LOAD_IMAGE:");

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);

                System.out.println("Picture path :" + picturePath);

                try {
                    File f = null;
                    if (picturePath != null)
                        f = new File(picturePath);
                    else
                        Toast.makeText(SubmitComplaintFormActivity.this, "Error while rendering image.", Toast.LENGTH_SHORT).show();

                    f.createNewFile();
                    UploadPhotoDialog.profile_ = f.toString();
                    System.out.println("Picture path in UploadPhotoDialog.profile_:" + UploadPhotoDialog.profile_);
                    cursor.close();
                } catch (FileNotFoundException e) {
                } catch (Exception e) {
                    Toast.makeText(SubmitComplaintFormActivity.this, "Error while rendering image.", Toast.LENGTH_SHORT).show();
                }
                Bitmap bmp = UploadPhotoDialog.decodeSampledBitmapFromPath(picturePath, 50, 50);

                if (bmp != null)
                    tempBmp = bmp;
                imgProfile.setImageBitmap(tempBmp);
                // Webservice call to upload image
                getServiceResponse();
            }
        }
    }

    /*
    * Creating file uri to store image/video
    */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * This method for upload profile picture to server
     *
     * @paramBitmap - bitmap
     */
    public void getServiceResponseForPhoto(Bitmap bitmap) {
        try {

            // create a file to write bitmap data.
            f = new File(Environment.getExternalStorageDirectory() + "/_camera.png");

            System.out.println("File :" + f.toString());

            if (f != null) {

                f.createNewFile();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array

                // save it in your external storage.
                FileOutputStream fo = new FileOutputStream(f);

                fo.write(byteArray);


            } else {

                // reqEntity.addPart("patient_image",new StringBody(""));
            }
            //service.getService(getActivity(), Constant.urlUploadPic, reqEntity);
        } catch (Exception e) {
        }
    }

    /**
     * This method for upload profile picture to server
     */
    public void getServiceResponse() {
        try {
            System.out.println("Picture path in UploadPhotoDialog.profile_:" + UploadPhotoDialog.profile_);


            // reqEntity.addPart("delete", new StringBody("N"));
            if (!(UploadPhotoDialog.profile_.equalsIgnoreCase(""))) {


                // create a file to write bitmap data
                f = new File(getCacheDir(), "useroriginal.png");
                f.createNewFile();

                // Convert bitmap to byte array
                Bitmap bitmap1 = UploadPhotoDialog.decodeSampledBitmapFromPath(UploadPhotoDialog.profile_, 320, 240);
                ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos1);
                byte[] bitmapdata1 = bos1.toByteArray();

                // write the bytes in file
                FileOutputStream fos1 = new FileOutputStream(f);
                fos1.write(bitmapdata1);


            } else {
                //reqEntity.addPart("patient_image",new StringBody(""));
            }
            // service.getService(getActivity(), Constant.urlUploadPic, reqEntity);
        } catch (Exception e) {
        }
    }

    public void submitData() {
        network_status = String.valueOf(AppUtil.isConnectingToInternet(SubmitComplaintFormActivity.this));
        if (network_status.equals("false")) {
            //AlertClass alert = new AlertClass();
            //alert.customDialog(AddPostActivity.this, getResources().getString(R.string.alert_conn), getResources().getString(R.string.msg_conn));
        } else {
            try {
                String token = AppSharedPreference.getValue(SubmitComplaintFormActivity.this, "token");
                Log.e("TAG", token);

                //SharedPref shared_pref = new SharedPref(AddPostActivity.this);
                WebServiceImage service = new WebServiceImage(callback);
                MultipartEntity reqEntity = new MultipartEntity();
                reqEntity.addPart("title", new StringBody("" + URLEncoder.encode(edtTitle.getText().toString(), "utf-8")));
                reqEntity.addPart("ward", new StringBody(URLEncoder.encode("PCMC Ward 11", "utf-8")));
                reqEntity.addPart("desc", new StringBody("" + URLEncoder.encode(edtDescription.getText().toString(), "utf-8")));
                reqEntity.addPart("date", new StringBody("" + URLEncoder.encode(edtDate.getText().toString(), "utf-8")));
                reqEntity.addPart("dept", new StringBody("" + URLEncoder.encode(spDepartment.getSelectedItem().toString(), "utf-8")));
                reqEntity.addPart("location", new StringBody("" + URLEncoder.encode(edtLocation.getText().toString(), "utf-8")));
                reqEntity.addPart("pin", new StringBody("" + URLEncoder.encode(edtPincode.getText().toString(), "utf-8")));
                reqEntity.addPart("token", new StringBody("" + URLEncoder.encode(token, "utf-8")));
                if (f == null) {
                    //  FLAG_IMG = true;
                    reqEntity.addPart("image", new StringBody(""));
                } else {
                    // FLAG_IMG = true;
                    reqEntity.addPart("image", new FileBody(f));
                }


                if (AppUtil.flagSuggestioComplaint.equalsIgnoreCase("suggestion")) {
                    Log.e("TAG", "adding suggeion");
                    service.getService(SubmitComplaintFormActivity.this, "http://103.224.243.227/codeigniter/Pcmc_ward/Api_controller/add_suggestion", reqEntity);

                } else {
                    Log.e("TAG", "adding complaint");
                    service.getService(SubmitComplaintFormActivity.this, "http://103.224.243.227/codeigniter/Pcmc_ward/Api_controller/add_complaint", reqEntity);

                }

            } catch (NullPointerException e) {
                System.out.println("Nullpointer Exception at Login Screen" + e);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    private void getCurrentLocation() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            showLocationDialog();
        } else {
            GPSTracker mGPS = new GPSTracker(SubmitComplaintFormActivity.this);
            if (mGPS.canGetLocation()) {
                mGPS = new GPSTracker(SubmitComplaintFormActivity.this);
                lattitude = mGPS.getLatitude();
                longitude = mGPS.getLongitude();
                Log.e("TAG", "Current Location > " + lattitude + "," + longitude);
                if (lattitude == 0.0 || longitude == 0.0) {
                    Toast.makeText(SubmitComplaintFormActivity.this, "unable to get location please try again", Toast.LENGTH_LONG).show();
                } else {
                    new DataLongOperationAsynchTask().execute();
                }
            } else {
                Toast.makeText(SubmitComplaintFormActivity.this, "unable to get location", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void showLocationDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(SubmitComplaintFormActivity.this);
        dialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        dialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
//                Config.isCheckGPS = true;
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(myIntent, 1);
                //get gps
            }
        });

        dialog.show();
    }

    public String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private class DataLongOperationAsynchTask extends AsyncTask<String, Void, String[]> {
        //Dialog dialog = AppUtils.customLoader(DrawerActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p.show();
        }

        @Override
        protected String[] doInBackground(String... params) {
            String response;
            try {
                response = getLatLongByURL("https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyCiFk8cJNVwvQ92IbTXXBduFS6EIjjYpEQ&latlng=" + lattitude + "," + longitude + "&sensor=true");
                Log.e("response", "" + response);
                return new String[]{response};
            } catch (Exception e) {
                return new String[]{"error"};
            }
        }

        @Override
        protected void onPostExecute(String... result) {
            try {
                System.out.println("result > " + result);
                JSONObject jsonObject = new JSONObject(result[0]);

                JSONArray jsonArray = jsonObject.getJSONArray("results");
                String address = jsonArray.getJSONObject(0).getString("formatted_address");
                Log.e("TAG", address);
                edtLocation.setText(address);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (p.isShowing()) {
                p.dismiss();
            }
        }
    }

}

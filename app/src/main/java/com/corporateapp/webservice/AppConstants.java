package com.corporateapp.webservice;

import com.corporateapp.models.home.Ambulance;
import com.corporateapp.models.home.BloodBank;
import com.corporateapp.models.home.CorporatorDatum;
import com.corporateapp.models.home.Department;
import com.corporateapp.models.home.Development;
import com.corporateapp.models.home.EmergencyContact;
import com.corporateapp.models.home.FireBrigade;
import com.corporateapp.models.home.Gallery;
import com.corporateapp.models.home.Hospital;
import com.corporateapp.models.home.NewsDatum;
import com.corporateapp.models.home.PolisStation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bitware on 2/9/17.
 */

public class AppConstants {

    public static final int API_REGISTRATION = 101;
    public static final int API_LOGIN = 102;
    public static final int API_FORGOTPASS = 103;
    public static final int API_ALLCOMPLAINTS = 104;
    public static final int API_SUGGETION = 105;
    public static final int API_MYCOMPLAINTS = 106;
    public static final int API_HOME = 107;
    public static final int API_LIKECOMPLAINT = 108;
    public static final int API_LIKESUGGESION = 109;
    public static final int API_ALL_SUGGETION = 110;
    public static String BASE_URL = "http://103.224.243.227/codeigniter/Pcmc_ward/Api_controller/";
    public static String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)$";
    public static String GET = "GET";
    public static String POST = "POST";
    public static int callService = 0;
    public static List<CorporatorDatum> arrCorporatorList = new ArrayList<>();
    public static List<Gallery> arrGallary = new ArrayList<>();
    public static List<NewsDatum> arrNews = new ArrayList<>();
    public static int currentFragmentPositionIncomplaint = 0;
    public static int curentFragmentPositionInSuggesion = 0;
    public static List<Development> arrDevelopment = new ArrayList<>();
    public static List<String> arrSliderImages = new ArrayList<>();
    public static List<Department> arrDepartment = new ArrayList<>();
    public static List<Ambulance> arrAmbulance = new ArrayList<>();
    public static List<PolisStation> arrPoliceStation = new ArrayList<>();
    public static List<Hospital> arrHospital = new ArrayList<>();
    public static List<BloodBank> arrBloodBank = new ArrayList<>();
    public static List<FireBrigade> arrFireBrigade = new ArrayList<>();
    public static List<EmergencyContact> arrEmergencyContact = new ArrayList<>();
    public static String contactType = "", userEmail = "", userName = "", userPhoneNumber = "", userBloodGrp = "";
    public static String isLocationTurned = "";
    public static boolean isLocationEnabled = false;
    public static String dataFormat = "utf-8";
    public static List<String> arrSlider = new ArrayList<>();

}

package com.nineleaps.greytHRClone.common;

import org.springframework.stereotype.Service;


@Service
public class Constants {
    public static final String FIREBASE_SDK_JSON ="firebase-sdk.json";
    public static final String FIREBASE_BUCKET = "greythrclone-291017.appspot.com";
    public static final String FIREBASE_PROJECT_ID ="greythrclone-291017";

    public static final String FIREBASE_URL_PREFIX ="https://firebasestorage.googleapis.com/v0/b/greythrclone-291017.appspot.com/o/";
    public static final String FIREBASE_URL_SUFFIX ="?alt=media";

    public static final int RANDOM_MIN = 1;
    public static final int RANDOM_MAX = 10;

    public static final String IMG_REGEX = "([^\\s]+(\\/(?i)(jpe?g|png|gif|bmp))$)";


    //authorisation token
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "greythrclone123";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";

    //salary policy lookup
    public static final double BASIC = 0.4836;
    public static final double HRA= 0.1934;
    public static final double SPECIAL_ALLOWANCE= 0.3228;
    public static final double PF= 0.05159;
    public static final double PROF_TAX= 0.00573;
    public static final double GROUP_MEDICAL_DEDUCTION= 0.00750;
    public static final double GROUP_POLICY_ACCIDENT_DEDUCTION= 0.00045;

}

package com.example.sample.utilities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.ShareCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * ConstantValues contains some constant variables, used all over the App.
 **/


 public  class ConstantValues {

    public static boolean IS_USER_LOGGED_IN_ADMIN;



    /*   Validating Fileds */
    // Validating email id
    public static boolean isValidEmail(String email1) {

        String EMAIL_PATTERN = "^([_A-Za-z0-9-+].{2,})+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";



        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email1);
        return !matcher.matches();

    }


    // Validating pincode
    public static  boolean isValidPincode(String pass1) {

        return pass1 == null || pass1.length() != 6;
    }

    // Validating password
    public static  boolean isValidPassword1(String pass1) {

        return pass1 == null || pass1.length() <= 5;
    }


    // validating password with retype password

    public static boolean isValidPassword(String pass1) {

        String PASSWORD_PATTERN =
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(pass1);
        return !matcher.matches();
    }


    //Validating Address
    public static boolean validAddress(String pass1) {

        String pat="^[a-zA-Z0-9]+([-/:\\s,][a-zA-Z0-9]+)*?$";
        return !pass1.matches(pat) || pass1.length() <= 2;

    }
    public static  boolean validSchool(String pass1) {

        return !pass1.matches("^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*?$") || pass1.length() <= 2;

    }

    //Validtaing Names
    public static boolean validateFirstName( String firstName )
    {
        return !firstName.matches( "^[a-zA-Z]+(\\s[a-zA-Z]+)*?$" );

    }

    //Validating Mobile
      public  static boolean isValidMoblie(String pass1) {

        return pass1 == null || pass1.length() != 10;

    }
    public  static boolean isValidOTP(String pass1) {

        return pass1 == null || pass1.length() != 6;

    }

    private static boolean isStringValid(String str) {
        return str != null && !str.isEmpty() && str.trim().length() > 0;
    }







}

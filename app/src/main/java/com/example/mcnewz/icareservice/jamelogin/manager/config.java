package com.example.mcnewz.icareservice.jamelogin.manager;

/**
 * Created by JAME on 14-Jan-17.
 */

public class config {
        //URL to our login.php file
        public static final String LOGIN_URL = "http://icareuserver.comscisau.com/icare/androidTest/login.php";

        public static final String CHECKFACEBOOK_URL = "http://icareuserver.comscisau.com/icare/androidTest/checkFacebook.php";

        //Keys for email and password as defined in our $_POST['key'] in login.php
        public static final String LOGIN_USERNAME = "username";
        public static final String LOGIN_PASSWORD = "password";
        public static final String KEY_IDFACE = "idFace";

        //If server response is equal to this that means login is successful
        public static final String LOGIN_SUCCESS = "success";


        //Keys for Sharedpreferences
        //This would be the name of our shared preferences
        public static final String SHARED_PREF_NAME = "myloginapp";

        //This would be used to store the email of current logged in user
        public static final String USERNAME_SHARED_PREF = "username";
        public static final String FiresName_SHARED_PREF = "firstname";
        public static final String LastName_SHARED_PREF = "lastname";
        public static final String Email_SHARED_PREF = "email";
        public static final String Photo_SHARED_PREF = "photo";

        //We will use this to store the boolean in sharedpreference to track user is loggedin or not
        public static final String LOGGEDIN_SHARED_PREF = "loggedin";

        //----------------------Register-------------------------------------------------
        //URL to our register.php file
        public static final String REGIS_FACEBOOK = "http://icareuserver.comscisau.com/icare/androidTest/insertFacebook.php";
        public static final String REGIS_URL = "http://icareuserver.comscisau.com/icare/androidTest/insertRegister.php";
        public static final String INSERT_FIRSTNAME = "firstname";
        public static final String INSERT_LASTNAME = "lastname";
        public static final String INSERT_USERNAME = "username";
        public static final String INSERT_PASSWORD = "password";
        public static final String INSERT_EMAIL = "email";
        public static final String INSERT_TEL = "tel";
        public static final String INSERT_DATA = "timedata";
        public static final String INSERT_IDFACE = "idFace";
        public static final String INSERT_TOKEN = "token";
        //----------------------------------------ReadData*------------------------------------
        public static final String URL_DATA = "http://icareuserver.comscisau.com/icare/androidTest/ReadData.php";
        public static final String USERNAME_SHARED = "user";
        public static final String READ_FIRSTNAME = "firstname";
        public static final String READ_LASTNAME = "lastname";
        public static final String READ_EMAIL = "email";
        public static final String READ_USERNAME = "username";
        public static final String READ_TEL = "tel";
        public static final String JSON_ARRAY = "result";
        //-------------------------------CodeVerify-------------------------------------------------------
        public static final String URL_CODEVERIFY = "http://icareuserver.comscisau.com/icare/androidTest/insrtCodeVerify.php";
        public static final String TOKEN_URL = "http://icareuserver.comscisau.com/icare/androidTest/updateToken.php";

        public static String token = "tokrn";
        public static final String URL_UPdatemember = "http://icareuserver.comscisau.com/icare/androidTest/UpdateMember.php";
        public static int status = 1;
        public static int status_verify = 1;
        public static int id = 1;
        public static String idcode = "7777";

        public static String idUserUpdate = "id";
        public static String PhotoUserUpdate = "photo";

}

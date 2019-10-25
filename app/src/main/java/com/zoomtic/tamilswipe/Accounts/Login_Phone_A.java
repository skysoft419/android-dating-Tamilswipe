package com.zoomtic.tamilswipe.Accounts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.ybs.countrypicker.Country;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;
import com.zoomtic.tamilswipe.CodeClasses.Variables;
import com.zoomtic.tamilswipe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Login_Phone_A extends AppCompatActivity {

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private FirebaseAuth fbAuth;
    EditText phoneText,digit1,digit2,digit3,digit4,digit5,digit6;
    TextView select_country,countrycodetxt,sendtotxt;
    ViewFlipper viewFlipper;
    String phoneNumber;
    public IOSDialog iosDialog;
    Button signButton;
    SharedPreferences sharedPreferences;

    public static final String BUNDLE_SIGN = "sign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_login_phone);
        fbAuth = FirebaseAuth.getInstance();

        sharedPreferences=getSharedPreferences(Variables.pref_name,MODE_PRIVATE);
        select_country = findViewById(R.id.select_country);
        countrycodetxt = findViewById(R.id.countrycodetxt);
        select_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Opencountry();
            }
        });
        phoneText = findViewById(R.id.phoneText);
        signButton = findViewById(R.id.sign_btn);
        signButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Nextbtn(view);
            }
        });
        viewFlipper=findViewById(R.id.viewfillper);
        sendtotxt=findViewById(R.id.sendtotxt);

        codefill();

        iosDialog = new IOSDialog.Builder(this)
                .setCancelable(false)
                .setSpinnerClockwise(false)
                .setMessageContentGravity(Gravity.END)
                .build();

        TextView signupTermsTextView = findViewById(R.id.textView_terms_signup);
        signupTermsTextView.setMovementMethod(LinkMovementMethod.getInstance());

        TextView signWithFacebookTextView = findViewById(R.id.textView_sign_facebook);
        signWithFacebookTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Login();
                Sign_in_with_gmail();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.getString(Login_Phone_A.BUNDLE_SIGN).equals("signin")){
            signButton.setText("Sign In");
            signupTermsTextView.setVisibility(View.GONE);
            signWithFacebookTextView.setText("Sign in with Facebook");
        }else{
            signButton.setText("Sign Up");
            signupTermsTextView.setVisibility(View.VISIBLE);
            signWithFacebookTextView.setText("Sign up with Facebook");
        }
    }

    //message code fill in edittext and change focus in android
    public void codefill(){
        digit1=findViewById(R.id.digitone);
        digit2=findViewById(R.id.digittwo);
        digit3=findViewById(R.id.digitthree);
        digit4=findViewById(R.id.digitfour);
        digit5=findViewById(R.id.digitfive);
        digit6=findViewById(R.id.digitsix);

        digit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(digit1.getText().toString().length()==0){
                    digit2.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        digit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(digit2.getText().toString().length()==0){
                    digit3.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        digit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(digit3.getText().toString().length()==0){
                    digit4.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        digit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(digit4.getText().toString().length()==0){
                    digit5.requestFocus();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        digit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(digit5.getText().toString().length()==0){
                    digit6.requestFocus();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    String country_iso_code="US";
    public void Opencountry(){
        final CountryPicker picker = CountryPicker.newInstance("Select Country");
        List<Country> countryArrayList = Country.getAllCountries();
        Collections.sort(countryArrayList, new Comparator<Country>() {
            @Override
            public int compare(Country country1, Country country2) {
                return country1.getName().trim().compareToIgnoreCase(country2.getName().trim());
            }
        });
        picker.setCountriesList(countryArrayList);
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                // Implement your code here
                select_country.setText(name);
                countrycodetxt.setText(dialCode);
                picker.dismiss();
                country_iso_code=code;
            }
        });
        picker.setStyle(R.style.countrypicker_style,R.style.countrypicker_style);
        picker.show(getSupportFragmentManager(), "Select Country");
    }

    public void Nextbtn(View view) {
        if(select_country.getText().equals("Country")){
            Toast.makeText(Login_Phone_A.this, "Select Country.", Toast.LENGTH_SHORT).show();
        }else {
            phoneNumber=countrycodetxt.getText().toString()+phoneText.getText().toString();
            Send_Number_tofirebase(phoneNumber);
        }
    }


    public void Send_Number_tofirebase(String phoneNumber){
        setUpVerificatonCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);
    }

    private void setUpVerificatonCallbacks() {
        iosDialog.show();
        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {

                        iosDialog.cancel();
                        signInWithPhoneAuthCredential(credential);

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        iosDialog.cancel();
                        Log.d("responce",e.toString());
                        Toast.makeText(Login_Phone_A.this, "Enter Correct Number.", Toast.LENGTH_SHORT).show();
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {

                        iosDialog.cancel();

                        phoneVerificationId = verificationId;
                        resendToken = token;
                        sendtotxt.setText(phoneNumber);
                        iosDialog.cancel();
                        viewFlipper.setInAnimation(Login_Phone_A.this, R.anim.in_from_right);
                        viewFlipper.setOutAnimation(Login_Phone_A.this, R.anim.out_to_left);
                        viewFlipper.setDisplayedChild(1);

                    }
                };
    }


    public void verifyCode(View view) {
        String code=""+digit1.getText().toString()+digit2.getText().toString()+digit3.getText().toString()+digit4.getText().toString()+digit5.getText().toString()+digit6.getText().toString();
        if(!code.equals("")){
            iosDialog.show();
            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        }else {
            Toast.makeText(this, "Enter the Correct varification Code", Toast.LENGTH_SHORT).show();
        }

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // get the user info to know that user is already login or not
                            Get_User_info();

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                iosDialog.cancel();
                            }
                        }
                    }
                });
    }


    public void resendCode(View view) {

        String phoneNumber = phoneText.getText().toString();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }


    public void Goback_1(View view) {
        finish();
    }

    public void Goback(View view) {
        viewFlipper.setInAnimation(Login_Phone_A.this, R.anim.in_from_left);
        viewFlipper.setOutAnimation(Login_Phone_A.this, R.anim.out_to_right);
        viewFlipper.setDisplayedChild(0);
    }


    private void Get_User_info() {
        iosDialog.show();
        final String phone_no=phoneNumber.replace("+","");
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("fb_id", phone_no);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Variables.getUserInfo, parameters, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d("responce",respo);

                        iosDialog.cancel();
                        try {
                            JSONObject jsonObject=new JSONObject(respo);
                            String code=jsonObject.optString("code");
                            if(code.equals("200")){

                                // if user is already logedin then we will save the user data and go to the enable location screen
                                JSONArray msg=jsonObject.getJSONArray("msg");
                                JSONObject userdata=msg.getJSONObject(0);

                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString(Variables.uid,phone_no);
                                editor.putString(Variables.f_name,userdata.optString("first_name"));
                                editor.putString(Variables.l_name,userdata.optString("last_name"));
                                editor.putString(Variables.birth_day,userdata.optString("age"));
                                editor.putString(Variables.gender,userdata.optString("gender"));
                                editor.putString(Variables.country_name,userdata.optString("country"));
                                editor.putString(Variables.country_code,userdata.optString("country_code"));
                                editor.putString(Variables.u_pic,userdata.optString("image1"));
                                editor.putBoolean(Variables.islogin,true);
                                editor.commit();

                                // after all things done we will move the user to enable location screen
                                enable_location();


                            }else {
                                // if user is first time login then we will get the usser picture and name
                                Intent intent=new Intent(Login_Phone_A.this, Get_User_Info_A.class);
                                intent.putExtra("id",phone_no);
                                startActivity(intent);
                                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                                finish();

                            }

                        }catch (JSONException e) {

                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("respo",error.toString());
                        iosDialog.cancel();
                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        rq.getCache().clear();

        rq.add(jsonObjectRequest);
    }


    private void enable_location() {
        // will move the user for enable location screen
        Enable_location_F enable_location_f = new Enable_location_F();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left,R.anim.in_from_left,R.anim.out_to_right);
        getSupportFragmentManager().popBackStackImmediate();
        transaction.replace(R.id.Login_Phone_A, enable_location_f).addToBackStack(null).commit();
    }

    public void Login() {

        /*
         if have an permision from facebook developer console to get the user_brithday
         and user_gender then you will use the below line
        */


       /*
        LoginManager.getInstance().
         logInWithReadPermissions(Login_A.this,
                 Arrays.asList("public_profile","email","user_birthday","user_gender"));

        */

        /*othetwise you will use the below line*/


        LoginManager.getInstance()
                .logInWithReadPermissions(Login_Phone_A.this,
                        Arrays.asList("public_profile","email"));

        Loginwith_FB();
    }

    private CallbackManager mCallbackManager;
    //facebook implimentation
    public void Loginwith_FB(){

        // initialze the facebook sdk and request to facebook for login
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>()  {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(Login_Phone_A.this, "Login Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("resp",""+error.toString());
                Toast.makeText(Login_Phone_A.this, "Login Error"+error.toString(), Toast.LENGTH_SHORT).show();
            }

        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        if(requestCode==123){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else if(mCallbackManager!=null)
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

//    String countryName, contryCode, city;
//    private void getLocationUser(String id) {
//        Bundle params = new Bundle();
//        params.putString("location", "id");
//        new GraphRequest(
//                AccessToken.getCurrentAccessToken(),
//                id+"/?fields=location",
//                params,
//                HttpMethod.GET,
//                new GraphRequest.Callback() {
//                    public void onCompleted(GraphResponse response) {
//                        Log.e("Response 2", response + "");
//                        try {
//                            countryName = (String) response.getJSONObject().getJSONObject("location").get("country");
//                            city = (String) response.getJSONObject().getJSONObject("location").get("city");
//
//                            Log.e("Location", countryName);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        ).executeAsync();
//    }

    private void handleFacebookAccessToken(final AccessToken token) {
        // if user is login then this method will call and
        // facebook will return us a token which will user for get the info of user
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            iosDialog.show();
                            final FirebaseUser user_firebase = fbAuth.getCurrentUser();
                            final String firebase_userid=user_firebase.getUid();
                            final String id = Profile.getCurrentProfile().getId();
                            GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject user, GraphResponse graphResponse) {

                                    Log.d("resp",user.toString());
                                    //after get the info of user we will pass to function which will store the info in our server
                                    Call_Api_For_Signup(""+id,""+user.optString("first_name")
                                            ,""+user.optString("last_name"),""+user.optString("birthday")
                                            ,""+user.optString("gender"),
                                            "https://graph.facebook.com/"+id+"/picture?width=500&width=500");

                                }
                            });

                            // here is the request to facebook sdk for which type of info we have required
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "last_name,first_name,email,birthday,gender");
                            request.setParameters(parameters);
                            request.executeAsync();
                        } else {

                            Toast.makeText(Login_Phone_A.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }




    //google Implimentation
    GoogleSignInClient mGoogleSignInClient;
    public void Sign_in_with_gmail(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(Login_Phone_A.this);
        if (account != null) {
            String id=account.getId();
            String f_name=account.getGivenName();
            String l_name=account.getFamilyName();

            if(account.getPhotoUrl()!=null) {
                String pic_url = account.getPhotoUrl().toString();
                Call_Api_For_Signup(id,f_name,l_name,"","" ,pic_url);
            }
            else {
                Get_User_info(id,f_name,l_name);
            }

        }
        else {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 123);
        }

    }

    //Relate to google login
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String id=account.getId();
                String f_name=account.getGivenName();
                String l_name=account.getFamilyName();

                // if we do not get the picture of user then we will use default profile picture

                if(account.getPhotoUrl()!=null) {
                    String pic_url = account.getPhotoUrl().toString();
                    Call_Api_For_Signup(id,f_name, l_name,"","" ,pic_url);
                }else {

                    Get_User_info(id,f_name,l_name);
                }


            }
        } catch (ApiException e) {
            Log.w("Error message", "signInResult:failed code=" + e.getStatusCode());
        }

    }



    // this method will store the info of user to  database
    private void Call_Api_For_Signup(String user_id,
                                     String f_name,String l_name,
                                     String birthday,String gender,String picture) {

        iosDialog.show();

        f_name=f_name.replaceAll("\\W+","");
        l_name=l_name.replaceAll("\\W+","");

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("fb_id", user_id);
            parameters.put("first_name",f_name);
            parameters.put("last_name", l_name);
            parameters.put("birthday", birthday);
            parameters.put("gender", gender);
            parameters.put("image1",picture);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("resp",parameters.toString());

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Variables.SignUp, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d("responce",respo);
                        iosDialog.cancel();
                        Parse_signup_data(respo);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        iosDialog.cancel();
                        Toast.makeText(Login_Phone_A.this, "Something wrong with Api", Toast.LENGTH_SHORT).show();
                        Log.d("respo",error.toString());
                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.getCache().clear();
        rq.add(jsonObjectRequest);
    }



    private void Get_User_info(final String user_id,final String f_name,final String l_name) {
        iosDialog.show();
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("fb_id", user_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Variables.getUserInfo, parameters, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d("responce",respo);

                        iosDialog.cancel();
                        try {
                            JSONObject jsonObject=new JSONObject(respo);
                            String code=jsonObject.optString("code");
                            if(code.equals("200")){

                                // if user is already logedin then we will save the user data and go to the enable location screen
                                JSONArray msg=jsonObject.getJSONArray("msg");
                                JSONObject userdata=msg.getJSONObject(0);

                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString(Variables.uid,user_id);
                                editor.putString(Variables.f_name,userdata.optString("first_name"));
                                editor.putString(Variables.l_name,userdata.optString("last_name"));
                                editor.putString(Variables.birth_day,userdata.optString("age"));
                                editor.putString(Variables.birth_day,userdata.optString("age"));
                                editor.putString(Variables.country_name,userdata.optString("country"));
                                editor.putString(Variables.country_code,userdata.optString("country_code"));
                                editor.putString(Variables.u_pic,userdata.optString("image1"));
                                editor.putString(Variables.created_time,userdata.optString("created"));
                                List<Country> allCountries = Country.getAllCountries();
                                String str = "";
                                for(int i=0;i<allCountries.size();i++){
                                    str += allCountries.get(i).getCode() + "_";
                                }
                                editor.putString(Variables.country_from,str);
                                editor.putBoolean(Variables.islogin,true);
                                editor.commit();

                                // after all things done we will move the user to enable location screen
                                enable_location();


                            }else {
                                // if user is first time login then we will get the usser picture and name
                                Intent intent=new Intent(Login_Phone_A.this, Get_User_Info_A.class);
                                intent.putExtra("id",user_id);
                                intent.putExtra("fname",f_name);
                                intent.putExtra("lname",l_name);
                                startActivity(intent);
                                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                                finish();

                            }

                        }catch (JSONException e) {

                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("respo",error.toString());
                        iosDialog.cancel();
                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        rq.getCache().clear();

        rq.add(jsonObjectRequest);
    }



    // if the signup successfull then this method will call and it store the user info in local
    public void Parse_signup_data(String loginData){
        try {
            JSONObject jsonObject=new JSONObject(loginData);
            String code=jsonObject.optString("code");
            if(code.equals("200")){
                JSONArray jsonArray=jsonObject.getJSONArray("msg");
                JSONObject userdata = jsonArray.getJSONObject(0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(Variables.uid,userdata.optString("fb_id"));
                editor.putString(Variables.f_name,userdata.optString("first_name"));
                editor.putString(Variables.l_name,userdata.optString("last_name"));
                editor.putString(Variables.birth_day,userdata.optString("age"));
                editor.putString(Variables.gender,userdata.optString("gender"));
                editor.putString(Variables.country_name,userdata.optString("country_name"));
                editor.putString(Variables.country_code,userdata.optString("country_code"));
                editor.putString(Variables.u_pic,userdata.optString("image1"));
                editor.putString(Variables.created_time,userdata.optString("created"));
                editor.putBoolean(Variables.islogin,true);
                editor.commit();

                // after all things done we will move the user to enable location screen
                enable_location();
            }else {
                Toast.makeText(this, ""+jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            iosDialog.cancel();
            e.printStackTrace();
        }

    }
}

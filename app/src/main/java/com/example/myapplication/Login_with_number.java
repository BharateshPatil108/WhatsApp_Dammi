package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Login_with_number extends AppCompatActivity {

    TextView textView, textView1, textView2, textView_time, textView_title, text_help, textView3;
    EditText editText1, editText2, editText;
    Button nextButton, verifyBtn;
    TextInputLayout inputLayout;
    AutoCompleteTextView autoCompleteTextView;

    private String verifiyCode;
    private String verificationCode;
    private FirebaseAuth auth;
    CountDownTimer countDownTimer;
    String Phone_number;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    HashMap<String, Integer> countryCodeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_number);

        Toolbar customToolbar = findViewById(R.id.tool_login_num);
        setSupportActionBar(customToolbar);

        textView1 = findViewById(R.id.login_info);
        textView2 = findViewById(R.id.text_num);
        textView = findViewById(R.id.login_info);
        textView_title = findViewById(R.id.number_tool);
        autoCompleteTextView = findViewById(R.id.textInput);
        nextButton = findViewById(R.id.btn_next);
        editText1 = findViewById(R.id.country_code_edt);
        editText2 = findViewById(R.id.number_edit);
        inputLayout = findViewById(R.id.input);

        editText = findViewById(R.id.edit_code);
        textView_time = findViewById(R.id.text_for_time);
        textView3 = findViewById(R.id.data_text);
        verifyBtn = findViewById(R.id.btn_code_verify);
        text_help = findViewById(R.id.data_for_help);

        editText.setVisibility(View.INVISIBLE);
        textView_time.setVisibility(View.INVISIBLE);
        textView3.setVisibility(View.INVISIBLE);
        verifyBtn.setVisibility(View.INVISIBLE);
        text_help.setVisibility(View.INVISIBLE);

        editText1.setFocusable(false);
        editText1.setFocusableInTouchMode(false);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        String Text = "Enter your mobile number";
        SpannableString spannableString = new SpannableString(Text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.argb(255, 71, 139, 125));
        spannableString.setSpan(colorSpan, 0, Text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView_title.setText(spannableString);

        // Show status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green));
        }

        // Dropdown added
        countryCodeMap.put("India", +91);
        countryCodeMap.put("France", +33);
        countryCodeMap.put("Saudi Arabia", +966);
        countryCodeMap.put("Australia", +61);
        countryCodeMap.put("America", +1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(countryCodeMap.keySet()));
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = parent.getItemAtPosition(position).toString();
                String countryCode = String.valueOf(countryCodeMap.get(selectedCountry));
                editText1.setText("+" + countryCode);
            }
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    editText1.setText("+");
                }
            }
        });

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty() || s.toString() == "+") {
                    autoCompleteTextView.setText("");
                }
            }
        });

        // Set text color
        String fullText = textView.getText().toString();
        SpannableString spanString = new SpannableString(fullText);
        int startIndex = 41;
        int endIndex = 58;
        ForegroundColorSpan colorSpanable = new ForegroundColorSpan(Color.argb(255, 99, 156, 201));
        spanString.setSpan(colorSpanable, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(spanString);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(autoCompleteTextView.getText().toString().isEmpty() || autoCompleteTextView.getText().toString().equals(null) ||
                        autoCompleteTextView.getText().toString().equals("choose a country") || editText2.getText().toString().isEmpty() ||
                        editText1.getText().toString().equals("+")) && editText2.getText().length() == 10) {
                    alertdialogs();
                } else {
                    Toast.makeText(Login_with_number.this, "Please enter required fields correctly..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = verifyBtn.getText().toString();
                if (buttonText.equals("Verify")) {
                    verifiyCode = editText.getText().toString();
                    if (!(verifiyCode == null || verifiyCode.isEmpty())) {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(
                                verificationCode,
                                verifiyCode
                        );

                        signInWithPhoneNumber(credential);
                    } else {
                        Toast.makeText(Login_with_number.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    countDownTimer.cancel();
                    //send otp using firebase
                    sendOtp();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            Phone_number,
                            60,
                            TimeUnit.SECONDS,
                            Login_with_number.this,
                            callbacks
                    );
                    verifyBtn.setText("Verify");
                }
            }
        });
    }

    private void alertdialogs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login_with_number.this);
        builder.setMessage(Html.fromHtml("Your entered the phone number :" + "<br><br><b><h3>" +
                editText1.getText() + " " + editText2.getText() + "</h3></b><br>" +
                "Is this OK ,or would you like to edit the number?"));
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Phone_number = editText1.getText().toString() + editText2.getText().toString();
                //send otp using firebase
                sendOtp();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        Phone_number,
                        60,
                        TimeUnit.SECONDS,
                        Login_with_number.this,
                        callbacks
                );

                editText.setVisibility(View.VISIBLE);
                textView_time.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
                verifyBtn.setVisibility(View.VISIBLE);
                text_help.setVisibility(View.VISIBLE);

                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                inputLayout.setVisibility(View.GONE);
                autoCompleteTextView.setVisibility(View.GONE);
                nextButton.setVisibility(View.GONE);
                editText1.setVisibility(View.GONE);
                editText2.setVisibility(View.GONE);
                nextButton.setVisibility(View.GONE);

                textView_title.setText("Verify " + Phone_number);
            }
        });
        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void sendOtp() {

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            auth.signOut();
        }

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(Login_with_number.this, "Verification successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                countDownTimer.cancel();
                String s = getResources().getString(R.string.info_for_time);
                textView_time.setText(Html.fromHtml("<div>" + s + " <b>" + "00:00" + "</b>" + "</div>"));
                verifyBtn.setText("ReSend-OTP");
                Log.e("TAG", "onVerificationFailed: " + e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(Login_with_number.this, "FirebaseAuthInvalidCredentialsException", Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(Login_with_number.this, "FirebaseTooManyRequestsException", Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException) {
                    // reCAPTCHA verification attempted with null Activity
                    Toast.makeText(Login_with_number.this, "third  reCAPTCHA verification", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(Login_with_number.this, "Verification Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.e("TAG", "onCodeSent: " + s);
                verificationCode = s;
                Toast.makeText(Login_with_number.this, "OTP Code sent", Toast.LENGTH_SHORT).show();
            }
        };
        //set time to 1 min
        setTimer(60 * 1000, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.number_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item:
                Uri webpage = Uri.parse("https://web.whatsapp.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
                return true;
            case R.id.menu_item1:
                Toast.makeText(this, "Setting item clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signInWithPhoneNumber(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult().getUser();
                    startActivity(new Intent(Login_with_number.this, UserActivity.class));
                    Toast.makeText(Login_with_number.this, "SignIn completed", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Login_with_number.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void setTimer(final long finish, long tick) {

        countDownTimer = new CountDownTimer(finish, tick) {
            @Override
            public void onTick(long l) {
                int secondsRemaining = (int) (l / 1000);
                String s = getResources().getString(R.string.info_for_time);
                String timeRemaining = String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60);
                textView_time.setText(Html.fromHtml("<div>" + s + " <b>" + timeRemaining + "</b>" + "</div>"));
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
            }
        }.start();

    }
}

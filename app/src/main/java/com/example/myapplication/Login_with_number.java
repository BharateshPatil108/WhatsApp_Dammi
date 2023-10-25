package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class Login_with_number extends AppCompatActivity {

    TextView textView,textView1,textView2;
    EditText editText1 , editText2;
    Button nextButton;
    AutoCompleteTextView autoCompleteTextView;
    HashMap<String, Integer> countryCodeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_number);

        Toolbar customToolbar = findViewById(R.id.tool_login_num);
        setSupportActionBar(customToolbar);

        textView1 = findViewById(R.id.login_info);
        textView2 = findViewById(R.id.text_num);
        textView = findViewById(R.id.number_tool);
        autoCompleteTextView = findViewById(R.id.textInput);
        nextButton = findViewById(R.id.btn_next);
        editText1 = findViewById(R.id.country_code_edt);
        editText2 = findViewById(R.id.number_edit);

        editText1.setFocusable(false);
        editText1.setFocusableInTouchMode(false);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        String Text = "Enter your mobile number";
        SpannableString spannableString = new SpannableString(Text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.argb(255, 71,139,125));
        spannableString.setSpan(colorSpan, 0, Text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(spannableString);

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
                editText1.setText("+"+countryCode);
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
        textView = findViewById(R.id.login_info);
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
                }else {
                    Toast.makeText(Login_with_number.this, "Please enter required fields correctly..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void alertdialogs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login_with_number.this);
        builder.setMessage(Html.fromHtml("Your entered the phone number :" +"<br><br><b><h3>"+
                editText1.getText()+" "+editText2.getText()+"</h3></b><br>"+
                "Is this OK ,or would you like to edit the number?"));
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Login_with_number.this, com.example.myapplication.VerificationActivity.class);
                intent.putExtra("data",editText1.getText().toString()+" "+editText2.getText().toString());
                startActivity(intent);
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
                // Handle the Menu Item click
                return true;
            case R.id.menu_item1:
                // Handle the Menu Item click
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

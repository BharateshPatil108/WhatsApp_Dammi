package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView textView1,textView2;
    Button button;
    ImageView imageView;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adding custom toolbar
        Toolbar cust_toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(cust_toolbar);

        textView1 = findViewById(R.id.text);
        textView2 = findViewById(R.id.policy);
        button = findViewById(R.id.btn_accept);
        imageView = findViewById(R.id.image);
        autoCompleteTextView = findViewById(R.id.lang);

        //showing status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green));
        }

        //Hide the toolbar appname text
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        // translateText();

        // text color change
        textColorChange();

        //dropdown added
        String[] lang = getResources().getStringArray(R.array.lang);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lang);
        autoCompleteTextView.setAdapter(adapter);

        for (int i = 0;i < lang.length;i++) {
            String selectedLang = adapter.getItem(i);
            Log.e("langauge", selectedLang);
        }

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                languageSelection(i);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(autoCompleteTextView.getText().toString().isEmpty() ||
                        autoCompleteTextView.getText().toString().equals(null) ||
                        autoCompleteTextView.getText().toString().equals("Select language"))) {
                    startActivity(new Intent(MainActivity.this, Login_with_number.class));
                }else {
                    Toast.makeText(MainActivity.this, "Please select a language", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    public void translateText() {
//        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
//                .setSourceLanguage(FirebaseTranslateLanguage.EN)
//                .setTargetLanguage(FirebaseTranslateLanguage.HI)
//                .build();
//
//        FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance().getTranslator(options);
//        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().build();
//
//        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                translator.translate("hello").addOnSuccessListener(new OnSuccessListener<String>() {
//                    @Override
//                    public void onSuccess(String s) {
//                        Log.d("TAG", " bharatesh" +s);
//                    }
//                });
//            }
//        });
//   }

//    public static void translateStrings(Context context, String targetLanguage) {
//        Resources resources = context.getResources();
//        String[] stringKeys = resources.getStringArray(R.array.dataToTranslate); // Define an array of string keys to translate
//
//        for (String stringKey : stringKeys) {
//            int resourceId = resources.getIdentifier(stringKey, "string", context.getPackageName());
//            String originalText = resources.getString(resourceId);
//            String translatedText = translateText(originalText, targetLanguage);
//
//            // TODO: Store or use translatedText as needed
//            // For example, you can update the strings in your app dynamically
//        }
//    }


    private void textColorChange() {
        String fullText = textView2.getText().toString();
        SpannableString spannableString = new SpannableString(fullText);
        int startIndex = 9;
        int endIndex = 23;
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.argb(255,96,139,255));
        spannableString.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView2.setText(spannableString);

        int start = 62;
        int end = 80;
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.argb(255,96,139,255));
        spannableString.setSpan(colorSpan1, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView2.setText(spannableString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // this method for showing item from menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // after showing menu items what action should happen
        int id = item.getItemId();

        if (id == R.id.menu_item) {
            //TODO
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void languageSelection(int j) {
        String selectLang = adapter.getItem(j);
        Log.e("TAG", " Lang selected : "+selectLang);
        switch (selectLang){
            case "English":
                setLocale("en");
                recreate();
                break;
            case "Kannada":
                setLocale("kn");
                recreate();
                break;
            case "Hindi":
                setLocale("hi");
                recreate();
                break;
            case "Arabic":
                setLocale("ar");
                recreate();
                break;
            case "French":
                setLocale("fr");
                recreate();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }


    @Override
    public void recreate() {
        super.recreate();
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);

        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class VerificationActivity extends AppCompatActivity {

    TextView textView_time,textView_title;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        //adding custom toolbar
        Toolbar customToolbar = findViewById(R.id.tool_login_num);
        setSupportActionBar(customToolbar);

        textView_time = findViewById(R.id.text_for_time);
        editText = findViewById(R.id.edit_code);
        textView_title = findViewById(R.id.number_tool);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        Intent intent = getIntent();
        String Text = "Verify "+intent.getStringExtra("data");
        SpannableString spannableString = new SpannableString(Text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.argb(255, 71,139,125));
        spannableString.setSpan(colorSpan, 0, Text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView_title.setText(spannableString);

        //showing status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green));
        }
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
            Toast.makeText(this, "help item clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
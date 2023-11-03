package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.vanniktech.emoji.EmojiPopup;

public class UserActivity extends AppCompatActivity {

    TextView textView, textView_1 ,textView_2;
    EditText editText;
    ImageView imageView , emojiView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        textView = findViewById(R.id.charCountTextView);
        textView_1 = findViewById(R.id.User_info);
        textView_2 = findViewById(R.id.profile_info);
        editText = findViewById(R.id.profile_name);
        imageView = findViewById(R.id.profile_img);
        emojiView = findViewById(R.id.profile_emoji);
        button = findViewById(R.id.Nxt_btn);

        Toolbar customToolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(customToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        //showing status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green));
        }

        //  Emoji setup
         EmojiPopup popup = EmojiPopup.Builder
                 .fromRootView(findViewById(R.id.rootView))
                 .build(editText);

        emojiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.toggle();
            }
        });

        //  Reverse count of length of given text
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed in this example
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed in this example
            }

            @Override
            public void afterTextChanged(Editable s) {
                int remainingChars = 25 - s.length();
                textView.setText(String.valueOf(remainingChars));
            }
        });

    }

}
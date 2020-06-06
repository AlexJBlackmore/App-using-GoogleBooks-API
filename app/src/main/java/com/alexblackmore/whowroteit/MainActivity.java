package com.alexblackmore.whowroteit;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    TextView authorResultTV;
    TextView titleResultTV;
    EditText inputET;
    Button searchBtn;
    View.OnClickListener searchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authorResultTV = findViewById(R.id.author_resultTV_wdg);
        titleResultTV = findViewById(R.id.title_resultTV_wdg);
        inputET = findViewById(R.id.user_inputET_wdg);
        searchBtn = findViewById(R.id.search_API_Btn_wdg);

        searchListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mQuery = inputET.getText().toString();

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }

                authorResultTV.setText("Loading...");
                titleResultTV.setText("Loading...");

                FetchBook mFetchBook = new FetchBook(authorResultTV, titleResultTV);
                mFetchBook.execute(mQuery);
            }
        };

        searchBtn.setOnClickListener(searchListener);
    }
}

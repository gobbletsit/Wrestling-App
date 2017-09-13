package com.example.android.wrestlingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button begin;

    private EditText nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // USING PREFERENCE MANAGER TO WRITE TO THE FILE
        final SharedPreferences my_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // CLEARING ALL PREVIOSLY STORED SHARED PREFERENCE VALUES
        SharedPreferences.Editor editorKoji = my_preferences.edit();
        editorKoji.clear();
        editorKoji.apply();

        // FINDING THE EDIT TEXT VIEW SO WE CAN EXTRACT THE STRING INPUT
        nameInput = (EditText) findViewById(R.id.name_edit_text);

        // STARTING THE ACTIVITY AND STORING NAME VALUE
        begin = (Button)findViewById(R.id.begin);
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor sharedPrefEditorStart = my_preferences.edit();
                if (nameInput.getText() == null) {
                    sharedPrefEditorStart.putString("name_value", "");
                } else {
                    sharedPrefEditorStart.putString("name_value", nameInput.getText().toString());
                }
                sharedPrefEditorStart.apply();
                Intent begin = new Intent(MainActivity.this, ScoreKeeper.class);
                MainActivity.this.startActivity(begin);
            }
        });
    }
}


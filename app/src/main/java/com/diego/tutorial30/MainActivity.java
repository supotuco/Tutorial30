package com.diego.tutorial30;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    final int BASE_TEXT_SIZE = 12;
    EditText message;
    SeekBar seekBar;
    float font_size;
    String font_color;
    String text_message;
    int bar_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioButton[] colorButtons = new RadioButton[3];

        colorButtons[0] = (RadioButton) findViewById(R.id.radio_button_red);
        colorButtons[1] = (RadioButton) findViewById(R.id.radio_button_blue);
        colorButtons[2] = (RadioButton) findViewById(R.id.radio_button_green);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(int i = 0; i < 3; i = i + 1){
                colorButtons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeColor(v);
                    }
                });
            }
        }else{
            for(int i = 0; i < 3; i = i + 1){
                colorButtons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeColorOld(v);
                    }
                });
            }
        }

        message = (EditText) findViewById(R.id.edit_text_message);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        SharedPreferences preferences = MainActivity.this.getSharedPreferences(getString(R.string.PREF_FILE),MODE_PRIVATE);

        font_size = preferences.getFloat(getString(R.string.FONT_SIZE), -1);
        font_color = preferences.getString(getString(R.string.FONT_COLOR), "BLACK");
        text_message = preferences.getString(getString(R.string.TEXT_INFO), "");
        bar_progress = preferences.getInt(getString(R.string.BAR_INFO), -1);

        if(bar_progress > 0 ){
            seekBar.setProgress(bar_progress);
        }

        if( font_size > 0 ){
            message.setTextSize(font_size);
        }
        if( !font_color.equals("BLACK") ){
            switch (font_color.charAt(0)){
                case 'R':
                    message.setTextColor(getResources().getColor(R.color.red));
                    break;
                case 'G':
                    message.setTextColor(getResources().getColor(R.color.green));
                    break;
                case 'B':
                    message.setTextColor(getResources().getColor(R.color.blue));
                    break;
            }
        }

        if(text_message.length() > 0 ){
            message.setText(text_message);
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                message.setTextSize(TypedValue.COMPLEX_UNIT_PX,progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                font_size = message.getTextSize();
                bar_progress = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @TargetApi(23)
    public void changeColor(View view){
        switch (view.getId()){
            case R.id.radio_button_red:
                message.setTextColor(getResources().getColor(R.color.red,null));
                font_color = "RED";
                return;
            case R.id.radio_button_blue:
                message.setTextColor(getResources().getColor(R.color.blue, null));
                font_color = "BLUE";
                return;
            case R.id.radio_button_green:
                message.setTextColor(getResources().getColor(R.color.green, null));
                font_color = "GREEN";
                return;
        }
    }

    public void changeColorOld(View view){

        switch (view.getId()){
            case R.id.radio_button_red:
                message.setTextColor(getResources().getColor(R.color.red));
                font_color = "RED";
                return;
            case R.id.radio_button_blue:
                message.setTextColor(getResources().getColor(R.color.blue));
                font_color = "BLUE";
                return;
            case R.id.radio_button_green:
                message.setTextColor(getResources().getColor(R.color.green));
                font_color = "GREEN";
                return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearSettings(null);
        saveSettings(null);
    }

    public void saveSettings(View view){
        SharedPreferences preferences = MainActivity.this.getSharedPreferences(getString(R.string.PREF_FILE),
                                                                                MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat(getString(R.string.FONT_SIZE), font_size);
        editor.putInt(getString(R.string.BAR_INFO), seekBar.getProgress());
        editor.putString(getString(R.string.FONT_COLOR), font_color);
        editor.putString(getString(R.string.TEXT_INFO), message.getText().toString());
        editor.putInt(getString(R.string.BAR_INFO),bar_progress);
        editor.commit();
    }

    public void clearSettings(View view){
        SharedPreferences preferences = MainActivity.this.getSharedPreferences( getString(R.string.PREF_FILE),
                                                                                MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}

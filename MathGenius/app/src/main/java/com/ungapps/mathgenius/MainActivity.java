package com.ungapps.mathgenius;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.InterstitialAd;

import java.util.Locale;


public class MainActivity extends ActionBarActivity implements TextToSpeech.OnInitListener {

    EditText et;
    TextToSpeech tts;
    TextView tv;

    Integer a, b;

    //InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.editText);
        tv = (TextView) findViewById(R.id.textView);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });*/

        /*requestNewInterstitial();*/

        tts = new TextToSpeech(this, this);
        tts.setPitch((float) 1.2);
        tts.setLanguage(Locale.ENGLISH);
        tts.setSpeechRate((float) 1.5);

        a = randomWithRange(1, 99);
        b = randomWithRange(1, 99);

        tv.setText(a + " + " + b +  " = ");

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                tts.speak(tv.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }, 400);

        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    jawab(v);
                }
                return false;
            }
        });
    }

    /*private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }*/

    @Override
    protected void onStart() {
        super.onStart();

        tts.speak(tv.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

    int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public void jawab(View v)
    {
        if(et.getText().toString().trim().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Inputkan Number !", Toast.LENGTH_LONG).show();
            return;
        }

        if(a + b == Integer.parseInt(et.getText().toString()))
        {
            tts.speak("You are right", TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(getApplicationContext(), "Anda Benar !", Toast.LENGTH_LONG).show();

            a = randomWithRange(1, 99);
            b = randomWithRange(1, 99);

            tv.setText(a + " + " + b +  " = ");

            tts.speak(tv.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);

            et.setText("");
        }
        else if(a + b != Integer.parseInt(et.getText().toString()))
        {
            tts.speak("You are Wrong", TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(getApplicationContext(), "Anda Salah !", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Kesalahan Lain", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInit(int status) {

    }
}

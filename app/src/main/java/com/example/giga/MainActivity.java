package com.example.giga;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;

import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private TextToSpeech tts;
    private SpeechRecognizer speechRecog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Here, thisActivity is the current activity

                    // Permission has already been granted
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                    speechRecog.startListening(intent);

            }
        });

        initializeTextToSpeech();
        initializeSpeechRecognizer();
    }

    private void initializeSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speechRecog = SpeechRecognizer.createSpeechRecognizer(this);
            speechRecog.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    List<String> result_arr = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    processResult(result_arr.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void processResult(String s) {
        s = s.toLowerCase();
        String[] speech=s.split(" ");
//        Handle at least four sample cases

//        First: What is your Name?
//        Second: What is the time?
//        Third: Is the earth flat or a sphere?
//        Fourth: Open a browser and open url
        if(s.indexOf("what") != -1){
            if(s.indexOf("your name") != -1){
                speak("My Name is Giga. Nice to meet you!");
            }
            if (s.indexOf("time") != -1){
                String time_now = DateUtils.formatDateTime(this, new Date().getTime(), DateUtils.FORMAT_SHOW_TIME);
                speak("The time is now: " + time_now);
            }
            if(s.indexOf("birthday") != -1)
            {
                speak("I was born on 26th may 2019");
            }
            if(s.indexOf("location") != -1)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/maps/place/Mahatma+Gandhi+Mission's+Jawaharlal+Nehru+Engineering+College/@19.8804713,75.3466403,15.38z/data=!4m8!1m2!2m1!1sjnec!3m4!1s0x3bdb9873aaaaaaa9:0x1ecfcf095ce54cae!8m2!3d19.8798976!4d75.3565746"));
                startActivity(intent);
            }



            /*if (s.indexOf("date") != -1){

                String date_now = DateUtils.formatDateTime(this, new Date().getDate(), DateUtils.FORMAT_SHOW_DATE);
                speak("The date is now: " + date_now);
            }*/
        } else if(s.indexOf("open") != -1) {

            if (s.indexOf("browser") != -1) {
                speak("Opening a browser right away master.");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
                startActivity(intent);
            }

            if (s.indexOf("youtube") != -1) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com"));
                startActivity(intent);
            }
            if(s.indexOf("music") != -1)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("spotify:album"));
                startActivity(intent);
            }
            if(s.indexOf("instagram") != -1)
            {
                Uri uri = Uri.parse("http://instagram.com/_u/rutujbhakre");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/rutujbhakre")));
                }

            }

            if(s.indexOf("camera") != -1) {


                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);

                }
            if(s.indexOf("calculator") != -1)
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_CALCULATOR);
                startActivity(intent);
            }
        }
        else if(s.indexOf("play") != -1)
        {
            if(s.indexOf("spotify") != -1)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=OPf0YbXqDm0&list=RDOPf0YbXqDm0&start_radio=1"));
                startActivity(intent);
            }
        }

        else if(s.indexOf("enable") != -1)
        {
            if(s.indexOf("bluetooth") != -1)
            {
                speak("Turning your device bluetooth on.");
                BluetoothAdapter bluetooth= BluetoothAdapter.getDefaultAdapter();
                bluetooth.enable();
            }
            if(s.indexOf("internet") != -1)
            {
                speak("Turning on  wifi");
                WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(true);
            }

        }


        else if(s.indexOf("disable") != -1)
        {
            if(s.indexOf("bluetooth") != -1)
            {
                speak("Turning your device bluetooth off.");
                BluetoothAdapter bluetooth= BluetoothAdapter.getDefaultAdapter();
                bluetooth.disable();
            }
            if(s.indexOf("internet") != -1)
            {
                speak("Turning off  wifi");
                WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(false);
            }

        }
        else if(s.contains("wake me up at")){
            speak(speech[speech.length-1]);
            String[] time = speech[speech.length-1].split(":");
            Log.i("HOUR",s);
            String hour = time[0];
            String minutes = time[1];
            Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
            i.putExtra(AlarmClock.EXTRA_HOUR,Integer.valueOf(hour));
            //Log.i("HOUR",hour);
            i.putExtra(AlarmClock.EXTRA_MINUTES, Integer.valueOf(minutes));
            startActivity(i);
            speak("Setting alarm to ring at " + hour + ":" + minutes);
        }
   /*     else if(s.contains("cancel alarm"))
        {
            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1253, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            Log.i("CAN","cel");
        }
        else if(s.contains("add two numbers"))
        {
            String add[]=speech[speech.length-1].split("and");
            String n1=add[1];
            Log.i("add",s);
//            String n2=add[1];
            Log.i("N1",n1);
  //          Log.i("n2",n2);
        }
*/
    }

    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (tts.getEngines().size() == 0 ){
                    Toast.makeText(MainActivity.this, "No tts installed",Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    tts.setLanguage(Locale.US);
                    speak("Welcome to GIGA !");
                }
            }
        });
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT >= 21){
            tts.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        } else {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH,null);
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
    protected void onPause() {
        super.onPause();
        tts.shutdown();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Reinitialize the recognizer and tts engines upon resuming from background such as after openning the browser
        initializeSpeechRecognizer();
        initializeTextToSpeech();
    }
}

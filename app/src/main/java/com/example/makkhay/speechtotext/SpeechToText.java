package com.example.makkhay.speechtotext;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechToText extends AppCompatActivity
{


    private TextView tv;
    RelativeLayout rl;
    TextToSpeech tts;
    private ImageButton btn;
    private final int REQ_CODE=100;  // the number can be anything


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);


        rl = (RelativeLayout)findViewById(R.id.rl);

        tv = (TextView) findViewById(R.id.tv);
        btn = (ImageButton) findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();

            }
        });

    }

    /**
     * You need to create an intent to set several attributes
     */

    private void promptSpeechInput()
    {
        // passing an intent of recognize speech
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Gets the default language. In our case, english is selected.
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.speech_prompt));

        try
        {
            startActivityForResult(i,REQ_CODE);
        }catch (ActivityNotFoundException e)
        {
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported),Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * For example, your app can start a camera app and receive the captured photo as a result. Or, you might start the
     * People app in order for the user to select a contact and you'll receive the contact details as a result.

     Of course, the activity that responds must be designed to return a result. When it does,
     it sends the result as another Intent object. Your activity receives it in the onActivityResult() callback.
     * @param reqCode
     * @param resCode
     * @param data
     */

    @Override
    protected void onActivityResult( int reqCode, int resCode, Intent data)
    {
        super.onActivityResult(reqCode,resCode,data);
        switch(reqCode)
        {
            case REQ_CODE:
                if(resCode == RESULT_OK && data != null)
                {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tv.setText(result.get(0));

                    // change the color of background when green is spoken
                    if(result.get(0).contains("green"))
                    {
                         ttS("Hello, I'm a bot, how are you?");
                        rl.setBackgroundColor(Color.GREEN);
                    }
                    if(result.get(0).contains("blue"))
                    {
                    ttS("I'm a bot made by Prakash");
                        rl.setBackgroundColor(Color.BLUE);
                     }

                    if(result.get(0).contains("black"))
                    {
                        ttS("I can only change colors for now, but more functionality will be coming soon");
                        rl.setBackgroundColor(Color.BLACK);
                    }
                    if(result.get(0).contains("normal"))
                    {
                        ttS("I can only change colors for now, but more functionality will be coming soon");
                        rl.setBackgroundColor(Color.WHITE);
                    }


                }
        break;
        }

    }

    /**
     * Convert text to speech to reply back.
     */

    protected void ttS(final String text)
    {

        tts = new android.speech.tts.TextToSpeech(getApplicationContext(), new android.speech.tts.TextToSpeech.OnInitListener()
        {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                if (status != android.speech.tts.TextToSpeech.ERROR)
                {
                    tts.setPitch(0.4f);
                    tts.setSpeechRate(1);
                    tts.setLanguage(Locale.US);
                    tts.speak(text, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, null);

                }
            }
        });
    }



}

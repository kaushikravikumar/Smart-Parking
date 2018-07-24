package com.pubnub.kaushik.smart_parking_android;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    PubNub pubNub;

    TextView occupiedText;

    ImageView car, parkingSpace;

    float outsideCar = 903f;

    float parkedCar = 203f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        occupiedText = findViewById(R.id.occupiedText);
        car = findViewById(R.id.car);
        parkingSpace = findViewById(R.id.parkingspace);

        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-e36bba74-8c65-11e8-85ee-866938e9174c");
        pnConfiguration.setPublishKey("pub-c-559f5d98-9a8a-42e0-8a38-dfe760065056");
        pnConfiguration.setSecure(true);
        pubNub = new PubNub(pnConfiguration);

        pubNub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {

            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                final boolean occupied = message.getMessage().getAsJsonObject().get("occupied").getAsBoolean();
                runOnUiThread(new Runnable() {
                    public void run() {
                        if(occupied)
                        {
                            carEnterAnimation();
                        }
                        else
                        {
                            carLeaveAnimation();
                        }
                    }
                });
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        });
        pubNub.subscribe()
                .channels(Arrays.asList("parking_spot")) // subscribe to channels
                .execute();

    }

    private void carEnterAnimation()
    {
        car.animate().y(parkedCar).setDuration(1500);

        occupiedText.setText("Occupied");
    }

    private void carLeaveAnimation()
    {
        car.animate().y(outsideCar).setDuration(1500);

        occupiedText.setText("Vacant");
    }
}

package com.pubnub.kaushik.smart_parking_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    PubNub pubNub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                boolean occupied = message.getMessage().getAsJsonObject().get("occupied").getAsBoolean();
                // DO SOMETHING WITH THIS TODO
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        });
        pubNub.subscribe()
                .channels(Arrays.asList("parking_spot")) // subscribe to channels
                .execute();

    }
}

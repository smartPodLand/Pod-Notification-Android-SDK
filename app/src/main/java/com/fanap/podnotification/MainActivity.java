package com.fanap.podnotification;

import android.arch.lifecycle.Observer;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fanap.podnotify.PodNotify;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.textview);

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        final PodNotify podNotify = new PodNotify.builder()
                .setAppId(getApplicationContext().getPackageName())
                .setServerName(getString(R.string.server_name))
                .setSocketServerAddress(getString(R.string.socket_server_address))
                .setSsoHost(getString(R.string.sso_host))
                .setToken(getString(R.string.server_token))
                .setDeviceId(androidId)
                .build(getApplicationContext());

        podNotify.start(getApplicationContext());

        podNotify.getState(getApplicationContext()).observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                MainActivity.this.setTitle(s);
                if (podNotify.getPeerId(getApplicationContext())!=null)
                    textView.setText(podNotify.getPeerId(getApplicationContext()));
            }
        });
    }
}
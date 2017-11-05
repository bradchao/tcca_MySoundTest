package com.example.administrator.mysoundtest1;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private SoundPool sp;
    private int s1, s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        s2 = sp.load(this, R.raw.s2,1);
        s1 = sp.load(this, R.raw.s1,1);

    }

    public void test1(View view) {
        sp.play(s1, 0.5f, 0.5f,1,0,1);
    }
    public void test2(View view) {
        sp.play(s2, 0.5f, 0.5f,1,0,1);
    }
}

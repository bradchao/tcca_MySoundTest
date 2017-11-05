package com.example.administrator.mysoundtest1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private SoundPool sp;
    private int s1, s2;
    private MediaRecorder mediaRecorder;
    private ImageView img;

    private Animation am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,},
                    0);

        }else{
            init();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
        }
    }

    private void init(){
        sp = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        s2 = sp.load(this, R.raw.s2,1);
        s1 = sp.load(this, R.raw.s1,1);

        img = (ImageView)findViewById(R.id.img);

        am = new AlphaAnimation(1f,0f);
        am.setDuration(500);
        am.setRepeatCount(-1);

    }

    public void test1(View view) {
        sp.play(s1, 0.5f, 0.5f,1,0,1);
    }
    public void test2(View view) {
        sp.play(s2, 0.5f, 0.5f,1,0,1);
    }

    public void test3(View view) {
        if (mediaRecorder != null) return;

        File sdroot = Environment.getExternalStorageDirectory();
        File rfile = new File(sdroot, "brad.3gp");

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setOutputFile(rfile.getAbsolutePath());

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();

            img.setAnimation(am);
            img.startAnimation(am);
        } catch (IOException e) {
            Log.i("brad", e.toString());
        }


    }
    public void test4(View view) {
        if (mediaRecorder != null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            img.setAnimation(null);
        }


    }

    public void test5(View view) {
        File sdroot = Environment.getExternalStorageDirectory();
        File rfile = new File(sdroot, "brad.3gp");

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(rfile.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.i("brad", e.toString());
        }

    }

    public void test6(View view) {
        Intent it = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(it, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Log.i("brad", "ok");
        }else if(resultCode == RESULT_CANCELED){
            Log.i("brad", "xx");
        }
    }
}

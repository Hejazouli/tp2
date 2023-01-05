package com.example.evolvedactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AutreLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autre_login);
    }
}
/*
l'Intent démarrera l'activité qui a été définie en premier dans le fichier AndroidManifest.xml. Si Login est défini avant AutreLogin, alors Login sera démarré. Si AutreLogin est défini avant Login, alors AutreLogin sera démarré.
* */
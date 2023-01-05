package com.example.evolvedactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageButton launchPhoneCallButton;
    private ImageButton openWebPageButton;
    private ImageButton openPersoActivityButton;
    private EditText phoneNumberEditText;
    private EditText urlEditText;

    private int CALL_Perm = 1;
    private String defaultUrl = "https://www.emi.ac.ma/";
    private boolean isUserLoggedIn=false;
    private static final int REQUEST_CODE = 1;

    private String phoneNumberToCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, CALL_Perm);

        launchPhoneCallButton = (ImageButton) findViewById(R.id.LaunchPhoneCallButton);
        phoneNumberEditText = (EditText) findViewById(R.id.editTextPhone);

        openWebPageButton = (ImageButton) findViewById(R.id.openWebPageButton);
        urlEditText = (EditText) findViewById(R.id.urlEditText);

        openPersoActivityButton = (ImageButton) findViewById(R.id.openPersoActivityButton);


        launchPhoneCallButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isUserLoggedIn()){
                            openLoginActivity();
                        } else {
                            launchPhoneCall();
                        }
                    }
                }
        );

        openWebPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Récupérer les valeurs des champs de challenge
                String challenge1 = ((EditText) findViewById(R.id.editTextNumber)).getText().toString();
                String challenge2 = ((EditText) findViewById(R.id.editTextNumber3)).getText().toString();
                // Vérifier que les champs de challenge ne sont pas vides
                if (!challenge1.isEmpty() && !challenge2.isEmpty()) {
                    // Convertir les valeurs des champs de challenge en entiers
                    int challenge1Int = Integer.parseInt(challenge1);
                    int challenge2Int = Integer.parseInt(challenge2);

                    // Créer un Intent pour démarrer l'activité de vérification et ajouter les valeurs des champs de challenge comme extra
                    Intent checkIntent = new Intent(MainActivity.this, CheckActivity.class);
                    checkIntent.putExtra("challenge1", challenge1Int);
                    checkIntent.putExtra("challenge2", challenge2Int);

                    // Démarrer l'activité de vérification en utilisant une intention explicite et en attendant un résultat
                    startActivityForResult(checkIntent, REQUEST_CODE);
                } else {
                    // Afficher un message Toast si les champs de challenge sont vides
                    Toast.makeText(MainActivity.this, "Veuillez remplir les champs de challenge avant de continuer", Toast.LENGTH_SHORT).show();
                }
            }
        });


        openPersoActivityButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Appel implicitement de l’activité Login.
                        Intent loginActivityIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(loginActivityIntent);
                    }
                }
        );
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_Perm) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission accordée", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void launchPhoneCall() {
        phoneNumberToCall = phoneNumberEditText.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumberToCall));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }
    private boolean isUserLoggedIn() {
        return isUserLoggedIn;
    }

    private void openLoginActivity() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Vérifier si l'activité de vérification a été démarrée et si elle a renvoyé un résultat OK
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Récupérer la valeur de la somme envoyée par l'activité de vérification
            int sum = data.getIntExtra("sum", -1);

            // Récupérer les valeurs des champs de challenge
            String challenge1 = ((EditText) findViewById(R.id.editTextNumber)).getText().toString();
            String challenge2 = ((EditText) findViewById(R.id.editTextNumber3)).getText().toString();

            // Convertir les valeurs des champs de challenge en entiers
            int challenge1Int = Integer.parseInt(challenge1);
            int challenge2Int = Integer.parseInt(challenge2);

            // Vérifier si la somme est correcte
            if (sum == challenge1Int + challenge2Int) {
                // Récupérer l'URL entrée par l'utilisateur ou utiliser l'URL par défaut si le champ est vide
                String url = urlEditText.getText().toString();
                if (url.isEmpty()) {
                    url = defaultUrl;
                }

                // Ouvrir l'URL dans le navigateur par défaut
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            } else {
                // Afficher un message d'erreur si la somme est incorrect
                Toast.makeText(this, "La somme des challenges est incorrecte. Navigation internet annulée.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void launchLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

package com.example.evolvedactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckActivity extends AppCompatActivity {

    private TextView challenge1TextView;
    private TextView challenge2TextView;
    private EditText sumEditText;
    private Button okButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        challenge1TextView = (TextView) findViewById(R.id.challenge1TextView);
        challenge2TextView = (TextView) findViewById(R.id.challenge2TextView);
        sumEditText = (EditText) findViewById(R.id.sumEditText);
        okButton = (Button) findViewById(R.id.okButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        // Récupérer les valeurs des champs de challenge envoyées par l'activité principale
        Bundle extras = getIntent().getExtras();
        final int challenge1 = extras.getInt("challenge1");
        final int challenge2 = extras.getInt("challenge2");

        // Afficher les valeurs des champs de challenge dans les TextView correspondants
        challenge1TextView.setText("Challenge 1 reçu : " + challenge1);
        challenge2TextView.setText("Challenge 2 reçu : " + challenge2);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupérer la valeur entrée dans le champ de la somme
                String sumString = sumEditText.getText().toString();

                // Vérifier que le champ de la somme n'est pas vide
                if (!sumString.isEmpty()) {
                    // Convertir la valeur de la somme en entier
                    int sum = Integer.parseInt(sumString);

                    // Créer un Intent pour retourner à l'activité principale et ajouter la valeur de la somme comme extra
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("sum", sum);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
// Afficher un message d'erreur si le champ de la somme est vide
                    Toast.makeText(CheckActivity.this, "Veuillez entrer la somme des challenges avant de continuer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Afficher un message Toast pour informer l'utilisateur que l'opération a été annulée
                Toast.makeText(CheckActivity.this, "Opération annulée", Toast.LENGTH_SHORT).show();

                // Retourner à l'activité principale sans envoyer de résultat
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}

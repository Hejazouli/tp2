package com.example.evolvedactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText passwordEditText;
    Button okButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText) findViewById(R.id.usernameTextEdit);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        okButton = (Button) findViewById(R.id.okButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        okButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String username = usernameEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        if (isCredentialValid(username, password)){
                            Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                            mainActivityIntent.putExtra("isLoggedIn", true);
                            setResult(Activity.RESULT_OK, mainActivityIntent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "CREDENTIALS INCORRECT", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }
        );

    }

    private boolean isCredentialValid(String username, String password){
        return username.equals("TPandINFO") && password.equals("secret");
    }
}
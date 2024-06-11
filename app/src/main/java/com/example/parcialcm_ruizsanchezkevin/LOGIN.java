package com.example.parcialcm_ruizsanchezkevin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LOGIN extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Vincular los componentes de la interfaz
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegistrar = findViewById(R.id.buttonRegistrar);

        // Configurar el botón de inicio de sesión
        buttonLogin.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LOGIN.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Validar los datos del usuario
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String registeredUsername = sharedPreferences.getString("username", null);
                String registeredPassword = sharedPreferences.getString("password", null);

                if (username.equals(registeredUsername) && password.equals(registeredPassword)) {
                    Toast.makeText(LOGIN.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    // Redirigir a GameActivity
                    Intent intent = new Intent(LOGIN.this, Play.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(LOGIN.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el botón de registro
        buttonRegistrar.setOnClickListener(v -> {
            // Iniciar RegisterActivity
            Intent intent = new Intent(LOGIN.this, Register.class);
            startActivity(intent);
        });
    }
}

package com.example.parcialcm_ruizsanchezkevin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {

    private EditText editTextNewUsername;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextName;
    private EditText editTextLastName;
    private Spinner spinnerDocumentType;
    private EditText editTextDocumentNumber;
    private RadioGroup radioGroupGender;
    private EditText editTextBirthdate;
    private EditText editTextEmail;
    private EditText editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vincular los componentes de la interfaz
        editTextNewUsername = findViewById(R.id.editTextNewUsername);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        spinnerDocumentType = findViewById(R.id.spinnerDocumentType);
        editTextDocumentNumber = findViewById(R.id.editTextDocumentNumber);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        editTextBirthdate = findViewById(R.id.editTextBirthdate);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        Button buttonBack = findViewById(R.id.buttonBack);

        // Configurar el Spinner de tipo de documento
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.document_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDocumentType.setAdapter(adapter);

        // Configurar el botón de registro
        buttonRegister.setOnClickListener(v -> {
            String username = editTextNewUsername.getText().toString();
            String password = editTextNewPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();
            String name = editTextName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            String documentType = spinnerDocumentType.getSelectedItem().toString();
            String documentNumber = editTextDocumentNumber.getText().toString();
            int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
            String gender = selectedGenderId == -1 ? "" : ((RadioButton) findViewById(selectedGenderId)).getText().toString();
            String birthdate = editTextBirthdate.getText().toString();
            String email = editTextEmail.getText().toString();
            String phone = editTextPhone.getText().toString();

            if (validateFields(username, password, confirmPassword, name, lastName, documentType, documentNumber, gender, birthdate, email, phone)) {
                // Guardar los datos del usuario en SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.putString("name", name);
                editor.putString("lastName", lastName);
                editor.putString("documentType", documentType);
                editor.putString("documentNumber", documentNumber);
                editor.putString("gender", gender);
                editor.putString("birthdate", birthdate);
                editor.putString("email", email);
                editor.putString("phone", phone);
                editor.apply();

                Toast.makeText(Register.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                // Opcional: Regresar a MainActivity después del registro
                finish();
            }
        });

        // Configurar el botón de regresar
        buttonBack.setOnClickListener(v -> {
            // Cerrar la actividad actual y regresar a la actividad anterior
            finish();
        });
    }

    private boolean validateFields(String username, String password, String confirmPassword, String name, String lastName,
                                   String documentType, String documentNumber, String gender, String birthdate,
                                   String email, String phone) {

        if (username.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un nombre de usuario", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (confirmPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, confirme su contraseña", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese su nombre", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (lastName.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese su apellido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentType.isEmpty()) {
            Toast.makeText(this, "Por favor, seleccione un tipo de documento", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (documentNumber.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el número de documento", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (gender.isEmpty()) {
            Toast.makeText(this, "Por favor, seleccione su género", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (birthdate.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese su fecha de nacimiento", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor, ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phone.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese su número de teléfono", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.PHONE.matcher(phone).matches()) {
            Toast.makeText(this, "Por favor, ingrese un número de teléfono válido", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
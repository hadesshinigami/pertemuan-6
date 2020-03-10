package com.example.daftarlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText emailid,pass;
    Button signin;
    TextView tvdaftar;
    FirebaseAuth mFirebasAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebasAuth = FirebaseAuth.getInstance();
        emailid = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        signin = findViewById(R.id.button);
        tvdaftar = findViewById(R.id.daftar);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebasAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(LoginActivity.this, "Anda Telah masuk", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show();
                }

            }
        };
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailid.getText().toString();
                String password = pass.getText().toString();
                if(email.isEmpty()){
                    emailid.setText("Tolong masukkan email");
                    emailid.requestFocus();
                }
                else if(password.isEmpty()){
                    pass.setText("Tolong masukkan password");
                    pass.requestFocus();
                }
                else if(email.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Isi yang kosong", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && password.isEmpty())){
                    mFirebasAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login eror, login kembali bos", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intoHome = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intoHome);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();

                }

            }
        });

        tvdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intoDftr = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intoDftr);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebasAuth.addAuthStateListener(mAuthStateListener);
    }
}

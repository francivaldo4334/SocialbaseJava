package com.example.socialbasejava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.socialbasejava.database.FireStore;
import com.example.socialbasejava.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Log extends AppCompatActivity {
    Button btnIn,btnUp;
    EditText edtEmail,edtPassword;
    private FirebaseAuth mAuth;
    public static FireStore db;
    private final String TAG = "LogActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        btnIn = findViewById(R.id.btn_log_In);
        btnUp = findViewById(R.id.btn_log_up);

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnIn.setOnClickListener(v ->onLogIn());
        btnUp.setOnClickListener(v -> onLogUp());
        mAuth = FirebaseAuth.getInstance();
        db = new FireStore();
    }
    public void onLogUp(){
        String email,password;
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Preencha os campos corretamete!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //uid save
                            db.setUDI(user.getUid());
                            //create user data
                            db.addData(new UserModel("null",user.getUid(),email));
                            //
                            android.util.Log.d(TAG, "createUserWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            android.util.Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Log.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }
    public void onLogIn(){
        String email,password;
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Preencha os campos corretamete!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //uid save
                            db.setUDI(user.getUid());
                            //
                            android.util.Log.d(TAG, "signInWithEmail:success");
                            startActivity(new Intent(Log.this,User.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            android.util.Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Log.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            db.setUDI(currentUser.getUid());
            startActivity(new Intent(this,User.class));
        }
    }
}
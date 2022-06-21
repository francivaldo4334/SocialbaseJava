package com.example.socialbasejava;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.socialbasejava.database.LogClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User extends AppCompatActivity {
    TextView txtUserInformation,txtDatabaseInformations;
    Button btnLogOut;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        txtUserInformation = findViewById(R.id.user_informations);
        txtDatabaseInformations = findViewById(R.id.json_text);
        btnLogOut = findViewById(R.id.btn_log_out);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        btnLogOut.setOnClickListener(v -> onLogOut());
        //log user infomations
        if (user != null) {
            // Name, email address, and profile photo Url
            settextInUserInformation(user);
            //
            settextInUserDatabase();
        }
    }

    private void settextInUserDatabase() {
        Log.db.logClass = new LogClass() {
            @Override
            public void onSucess() {
                txtDatabaseInformations.setText(Log.db.resulte);
            }
        };
        Log.db.getData();
    }

    private void settextInUserInformation(FirebaseUser user) {
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        // Check if user's email is verified
        boolean emailVerified = user.isEmailVerified();

        // The user's ID, unique to the Firebase project. Do NOT use this value to
        // authenticate with your backend server, if you have one. Use
        // FirebaseUser.getIdToken() instead.
        String uid = user.getUid();

        String result =
                name + ",\n" +
                        email + ",\n" +
                        photoUrl + ",\n" +
                        emailVerified  + ",\n" +
                        uid  + ",\n";
        txtUserInformation.setText(result);
    }

    private void onLogOut() {
        mAuth.signOut();
        finish();
    }
}
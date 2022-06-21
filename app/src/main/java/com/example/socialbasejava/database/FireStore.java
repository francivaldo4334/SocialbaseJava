package com.example.socialbasejava.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.socialbasejava.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FireStore {
    FirebaseFirestore db;
    private final String TAG = "FireStore",NAME = "name",EMAIL = "email",UID = "uid";
    public static String CURRENTUID;
    public String resulte = "null";
    public LogClass logClass;
    public FireStore(){
        db = FirebaseFirestore.getInstance();
    }
    public void setUDI(String CURRENTUID){
        this.CURRENTUID = CURRENTUID;
    }
    public void addData(UserModel userModel){
        String uid = CURRENTUID;
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put(NAME, userModel.name);
        user.put(EMAIL,userModel.email);
        user.put(UID, uid);

// Add a new document with a generated ID
        db.collection("users")
                .document(uid)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + uid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    public void getData(){
        if(CURRENTUID == null)
            return;
        db.collection("users")
                .document(CURRENTUID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            FireStore.this.resulte = " => " +  document.getData();
                            if(logClass != null)
                                logClass.onSucess();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}

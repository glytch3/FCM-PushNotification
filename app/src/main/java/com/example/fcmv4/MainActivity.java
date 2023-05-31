package com.example.fcmv4;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private EditText mTitle,mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle=findViewById(R.id.mTitle);
        mMessage=findViewById(R.id.mMessage);
        Log.d("START SEND", "onCreate: ");
        findViewById(R.id.mSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=mTitle.getText().toString().trim();
                String message=mMessage.getText().toString().trim();
                if(!title.equals("") && !message.equals("")){
                    FCMSend.pushNotification(
                            MainActivity.this,
                            "XXXXXXXXXXXXXXXX", //ENTER THE TOKEN FOR THE DEVICE YOU WANT TO SEND THE MESSAGE TO.... HOW? LINE NO. 54
                            title,
                            message
                    );
                }
            }
        });
        Log.d("END SEND", "onCreate: ");

// THE COMMENTED OUT CODE IS USED TO GET THE TOKEN FOR YOUR DEVICE THAT YOU WANT TO SEND THE NOTIFICATION TO
//        // This is only necessary for API level >= 33 (TIRAMISU)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
//                    PackageManager.PERMISSION_GRANTED) {
//                // FCM SDK (and your app) can post notifications.
//            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//                // TODO: display an educational UI explaining to the user the features that will be enabled
//                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
//                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
//                //       If the user selects "No thanks," allow the user to continue without notifications.
//            } else {
//                // Directly ask for the permission
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
//            }
//        }
//
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//                        // Get new FCM registration token
//                        String token = task.getResult();
//                        Log.d("token", ""+token);
//                    }
//                });

    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

}
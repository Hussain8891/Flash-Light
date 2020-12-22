package hussainshaikh.com.flashlight;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
    Button button;
    boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn_flash);

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {


            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                runFlashlight();

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Camera permission required", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();


    }



    private void runFlashlight() {
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
              if (!state){
                  CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                  Toast.makeText(MainActivity.this, "Torch is on", Toast.LENGTH_SHORT).show();

                  try {
                      String cameraid = cameraManager.getCameraIdList()[0];
                      cameraManager.setTorchMode(cameraid,true);
                      state = true;
                      button.setBackgroundResource(R.drawable.on);

                  } catch (CameraAccessException e) {
                      e.printStackTrace();
                  }

              }else {
                  CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                  try {
                      String cameraid = cameraManager.getCameraIdList()[0];
                      cameraManager.setTorchMode(cameraid,false);
                      state = false;
                      button.setBackgroundResource(R.drawable.off);

                  } catch (CameraAccessException e) {
                      e.printStackTrace();
                  }


              }


            }
        });

    }

    //Exit dialog
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you wants to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}
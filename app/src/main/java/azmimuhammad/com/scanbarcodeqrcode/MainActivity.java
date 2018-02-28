package azmimuhammad.com.scanbarcodeqrcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity {
    Button btn;

    //untuk penyocokan variabel
    static final int CAMERA_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisialisasi
        //id harus sesuai dengan ada yg ada di xml setContentView(xml)
        btn = findViewById(R.id.btn);

        //aksi dijalankan apabila button ditekan
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
            }
        });

        //untuk mengambil versi yang ada pada Device
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;

        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();

            } else {
                requestPermission();
            }
        }
    }

    //cek apakah user udah mengizinkan permission yang diberikan
    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    //untuk memunculkan dialog box berisikan permission
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, CAMERA_PERMISSION);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            //cek apakah nilai requestCode == CAMERA_PERMISSION
            case CAMERA_PERMISSION:
                if (grantResults.length > 0) {
                    //mengecek apakah diberikan atau tidak
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
                break;
        }
    }
}

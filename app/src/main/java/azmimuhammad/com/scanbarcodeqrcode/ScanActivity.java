package azmimuhammad.com.scanbarcodeqrcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    ZXingScannerView zXingScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);

        //kyknya lo tadi kurang yang ini tih
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    //method ini jalan kalo seandainya kelas ini dipake lagi
    @Override
    protected void onResume() {
        super.onResume();
        zXingScannerView.resumeCameraPreview(this);
    }

    //method ini jalan kalo seandainya class di finish() atau pindah class pake intent
    @Override
    public void onDestroy() {
        super.onDestroy();
        zXingScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result rawResult) {
        final String result = rawResult.getText();
        Log.d("QRCodeScanner", rawResult.getText());
        Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                zXingScannerView.resumeCameraPreview(ScanActivity.this);
            }
        });
        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
                startActivity(browserIntent);
            }
        });
        builder.setCancelable(false);
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
    }
}

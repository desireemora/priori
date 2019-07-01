package com.priori.app.beta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.content.ContextCompat;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;




public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;

    public FingerprintHandler(Context context){
        this.context = context;
    }
    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There was an Auth Error. " + errString, false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Authentication failed. " + helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("You can now access the app.", true);
    }
    private void update(String s, boolean b){
        TextView txtfingerprint = ((Activity)context).findViewById(R.id.tv_fingerprint);
        ImageButton fingBtn = ((Activity)context).findViewById(R.id.ibtn_fingerprint);

        txtfingerprint.setText(s);

        if (b == false){
            txtfingerprint.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            fingBtn.setImageResource(R.mipmap.fingererror);
        } else {
            fingBtn.setImageResource(R.mipmap.done);
            txtfingerprint.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            txtfingerprint.setText("Access Granted");
            //        Toast.makeText(this, "Your Fingerprint Works", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, Welcome.class) );
        }
    }
}

package org.ea.sqrl.processors;


import android.annotation.TargetApi;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Handler;
import android.widget.PopupWindow;

@TargetApi(value=28)
public class BioAuthenticationCallback extends BiometricPrompt.AuthenticationCallback {

    private Handler handler;
    private final PopupWindow loginWindow;
    private final Runnable doneCallback;

    public BioAuthenticationCallback(Handler handler, PopupWindow loginWindow, Runnable doneCallback) {
        this.handler = handler;
        this.loginWindow = loginWindow;
        this.doneCallback = doneCallback;
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
    }

    @Override
    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        try {
            BiometricPrompt.CryptoObject co = result.getCryptoObject();
            SQRLStorage.getInstance().decryptIdentityKeyBiometric(co.getCipher());

            new Thread(doneCallback).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
    }
}
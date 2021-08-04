package com.example.textencrypter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;


public class aes extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.info_popup, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //dismiss window when tapped outside
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, false);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        TextView aboutTV = popupView.findViewById(R.id.about_TV);
        aboutTV.setText(R.string.about_aes);
        aboutTV.setMovementMethod(LinkMovementMethod.getInstance());


        //Blur background when popup window is active
        View container = (View) popupWindow.getContentView().getParent();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        // add flag
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);


        // dismiss the popup window when touched closeTV
        TextView closeTV = popupView.findViewById(R.id.close_TV);
        closeTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aes);

        Button encrptButton = findViewById(R.id.encrypt_button);
        Button decrptButton = findViewById(R.id.decrypt_button);
        Button copyButton = findViewById(R.id.copy_button);
        Button resetButton = findViewById(R.id.reset_button);

        copyButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);

        EditText aesKey = findViewById(R.id.aes_key_et);
        EditText aesMessage = findViewById(R.id.aes_message_et);
        TextView displayTV = findViewById(R.id.display_tv);
        TextView outputTV = findViewById(R.id.output_tv);

        outputTV.setVisibility(View.GONE);
        displayTV.setVisibility(View.GONE);

        encrptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String encrypted = AESCrypt.encrypt(aesKey.getText().toString(), aesMessage.getText().toString());
                    displayTV.setText(encrypted);
                    Toast.makeText(aes.this, "Encryption successfull", Toast.LENGTH_SHORT).show();
                    outputTV.setVisibility(View.VISIBLE);
                    displayTV.setVisibility(View.VISIBLE);
                    copyButton.setVisibility(View.VISIBLE);
                    resetButton.setVisibility(View.VISIBLE);

                    //copies the output
                    copy(encrypted, copyButton);

                    //resets all values
                    reset(resetButton, aesKey, aesMessage, displayTV, outputTV, copyButton);

                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }
            }
        });

        decrptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String decrypted = AESCrypt.decrypt(aesKey.getText().toString(), aesMessage.getText().toString());
                    displayTV.setText(decrypted);
                    Toast.makeText(aes.this, "Decryption successfull", Toast.LENGTH_SHORT).show();
                    outputTV.setVisibility(View.VISIBLE);
                    displayTV.setVisibility(View.VISIBLE);
                    copyButton.setVisibility(View.VISIBLE);
                    resetButton.setVisibility(View.VISIBLE);

                    //copies the output
                    copy(decrypted, copyButton);

                    //resets all values
                    reset(resetButton, aesKey, aesMessage, displayTV, outputTV, copyButton);

                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void copy(String value, Button copyButton){
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("label", value);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(aes.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void reset(Button resetButton, EditText aesKey, EditText aesMessage, TextView displayTV, TextView outputTV, Button copyButton){
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aesKey.setText("");
                aesMessage.setText("");
                displayTV.setText("");

                copyButton.setVisibility(View.GONE);
                resetButton.setVisibility(View.GONE);
                outputTV.setVisibility(View.GONE);
                displayTV.setVisibility(View.GONE);
            }
        });
    }
}
package com.example.textencrypter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
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
import android.widget.TextView;
import android.widget.Toast;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class md5 extends AppCompatActivity {

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
        aboutTV.setText(R.string.about_md5);
        aboutTV.setMovementMethod(LinkMovementMethod.getInstance());

        //Blur background when popup window is active
        View container = (View) popupWindow.getContentView().getParent();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        // add flag
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
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
        setContentView(R.layout.activity_md5);

        EditText messageET = findViewById(R.id.md5_message_et);
        Button hashButton = findViewById(R.id.hash_button);
        TextView md5DisplayTV = findViewById(R.id.md5_display_tv);

        String message = messageET.getText().toString();
        String output =  md5(message);

        TextView md5OutputTV = findViewById(R.id.md5_output_tv);
        Button md5CopyButton = findViewById(R.id.md5_copy_button);
        Button md5ResetButton = findViewById(R.id.md5_reset_button);

        md5OutputTV.setVisibility(View.GONE);
        md5DisplayTV.setVisibility(View.GONE);
        md5CopyButton.setVisibility(View.GONE);
        md5ResetButton.setVisibility(View.GONE);

        hashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md5DisplayTV.setText(output);

                md5OutputTV.setVisibility(View.VISIBLE);
                md5DisplayTV.setVisibility(View.VISIBLE);
                md5CopyButton.setVisibility(View.VISIBLE);
                md5ResetButton.setVisibility(View.VISIBLE);
            }
        });

        md5CopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("label", output);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(md5.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        md5ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageET.setText("");
                md5DisplayTV.setText("");

                md5OutputTV.setVisibility(View.GONE);
                md5DisplayTV.setVisibility(View.GONE);
                md5CopyButton.setVisibility(View.GONE);
                md5ResetButton.setVisibility(View.GONE);
            }
        });
    }


// THIS WORKS FINE (THIS IS FROM STACK OVERFLOW)
// STRINGBUFFER IS USED INSTEAD OF STRINGBUILDER, DON'T KNOW WHY

    private String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            byte messageDigest[] = md.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

//  FOR SOME REASON IAM GETTING KIND OF WRONG STRING CONVERSION IN BELOW FUNCTION(THIS IS FROM DEVELOPER.ANDROID.COM)

//    private String md5(String message){
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(message.getBytes());
//            byte[] bytes = md.digest();
//
//            StringBuilder sb = new StringBuilder();
//            for(int i=0; i<bytes.length; i++){
//                sb.append(Integer.toString(( bytes[i]&0xff)+0x100,32).substring(1));
//            }
//            return sb.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
}
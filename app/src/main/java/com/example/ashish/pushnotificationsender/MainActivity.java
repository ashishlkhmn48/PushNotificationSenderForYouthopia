package com.example.ashish.pushnotificationsender;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText messageText = (EditText) findViewById(R.id.message_text);
        final EditText titleText = (EditText) findViewById(R.id.title_text);
        final EditText pictureText = (EditText) findViewById(R.id.picture_text);
        Button sendButton= (Button) findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendDetailsToServer detailsToServer = new SendDetailsToServer(MainActivity.this,MainActivity.this);
                detailsToServer.execute(messageText.getText().toString(), titleText.getText().toString(),pictureText.getText().toString());
            }
        });
    }
}

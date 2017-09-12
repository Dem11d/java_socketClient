package com.example.socketclient;

import android.app.Activity;
import android.graphics.Color;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class MessageWriter {
    public static enum messageType {
        Own,
        Foreign,
        Info
    }

    private Activity activity;

    public MessageWriter(Activity activity) {
        this.activity = activity;
    }

    public void writeMessage(messageType type, User user, final Message message) {

        String str_message = message.getMessage();
        String user_name = user.getName();
        int color = user.getColor();
        //// TODO: 12.09.2017 create color text output
        final String out_message = user.getName() + ": " + message.getMessage();
        final int elemId;
        switch (type) {
            case Own:
                elemId = R.layout.own_message;
                break;
            case Foreign:
                elemId = R.layout.foreign_message;
                break;
            case Info:
                elemId = R.layout.info_message;
                break;
            default:
                elemId = 0;
        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ScrollView chatArea = (ScrollView) activity.findViewById(R.id.chatArea);
                TextView myText = (TextView) activity.getLayoutInflater().inflate(elemId, null);
                myText.setText(out_message);
            }
        });
    }

    ;
}

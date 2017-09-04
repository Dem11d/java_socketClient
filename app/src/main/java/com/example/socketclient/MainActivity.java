package com.example.socketclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private Socket socket;

    private static final int SERVERPORT = 6565;
    private static final String SERVER_IP = "192.168.0.103";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new Thread(new ClientSendThread()).start();
        new Thread(new ClientInputThread()).start();
    }

    public void onClick(View view) {
        EditText et = (EditText) findViewById(R.id.EditText01);
        String str = et.getText().toString();
        sendMessage("message",str);
    }

    public void sendMessage(String type, String message){
        try {
            JSONObject jo = new JSONObject();

            jo.put("type",type);
            jo.put("value",message);
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())),
                    true);
            out.println(jo.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    class ClientSendThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                socket = new Socket(serverAddr, SERVERPORT);
                while(socket==null){
                    Thread.currentThread().sleep(500);
                    socket = new Socket(serverAddr, SERVERPORT);
                }
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                sendMessage("name",name);

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
    class ClientInputThread implements Runnable {

        @Override
        public void run() {

            try {
                BufferedReader input = null;
                while(socket==null){
                    Thread.currentThread().sleep(500);
                }
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                TextView tv = (TextView)findViewById(R.id.chatText);

                while(true){
                    String read = input.readLine();
                    tv.append(read);
                }
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}

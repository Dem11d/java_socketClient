package com.example.socketclient;

import android.app.Activity;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ChatApplication {
    private User user;

    private Map<String, User> users;
    private Activity activity;
    private Socket socket;
    private MessageWriter mw;

    private static final int SERVERPORT = 6060;
    private static final String SERVER_IP = "10.70.74.119";

    public ChatApplication(Activity activity) {
        this.activity = activity;
        this.mw = new MessageWriter(activity);
    }

    public void init() {

        initSocket();
        getUsers();
        registerUser();
        new Thread(new ClientInputThread()).start();
    }

    public void sendMessage(Map<String, String> values) {
        try {
            JSONObject jo = new JSONObject(values);
            if (!jo.has("type"))
                throw new Exception("Type is require in map set");
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())),
                    true);
            out.println(jo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUsers() {
        Map<String, String> map = new TreeMap();
        map.put("type", "getUsers");
        sendMessage(map);
    }

    private void initSocket() {
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            BufferedReader input = null;

            socket = new Socket(serverAddr, SERVERPORT);
            Intent intent = activity.getIntent();
            String name = intent.getStringExtra("name");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private class ClientInputThread implements Runnable {
        @Override
        public void run() {
            BufferedReader input = null;
            while (socket == null) {
                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    String read = input.readLine();
                    messageProcessor(read);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    private void messageProcessor(String message) {
        try {

            JSONObject jo = new JSONObject(message);

            String type = jo.getString("type");
            switch (type) {
                case "users":
                    JSONObject users_js = new JSONObject(jo.getString("value"));
                    Iterator<String> keys = users_js.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        JSONObject user = new JSONObject(users_js.getString("key"));
                        String name = user.getString("name");
                        users.put(key, new User(Integer.parseInt(key), name));
                    }
                    break;
                case "message":
                    int id = Integer.parseInt(jo.getString("userId"));
                    String str_message = jo.getString("value");
                    MessageWriter.messageType messageType;
                    User messageUser;
                    if (user.getId() == id) {
                        messageType = MessageWriter.messageType.Own;
                        messageUser = user;
                    } else {
                        messageUser = users.get(id);
                        messageType = MessageWriter.messageType.Own;
                        messageUser = user;
                    }

                    mw.writeMessage(messageType, messageUser, new Message(str_message,new Date()));
                    break;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void registerUser() {
        Intent intent = activity.getIntent();
        String name = intent.getStringExtra("name");
        Map<String,String> map = new TreeMap<String,String>();
        map.put("type","name");
        map.put("value",name);
    }

    public void sendMessageFromActivity(String message){
        Map<String, String> map = new TreeMap();
        map.put("type", "message");
        map.put("value", message);
        sendMessage(map);
    }
}

package com.upskillx.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.upskillx.model.Meeting;

import javafx.application.Platform;

public class ChatDAO {

    private DatabaseReference chatReference;
    private DatabaseReference meetingReference;

    public ChatDAO(String chatID) {
        DatabaseReference baseRef = FirebaseDatabase.getInstance().getReference("chatrooms/" + chatID);
        this.chatReference= baseRef.child("messages");
        this.meetingReference = baseRef.child("meetings");
        
    }

    public void sendMessage(String sender, String msg) {

        Map<String,Object> msgData = new HashMap<>();

        msgData.put("sender",sender);
        msgData.put("message", msg);
        msgData.put("timestamp", System.currentTimeMillis());

        chatReference.push().setValueAsync(msgData);
    }


    public void startListening(BiConsumer<String, String> onMessageReceived) {
        chatReference.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                String sender = snapshot.child("sender").getValue(String.class);
                String message = snapshot.child("message").getValue(String.class);

                Platform.runLater(() -> {
                    onMessageReceived.accept(sender, message);
                });
            }
            public void onCancelled(DatabaseError error) {}
            public void onChildChanged(DataSnapshot ds, String s) {}
            public void onChildRemoved(DataSnapshot ds) {}
            public void onChildMoved(DataSnapshot ds, String s) {}
        });

    }  
    
    public void scheduleMeeting(Meeting meeting) {
        String meetingId = meetingReference.push().getKey();
        meetingReference.child(meetingId).setValueAsync(meeting);
    }

}

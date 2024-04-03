package com.example.bookingapp;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.Database.Room;
import com.example.bookingapp.Database.RoomAdapter;
import com.example.bookingapp.Database.RoomShow;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recent;
    RoomShow roomAdapter;
    List<Room> listRoom;
    EditText roomid,roomname,roomavatar,roomtypeid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //anh xa
        anhxa();
        FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mAuth.getReference("Room");
        Query query= mRef.orderByChild("roomID");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listRoom!=null) {
                    listRoom.clear();

                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Room room = dataSnapshot.getValue(Room.class);
                    listRoom.add(room);
                }
                roomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Lấy danh sách loại phòng không thành công", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void anhxa(){
        recent = findViewById(R.id.recent_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recent.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recent.addItemDecoration(dividerItemDecoration);
        listRoom = new ArrayList<>();
        roomAdapter = new RoomShow(listRoom);
        recent.setAdapter(roomAdapter);
    }

}
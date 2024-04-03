package com.example.bookingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.Database.Room;
import com.example.bookingapp.Database.RoomAdapter;
import com.example.bookingapp.Database.RoomType;
import com.example.bookingapp.Database.RoomTypeAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    private EditText edid,edname,edavatar,edrtid;
    private Button add,goRoomtype;
    private ImageButton back;
    private RecyclerView rcvRoom;
    private RoomAdapter roomAdapter;
    private List<Room> lstRoom;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        // anh xa du lieu
        anhxa();
        // tao su kien cho button them phong
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id= edid.getText().toString().trim();
                String name = edname.getText().toString().trim();
                String avatar = edavatar.getText().toString().trim();
                String rtid = edrtid.getText().toString().trim();
                String rtname = null;
                if (rtid=="1")
                {
                    rtname = ("Phòng đơn");
                }
                if (rtid=="2"){
                    rtname =("Phòng đôi");
                }
                if (rtid=="3"){
                    rtname =("Phòng nhóm");
                }
                if (rtid=="4"){
                    rtname =("Phòng vip");
                }
                Room room= new Room(id,name,rtid,avatar,rtname);
                onClickAddRoom(room);
//                onClickGoRoomType();
            }
        });
        // tao su kien cho button di toi loai phong
        getlstRoomFromFirebase();
        goRoomtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoRoomType();
            }
        });
        // tao su kien cho button tro lai
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RoomActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    // anh xa du lieu
    private void anhxa(){
        edid=findViewById(R.id.rID);
        edname=findViewById(R.id.rName);
        edavatar=findViewById(R.id.rAvatar);
        edrtid=findViewById(R.id.rtID);
        add=findViewById(R.id.rtButton);
        back = findViewById(R.id.btn_back);
        goRoomtype=findViewById(R.id.btn_goroomtype);
        rcvRoom=findViewById(R.id.r_rcv_roomtype);
//        Spinner spnChooseRoomType = findViewById(R.id.spnchooseRoomType);
//        ArrayList<RoomType> listRoomType =RoomTypeAdapter.getAll(this);
//        listRoomType.add(0,new RoomType("0","Chọn phòng ban"));
//        ArrayAdapter<RoomType> departmentArrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listRoomType);
//        departmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spnChooseRoomType.setAdapter(departmentArrayAdapter);
        //
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvRoom.setLayoutManager(linearLayoutManager);
        // tạo đường kẻ ngang
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvRoom.addItemDecoration(dividerItemDecoration);
        // tạo danh sách phòng
        lstRoom = new ArrayList<>();
        roomAdapter = new RoomAdapter(lstRoom, new RoomAdapter.RoomCallBack() {
            @Override
            public void onClickUpdate(Room room) {
                openDialogUpdateItem(room);
            }

            @Override
            public void onClickDelete(Room room) {
                onclickDeleteData(room);
            }
        });

        rcvRoom.setAdapter(roomAdapter);
    }
    private void onClickAddRoom(Room room){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Room");

        String pathObject = String.valueOf(room.getRoomID());
        reference.child(pathObject).setValue(room, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(RoomActivity.this,"Đã hoàn thành",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getlstRoomFromFirebase(){
        FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mAuth.getReference("Room");
        Query query= mRef.orderByChild("roomID");
        //Cách 2
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Room room = snapshot.getValue(Room.class);
                if(room!=null){
                    lstRoom.add(room);
                    roomAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Room room = snapshot.getValue(Room.class);
                if (room==null||lstRoom ==null|| lstRoom.isEmpty()){return;}
                for (int i =0 ;i<lstRoom.size();i++){
                    if (room.getRoomID() == lstRoom.get(i).getRoomID()){
                        lstRoom.set(i,room);
                        break;
                    }
                }

                roomAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Room room = snapshot.getValue(Room.class);
                if (room==null||lstRoom ==null|| lstRoom.isEmpty()){return;}
                for (int i =0 ;i<lstRoom.size();i++){
                    if (room.getRoomID() == lstRoom.get(i).getRoomID()){
                        lstRoom.remove(lstRoom.get(i));
                        break;
                    }
                }
                roomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Query rtID = FirebaseDatabase.getInstance().getReference().child("RoomType").child("id");
//        Query rtName = FirebaseDatabase.getInstance().getReference().child("RoomType").child("rtname");
//        Query r_rtID = FirebaseDatabase.getInstance().getReference().child("Room").child("roomid");
//        if (rtID==r_rtID){
//            Room room = new Room();
//            room.setRtname(rtName);
//        }

    }
        private void openDialogUpdateItem(Room room){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set layout cho dialog
        dialog.setContentView(R.layout.layout_dialog_room_update);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        // ánh xạ dialog
        EditText edtUpdateName= dialog.findViewById(R.id.edt__dialog_update_name);
        EditText edtUpdateAvatar= dialog.findViewById(R.id.edt__dialog_update_avt);
        EditText edtUpdateRTID= dialog.findViewById(R.id.edt__dialog_update_rtid);
        Button btnCancel= dialog.findViewById(R.id.btn_dialog_cancel);
        Button btnUpdate= dialog.findViewById(R.id.btn_dialog_update);

        edtUpdateName.setText(room.getRoomName());
        edtUpdateAvatar.setText(room.getRoomAvatar());
        edtUpdateRTID.setText(room.getRtid());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
                DatabaseReference mRef = mAuth.getReference("Room");
                String newname = edtUpdateName.getText().toString().trim();
                room.setRoomName(newname);
                mRef.child(String.valueOf(room.getRoomID())).updateChildren(room.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(RoomActivity.this,"Sửa thành công",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }

    private void onclickDeleteData(Room room){
        new AlertDialog.Builder(this)
//                .setTitle(getString(R.string.app_name))
                .setTitle("Xoá phòng")
                .setMessage("Có đồng ý xoá không?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
                        DatabaseReference mRef = mAuth.getReference("Room");

                        mRef.child(String.valueOf(room.getRoomID())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(RoomActivity.this,"Xoá thành công",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Không",null)
                .show();
    }
    private void onClickGoRoomType(){
        Intent i = new Intent(RoomActivity.this,RoomTypeActivity.class);
        startActivity(i);
    }
//    private void loadRoomType() {
//        Spinner snRoomType =findViewById(R.id.snRoomType);
//        ArrayList<RoomType> lstRoomType = new ArrayList<>();
//        FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
//        DatabaseReference mRef = mAuth.getReference("RoomType");
//        //cach 1
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (lstRoomType!=null) {
//                    lstRoomType.clear();
//                }
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    RoomType roomType = dataSnapshot.getValue(RoomType.class);
//                    lstRoomType.add(roomType);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(RoomActivity.this,"Lấy danh sách loại phòng không thành công",Toast.LENGTH_SHORT).show();
//            }
//        });
//        Query query= mRef.orderByChild("id");
//        query.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                RoomType roomType = snapshot.getValue(RoomType.class);
//                if(roomType!=null){
//                    lstRoomType.add(roomType);
//                    roomAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                RoomType roomType = snapshot.getValue(RoomType.class);
//                if (roomType==null||lstRoomType ==null|| lstRoomType.isEmpty()){return;}
//                for (int i =0 ;i<lstRoomType.size();i++){
//                    if (roomType.getId() == lstRoomType.get(i).getId()){
//                        lstRoomType.set(i,roomType);
//                        break;
//                    }
//                }
//                roomAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        ArrayAdapter<RoomType> roomTypeArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,lstRoomType);
//        roomTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        snRoomType.setAdapter(roomTypeArrayAdapter);
//
//    }

}

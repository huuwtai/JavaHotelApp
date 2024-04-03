package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class RoomTypeActivity extends AppCompatActivity {
    private EditText edid,edname;
    private Button add;
    private ImageButton back;
    private RecyclerView rcvRoomType;
    private RoomTypeAdapter roomTypeAdapter;
    private List<RoomType> lstRoomType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomtype2);
        anhxa();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id= edid.getText().toString().trim();
                String name = edname.getText().toString().trim();
                RoomType roomType= new RoomType(id,name);
                onClickAddRoomType(roomType);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RoomTypeActivity.this,RoomActivity.class);
                startActivity(i);
            }
        });
        getlstRoomTypeFromFirebase();
    }
    private void anhxa(){
        edid = findViewById(R.id.rtID);
        edname = findViewById(R.id.rtName);
        add = findViewById(R.id.rtButton);
        back = findViewById(R.id.imageButtonback);
        rcvRoomType = findViewById(R.id.rt_rcv_roomtype);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvRoomType.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvRoomType.addItemDecoration(dividerItemDecoration);
        lstRoomType = new ArrayList<>();
        roomTypeAdapter = new RoomTypeAdapter(lstRoomType, new RoomTypeAdapter.IclickListener() {
            @Override
            public void onClickUpdate(RoomType roomType) {
                openDialogUpdateItem(roomType);
            }

            @Override
            public void onClickDelete(RoomType roomType) {
                onclickDeleteData(roomType);
            }
        });

        rcvRoomType.setAdapter(roomTypeAdapter);
    }
    private void onClickAddRoomType(RoomType roomType){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("RoomType");

        String pathObject = String.valueOf(roomType.getId());
        reference.child(pathObject).setValue(roomType, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(RoomTypeActivity.this,"Đã hoàn thành",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void clickAddAllUsers(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("RoomType");
        List<RoomType> lst = new ArrayList<>();
        lst.add(new RoomType("1","Phòng đơn"));
        lst.add(new RoomType("2","Phòng đôi"));
        lst.add(new RoomType("3","Phòng nhóm"));
        myref.setValue(lst, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(RoomTypeActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getlstRoomTypeFromFirebase(){
        FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mAuth.getReference("RoomType");
        //cach 1
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (lstRoomType!=null) {
//                    lstRoomType.clear();
//
//                }
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    RoomType roomType = dataSnapshot.getValue(RoomType.class);
//                    lstRoomType.add(roomType);
//                }
//                roomTypeAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(RoomTypeActivity.this,"Lấy danh sách loại phòng không thành công",Toast.LENGTH_SHORT).show();
//            }
//        });
        Query query= mRef.orderByChild("id");
        //Cách 2
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RoomType roomType = snapshot.getValue(RoomType.class);
                if(roomType!=null){
                    lstRoomType.add(roomType);
                    roomTypeAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RoomType roomType = snapshot.getValue(RoomType.class);
                if (roomType==null||lstRoomType ==null|| lstRoomType.isEmpty()){return;}
                for (int i =0 ;i<lstRoomType.size();i++){
                    if (roomType.getId() == lstRoomType.get(i).getId()){
                        lstRoomType.set(i,roomType);
                        break;
                    }
                }
                roomTypeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                RoomType roomType = snapshot.getValue(RoomType.class);
                if (roomType==null||lstRoomType ==null|| lstRoomType.isEmpty()){return;}
                for (int i =0 ;i<lstRoomType.size();i++){
                    if (roomType.getId() == lstRoomType.get(i).getId()){
                        lstRoomType.remove(lstRoomType.get(i));
                        break;
                    }
                }
                roomTypeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RoomTypeActivity.this, "Tải danh sách thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void openDialogUpdateItem(RoomType roomType){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_update);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edtUpdateName= dialog.findViewById(R.id.edt__dialog_update_name);
        Button btnCancel= dialog.findViewById(R.id.btn_dialog_cancel);
        Button btnUpdate= dialog.findViewById(R.id.btn_dialog_update);

        edtUpdateName.setText(roomType.getRtname());
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
                DatabaseReference mRef = mAuth.getReference("RoomType");
                String newname = edtUpdateName.getText().toString().trim();
                roomType.setRtname(newname);
                mRef.child(String.valueOf(roomType.getId())).updateChildren(roomType.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(RoomTypeActivity.this,"Sửa thành công",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }

    private void onclickDeleteData(RoomType roomType){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Có đồng ý xoá không?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
                        DatabaseReference mRef = mAuth.getReference("RoomType");

                        mRef.child(String.valueOf(roomType.getId())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(RoomTypeActivity.this,"Xoá thành công",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Không",null)
                .show();
    }
}
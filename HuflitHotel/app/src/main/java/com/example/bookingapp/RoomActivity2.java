//package com.example.bookingapp;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ListAdapter;
//import android.widget.Spinner;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.bookingapp.Database.Room;
//import com.example.bookingapp.Database.RoomAdapter;
//import com.example.bookingapp.Database.RoomType;
//import com.example.bookingapp.Database.RoomTypeAdapter;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RoomActivity2 extends AppCompatActivity {
//    private EditText edid,edname,edavatar,edrtid;
//    private Button add,goRoomtype;
//    private ImageButton back;
//    private RecyclerView rcvRoom;
//    private RoomAdapter roomAdapter;
//    private List<Room> lstRoom;
//    Context context;
//    private Spinner spinner;
//    private List<Room> listroomforSpn;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_room2);
//    }
//    private void anhxa(){
//        edid=findViewById(R.id.rID);
//        edname=findViewById(R.id.rName);
//        edavatar=findViewById(R.id.rAvatar);
//        edrtid=findViewById(R.id.rtID);
//        add=findViewById(R.id.rtButton);
//        back = findViewById(R.id.btn_back);btn_back
//        goRoomtype=findViewById(R.id.btn_goroomtype);
//        rcvRoom=findViewById(R.id.r_rcv_roomtype);
//        spinner = findViewById(R.id.spnchooseRoomType);
//        List<RoomType> listRoomType = new ArrayList<>();
//        FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
//        DatabaseReference mRef = mAuth.getReference("Room");
//        listRoomType.add(0,new RoomType("0","Chọn phòng ban"));
//        ArrayAdapter<RoomType> departmentArrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listRoomType);
//        departmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spnChooseRoomType.setAdapter(departmentArrayAdapter);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rcvRoom.setLayoutManager(linearLayoutManager);
//        // tạo đường kẻ ngang
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        rcvRoom.addItemDecoration(dividerItemDecoration);
//        // tạo danh sách phòng
//        lstRoom = new ArrayList<>();
//        roomAdapter = new RoomAdapter(lstRoom, new RoomAdapter.RoomCallBack() {
//            @Override
//            public void onClickUpdate(Room room) {
//                openDialogUpdateItem(room);
//            }
//
//            @Override
//            public void onClickDelete(Room room) {
//                onclickDeleteData(room);
//            }
//        });
//
//        rcvRoom.setAdapter(roomAdapter);
//    }
//    private void getlstRoomFromFirebase(){
//        FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
//        DatabaseReference mRef = mAuth.getReference("Room");
//        Query query= mRef.orderByChild("roomID");
//        //Cách 2
//        query.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Room room = snapshot.getValue(Room.class);
//                if(room!=null){
//                    lstRoom.add(room);
//                    roomAdapter.notifyDataSetChanged();
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Room room = snapshot.getValue(Room.class);
//                if (room==null||lstRoom ==null|| lstRoom.isEmpty()){return;}
//                for (int i =0 ;i<lstRoom.size();i++){
//                    if (room.getRoomID() == lstRoom.get(i).getRoomID()){
//                        lstRoom.set(i,room);
//                        break;
//                    }
//                }
//
//                roomAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                Room room = snapshot.getValue(Room.class);
//                if (room==null||lstRoom ==null|| lstRoom.isEmpty()){return;}
//                for (int i =0 ;i<lstRoom.size();i++){
//                    if (room.getRoomID() == lstRoom.get(i).getRoomID()){
//                        lstRoom.remove(lstRoom.get(i));
//                        break;
//                    }
//                }
//                roomAdapter.notifyDataSetChanged();
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
////        Query rtID = FirebaseDatabase.getInstance().getReference().child("RoomType").child("id");
////        Query rtName = FirebaseDatabase.getInstance().getReference().child("RoomType").child("rtname");
////        Query r_rtID = FirebaseDatabase.getInstance().getReference().child("Room").child("roomid");
////        if (rtID==r_rtID){
////            Room room = new Room();
////            room.setRtname(rtName);
////        }
//
//    }
//}

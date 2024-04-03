package com.example.bookingapp.Database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.Utils;


import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private List<Room> lstRoom;
    private RoomCallBack callBack;

    public interface RoomCallBack{
        void onClickUpdate(Room room);
        void onClickDelete(Room room);
    }
    public RoomAdapter(List<Room> mlstRoom, RoomCallBack callBack) {
        this.lstRoom = mlstRoom;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public RoomAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room,parent,false);
        return new RoomAdapter.RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.RoomViewHolder holder, int position) {
        Room room = lstRoom.get(position);
        if(room==null){return;}
//        holder.roomAvt.setImageBitmap(Utils.convertToBitmapFromAssets(mcontext, room.getRoomAvatar()));
        holder.roomID.setText("ID: "+ room.getRoomID());
        holder.roomName.setText("Name: "+ room.getRoomName());
        holder.roomtypeName.setText("Loại Phòng: "+room.getRtid());
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickUpdate(room);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickDelete(room);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(lstRoom != null)
        {
            return lstRoom.size();
        }
        return 0;
    }
    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private ImageView roomAvt;
        private TextView roomID;
        private TextView roomName;
        private TextView roomtypeName;
        private Button btnUpdate;
        private Button btnDelete;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            roomAvt = itemView.findViewById(R.id.r_Avatar);
            roomID = itemView.findViewById(R.id.tv_r_id);
            roomName = itemView.findViewById(R.id.tv_r_name);
            roomtypeName = itemView.findViewById(R.id.tv_r_rtname);
            btnUpdate = itemView.findViewById(R.id.btn_r_update);
            btnDelete = itemView.findViewById(R.id.btn_r_delete);
        }
    }

}

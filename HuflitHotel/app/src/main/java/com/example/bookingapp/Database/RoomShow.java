package com.example.bookingapp.Database;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RoomShow extends RecyclerView.Adapter<RoomShow.RoomShowViewHolder> {
    private List<Room> lstRoom;
    private RoomAdapter.RoomCallBack callBack;
    Context context;

    public RoomShow(List<Room> mlstRoom) {
        this.lstRoom = mlstRoom;
    }

    @NonNull
    @Override
    public RoomShow.RoomShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutitem,parent,false);
        return new RoomShow.RoomShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomShowViewHolder holder, int position) {
        Room room = lstRoom.get(position);
        if (room == null) {
            return;
        }
        holder.roomAvt.setImageBitmap(Utils.convertToBitmapFromAssets(context,room.getRoomAvatar()));
//        holder.roomID.setText("ID: " + room.getRoomID());
        holder.roomName.setText(room.getRoomName());
        holder.roomtypeName.setText("Loại: " + room.getRtid());
    }


    @Override
    public int getItemCount() {
        if (lstRoom != null) {
            return lstRoom.size();
        }
        return 0;
    }

    public class RoomShowViewHolder extends RecyclerView.ViewHolder {
        private ImageView roomAvt;
        private TextView roomID;
        private TextView roomName;
        private TextView roomtypeName;

        public RoomShowViewHolder(@NonNull View itemView) {
            super(itemView);
            roomAvt = itemView.findViewById(R.id.ivAvatar);
//            roomID = itemView.findViewById(R.id.tv_itemID);
            roomName = itemView.findViewById(R.id.tv_itemName);
            roomtypeName = itemView.findViewById(R.id.tv_typeName);
        }
    }
}
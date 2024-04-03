package com.example.bookingapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapter.RoomTypeViewHolder> {
    private List<RoomType> lstRoomType;
    private  IclickListener iclickListener;
    public interface IclickListener{
        void onClickUpdate(RoomType roomType);
        void onClickDelete(RoomType roomType);

    }
    public RoomTypeAdapter(List<RoomType> mlstRoomType,IclickListener listener) {
        this.lstRoomType = mlstRoomType;
        this.iclickListener = listener;
    }

    @NonNull
    @Override
    public RoomTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roomtype,parent,false);
        return new RoomTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomTypeViewHolder holder, int position) {
        RoomType roomType = lstRoomType.get(position);
        if(roomType==null){return;}
        holder.tvID.setText("ID: "+roomType.getId());
        holder.tvName.setText("Name: "+ roomType.getRtname());

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iclickListener.onClickUpdate(roomType);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iclickListener.onClickDelete(roomType);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(lstRoomType != null)
        {
            return lstRoomType.size();
        }
        return 0;
    }
    public class RoomTypeViewHolder extends RecyclerView.ViewHolder{

        private TextView tvID;
        private TextView tvName;
        private Button btnUpdate;
        private Button btnDelete;

        public RoomTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_rtid);
            tvName = itemView.findViewById(R.id.tv_rtname);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
//    public static ArrayList<RoomType> getAll(Context context){
//        ArrayList<RoomType> lstRoomType = new ArrayList<>();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference("RoomType");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String id = snapshot.getValue(String.class);
//                String name = snapshot.getValue(String.class);
//                lstRoomType.add(new RoomType(id,name));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return lstRoomType;
//    }
}

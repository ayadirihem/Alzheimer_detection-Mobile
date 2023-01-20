package com.example.alzheimer_detection;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PredictListAdapter extends RecyclerView.Adapter<PredictListAdapter.ViewHolder> {

    private List<ListPredictData> listData;
    Context c;

    public PredictListAdapter(Context c, List<ListPredictData> listData){
        this.listData = listData;
        this.c = c;
    }

    @NonNull
    @Override
    public PredictListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(c).inflate(R.layout.row,parent, false));
    }

    public void setListData(List<ListPredictData> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PredictListAdapter.ViewHolder holder, int position) {
        final ListPredictData data = listData.get(position);
        holder.Fullname.setText("Name : "+data.getFullName());
        holder.Age.setText("Age: "+data.getAge());
        holder.Group.setText("Prediction: "+data.getGroup());
    }

    public void delete(int position) {
        // creating a variable for our Database
        // Reference for Firebase.
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference().child(listData.get(position).getId());
        // we are use add listerner
        // for event listener method
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // remove the value at reference
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView Fullname, Age, Group;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.Fullname = (TextView) itemView.findViewById(R.id.FullName);
            this.Age = (TextView) itemView.findViewById(R.id.Age);
            this.Group =(TextView) itemView.findViewById(R.id.Group);
            this.image = (ImageView) itemView.findViewById(R.id.imageP);
        }
    }
}

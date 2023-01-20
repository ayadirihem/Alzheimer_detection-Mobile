package com.example.alzheimer_detection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PredictList extends AppCompatActivity {

    // Use the FloatingActionButton for all the add person
    // and add alarm
    FloatingActionButton mAddAlarmFab, mAddPersonFab;

    // Use the ExtendedFloatingActionButton to handle the
    // parent FAB
    ExtendedFloatingActionButton mAddFab;

    // These TextViews are taken to make visible and
    // invisible along with FABs except parent FAB's action
    // name
    TextView addAlarmActionText, addPersonActionText;

    // to check whether sub FABs are visible or not
    Boolean isAllFabsVisible;

    private CoordinatorLayout coordinatorLayout;

    RecyclerView recyclerView;
    private FirebaseDatabase mDatabase;
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_list);
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDatabase.getReference("PatientSheet");
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Predict);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),Dashbord.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Test:
                        startActivity(new Intent(getApplicationContext(),QuizHome.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Info:
                        startActivity(new Intent(getApplicationContext(), InfoActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Predict:
                        return true;
                }
                return false;
            }
        });
        List<ListPredictData> data = new ArrayList<>();

        mAddFab = findViewById(R.id.add_fab);
        // FAB button

        mAddPersonFab = findViewById(R.id.add_person_fab);

        // Also register the action name text, of all the
        // FABs. except parent FAB action name text

        addPersonActionText = findViewById(R.id.add_person_action_text);

        // Now set all the FABs and all the action name
        // texts as GONE
        mAddPersonFab.setVisibility(View.GONE);
        addPersonActionText.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.Patient_List);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PredictListAdapter adapter = new PredictListAdapter(PredictList.this,null);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot messageSnapshot: snapshot.getChildren()) {
                    PatientSheet iM1 = messageSnapshot.getValue(PatientSheet.class);
                    String key = messageSnapshot.getKey();
                    data.add(new ListPredictData(key,iM1.getFullName(), String.valueOf(iM1.getAge()),iM1.getGroup(), iM1.getEDUC(),iM1.getSES(), iM1.getMMSE(), iM1.getCDR(), iM1.geteTIV(),iM1.getnWBV(), iM1.getASF(), iM1.getGender()));
                    adapter.setListData(data);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                RecyclerTouchListener touchListener = new RecyclerTouchListener(PredictList.this, recyclerView);
                touchListener
                        .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                            @Override
                            public void onRowClicked(int position) {
                                ShowDetailDialog(data.get(position));
                            }

                            @Override
                            public void onIndependentViewClicked(int independentViewID, int position) {

                            }
                        })
                        .setSwipeOptionViews(R.id.delete_task,R.id.edit_task)
                        .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                            @Override
                            public void onSwipeOptionClicked(int viewID, int position) {
                                switch (viewID){
                                    case R.id.delete_task:
                                        String uid = data.get(position).getId();
                                        myRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot messageSnapshot: snapshot.getChildren()) {
                                                    if (messageSnapshot.getKey().equals(uid)) {
                                                        messageSnapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Snackbar.make(findViewById(android.R.id.content), "Patient have been deleted with success", Snackbar.LENGTH_LONG).show();
                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Snackbar.make(findViewById(android.R.id.content), "Oooops , ", Snackbar.LENGTH_LONG).show();
                                            }

                                        });
                                        break;

                                    case R.id.edit_task:
                                        Intent intent = new Intent(PredictList.this, UpdateForm.class);
                                        intent.putExtra("id", data.get(position).getId());
                                        intent.putExtra("FullName", data.get(position).getFullName());
                                        intent.putExtra("Age", data.get(position).getAge());
                                        intent.putExtra("EDUC",data.get(position).getEDUC());
                                        intent.putExtra("SES", data.get(position).getSES());
                                        intent.putExtra("MMSE", data.get(position).getMMSE());
                                        intent.putExtra("MMSE",data.get(position).getMMSE());
                                        intent.putExtra("CDR", data.get(position).getCDR());
                                        intent.putExtra("eTIV", data.get(position).geteTIV());
                                        intent.putExtra("nWBV", data.get(position).getnWBV());
                                        intent.putExtra("ASF", data.get(position).getASF());
                                        intent.putExtra("Gender", data.get(position).getGender());
                                        startActivity(intent);

                                }
                            }
                        });
                recyclerView.addOnItemTouchListener(touchListener);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: "+ error.getCode());
            }

        });



        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are
        // invisible
        isAllFabsVisible = false;

        // Set the Extended floating action button to
        // shrinked state initially
        mAddFab.shrink();

        // We will make all the FABs and action name texts
        // visible only when Parent FAB button is clicked So
        // we have to handle the Parent FAB button first, by
        // using setOnClickListener you can see below
        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {

                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs VISIBLE.
                            mAddPersonFab.show();
                            addPersonActionText.setVisibility(View.VISIBLE);

                            // Now extend the parent FAB, as
                            // user clicks on the shrinked
                            // parent FAB
                            mAddFab.extend();

                            // make the boolean variable true as
                            // we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = true;
                        } else {

                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs GONE.
                            mAddPersonFab.hide();
                            addPersonActionText.setVisibility(View.GONE);

                            // Set the FAB to shrink after user
                            // closes all the sub FABs
                            mAddFab.shrink();

                            // make the boolean variable false
                            // as we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = false;
                        }
                    }
                });

        // below is the sample action to handle add person
        // FAB. Here it shows simple Toast msg. The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        mAddPersonFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PredictList.this, AddPredict.class);
                        startActivity(intent);
                    }
                });



    }

    public void ShowDetailDialog(ListPredictData predict){
        AlertDialog.Builder alert = new AlertDialog.Builder(PredictList.this);
        final View layout = getLayoutInflater().inflate(R.layout.viewp_dialog, null);
        TextView name = layout.findViewById(R.id.Name);
        TextView educ = layout.findViewById(R.id.educ_v);
        TextView ses = layout.findViewById(R.id.ses_v);
        TextView mmse = layout.findViewById(R.id.mmse_v);
        TextView etiv = layout.findViewById(R.id.eTIV_v);
        TextView nwbv = layout.findViewById(R.id.nwbv_v);
        TextView asf = layout.findViewById(R.id.asf_v);
        TextView gender = layout.findViewById(R.id.gend_v);
        TextView age = layout.findViewById(R.id.age_p);
        TextView predct = layout.findViewById(R.id.predct);
        name.setText("Patient Name: "+predict.getFullName());
        educ.setText(String.valueOf(predict.getEDUC()));
        ses.setText(String.valueOf(predict.getSES()));
        mmse.setText(String.valueOf(predict.getMMSE()));
        etiv.setText(String.valueOf(predict.geteTIV()));
        nwbv.setText(String.valueOf(predict.getnWBV()));
        asf.setText(String.valueOf(predict.getASF()));
        gender.setText(String.valueOf(predict.getGender()));
        predct.setText(predict.getGroup());
        age.setText(String.valueOf(predict.getAge()));
        alert.setView(layout);
        alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog d = alert.create();
        d.show();

    }

}

package com.example.dell.collagepredicter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView recyclerView;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent intent = getIntent();

        final String wanted_branch = intent.getStringExtra("branch");
        final String wanted_rank = intent.getStringExtra("rank");

        mToolbar=(Toolbar)findViewById(R.id.view_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Available Colleges");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("college_list");
        databaseReference.keepSynced(true);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);



        FirebaseRecyclerAdapter<College, ViewCollegeViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<College, ViewCollegeViewHolder>(
                College.class,
                R.layout.single_layout,
                ViewCollegeViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(final ViewCollegeViewHolder viewHolder, College model, int position) {

                final String list_college_id = getRef(position).getKey();

                databaseReference.child(list_college_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String college = dataSnapshot.child("college").getValue().toString();
                        final String branch = dataSnapshot.child("branch").getValue().toString();
                        final String rank = dataSnapshot.child("rank").getValue().toString();

                        int finalRank=Integer.parseInt(rank);
                        int wantedRank=Integer.parseInt(wanted_rank);

                        if(wanted_branch.equals(branch) && finalRank>=wantedRank ){
                            viewHolder.setCollege(college);
                            viewHolder.setBranch(branch);
                            viewHolder.setRank(rank);

                            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                            /*    Intent intent = new Intent(ViewActivity.this, ViewUserEventActivity.class);
                                intent.putExtra("title", header);
                                intent.putExtra("key", key);
                                startActivity(intent);c  */
                                }
                            });
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public static class ViewCollegeViewHolder extends RecyclerView.ViewHolder {
        View item;

        public ViewCollegeViewHolder(View itemView) {
            super(itemView);
            item = itemView;
        }


        public void setCollege(String college) {
            TextView collegeName = (TextView) item.findViewById(R.id.single_college_text);
            collegeName.setText(college);
        }

        public void setBranch(String branch) {
            TextView branchName = (TextView) item.findViewById(R.id.single_branch_text);
            branchName.setText(branch);
        }
        public void setRank(String rank) {
            TextView rankText=(TextView)item.findViewById(R.id.single_rank_text);
            rankText.setText(rank);
        }


    }


}

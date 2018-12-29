package com.example.dell.collagepredicter;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    private EditText rank_text,branch_Text,collage_text;
    private Button upload_Button;
    private DatabaseReference mRootRef;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        rank_text=(EditText)findViewById(R.id.editText_add_rank);
        branch_Text=(EditText)findViewById(R.id.editText_add_branchName);
        upload_Button=(Button)findViewById(R.id.upload_button);
        collage_text=(EditText)findViewById(R.id.editText_add_CollegeName);

        toolbar=(Toolbar)findViewById(R.id.addActivity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add College");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRootRef= FirebaseDatabase.getInstance().getReference();

        upload_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String collegeName=collage_text.getText().toString();
                String branchName=branch_Text.getText().toString();
                String rank=rank_text.getText().toString();

                if(!TextUtils.isEmpty(collegeName) && !TextUtils.isEmpty(branchName) && !TextUtils.isEmpty(rank)) {

                    progressDialog=new ProgressDialog(AddActivity.this);
                    progressDialog.setTitle("Upload Data");
                    progressDialog.setMessage("please wait while we uploding data..");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);

                    String college_Ref="college_list/";

                    DatabaseReference college_push=mRootRef.child("college_list").push();
                    String college_push_id=college_push.getKey();

                    Map messageMap=new HashMap();
                    messageMap.put("college",collegeName);
                    messageMap.put("branch",branchName);
                    messageMap.put("rank",rank);

                    Map messageUserMap=new HashMap();
                    messageUserMap.put(college_Ref+"/"+college_push_id,messageMap);

                    collage_text.setText("");
                    branch_Text.setText("");
                    rank_text.setText("");

                    mRootRef.updateChildren(messageUserMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(AddActivity.this,"data uploded",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(AddActivity.this,"data is not uploded",Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }


            }
        });



    }




}

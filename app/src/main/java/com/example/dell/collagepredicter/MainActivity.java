package com.example.dell.collagepredicter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private EditText rank_text,branch_Text;
    private Button predict_Button;
    private Toolbar mToolbar;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rank_text=(EditText)findViewById(R.id.editText_Rank);
        spinner=(Spinner)findViewById(R.id.spinner);
        predict_Button=(Button)findViewById(R.id.predict_button);

        mToolbar=(Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("KNIT Literary");

         predict_Button.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                String branch=spinner.getSelectedItem().toString();
                String rank=rank_text.getText().toString();

                if(!TextUtils.isEmpty(branch) && !TextUtils.isEmpty(rank)) {
                    Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                    intent.putExtra("branch", branch);
                    intent.putExtra("rank", rank);
                    startActivity(intent);
                }

                }
                });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.login_button){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        return true;
    }

}

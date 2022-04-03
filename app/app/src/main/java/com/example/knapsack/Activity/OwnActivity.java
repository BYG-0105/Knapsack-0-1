package com.example.knapsack.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knapsack.R;


public class OwnActivity extends AppCompatActivity implements View.OnClickListener {

    String name;
    String table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own);


        findViewById(R.id.btn_xgmm).setOnClickListener(this);
        findViewById(R.id.btn_xgmes).setOnClickListener(this);
        findViewById(R.id.button_finish).setOnClickListener(this);

        TextView tvname = findViewById(R.id.tvname);
        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        table = intent.getStringExtra("table");
        tvname.setText("欢迎您，"+name+" ! 您已成功登录");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_xgmm:
                Intent intentm = new Intent(OwnActivity.this,ChangePwdActivity.class);
                intentm.putExtra("username",name);
                intentm.putExtra("table",table);
                startActivity(intentm);
                break;
            case R.id.btn_xgmes:
                Intent intentmes = new Intent(OwnActivity.this,RegisterActivity.class);
                intentmes.putExtra("username",name);
                intentmes.putExtra("table",table);
                startActivity(intentmes);
                break;
            case R.id.button_finish:
                Intent intentf = new Intent(OwnActivity.this,MainActivity.class);
                intentf.putExtra("username", name);
                intentf.putExtra("table",table);
                startActivity(intentf);
                break;
        }

    }
}

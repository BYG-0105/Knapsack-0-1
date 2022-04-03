package com.example.knapsack.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.knapsack.R;

public class GeneticActivity extends AppCompatActivity implements View.OnClickListener{
    String name;
    String table;
    TextView result;
    Button button;
    private ImageView tanxin,main,huisu,dtgh,paint,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genetic);
        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        table = intent.getStringExtra("table");
        initview();
    }




    public void  initview()
    {
        back = findViewById(R.id.back);
        tanxin = findViewById(R.id.im_tanxin);
        main = findViewById(R.id.im_main);
        huisu = findViewById(R.id.im_huisu);
        dtgh = findViewById(R.id.im_dtgh);
        paint = findViewById(R.id.im_paint);
        button = findViewById(R.id.button_save);
        result = findViewById(R.id.tv_result);


        back.setOnClickListener(this);

        tanxin.setOnClickListener(this);
        main.setOnClickListener(this);
        huisu.setOnClickListener(this);
        dtgh.setOnClickListener(this);
        paint.setOnClickListener(this);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_tanxin:
                Intent intentt = new Intent(GeneticActivity.this,GreedyActivity.class);
                intentt.putExtra("username",name);
                intentt.putExtra("table",table);
                startActivity(intentt);
                break;
            case R.id.im_main:
                Intent intentm = new Intent(GeneticActivity.this,MainActivity.class);
                intentm.putExtra("username",name);
                intentm.putExtra("table",table);
                startActivity(intentm);
                break;
            case R.id.im_ycsf:
                Intent intenty = new Intent(GeneticActivity.this,GeneticActivity.class);
                intenty.putExtra("username",name);
                intenty.putExtra("table",table);
                startActivity(intenty);
                break;
            case R.id.im_dtgh:
                Intent intentd = new Intent(GeneticActivity.this,DynamicActivity.class);
                intentd.putExtra("username",name);
                intentd.putExtra("table",table);
                startActivity(intentd);
                break;
            case R.id.im_paint:
                Intent intentp = new Intent(GeneticActivity.this,PaintActivity.class);
                intentp.putExtra("username",name);
                intentp.putExtra("table",table);
                startActivity(intentp);
                break;
        }
    }
}
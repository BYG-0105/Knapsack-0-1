package com.example.knapsack.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.knapsack.Bean.Goods;
import com.example.knapsack.R;
import com.example.knapsack.Service.Algorithm;
import com.example.knapsack.adapter.ResultAdapter;

import java.util.ArrayList;
import java.util.List;

public class DynamicActivity extends AppCompatActivity  implements View.OnClickListener {

    String name;
    String table;
    TextView result;
    Button button;
    ListView listView;
    private ImageView tanxin,main,huisu,gene,paint,back;
    public List<Goods> goods = new ArrayList<>();//用于存放商品列表
    public ResultAdapter resultAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        table = intent.getStringExtra("table");
        goods = intent.getParcelableArrayListExtra("list");
        initview();

        Algorithm algorithm = new Algorithm();
        goods = algorithm.Dynamic(goods);
        resultAdapter = new ResultAdapter(DynamicActivity.this,goods);
        listView.setAdapter(resultAdapter);
    }

    public void  initview()
    {
        back = findViewById(R.id.back);
        tanxin = findViewById(R.id.im_tanxin);
        main = findViewById(R.id.im_main);
        huisu = findViewById(R.id.im_huisu);
        gene = findViewById(R.id.im_ycsf);
        paint = findViewById(R.id.im_paint);
        button = findViewById(R.id.button_save);
        result = findViewById(R.id.tv_result);
        listView = findViewById(R.id.list);

        back.setOnClickListener(this);

        tanxin.setOnClickListener(this);
        main.setOnClickListener(this);
        huisu.setOnClickListener(this);
        gene.setOnClickListener(this);
        paint.setOnClickListener(this);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_tanxin:
                Intent intentt = new Intent(DynamicActivity.this,GreedyActivity.class);
                intentt.putExtra("username",name);
                intentt.putExtra("table",table);
                startActivity(intentt);
                break;
            case R.id.im_main:
                Intent intentm = new Intent(DynamicActivity.this,MainActivity.class);
                intentm.putExtra("username",name);
                intentm.putExtra("table",table);
                startActivity(intentm);
                break;
            case R.id.im_ycsf:
                Intent intenty = new Intent(DynamicActivity.this,GeneticActivity.class);
                intenty.putExtra("username",name);
                intenty.putExtra("table",table);
                startActivity(intenty);
                break;
            case R.id.im_dtgh:
                Intent intentd = new Intent(DynamicActivity.this,DynamicActivity.class);
                intentd.putExtra("username",name);
                intentd.putExtra("table",table);
                startActivity(intentd);
                break;
            case R.id.im_paint:
                Intent intentp = new Intent(DynamicActivity.this,PaintActivity.class);
                intentp.putExtra("username",name);
                intentp.putExtra("table",table);
                startActivity(intentp);
                break;
        }
    }

}
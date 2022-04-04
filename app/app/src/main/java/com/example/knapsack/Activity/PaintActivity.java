package com.example.knapsack.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.knapsack.Bean.Goods;
import com.example.knapsack.R;
import com.example.knapsack.Service.ScatterView;

import java.util.ArrayList;
import java.util.List;

public class PaintActivity extends AppCompatActivity implements View.OnClickListener{
    String name;
    String table;
    TextView result;
    Button button;
    private ImageView tanxin,main,huisu,gene,dtgh,back;
    public List<Goods> goods = new ArrayList<>();//用于存放商品列表
    List<int []> xData = new ArrayList<>();
    List<int []> yData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        table = intent.getStringExtra("table");
        goods = intent.getParcelableArrayListExtra("list");
        int [] x = new int[10000];
        int [] y = new int[10000];
        for(int i = 1;i < goods.size();i++)
        {
            x[i-1] = goods.get(i).getWeight();
            y[i-1] = (int) goods.get(i).getValue();
        }
        xData.add(x);
        yData.add(y);
        ScatterView scatterView = new ScatterView(this,yData,xData);
        initview();
    }


    public void  initview()
    {
        back = findViewById(R.id.back);
        tanxin = findViewById(R.id.im_tanxin);
        main = findViewById(R.id.im_main);
        huisu = findViewById(R.id.im_huisu);
        gene = findViewById(R.id.im_ycsf);
        dtgh = findViewById(R.id.im_dtgh);
        button = findViewById(R.id.button_save);
        result = findViewById(R.id.tv_result);


        back.setOnClickListener(this);

        tanxin.setOnClickListener(this);
        main.setOnClickListener(this);
        huisu.setOnClickListener(this);
        gene.setOnClickListener(this);
        dtgh.setOnClickListener(this);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_tanxin:
                Intent intentt = new Intent(PaintActivity.this,GreedyActivity.class);
                intentt.putExtra("username",name);
                intentt.putExtra("table",table);
                startActivity(intentt);
                break;
            case R.id.im_main:
                Intent intentm = new Intent(PaintActivity.this,MainActivity.class);
                intentm.putExtra("username",name);
                intentm.putExtra("table",table);
                startActivity(intentm);
                break;
            case R.id.im_ycsf:
                Intent intenty = new Intent(PaintActivity.this,GeneticActivity.class);
                intenty.putExtra("username",name);
                intenty.putExtra("table",table);
                startActivity(intenty);
                break;
            case R.id.im_dtgh:
                Intent intentd = new Intent(PaintActivity.this,DynamicActivity.class);
                intentd.putExtra("username",name);
                intentd.putExtra("table",table);
                startActivity(intentd);
                break;
            case R.id.im_paint:
                Intent intentp = new Intent(PaintActivity.this,PaintActivity.class);
                intentp.putExtra("username",name);
                intentp.putExtra("table",table);
                startActivity(intentp);
                break;
        }
    }


}
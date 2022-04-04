package com.example.knapsack.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knapsack.Bean.Goods;
import com.example.knapsack.R;
import com.example.knapsack.Service.DLLog;

import java.util.ArrayList;
import java.util.List;


public class OwnActivity extends AppCompatActivity implements View.OnClickListener {

    String name;
    String table;
    public List<Goods> goods = new ArrayList<>();//用于存放商品列表

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
        goods = intent.getParcelableArrayListExtra("list");
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
                intentm.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentm);
                DLLog.i("个人中心界面", "用户进行页面跳转操作，跳转至修改密码界面");
                break;
            case R.id.btn_xgmes:
                Intent intentmes = new Intent(OwnActivity.this,RegisterActivity.class);
                intentmes.putExtra("username",name);
                intentmes.putExtra("table",table);
                intentmes.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentmes);
                DLLog.i("个人中心界面", "用户进行页面跳转操作，跳转至修改信息界面");
                break;
            case R.id.button_finish:
                Intent intentf = new Intent(OwnActivity.this,MainActivity.class);
                intentf.putExtra("username", name);
                intentf.putExtra("table",table);
                intentf.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentf);
                DLLog.i("个人中心界面", "用户进行页面跳转操作，跳转至主界面界面");
                break;
        }

    }
}

package com.example.knapsack.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knapsack.Bean.Goods;
import com.example.knapsack.R;
import com.example.knapsack.Service.Algorithm;
import com.example.knapsack.Service.ExcelUtil;
import com.example.knapsack.adapter.GoodsAdapter;
import com.example.knapsack.adapter.ResultAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class BacktrackingActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST = 112;
    String name;
    String table;
    TextView result;
    Button button;
    ListView listView;
    private ImageView tanxin,main,dtgh,gene,paint,back;
    public List<Goods> goods = new ArrayList<>();//用于存放商品列表
    public ResultAdapter resultAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backtracking);

        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        table = intent.getStringExtra("table");

        goods = intent.getParcelableArrayListExtra("list");
        initview();

        checkNeedPermissions();
        Algorithm algorithm = new Algorithm();
        goods = algorithm.Backtracking(goods);
        resultAdapter = new ResultAdapter(BacktrackingActivity.this,goods);
        listView.setAdapter(resultAdapter);
    }

    private void checkNeedPermissions(){
        //6.0以上需要动态申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //多个权限一起申请
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
            if (Build.VERSION.SDK_INT >= 23) {
                int REQUEST_CODE_CONTACT = 101;
                String[] permissions = {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //验证是否许可权限
                for (String str : permissions) {
                    if (BacktrackingActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        //申请权限
                        BacktrackingActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                        return;
                    } else {
                        //这里就是权限打开之后自己要操作的逻辑
                    }
                }
            }

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //do here
                } else {
                    Toast.makeText(BacktrackingActivity.this, "The app was not allowed to read your store.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void  initview()
    {
        back = findViewById(R.id.back);
        tanxin = findViewById(R.id.im_tanxin);
        main = findViewById(R.id.im_main);
        dtgh = findViewById(R.id.im_dtgh);
        gene = findViewById(R.id.im_ycsf);
        paint = findViewById(R.id.im_paint);
        button = findViewById(R.id.button_save);
        result = findViewById(R.id.tv_resultb);
        listView = findViewById(R.id.list);

        back.setOnClickListener(this);

        tanxin.setOnClickListener(this);
        main.setOnClickListener(this);
        dtgh.setOnClickListener(this);
        gene.setOnClickListener(this);
        paint.setOnClickListener(this);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_tanxin:
                Intent intentt = new Intent(BacktrackingActivity.this,GreedyActivity.class);
                intentt.putExtra("username",name);
                intentt.putExtra("table",table);
                startActivity(intentt);
                break;
            case R.id.im_main:
                Intent intentm = new Intent(BacktrackingActivity.this,MainActivity.class);
                intentm.putExtra("username",name);
                intentm.putExtra("table",table);
                startActivity(intentm);
                break;
            case R.id.im_ycsf:
                Intent intenty = new Intent(BacktrackingActivity.this,GeneticActivity.class);
                intenty.putExtra("username",name);
                intenty.putExtra("table",table);
                startActivity(intenty);
                break;
            case R.id.im_dtgh:
                Intent intentd = new Intent(BacktrackingActivity.this,DynamicActivity.class);
                intentd.putExtra("username",name);
                intentd.putExtra("table",table);
                startActivity(intentd);
                break;
            case R.id.im_paint:
                Intent intentp = new Intent(BacktrackingActivity.this,PaintActivity.class);
                intentp.putExtra("username",name);
                intentp.putExtra("table",table);
                startActivity(intentp);
                break;
            case R.id.button_save:

                String filePath ="/data"
                        + Environment.getDataDirectory().getAbsolutePath() + "/"
                        +  "com.example.knapsack" + "/file";
                File file = new File(filePath);
                if (!file.exists()) {
                    file.mkdirs();
                }

                String excelFileName = "/data.xls";


                String[] title = {"物品重量", "物品价值", "物品价值重量比","物品是否被选用"};
                String sheetName = "回溯法解决0-1背包问题结果";

                filePath = filePath+excelFileName;
                ExcelUtil.initExcel(filePath, sheetName, title);
                ExcelUtil.writeObjListToExcel(goods, filePath, BacktrackingActivity.this);
                //Toast.makeText(BacktrackingActivity.this,"excel已导出至："+filePath,Toast.LENGTH_LONG).show();
                break;
        }
    }
}
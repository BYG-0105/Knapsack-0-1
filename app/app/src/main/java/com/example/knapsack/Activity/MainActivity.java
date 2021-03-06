package com.example.knapsack.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.knapsack.Bean.Goods;
import com.example.knapsack.R;
import com.example.knapsack.Service.Algorithm;
import com.example.knapsack.Service.DLLog;
import com.example.knapsack.adapter.GoodsAdapter;
import com.example.knapsack.database.Beibao;
import com.example.knapsack.database.DBManager;
import com.example.knapsack.database.Users;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //控件定义
    private ImageView back,change;
    private ImageView tanxin,huisu,dtgh,gene,paint;
    private Button jisuan;
    private TextView username;
    private ListView listView;

    public List<Goods> goods = new ArrayList<>();//用于存放商品列表
    //定义常量
    int iconsort = 0;
    int back1 = 0;
    String name;
    String table;
    String logmes;
    //定义用户和商品对象
    Users users = new Users();
    Beibao beibao = new Beibao();
    //数据库变量
    public DBManager dbHelper;
    private SQLiteDatabase database;
    private GoodsAdapter goodsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkNeedPermissions();
        initview();
        Intent intent = getIntent();
        name = intent.getStringExtra("username");

        username.setText("欢迎您！"+name);
        dbHelper = new DBManager(this);
        dbHelper.openDatabase();
        dbHelper.closeDatabase();

        database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
        table = intent.getStringExtra("table");
        if(table != null )
        {
            goods = beibao.query(table);
        }
        goodsAdapter = new GoodsAdapter(MainActivity.this,goods);
        listView.setAdapter(goodsAdapter);
        logmes = intent.getStringExtra("log");
       // database.close();

        DLLog.i("主界面", "已成功进入主界面");
    }

    //初始化界面控件
    private void initview() {
        back = findViewById(R.id.back);
        change = findViewById(R.id.save);
        tanxin = findViewById(R.id.im_tanxin);
        huisu = findViewById(R.id.im_huisu);
        dtgh = findViewById(R.id.im_dtgh);
        gene = findViewById(R.id.im_ycsf);
        paint = findViewById(R.id.im_paint);
        jisuan = findViewById(R.id.cal);
        listView  = findViewById(R.id.list);
        username = findViewById(R.id.user_name);


        back.setOnClickListener(this);
        change.setOnClickListener(this);
        tanxin.setOnClickListener(this);
        huisu.setOnClickListener(this);
        dtgh.setOnClickListener(this);
        gene.setOnClickListener(this);
        paint.setOnClickListener(this);
        jisuan.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("请选择返回界面")
                        .setIcon(R.drawable.save)
                        .setSingleChoiceItems(new String[]{"个人中心","登录界面"},
                                back1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        back1=which;
                                    }
                                })
                        .setPositiveButton("确定", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                       if(back1 == 0)
                                       {
                                           Intent intentb = new Intent(MainActivity.this,OwnActivity.class);
                                           intentb.putExtra("username",name);
                                           intentb.putExtra("table",table);
                                           DLLog.i("主界面", "用户进行页面跳转操作，跳转至个人中心界面");
                                           startActivity(intentb);
                                       }
                                        if(back1 == 1)
                                        {
                                            Intent intentb = new Intent(MainActivity.this,LoginActivity.class);
                                            intentb.putExtra("username",name);
                                            intentb.putExtra("table",table);
                                            DLLog.i("主界面", "用户进行页面跳转操作，跳转至登录界面");
                                            startActivity(intentb);
                                        }
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("取消", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DLLog.i("主界面", "用户进行页面跳转操作，已取消该操作");
                                        dialog.dismiss();
                                    }
                                });
                dialog=builder.create();
                dialog.show();
                break;
            case R.id.save:
                AlertDialog dialogs;
                AlertDialog.Builder builders=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("请选择数据集")
                        .setIcon(R.drawable.save)
                        .setSingleChoiceItems(new String[]{"beibeo0","beibeo1","beibeo2","beibeo3","beibeo4","beibeo5","beibeo6","beibeo7","beibeo8","beibeo9"},
                                iconsort, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        iconsort=which;

                                    }
                                })
                        .setPositiveButton("确定", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        table = "beibao"+iconsort;
                                        goods = beibao.query(table);
                                        goodsAdapter = new GoodsAdapter(MainActivity.this,goods);
                                        listView.setAdapter(goodsAdapter);
                                        DLLog.i("主界面", "用户进行选择数据集操作，已选择数据集"+table);
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("取消", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DLLog.i("主界面", "用户进行选择数据集操作，已取消该操作");
                                        dialog.dismiss();
                                    }
                                });
                dialogs=builders.create();
                dialogs.show();

                break;
            case R.id.im_tanxin:
                Intent intentt = new Intent(MainActivity.this,GreedyActivity.class);
                intentt.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                intentt.putExtra("username",name);
                intentt.putExtra("table",table);
                DLLog.i("主界面", "用户进行页面跳转操作，跳转至贪心算法界面");
                startActivity(intentt);
                break;
            case R.id.im_huisu:
                Intent intenth = new Intent(MainActivity.this,BacktrackingActivity.class);
                intenth.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                intenth.putExtra("username",name);
                intenth.putExtra("table",table);
                DLLog.i("主界面", "用户进行页面跳转操作，跳转至回溯算法界面");
                startActivity(intenth);
                break;
            case R.id.im_ycsf:
                Intent intenty = new Intent(MainActivity.this,GeneticActivity.class);
                intenty.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                intenty.putExtra("username",name);
                intenty.putExtra("table",table);
                DLLog.i("主界面", "用户进行页面跳转操作，跳转至遗传算法界面");
                startActivity(intenty);
                break;
            case R.id.im_dtgh:
                Intent intentd = new Intent(MainActivity.this,DynamicActivity.class);
                intentd.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                intentd.putExtra("username",name);
                intentd.putExtra("table",table);
                DLLog.i("主界面", "用户进行页面跳转操作，跳转至动态规划算法界面");
                startActivity(intentd);
                break;
            case R.id.im_paint:
                Intent intentp = new Intent(MainActivity.this,BubbleChartActivity.class);
                intentp.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                intentp.putExtra("username",name);
                intentp.putExtra("table",table);
                DLLog.i("主界面", "用户进行页面跳转操作，跳转至绘制图像界面");
                startActivity(intentp);
                break;
            case R.id.cal:
                for(int i = 1;i < goods.size();i++)
                {
                    Goods g = goods.get(i);
                    double d = g.getValue()/g.getWeight();
                    // d = new BigDecimal(d.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
                    g.setWvproportion( d);
                    goods.get(i).setWvproportion(g.getWvproportion());
                }
                Algorithm algorithm = new Algorithm();
                goods = algorithm.quickSort(goods,0,goods.size()-1);
                /*
                Collections.sort(goods, new Comparator<Goods>(){

                    /*
                     * int compare(Goods o1, Goods o2) 返回一个基本类型的整型，
                     * 返回负数表示：o1 小于o2，
                     * 返回0 表示：o1和o2相等，
                     * 返回正数表示：o1大于o2。

                    public int compare(Goods o1, Goods o2) {

                        //按照学生的年龄进行降序排列
                        if(o1.getWvproportion() < o2.getWvproportion()){
                            return -1;
                        }
                        if(o1.getWvproportion() == o2.getWvproportion()){
                            return 0;
                        }
                        return 1;
                    }
                });
*/
                goodsAdapter = new GoodsAdapter(MainActivity.this,goods);
                listView.setAdapter(goodsAdapter);
                DLLog.i("主界面", "用户进行计算物品价值重量比操作，并将其排序进行数据查看");
                break;
        }

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
            DLLog.i("主界面", "用户进行动态申请权限操作");
        }

    }

    public void showToast(String message)
    {
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
    }


}
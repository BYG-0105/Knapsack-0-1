package com.example.knapsack.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knapsack.Bean.Goods;
import com.example.knapsack.R;
import com.example.knapsack.Service.Algorithm;
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
    private Button que,jisuan;
    private TextView username;
    private ListView listView;

    public List<Goods> goods = new ArrayList<>();//用于存放商品列表
    //定义常量
    int iconsort = 0;
    int back1 = 0;
    String name;
    String table;
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
       // database.close();

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
        que = findViewById(R.id.que);
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
        que.setOnClickListener(this);
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
                                           startActivity(intentb);
                                       }
                                        if(back1 == 1)
                                        {
                                            Intent intentb = new Intent(MainActivity.this,LoginActivity.class);
                                            intentb.putExtra("username",name);
                                            intentb.putExtra("table",table);
                                            startActivity(intentb);
                                        }
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("取消", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
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
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("取消", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
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
                startActivity(intentt);
                break;
            case R.id.im_huisu:
                Intent intenth = new Intent(MainActivity.this,BacktrackingActivity.class);
                intenth.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                intenth.putExtra("username",name);
                intenth.putExtra("table",table);
                startActivity(intenth);
                break;
            case R.id.im_ycsf:
                Intent intenty = new Intent(MainActivity.this,GeneticActivity.class);
                intenty.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                intenty.putExtra("username",name);
                intenty.putExtra("table",table);
                startActivity(intenty);
                break;
            case R.id.im_dtgh:
                Intent intentd = new Intent(MainActivity.this,DynamicActivity.class);
                intentd.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                intentd.putExtra("username",name);
                intentd.putExtra("table",table);
                startActivity(intentd);
                break;
            case R.id.im_paint:
                Intent intentp = new Intent(MainActivity.this,PaintActivity.class);
                intentp.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                intentp.putExtra("username",name);
                intentp.putExtra("table",table);
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
                break;
        }

    }


    public void showToast(String message)
    {
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
    }


}
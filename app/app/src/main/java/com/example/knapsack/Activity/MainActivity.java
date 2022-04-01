package com.example.knapsack.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;

import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;



import android.os.Parcelable;

import android.view.View;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.knapsack.Bean.Goods;
import com.example.knapsack.R;
import com.example.knapsack.database.DBManager;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back,change;
    private ImageView tanxin,huisu,dtgh,gene,paint;
    private TextView username;
    public List<Goods> musicmesList = new ArrayList<>();
    int iconsort = 0;
    String name;
    public DBManager dbHelper;
    private SQLiteDatabase database;
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



        database.close();



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

        username = findViewById(R.id.user_name);

        album = findViewById(R.id.music_album);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_total = (TextView) findViewById(R.id.tv_total);
        sb = (SeekBar) findViewById(R.id.sbm);
        fir = findViewById(R.id.im_paint);
        sec = findViewById(R.id.im_tanxin);
        ban = findViewById(R.id.im_main);
        ord = findViewById(R.id.im_ycsf);
        alb = findViewById(R.id.im_huisu);
        back.setOnClickListener(this);
        change.setOnClickListener(this);
        last.setOnClickListener(this);
        stop.setOnClickListener(this);
        next.setOnClickListener(this);
        look.setOnClickListener(this);
        fir.setOnClickListener(this);
        sec.setOnClickListener(this);
        ban.setOnClickListener(this);
        ord.setOnClickListener(this);
        alb.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.look:
               if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    showToast("该应用无访问存储权限");
                    checkPermission();
                }
                loadLocalMusicData();
                if(playPosition == -1)
                {
                    showToast("当前无音乐正在播放，请选择你所要播放的音乐");
                    return;
                }
                else
                {
                    musicControl.stopMusic();
                    Intent intent = new Intent(MainActivity.this,MusicActivity.class);
                    intent.putParcelableArrayListExtra("musiclist", (ArrayList<? extends Parcelable>) musicmesList);
                    intent.putExtra("username",name);
                    intent.putExtra("playposition",playPosition+"");
                    startActivity(intent);
                }

                break;
            case R.id.back:
                Intent intent = new Intent(MainActivity.this,WebActivity.class);
                intent.putExtra("username",name);
                startActivity(intent);
                break;
            case R.id.save:
                AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("设置播放顺序")
                        .setIcon(R.drawable.sz)
                        .setSingleChoiceItems(new String[]{"顺序播放","随机播放","循环播放"},
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
                                        playsort = iconsort;
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
            case R.id.iv_last:
                if(playPosition == -1)
                {
                    showToast("当前无音乐正在播放，请选择你所要播放的音乐");
                    return;
                }
                else if(playPosition == 0)
                {
                    showToast("当前歌曲为第一首，没有上一曲了！！！");
                    return;
                }
                else
                {
                    playPosition = playPosition - 1;
                    Musicmes musicmes = musicmesList.get(playPosition);
                    LastNextMusic(musicmes);
                }
                break;
            case R.id.iv_stop:
                if(playPosition == -1)
                {
                    showToast("当前无音乐正在播放，请选择你所要播放的音乐");
                    return;
                }
                else if(musicControl.isplay())
                {
                    //正在播放音乐，进行暂停操作
                    musicControl.pauseMusic();
                    stop.setImageResource(R.drawable.pasue);
                }
                else
                {
                    //此时没有播放音乐，点击开始播放音乐
                    musicControl.playMusic();
                    stop.setImageResource(R.drawable.play);
                }
                break;
            case R.id.iv_next:
                if(playPosition == -1)
                {
                    showToast("当前无音乐正在播放，请选择你所要播放的音乐");
                    return;
                }
                else if(playPosition == musicmesList.size()-1)
                {
                    showToast("当前歌曲为最后一首歌曲，没有下一曲了！！！");
                    return;
                }
                else
                {
                  //  Log.i("geqn1","当前这一首歌曲为"+playPosition+"          ssdff      "+musicmesList.get(playPosition).toString());
                    playPosition = playPosition + 1;
                    Musicmes musicmes = musicmesList.get(playPosition);
                   // Log.i("gw2","下一首歌曲为"+playPosition+"          ssdff      "+musicmes.toString());
                    LastNextMusic(musicmes);

                }
                break;
            case R.id.im_paint:
                Intent intentf = new Intent(MainActivity.this,OwnActivity.class);
                intentf.putExtra("username",name);
                startActivity(intentf);
                break;
            case R.id.im_tanxin:
                if(playPosition == -1)
                {

                    Intent intentq = new Intent(MainActivity.this,MusicActivity.class);
                    intentq.putParcelableArrayListExtra("musiclist", (ArrayList<? extends Parcelable>) musicmesList);
                    intentq.putExtra("playposition",0+"");
                    intentq.putExtra("username",name);
                    startActivity(intentq);
                }
                else
                {
                    musicControl.stopMusic();
                    Intent intentq = new Intent(MainActivity.this,MusicActivity.class);
                    intentq.putParcelableArrayListExtra("musiclist", (ArrayList<? extends Parcelable>) musicmesList);
                    intentq.putExtra("playposition",playPosition+"");
                    intentq.putExtra("username",name);
                    startActivity(intentq);
                }
                break;
            case R.id.im_main:
                Intent intentb = new Intent(MainActivity.this,SingbanActivity.class);
                intentb.putExtra("username",name);
                startActivity(intentb);
                break;
            case R.id.im_ycsf:
                Intent intento = new Intent(MainActivity.this,OrderActivity.class);
                intento.putExtra("username",name);
                startActivity(intento);
                break;
            case R.id.im_huisu:
                Intent intenta = new Intent(MainActivity.this,AlbumActivity.class);
                intenta.putExtra("username",name);
                startActivity(intenta);
                break;
        }

    }







    public void showToast(String message)
    {
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
    }


}
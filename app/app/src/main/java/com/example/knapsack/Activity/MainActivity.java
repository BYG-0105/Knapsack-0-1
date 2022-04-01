package com.example.knapsack.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.knapsack.R;
import com.example.potplayer.Bean.Musicmes;
import com.example.potplayer.Service.MusicplayService;
import com.example.potplayer.adapter.MusicAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back,change;
    private ImageView look,last,stop,next;
    private ImageView fir,sec,ord,ban,alb;
    private TextView username;
    private TextView singer;
    private TextView music;
    private TextView album;
    private static TextView tv_progress;
    private static TextView tv_total;
    private static SeekBar sb;//滑动条

    public List<Musicmes> musicmesList = new ArrayList<>();
    private MusicAdapter musicAdapter;
    private ListView listView;
    private MusicplayService.MusicControl musicControl;
    MyServiceConn conn;
    private static final int REQUEST_CODE = 1;
    private int playPosition = -1;//记录正在播放的位置
    private int playsort = 0; //默认顺序播放，1---随机播放，2-----循环播放
    int iconsort = 0;
    private boolean isUnbind = false;//记录服务是否被解绑
    String name;

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




    }

    //初始化界面控件
    private void initview() {
        back = findViewById(R.id.back);
        change = findViewById(R.id.save);
        look = findViewById(R.id.look);
        last = findViewById(R.id.iv_last);
        stop = findViewById(R.id.iv_stop);
        next = findViewById(R.id.iv_next);
        singer = findViewById(R.id.music_gs);
        music = findViewById(R.id.music_gq);
        username = findViewById(R.id.user_name);
        listView = findViewById(R.id.order_listview);
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
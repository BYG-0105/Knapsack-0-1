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
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knapsack.Bean.Goods;
import com.example.knapsack.R;
import com.example.knapsack.Service.Algorithm;
import com.example.knapsack.Service.DLLog;
import com.example.knapsack.Service.ExcelUtil;
import com.example.knapsack.Service.Gene;
import com.example.knapsack.adapter.ResultAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GeneticActivity extends AppCompatActivity implements View.OnClickListener{
    String name;
    String table;
    TextView result;
    Button button;
    private ImageView tanxin,main,huisu,dtgh,paint,back;
    public List<Goods> goods = new ArrayList<>();//用于存放商品列表
    private String[] colNames = new String[]{"物品重量", "物品价值", "物品价值重量比","物品是否被选用"};
    String[] pess = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private String excelFilePath = "";
    public ResultAdapter resultAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genetic);
        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        table = intent.getStringExtra("table");
        goods = intent.getParcelableArrayListExtra("list");
        initview();


        long start=System.nanoTime();
        Gene gaKnapsack = new Gene(goods.get(0).getWeight(), 200, 2000, 0.5f, 0.05f, 0.1f, goods);
        gaKnapsack.solve();

        long end=System.nanoTime();
        long Time=end-start;

        result.setText("运行时间为："+(double)Time/1000000000+" s");
        resultAdapter = new ResultAdapter(GeneticActivity.this,goods);
        listView.setAdapter(resultAdapter);
        DLLog.i("遗传算法界面", "以成功进入遗传算法界面，用户采用遗传算法解决0-1背包问题，已成功求解");
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
        result = findViewById(R.id.tv_result3);
        listView = findViewById(R.id.list);

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
                intentt.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentt);
                DLLog.i("遗传算法界面", "用户进行页面跳转操作，跳转至贪心算法界面");
                break;
            case R.id.im_main:
                Intent intentm = new Intent(GeneticActivity.this,MainActivity.class);
                intentm.putExtra("username",name);
                intentm.putExtra("table",table);
                intentm.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentm);
                DLLog.i("遗传算法界面", "用户进行页面跳转操作，跳转至主界面");
                break;
            case R.id.im_huisu:
                Intent intenty = new Intent(GeneticActivity.this,BacktrackingActivity.class);
                intenty.putExtra("username",name);
                intenty.putExtra("table",table);
                intenty.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intenty);
                DLLog.i("遗传算法界面", "用户进行页面跳转操作，跳转至回溯算法界面");
                break;
            case R.id.im_dtgh:
                Intent intentd = new Intent(GeneticActivity.this,DynamicActivity.class);
                intentd.putExtra("username",name);
                intentd.putExtra("table",table);
                intentd.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentd);
                DLLog.i("遗传算法界面", "用户进行页面跳转操作，跳转至动态规划算法界面");
                break;
            case R.id.im_paint:
                Intent intentp = new Intent(GeneticActivity.this,BubbleChartActivity.class);
                intentp.putExtra("username",name);
                intentp.putExtra("table",table);
                intentp.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentp);
                DLLog.i("遗传算法界面", "用户进行页面跳转操作，跳转至绘制图像界面");
                break;
            case R.id.button_save:
                export();
                DLLog.i("遗传算法界面", "用户进行结果文件导出操作，文件已成功导出");
                break;
        }
    }
    /**
     * 导出表格的操作
     * "新的运行时权限机制"只在应用程序的targetSdkVersion>=23时生效，并且只在6.0系统之上有这种机制，在低于6.0的系统上应用程序和以前一样不受影响。
     * 当前应用程序的targetSdkVersion小于23（为22），系统会默认其尚未适配新的运行时权限机制，安装后将和以前一样不受影响：即用户在安装应用程序的时候默认允许所有被申明的权限
     */
    private void export() {
        if (this.getApplicationInfo().targetSdkVersion >= 23 && Build.VERSION.SDK_INT >= 23) {
            requestPermission();
        } else {
            writeExcel();
        }
    }


    /**
     * 动态请求读写权限
     */
    private void requestPermission() {
        if (!checkPermission()) {//如果没有权限则请求权限再写
            ActivityCompat.requestPermissions(this, pess, 100);
        } else {//如果有权限则直接写
            writeExcel();
        }
    }


    /**
     * 检测权限
     *
     * @return
     */
    private boolean checkPermission() {
        for (String permission : pess) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            boolean isAllGranted = true;
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {//请求到权限了，写Excel
                writeExcel();
            } else {//权限被拒绝，不能写
                Toast.makeText(this, "读写手机存储权限被禁止，请在权限管理中心手动打开权限", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 将数据写入excel表格
     */
    private void writeExcel() {
        if (getExternalStoragePath() == null) return;
        excelFilePath = getExternalStoragePath() + "/ExportExcel/遗传算法解决0-1背包问题.xls";
        if (checkFile(excelFilePath)) {
            deleteByPath(excelFilePath);//如果文件存在则先删除原有的文件
        }
        File file = new File(getExternalStoragePath() + "/ExportExcel");
        makeDir(file);
        ExcelUtil.initExcel(excelFilePath, "中文版", colNames);//需要写入权限
        ExcelUtil.writeObjListToExcel(goods, excelFilePath, this);
    }

    /**
     * 根据路径生成文件夹
     *
     * @param filePath
     */
    public static void makeDir(File filePath) {
        if (!filePath.getParentFile().exists()) {
            makeDir(filePath.getParentFile());
        }
        filePath.mkdir();
    }

    /**
     * 获取外部存储路径
     *
     * @return
     */
    public String getExternalStoragePath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
            return sdDir.toString();
        } else {
            Toast.makeText(this, "找不到外部存储路径，读写手机存储权限被禁止，请在权限管理中心手动打开权限", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    /**
     * 测试数据
     *
     * @return
     */
    public ArrayList<ArrayList<String>> getTravelData() {
        String s = "test string ";
        ArrayList<ArrayList<String>> datas = new ArrayList<>();
        ArrayList<String> data = null;
        for (int i = 0; i < 10; i++) {
            data = new ArrayList<>();
            data.clear();
            for (int j = 0; j < 8; j++) {
                data.add(s + j);
            }
            datas.add(data);
        }
        return datas;
    }

    /**
     * 根据文件路径检测文件是否存在,需要读取权限
     *
     * @param filePath 文件路径
     * @return true存在
     */
    private boolean checkFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isFile()) return true;
            else return false;
        } else {
            return false;
        }
    }


    /**
     * 根据文件路径删除文件
     *
     * @param filePath
     */
    private void deleteByPath(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isFile())
                file.delete();
        }
    }
}
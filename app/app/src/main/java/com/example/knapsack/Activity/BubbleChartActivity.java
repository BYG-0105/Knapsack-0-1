package com.example.knapsack.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.knapsack.Bean.Goods;
import com.example.knapsack.R;
import com.example.knapsack.Service.DLLog;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class BubbleChartActivity extends AppCompatActivity implements OnChartValueSelectedListener, View.OnClickListener {
    private BubbleChart mBubbleChart;
    //显示顶点值
    private Button btn_show_values;
    //x轴动画
    private Button btn_anim_x;
    //y轴动画
    private Button btn_anim_y;
    //xy轴动画
    private Button btn_anim_xy;
    //保存到sd卡
    private Button btn_save_pic;
    //切换自动最大最小值
    private Button btn_auto_mix_max;
    //高亮显示
    private Button btn_actionToggleHighlight;
    private ImageView tanxin,main,dtgh,gene,huisu,back;
    private Button buttons;
    String name;
    String table;
    public List<Goods> goods = new ArrayList<>();//用于存放商品列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble);
        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        table = intent.getStringExtra("table");
        goods = intent.getParcelableArrayListExtra("list");
        DLLog.i("绘制气泡图界面", "以成功进入绘制气泡图界面，用户根据0-1背包问题数据集绘制并查看其气泡图");
        initView();
    }
    //初始化View
    private void initView() {
        //基本控件
        btn_show_values = (Button) findViewById(R.id.btn_show_values);
        btn_show_values.setOnClickListener(this);
        btn_anim_x = (Button) findViewById(R.id.btn_anim_x);
        btn_anim_x.setOnClickListener(this);
        btn_anim_y = (Button) findViewById(R.id.btn_anim_y);
        btn_anim_y.setOnClickListener(this);
        btn_anim_xy = (Button) findViewById(R.id.btn_anim_xy);
        btn_anim_xy.setOnClickListener(this);
        btn_save_pic = (Button) findViewById(R.id.btn_save_pic);
        btn_save_pic.setOnClickListener(this);
        btn_auto_mix_max = (Button) findViewById(R.id.btn_auto_mix_max);
        btn_auto_mix_max.setOnClickListener(this);
        btn_actionToggleHighlight = (Button) findViewById(R.id.btn_actionToggleHighlight);
        btn_actionToggleHighlight.setOnClickListener(this);
        tanxin = findViewById(R.id.im_tanxin);
        main = findViewById(R.id.im_main);
        dtgh = findViewById(R.id.im_dtgh);
        gene = findViewById(R.id.im_ycsf);
        huisu = findViewById(R.id.im_huisu);
        buttons = findViewById(R.id.button_s);
        tanxin.setOnClickListener(this);
        main.setOnClickListener(this);
        dtgh.setOnClickListener(this);
        gene.setOnClickListener(this);
        huisu.setOnClickListener(this);
        buttons.setOnClickListener(this);
        //起泡图
        mBubbleChart = (BubbleChart) findViewById(R.id.mBubbleChart);
        mBubbleChart.getDescription().setEnabled(false);
        mBubbleChart.setOnChartValueSelectedListener(this);
        mBubbleChart.setDrawGridBackground(false);
        mBubbleChart.setTouchEnabled(true);
        mBubbleChart.setDragEnabled(true);
        mBubbleChart.setScaleEnabled(true);
        mBubbleChart.setMaxVisibleValueCount(200);
        mBubbleChart.setPinchZoom(true);
        Legend l = mBubbleChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        YAxis yl = mBubbleChart.getAxisLeft();
        yl.setSpaceTop(20f);
        yl.setSpaceBottom(20f);
        yl.setDrawZeroLine(false);
        mBubbleChart.getAxisRight().setEnabled(false);
        XAxis xl = mBubbleChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        setData();
    }
    //设置数据
    private void setData() {
        ArrayList<BubbleEntry> yVals1 = new ArrayList<BubbleEntry>();
        ArrayList<BubbleEntry> yVals2 = new ArrayList<BubbleEntry>();
        ArrayList<IBubbleDataSet> dataSets = new ArrayList<IBubbleDataSet>();
        if(goods.get(0).getSelect() != null)
        {
            for (int i = 1; i < goods.size(); i++) {
                float x = (float) goods.get(i).getWeight();
                float y = (float) goods.get(i).getValue();
                if(goods.get(i).getSelect() .equals("Yes") )
                {
                    yVals1.add(new BubbleEntry(x, y, 200));
                }
                else
                {
                    yVals2.add(new BubbleEntry(x, y, 100));
                }

            }
            BubbleDataSet set1 = new BubbleDataSet(yVals1, "选中");
            //可以谁知alpha
            set1.setColor(ColorTemplate.COLORFUL_COLORS[3]);
            set1.setDrawValues(true);
            BubbleDataSet set2 = new BubbleDataSet(yVals2, "未选中");
            set2.setColor(ColorTemplate.COLORFUL_COLORS[1]);
            set2.setDrawValues(true);

            dataSets.add(set1);
            dataSets.add(set2);
        }
        else
        {
            for (int i = 1; i < goods.size(); i++) {
                float x = (float) goods.get(i).getWeight();
                float y = (float) goods.get(i).getValue();
                yVals1.add(new BubbleEntry(x, y, 200));
            }
            BubbleDataSet set1 = new BubbleDataSet(yVals1, "物品");
            //可以谁知alpha
            set1.setColor(ColorTemplate.COLORFUL_COLORS[3]);
            set1.setDrawValues(true);
            dataSets.add(set1);
        }

        BubbleData data = new BubbleData(dataSets);
        data.setDrawValues(false);
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.WHITE);
        data.setHighlightCircleWidth(1.5f);
        mBubbleChart.setData(data);
        mBubbleChart.invalidate();
        //默认动画
        mBubbleChart.animateXY(3000, 3000);
    }
    @Override
    public void onValueSelected(Entry e, Highlight h) {
    }
    @Override
    public void onNothingSelected() {
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //显示顶点值
            case R.id.btn_show_values:
                for (IDataSet set : mBubbleChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());
                mBubbleChart.invalidate();
                DLLog.i("绘制图像界面", "用户进行显示顶点值操作");
                break;
            //x轴动画
            case R.id.btn_anim_x:
                mBubbleChart.animateX(3000);
                DLLog.i("绘制图像界面", "用户进行显示x轴动画操作");
                break;
            //y轴动画
            case R.id.btn_anim_y:
                mBubbleChart.animateY(3000);
                DLLog.i("绘制图像界面", "用户进行显示y轴动画操作");
                break;
            //xy轴动画
            case R.id.btn_anim_xy:
                mBubbleChart.animateXY(3000, 3000);
                DLLog.i("绘制图像界面", "用户进行显示xy轴动画操作");
                break;
            //保存到sd卡
            case R.id.btn_save_pic:
                if (mBubbleChart.saveToGallery("title" + System.currentTimeMillis(), 50))
                {
                    Toast.makeText(getApplicationContext(), "保存成功",
                            Toast.LENGTH_SHORT).show();
                    DLLog.i("绘制图像界面", "用户进行保存图像操作，已成功保存");
                } else {
                    Toast.makeText(getApplicationContext(), "保存失败",
                            Toast.LENGTH_SHORT).show();
                    DLLog.e("绘制图像界面", "用户进行保存图像操作，保存失败");
                }
                break;
            //切换自动最大最小值
            case R.id.btn_auto_mix_max:
                mBubbleChart.setAutoScaleMinMaxEnabled(!mBubbleChart.isAutoScaleMinMaxEnabled());
                mBubbleChart.notifyDataSetChanged();
                DLLog.i("绘制图像界面", "用户进行切换自动最大最小值操作");
                break;
            //高亮显示
            case R.id.btn_actionToggleHighlight:
                if (mBubbleChart.getData() != null) {
                    mBubbleChart.getData().setHighlightEnabled(
                            !mBubbleChart.getData().isHighlightEnabled());
                    mBubbleChart.invalidate();
                }
                DLLog.i("绘制图像界面", "用户进行高亮显示操作");
                break;
            case R.id.im_tanxin:
                Intent intentt = new Intent(BubbleChartActivity.this,GreedyActivity.class);
                intentt.putExtra("username",name);
                intentt.putExtra("table",table);
                intentt.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentt);
                DLLog.i("绘制图像界面", "用户进行页面跳转操作，跳转至贪心算法界面");
                break;
            case R.id.im_main:
                Intent intentm = new Intent(BubbleChartActivity.this,MainActivity.class);
                intentm.putExtra("username",name);
                intentm.putExtra("table",table);
                intentm.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentm);
                DLLog.i("绘制图像界面", "用户进行页面跳转操作，跳转至主界面");
                break;
            case R.id.im_ycsf:
                Intent intenty = new Intent(BubbleChartActivity.this,GeneticActivity.class);
                intenty.putExtra("username",name);
                intenty.putExtra("table",table);
                intenty.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intenty);
                DLLog.i("绘制图像界面", "用户进行页面跳转操作，跳转至遗传算法界面");
                break;
            case R.id.im_dtgh:
                Intent intentd = new Intent(BubbleChartActivity.this,DynamicActivity.class);
                intentd.putExtra("username",name);
                intentd.putExtra("table",table);
                intentd.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentd);
                DLLog.i("绘制图像界面", "用户进行页面跳转操作，跳转至动态规划算法界面");
                break;
            case R.id.im_huisu:
                Intent intentp = new Intent(BubbleChartActivity.this,BacktrackingActivity.class);
                intentp.putExtra("username",name);
                intentp.putExtra("table",table);
                intentp.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentp);
                DLLog.i("绘制图像界面", "用户进行页面跳转操作，跳转至回溯算法界面");
                break;
            case R.id.button_s:
                Intent intents = new Intent(BubbleChartActivity.this,BarChartActivity.class);
                intents.putExtra("username",name);
                intents.putExtra("table",table);
                intents.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intents);
                DLLog.i("绘制气泡图界面", "用户进行页面跳转操作，跳转至绘制条形图界面");
                break;
        }
    }
}
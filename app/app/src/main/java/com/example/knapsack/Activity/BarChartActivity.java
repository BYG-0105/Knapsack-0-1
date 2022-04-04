package com.example.knapsack.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.knapsack.Bean.Goods;
import com.example.knapsack.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BarChartActivity extends AppCompatActivity implements OnChartValueSelectedListener, View.OnClickListener {

    private BarChart mBarChart;
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
    //显示边框
    private Button btn_actionToggleBarBorders;
    private ImageView tanxin,main,dtgh,gene,huisu;
    private Button buttons;
    String name;
    String table;
    public List<Goods> goods = new ArrayList<>();//用于存放商品列表
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchar);
        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        table = intent.getStringExtra("table");
        goods = intent.getParcelableArrayListExtra("list");

        initView();
    }

    //初始化
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
        btn_actionToggleBarBorders = (Button) findViewById(R.id.btn_actionToggleBarBorders);
        btn_actionToggleBarBorders.setOnClickListener(this);
        tanxin = findViewById(R.id.im_tanxin);
        main = findViewById(R.id.im_main);
        dtgh = findViewById(R.id.im_dtgh);
        gene = findViewById(R.id.im_ycsf);
        huisu = findViewById(R.id.im_huisu);
        buttons = findViewById(R.id.button_s2);
        tanxin.setOnClickListener(this);
        main.setOnClickListener(this);
        dtgh.setOnClickListener(this);
        gene.setOnClickListener(this);
        huisu.setOnClickListener(this);
        buttons.setOnClickListener(this);
        //条形图
        mBarChart = (BarChart) findViewById(R.id.mBarChart);
        //设置表格上的点，被点击的时候，的回调函数
        mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        // 如果60多个条目显示在图表,drawn没有值
        mBarChart.setMaxVisibleValueCount(60);
        // 扩展现在只能分别在x轴和y轴
        mBarChart.setPinchZoom(false);
        //是否显示表格颜色
        mBarChart.setDrawGridBackground(false);



        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        // 只有1天的时间间隔
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        xAxis.setAxisMaximum(120f);
        xAxis.setAxisMinimum(0f);




        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        //这个替换setStartAtZero(true)
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(120f);

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setAxisMaximum(120f);

        // 设置标示，就是那个一组y的value的
        Legend l = mBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        //样式
        l.setForm(Legend.LegendForm.SQUARE);
        //字体
        l.setFormSize(9f);
        //大小
        l.setTextSize(11f);
        l.setXEntrySpace(50f);

        //模拟数据
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        Collections.sort(goods, new Comparator<Goods>(){

                    /*
                     * int compare(Goods o1, Goods o2) 返回一个基本类型的整型，
                     * 返回负数表示：o1 小于o2，
                     * 返回0 表示：o1和o2相等，
                     * 返回正数表示：o1大于o2。
                    */
                    public int compare(Goods o1, Goods o2) {

                        //按照学生的年龄进行降序排列
                        if(o1.getWeight() < o2.getWeight()){
                            return -1;
                        }
                        if(o1.getWeight() == o2.getWeight()){
                            return 0;
                        }
                        return 1;
                    }
                });
       for(int i = 1;i < goods.size();i++)
        {
            float x = (float) goods.get(i).getWeight() ;
            float y = (float) goods.get(i).getValue() ;
            yVals1.add(new BarEntry(x, y));
        }

        setData(yVals1);

    }

    //设置数据
    private void setData(ArrayList yVals1) {
        float start = 1f;
        BarDataSet set1;
        if (mBarChart.getData() != null &&
                mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "0-1背包问题");
            //设置有四种颜色
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            //设置数据
            mBarChart.setData(data);
        }
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
                for (IDataSet set : mBarChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mBarChart.invalidate();
                break;
            //x轴动画
            case R.id.btn_anim_x:
                mBarChart.animateX(3000);
                break;
            //y轴动画
            case R.id.btn_anim_y:
                mBarChart.animateY(3000);
                break;
            //xy轴动画
            case R.id.btn_anim_xy:
                mBarChart.animateXY(3000, 3000);
                break;
            //保存到sd卡
            case R.id.btn_save_pic:
                if (mBarChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
                    Toast.makeText(getApplicationContext(), "保存成功",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "保存失败",
                            Toast.LENGTH_SHORT).show();
                break;
            //切换自动最大最小值
            case R.id.btn_auto_mix_max:
                mBarChart.setAutoScaleMinMaxEnabled(!mBarChart.isAutoScaleMinMaxEnabled());
                mBarChart.notifyDataSetChanged();
                break;
            //高亮显示
            case R.id.btn_actionToggleHighlight:
                if (mBarChart.getData() != null) {
                    mBarChart.getData().setHighlightEnabled(
                            !mBarChart.getData().isHighlightEnabled());
                    mBarChart.invalidate();
                }
                break;
            //显示边框
            case R.id.btn_actionToggleBarBorders:
                for (IBarDataSet set : mBarChart.getData().getDataSets())
                    ((BarDataSet) set)
                            .setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f
                                    : 1.f);
                mBarChart.invalidate();
                break;
            case R.id.im_tanxin:
                Intent intentt = new Intent(BarChartActivity.this,GreedyActivity.class);
                intentt.putExtra("username",name);
                intentt.putExtra("table",table);
                startActivity(intentt);
                break;
            case R.id.im_main:
                Intent intentm = new Intent(BarChartActivity.this,MainActivity.class);
                intentm.putExtra("username",name);
                intentm.putExtra("table",table);
                intentm.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentm);
                break;
            case R.id.im_ycsf:
                Intent intenty = new Intent(BarChartActivity.this,GeneticActivity.class);
                intenty.putExtra("username",name);
                intenty.putExtra("table",table);
                intenty.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intenty);
                break;
            case R.id.im_dtgh:
                Intent intentd = new Intent(BarChartActivity.this,DynamicActivity.class);
                intentd.putExtra("username",name);
                intentd.putExtra("table",table);
                intentd.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentd);
                break;
            case R.id.im_huisu:
                Intent intentp = new Intent(BarChartActivity.this,BacktrackingActivity.class);
                intentp.putExtra("username",name);
                intentp.putExtra("table",table);
                intentp.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intentp);
                break;
            case R.id.button_s2:
                Intent intents = new Intent(BarChartActivity.this,BubbleChartActivity.class);
                intents.putExtra("username",name);
                intents.putExtra("table",table);
                intents.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                startActivity(intents);
                break;
        }
    }
}

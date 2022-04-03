package com.example.knapsack.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.knapsack.Bean.Goods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Beibao {
    private static SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
    ;

    public Beibao() {

    }

    //添加数据
    public boolean insertData(String table,double weight,double value,double wv)
    {

        ContentValues contentValues = new ContentValues();
        contentValues.put("weight", weight);
        contentValues.put("value",value);
        contentValues.put("wv",wv);
        return sqLiteDatabase.insert(table, null, contentValues) > 0;
    }

    //删除数据
    public boolean deleteData (String table,String id)
    {
        String sql = "id"+"=?";
        String[] contentValuesArray = new String[]{String.valueOf(id)};
        return sqLiteDatabase.delete(table,sql,contentValuesArray)>0;
    }

    //修改
    public boolean updateData(String id,String table,double weight,double value,double wv)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("weight", weight);
        contentValues.put("value",value);
        contentValues.put("wv",wv);
        String sql = "id"+"=?";
        String[] strings = new String[]{id};
        return sqLiteDatabase.update(table,contentValues,sql,strings)>0;
    }

    //查询数据
    public List<Goods> query(String table)
    {
        List<Goods> list = new ArrayList<Goods>();
        Cursor cursor = sqLiteDatabase.query(table,null,null,null,null,null,"id"+" desc");
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                Goods goods = new Goods();
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"))-1;
                @SuppressLint("Range") int weight = cursor.getInt(cursor.getColumnIndex("weight"));
                @SuppressLint("Range") double value = cursor.getInt(cursor.getColumnIndex("value"));
                @SuppressLint("Range") double wv = cursor.getInt(cursor.getColumnIndex("wv"));
                goods.setId(id);
                goods.setWeight(weight);
                goods.setValue(value);
                goods.setWvproportion(wv);
                list.add(goods);
            }
            cursor.close();
        }
        Collections.sort(list, new Comparator<Goods>(){

            /*
             * int compare(Goods o1, Goods o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             */
            public int compare(Goods o1, Goods o2) {

                //按照学生的年龄进行升序排列
                if(o1.getId() > o2.getId()){
                    return 1;
                }
                if(o1.getId() == o2.getId()){
                    return 0;
                }
                return -1;
            }
        });
        return list;
    }
}


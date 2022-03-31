package com.example.knapsack.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.knapsack.Bean.Loginuser;

import java.util.ArrayList;
import java.util.List;


public class SQLiteHelper extends SQLiteOpenHelper {


    private static SQLiteDatabase sqLiteDatabase;
    

    //创建数据库
    public SQLiteHelper(Context context)
    {
        super(context, DBinformation.DATABASE_NAME,null,DBinformation.DATABASE_VERION);
        sqLiteDatabase = this.getWritableDatabase();
    }


    //创建表
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists "+DBinformation.DATABASE_LOGINTABLE
                +"("+ DBinformation.USERS_ID+" integer primary key autoincrement,"
                + DBinformation.USERS_NAME+" varchar(64),"
                + DBinformation.USERS_PWD+" varchar(64),"
                + DBinformation.USERS_CITY+" varchar(64),"
                + DBinformation.USERS_AGE+" varchar(64),"
                + DBinformation.USERS_NUM+" varchar(64),"
                + DBinformation.USERS_GENDER+" varchar(64))");

        sqLiteDatabase.execSQL("create table if not exists "+DBinformation.DATABASE_GOODSTABLE
                +" ("+ DBinformation.GOODS_ID+" integer primary key autoincrement,"
                + DBinformation.GOODS_WEIGHT+" float,"
                + DBinformation.GOODS_VALUE+" float,"
                + DBinformation.GOODS_WV+" float,"
                + DBinformation.GOODS_SELECT+" bit)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public static class User{
        public User() { }


        //查询
        public Loginuser userquery(String name)
        {
            Loginuser loginuser = null;
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_LOGINTABLE,null,null,null,null,null,DBinformation.USERS_ID+" desc");
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {

                    @SuppressLint("Range") String uname = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_NAME));
                    if(name.equals(uname))
                    {
                        loginuser = new Loginuser();
                        @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBinformation.USERS_ID)));
                        @SuppressLint("Range") String pwd = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_PWD));
                        @SuppressLint("Range") String num = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_NUM));
                        @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_CITY));
                        @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_GENDER));
                        @SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_AGE));
                        loginuser.setId(id);
                        loginuser.setName(uname);
                        loginuser.setPwd(pwd);
                        loginuser.setNum(num);
                        loginuser.setCity(city);
                        loginuser.setGender(gender);
                        loginuser.setAge(age);
                        break;
                    }
                }
                cursor.close();
            }
            //Log.i("pwd",loginuser.toString());
            return loginuser;
        }

        //添加数据
        public boolean insertData(String username,String userpwd,String city,String age,String gender,String style,String num)
        {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.USERS_NAME, username);
            contentValues.put(DBinformation.USERS_PWD, userpwd);
            contentValues.put(DBinformation.USERS_CITY, city);
            contentValues.put(DBinformation.USERS_AGE, age);
            contentValues.put(DBinformation.USERS_GENDER, gender);
            contentValues.put(DBinformation.USERS_NUM, num);
            return sqLiteDatabase.insert(DBinformation.DATABASE_LOGINTABLE, null, contentValues) > 0;
        }

        //删除数据
        public boolean deleteData (String id)
        {
            String sql = DBinformation.USERS_ID+"=?";
            String[] contentValuesArray = new String[]{String.valueOf(id)};
            return sqLiteDatabase.delete(DBinformation.DATABASE_LOGINTABLE,sql,contentValuesArray)>0;
        }

        //修改密码
        public boolean updateData(String id,String username,String userpwd)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.USERS_NAME,username);
            contentValues.put(DBinformation.USERS_PWD,userpwd);
            String sql = DBinformation.USERS_ID+"=?";
            String[] strings = new String[]{id};
            return sqLiteDatabase.update(DBinformation.DATABASE_LOGINTABLE,contentValues,sql,strings)>0;
        }

        //修改个人信息
        public boolean updatemesData(String id,String username,String userpwd,String city,String age,String gender,String num)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.USERS_NAME,username);
            contentValues.put(DBinformation.USERS_PWD,userpwd);
            contentValues.put(DBinformation.USERS_CITY, city);
            contentValues.put(DBinformation.USERS_AGE, age);
            contentValues.put(DBinformation.USERS_GENDER, gender);
            contentValues.put(DBinformation.USERS_NUM, num);
            String sql = DBinformation.USERS_ID+"=?";
            String[] strings = new String[]{id};
            return sqLiteDatabase.update(DBinformation.DATABASE_LOGINTABLE,contentValues,sql,strings)>0;
        }

        //查询数据
        public List<Loginuser> query()
        {
            List<Loginuser> list = new ArrayList<Loginuser>();
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_LOGINTABLE,null,null,null,null,null,DBinformation.USERS_ID+" desc");
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {
                    Loginuser loginuser = new Loginuser();
                    @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBinformation.USERS_ID)));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_NAME));
                    @SuppressLint("Range") String pwd = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_PWD));
                    @SuppressLint("Range") String num = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_NUM));
                    @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_CITY));
                    @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_GENDER));
                    @SuppressLint("Range") String style = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_STYLE));
                    @SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_AGE));
                    loginuser.setId(id);
                    loginuser.setName(name);
                    loginuser.setPwd(pwd);
                    loginuser.setPwd(num);
                    loginuser.setId(city);
                    loginuser.setName(gender);
                    loginuser.setPwd(style);
                    loginuser.setPwd(age);
                    list.add(loginuser);
                }
                cursor.close();
            }
            return list;
        }
    }

    public static class Beibeo  {


        public Beibeo() {

        }

        //添加数据
        public boolean insertData(String table,String weight,String vlaue)
        {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.ORDER_CONTENT, content);
            contentValues.put(DBinformation.ORDER_TIME, time);
            return sqLiteDatabase.insert(DBinformation.DATABASE_ORDERTABLE, null, contentValues) > 0;
        }

        //删除数据
        public boolean deleteData (String id)
        {
            String sql = DBinformation.USERS_ID+"=?";
            String[] contentValuesArray = new String[]{String.valueOf(id)};
            return sqLiteDatabase.delete(DBinformation.DATABASE_ORDERTABLE,sql,contentValuesArray)>0;
        }

        //修改
        public boolean updateData(String id,String content,String time)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.ORDER_CONTENT,content);
            contentValues.put(DBinformation.ORDER_TIME,time);
            String sql = DBinformation.USERS_ID+"=?";
            String[] strings = new String[]{id};
            return sqLiteDatabase.update(DBinformation.DATABASE_ORDERTABLE,contentValues,sql,strings)>0;
        }

        //查询数据
        public List<OrderBean> query()
        {
            List<OrderBean> list = new ArrayList<OrderBean>();
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_ORDERTABLE,null,null,null,null,null,DBinformation.ORDER_ID+" desc");
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {
                    OrderBean orderBean = new OrderBean();
                    @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBinformation.USERS_ID)));
                    @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(DBinformation.ORDER_CONTENT));
                    @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DBinformation.ORDER_TIME));
                    orderBean.setId(id);
                    orderBean.setContent(content);
                    orderBean.setTime(time);
                    list.add(orderBean);
                }
                cursor.close();
            }
            return list;
        }
    }



}


package com.example.knapsack.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.knapsack.Bean.Goods;
import com.example.knapsack.Bean.Loginuser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public  class Users{

    private static SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
    ;
    public Users() { }

    //数据库变量
    public DBManager dbHelper;

    //查询
    public Loginuser userquery(String name)
    {
        Loginuser loginuser = null;
        Cursor cursor = sqLiteDatabase.query("users",null,null,null,null,null,"id"+" desc");
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {

                @SuppressLint("Range") String uname = cursor.getString(cursor.getColumnIndex("username"));
                if(name.equals(uname))
                {
                    loginuser = new Loginuser();
                    @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                    @SuppressLint("Range") String pwd = cursor.getString(cursor.getColumnIndex("userpwd"));
                    @SuppressLint("Range") String num = cursor.getString(cursor.getColumnIndex("usernum"));
                    @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex("usercity"));
                    @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex("usergender"));
                    @SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex("userage"));
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
        contentValues.put("username", username);
        contentValues.put("userpwd", userpwd);
        contentValues.put("usercity", city);
        contentValues.put("userage", age);
        contentValues.put("usergender", gender);
        contentValues.put("usernum", num);
        return sqLiteDatabase.insert("users", null, contentValues) > 0;
    }

    //删除数据
    public boolean deleteData (String id)
    {
        String sql = "id"+"=?";
        String[] contentValuesArray = new String[]{String.valueOf(id)};
        return sqLiteDatabase.delete("users",sql,contentValuesArray)>0;
    }

    //修改密码
    public boolean updateData(String id,String username,String userpwd)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("userpwd",userpwd);
        String sql = "id"+"=?";
        String[] strings = new String[]{id};
        return sqLiteDatabase.update("users",contentValues,sql,strings)>0;
    }

    //修改个人信息
    public boolean updatemesData(String id,String username,String userpwd,String city,String age,String gender,String num)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("userpwd", userpwd);
        contentValues.put("usercity", city);
        contentValues.put("userage", age);
        contentValues.put("usergender", gender);
        contentValues.put("usernum", num);
        String sql = "id"+"=?";
        String[] strings = new String[]{id};
        return sqLiteDatabase.update("users",contentValues,sql,strings)>0;
    }

    //查询数据
    public List<Loginuser> query()
    {
        List<Loginuser> list = new ArrayList<Loginuser>();
        Cursor cursor = sqLiteDatabase.query("users",null,null,null,null,null,"id"+" desc");
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                Loginuser loginuser = new Loginuser();
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String pwd = cursor.getString(cursor.getColumnIndex("userpwd"));
                @SuppressLint("Range") String num = cursor.getString(cursor.getColumnIndex("usernum"));
                @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex("usercity"));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex("usergender"));
                @SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex("userage"));
                loginuser.setId(id);
                loginuser.setName(name);
                loginuser.setPwd(pwd);
                loginuser.setPwd(num);
                loginuser.setId(city);
                loginuser.setName(gender);
                loginuser.setPwd(age);
                list.add(loginuser);
            }
            cursor.close();
        }

        return list;
    }
}
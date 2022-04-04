package com.example.knapsack.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knapsack.Bean.Loginuser;
import com.example.knapsack.R;
import com.example.knapsack.Service.DLLog;
import com.example.knapsack.database.DBManager;
import com.example.knapsack.database.MD5Utils;
import com.example.knapsack.database.Users;


import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_register;
    private Button btn_cancal;
    private EditText username;
    private EditText userpwd;
    private List<String> styleString=new ArrayList<String>();
    private CheckBox stylehy,styleyg,styleom,stylegd;
    private Users UsersqLiteHelper = new Users();
    private RadioGroup radioGroup;
    String uGender  = "男";
    private TextView city;
    private TextView age;
    private TextView num;
    private MD5Utils md5Utils;
    private SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_register);


        DLLog.i("注册界面", "以成功进入注册界面");
        initview();
        Intent intent = getIntent();
        String name = intent.getStringExtra("username");
        if(name != null)
        {
            initmes(name);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId())
                {
                    case R.id.radioButton_Male:
                        uGender="男";
                        break;
                    case R.id.radioButton_Female:
                        uGender="女";
                        break;
                    default:break;
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String pwd = md5Utils.md5Password(userpwd.getText().toString());
                String citys = city.getText().toString();
                String ages = age.getText().toString();
                String nums = num.getText().toString();
                checkBoxlis();
                if(btn_register.getText().equals("注册"))
                {

                    if(UsersqLiteHelper.userquery(name) != null)
                    {
                        showToast("该账户已存在！！！");
                        DLLog.i("注册界面", "用户进行注册操作，提示账户已存在");

                    }
                    else if(name.length() <= 0 || pwd.length() <= 0 || nums.length() <= 0)
                    {
                        showToast("账号，密码，电话号码为必填项，请重新填写！！！");
                        DLLog.i("注册界面", "用户进行注册操作，提示账号，密码，电话号码为必填项");
                    }
                    else if(UsersqLiteHelper.userquery(name) == null && UsersqLiteHelper.insertData(name,pwd,citys,ages,uGender,styleString.toString(),nums))
                    {
                        showToast("注册成功！");
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        DLLog.i("注册界面", "用户进行注册操作，已成功注册，跳转至登录界面");
                    }
                    else
                    {
                        showToast("注册失败！！！请重新尝试注册");
                        DLLog.i("注册界面", "用户进行注册操作，注册失败");
                    }
                }
                if(btn_register.getText().equals("修改"))
                {
                    Loginuser loginuser = UsersqLiteHelper.userquery(name);
                    String id = loginuser.getId();
                    if(UsersqLiteHelper.updatemesData(id,name,pwd,citys,ages,uGender,nums))
                    {
                        showToast("修改成功！");
                        DLLog.i("修改个人信息界面", "用户进行修改信息操作，修改成功");
                    }
                    else
                    {
                        showToast("修改失败！！！请重新尝试注册");
                        DLLog.i("修改个人信息界面", "用户进行修改信息操作，修改失败");
                    }
                    Intent intent = new Intent(RegisterActivity.this,OwnActivity.class);
                    intent.putExtra("username",name);
                    startActivity(intent);
                    finish();
                }


            }
        });

        btn_cancal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_register.getText().equals("注册"))
                {

                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(btn_register.getText().equals("修改"))
                {
                    Intent intent = new Intent(RegisterActivity.this,OwnActivity.class);
                    intent.putExtra("username",name);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    private void initmes(String name) {
        Loginuser loginuser = UsersqLiteHelper.userquery(name);
        username.setText(loginuser.getName());
        userpwd.setText(loginuser.getPwd());
        city.setText(loginuser.getCity());
        age.setText(loginuser.getAge());
        num.setText(loginuser.getNum());
        btn_register.setText("修改");
    }

    public void initview()
    {
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_cancal = (Button)findViewById(R.id.btn_cancal) ;
        username = (EditText) findViewById(R.id.etPersonName);
        userpwd = (EditText) findViewById(R.id.etPwd);
        stylehy = (CheckBox)findViewById(R.id.checkBoxhy);
        styleyg = (CheckBox)findViewById(R.id.checkBoxyg);
        styleom = (CheckBox)findViewById(R.id.checkBoxom);
        stylegd = (CheckBox)findViewById(R.id.checkBoxgd);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        city = (TextView)findViewById(R.id.etCity);
        age = (TextView) findViewById(R.id.etAge);
        num = (TextView) findViewById(R.id.etNum);

    }
    private void checkBoxlis() {
        if(stylehy.isChecked())
        {
            styleString.add(stylehy.getText().toString());
        }
        else {
            styleString.remove(stylehy.getText().toString());
        }
        if(styleyg.isChecked())
        {
            styleString.add(styleyg.getText().toString());
        }
        else {
            styleString.remove(styleyg.getText().toString());
        }
        if (styleom.isChecked())
        {
            styleString.add(styleom.getText().toString());
        }
        else {
            styleString.remove(styleom.getText().toString());
        }
        if (stylegd.isChecked())
        {
            styleString.add(stylegd.getText().toString());
        }
        else {
            styleString.remove(stylegd.getText().toString());
        }
    }



    public void showToast(String message)
    {
        Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
    }
}
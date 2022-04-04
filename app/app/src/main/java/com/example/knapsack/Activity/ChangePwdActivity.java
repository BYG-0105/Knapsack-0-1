package com.example.knapsack.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knapsack.Bean.Goods;
import com.example.knapsack.Bean.Loginuser;
import com.example.knapsack.R;
import com.example.knapsack.Service.DLLog;
import com.example.knapsack.database.MD5Utils;
import com.example.knapsack.database.Users;

import java.util.ArrayList;
import java.util.List;


public class ChangePwdActivity extends AppCompatActivity {

    private EditText name,pwd,num;
    private Button btnxg,btnfinish;
    private ImageView back;
    String namess;
    String table;
    public List<Goods> goods = new ArrayList<>();//用于存放商品列表

    private MD5Utils md5Utils ;
    private Users users = new Users();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//这行代码必须写在setContentView()方法的前面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        DLLog.i("修改密码界面", "以成功进入修改密码界面");
        name = (EditText) findViewById(R.id.e_namesc);
        pwd = (EditText) findViewById(R.id.e_pwdsc);
        btnxg = (Button)findViewById(R.id.btn_c);
        btnfinish = (Button) findViewById(R.id.btn_f);
        back = (ImageView)findViewById(R.id.back);
        num = (EditText)findViewById(R.id.e_lxfs);

        Intent intent = getIntent();
        namess = intent.getStringExtra("username");
        table = intent.getStringExtra("table");
        goods = intent.getParcelableArrayListExtra("list");
        btnxg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names = name.getText().toString();
                String pwds = md5Utils.md5Password(pwd.getText().toString());
                String nums = num.getText().toString();
               if(names.length() <= 0 )
               {
                   showToast("账号为空！！！请重新输入");
                   DLLog.e("修改密码界面", "用户进行修改密码操作，未输入账号");
               }
               if(pwds.length() <= 0 )
               {
                   showToast("密码为空！！！请重新输入");
                   DLLog.e("修改密码界面", "用户进行修改密码操作，未输入密码");
               }
               if(nums.isEmpty())
               {
                   showToast("联系方式为空！！！请重新输入");
                   DLLog.e("修改密码界面", "用户进行修改密码操作，未输入联系方式");
               }
               else if(users.userquery(names) == null)
               {
                   showToast("账号不存在！！！检查账号是否正确！");
                   DLLog.e("修改密码界面", "用户进行修改密码操作，账号不存在");
               }
               else if(users.userquery(names) != null)
               {
                   Loginuser loginuser = users.userquery(names);
                   String id = loginuser.getId();
                   if(users.updateData(id,names,pwds) && loginuser.getNum().equals(nums))
                   {
                       showToast("密码修改成功！！！");
                       if(namess == null)
                       {
                           Intent intent = new Intent(ChangePwdActivity.this,LoginActivity.class);
                           finish();
                           startActivity(intent);
                           DLLog.i("修改密码界面", "用户进行修改密码操作，密码修改成功，跳转至登录界面");
                       }
                       else
                       {
                           Intent intent = new Intent(ChangePwdActivity.this,MainActivity.class);
                           intent.putExtra("username",names);
                           intent.putExtra("table",table);
                           intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                           finish();
                           startActivity(intent);
                           DLLog.i("修改密码界面", "用户进行修改密码操作，密码修改成功，跳转至主界面");
                       }
                   }
                   else if(loginuser.getNum() !=  nums)
                   {
                       DLLog.e("修改密码界面", "用户进行修改密码操作，输入联系方式错误");
                      showToast("输入联系方式错误！！！请重新输入！！！");
                   }
               }
            }
        });

        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(namess == null)
                {
                    Intent intent = new Intent(ChangePwdActivity.this,LoginActivity.class);

                    finish();
                    startActivity(intent);
                    DLLog.i("修改密码界面", "用户进行修改密码操作，密码修改成功，跳转至登录界面");
                }
                else
                {
                    Intent intent = new Intent(ChangePwdActivity.this,OwnActivity.class);
                    finish();
                    intent.putExtra("username",namess);
                    intent.putExtra("table",table);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                    startActivity(intent);
                    DLLog.i("修改密码界面", "用户进行修改密码操作，密码修改成功，跳转至个人中心界面");
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(namess == null)
                {
                    Intent intent = new Intent(ChangePwdActivity.this,LoginActivity.class);
                    finish();
                    startActivity(intent);
                    DLLog.i("修改密码界面", "用户进行修改密码操作，密码修改成功，跳转至登录界面");
                }
                else
                {
                    Intent intent = new Intent(ChangePwdActivity.this,OwnActivity.class);
                    finish();
                    intent.putExtra("username",namess);
                    intent.putExtra("table",table);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) goods);
                    startActivity(intent);
                    DLLog.i("修改密码界面", "用户进行修改密码操作，密码修改成功，跳转至个人中心界面");
                }
            }
        });
    }
    public void showToast(String message)
    {
        Toast.makeText(ChangePwdActivity.this,message,Toast.LENGTH_LONG).show();
    }
}
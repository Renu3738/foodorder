package com.example.android.foodorder;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class Registration extends AppCompatActivity {
    EditText txtFName, txtMobile, txtPassword, txtPass, txtCode;
    Button btnRegister, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        txtFName = (EditText) findViewById(R.id.txtFName);
        txtMobile = (EditText) findViewById(R.id.txtMobile);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPass = (EditText) findViewById(R.id.txtPass);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnBack = (Button) findViewById(R.id.btnBack);

       btnRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               sendRegistration();
           }
       });
       btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(Registration.this, Login.class);
               startActivity(intent);
           }
       });
    }

    public void sendRegistration(){
        String name=txtFName.getText().toString();
        String phone=txtMobile.getText().toString();
        String password=txtPassword.getText().toString();

        Random random=new Random();
        int value = random.nextInt(1000);
        String code=value+"";
        String url="http://renupun.com.np/food/request.php?name="+name+"&mobile="+phone+"&pass="+password+"&code="+code;
        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String response = msg.obj.toString();
                Toast.makeText(Registration.this, ""+response, Toast.LENGTH_SHORT).show();
                if (response.equals("")){
                    Toast.makeText(Registration.this, "Server error, Try again later", Toast.LENGTH_SHORT).show();
                }
                else if (response.equals("1")){
                    Toast.makeText(Registration.this, "Successfully registered. Please Confirm", Toast.LENGTH_SHORT).show();
                }
                else if (response.equals("0")){
                    Toast.makeText(Registration.this, "Mobile number is already registered", Toast.LENGTH_SHORT).show();
                }
            }
        };
       HttpSourceRequest httpSourceRequest=new HttpSourceRequest(handler,url);
        }
    }


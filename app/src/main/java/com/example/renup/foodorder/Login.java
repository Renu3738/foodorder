package com.example.renup.foodorder;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText txtmobile, txtPassword;
    Button btnSignIn;
    TextView txtclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtmobile=(EditText)findViewById(R.id.txtmobile);
        txtPassword=(EditText)findViewById(R.id.txtPassword);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        txtclick=(TextView)findViewById(R.id.txtclick);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        txtclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Registration.class);
                startActivity(intent);
            }
        });
    }
    public void login(){
        final String mobile=txtmobile.getText().toString();
        String pass=txtPassword.getText().toString();
        String url="http://renupun.com.np/food/request.php?mob="+mobile+"&pas="+pass+"";
        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String response=msg.obj.toString();
                if (response.equals("0")){
                    Toast.makeText(Login.this, "Please Verify", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Login.this,Verification.class);
                    intent.putExtra("mobile",mobile);
                    startActivity(intent);
                }
                else if (response.equals("11")){
                    Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Login.this,Home.class);
                    startActivity(intent);

                }
                else if (response.equals("00")){
                    Toast.makeText(Login.this, " Server Error", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Login.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpSourceRequest httpSourceRequest=new HttpSourceRequest(handler,url);
    }
}

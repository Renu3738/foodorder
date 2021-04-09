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

public class Verification extends AppCompatActivity {
    EditText txtcode;
    Button btnVerify;
    String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mobile=getIntent().getStringExtra("mobile");
        setContentView(R.layout.activity_verification);
        txtcode=findViewById(R.id.txtcode);
        btnVerify=findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
    }
    public void verify(){
        String code=txtcode.getText().toString();
        String url="http://renupun.com.np/food/request.php?mobb="+mobile+"&code="+code+"";
        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String response = msg.obj.toString();
                if (response.equals("11")){
                    Toast.makeText(Verification.this, "Verified", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Verification.this,Home.class);
                    startActivity(intent);
                }
                else if (response.equals("00")){
                    Toast.makeText(Verification.this, "Not verified", Toast.LENGTH_SHORT).show();
                }
                else if (response.equals("0")){
                    Toast.makeText(Verification.this, "Please enter correct code", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Verification.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }
        };HttpSourceRequest httpSourceRequest=new HttpSourceRequest(handler,url);
    }
}

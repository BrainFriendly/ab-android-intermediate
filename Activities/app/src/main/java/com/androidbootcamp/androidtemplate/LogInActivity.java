package com.androidbootcamp.androidtemplate;

import android.os.Bundle;
import android.view.View;

import com.androidbootcamp.androidtemplate.model.MyEntity;

public class LogInActivity extends BaseActivity {

    private View btnNext,llaySignUp;

    private String userId,userName;
    private MyEntity myEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //extras();
        ui();

        //showMessage("username "+userName);
        //showMessage("entity "+myEntity.toString());
    }

    private void extras(){
        /*if(getIntent()!=null && getIntent().getExtras()!=null){
            Bundle bundle= getIntent().getExtras();
            userId= bundle.getString("USERID",null);
            userName= bundle.getString("USERNAME",null);
            myEntity= (MyEntity) bundle.getSerializable("OBJSR");
        }*/
    }
    private void ui() {
        btnNext= findViewById(R.id.btnNext);
        llaySignUp= findViewById(R.id.llaySignUp);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMain();
            }
        });

        llaySignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignUp();
            }
        });
    }

    private void gotoMain() {
        /*Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();*/
    }

    private void gotoSignUp() {
        //Intent intent= new Intent(this,SignUpActivity.class);
        //startActivity(intent);
        //next(SignUpActivity.class,null,false);
    }


}

package com.example.user.pulse_i;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity{

    EditText login_id_et;
    EditText login_password_et;

    AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        aq = new AQuery(this);

        login_id_et = (EditText)findViewById(R.id.login_id_et);
        login_password_et = (EditText)findViewById(R.id.login_password_et);

        aq.id(R.id.login_title_back_but).clicked(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        aq.id(R.id.login_login_but).clicked(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String url = "http://arcreation.xyz/pulsei/login_check.php";

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", login_id_et.getText().toString());
                params.put("password", login_password_et.getText().toString());

                aq.ajax(url, params, String.class, new AjaxCallback<String>() {

                    @Override
                    public AjaxCallback<String> progress(Dialog dialog) {
                        // TODO Auto-generated method stub
                        return super.progress(dialog);
                    }

                    @Override
                    public void callback(String url, String html, AjaxStatus status) {
                        try {
                            JSONObject object = new JSONObject(html);
                            int cnt = Integer.parseInt(object.getString("cnt"));
                            String member_key = object.getString("member_key");
                            if(cnt==0){
                                Toast.makeText(getBaseContext(), "아이디 혹은 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getBaseContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                Intent loginintent=new Intent(LoginActivity.this, FirstActivity.class);
                                startActivity(loginintent);
                            }

                            Log.d("test", html);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

        aq.id(R.id.login_signin_tv).text(Html.fromHtml("<u>회원가입</u>")).clicked(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });


    }

}
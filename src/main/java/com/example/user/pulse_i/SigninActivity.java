package com.example.user.pulse_i;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
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

public class SigninActivity extends Activity{

    AQuery aq;
    EditText name_et;
    EditText id_et;
    EditText pass_et;
    EditText pass_confirm_et;
    EditText phone_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        aq = new AQuery(this);


        name_et = (EditText)findViewById(R.id.signin_name_et);
        id_et = (EditText)findViewById(R.id.signin_id_et);
        pass_et = (EditText)findViewById(R.id.signin_password_et);
        pass_confirm_et = (EditText)findViewById(R.id.signin_password_confirm_et);
        phone_et = (EditText)findViewById(R.id.signin_phone_et);

        aq.id(R.id.signin_confirm_but).clicked(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!pass_et.getText().toString().equals(pass_confirm_et.getText().toString())){
                    Toast.makeText(getBaseContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else if("".equals(id_et.getText().toString())){
                    Toast.makeText(getBaseContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if("".equals(name_et.getText().toString())){
                    Toast.makeText(getBaseContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if("".equals(phone_et.getText().toString())){
                    Toast.makeText(getBaseContext(), "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(pass_et.getText().toString().length()<6){
                    Toast.makeText(getBaseContext(), "비밀번호를 6자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(getBaseContext(), "TODO : 회원 가입 처리", Toast.LENGTH_SHORT).show();
                    String url = "http://arcreation.xyz/pulsei/member_sign.php";

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("name", name_et.getText().toString());
                    params.put("id", id_et.getText().toString());
                    params.put("pass", pass_et.getText().toString());
                    params.put("phone", phone_et.getText().toString());

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
                                String result_code = object.getString("result_code");
                                if("9999".equals(result_code)){
                                    String member_key = object.getString("member_key");

                                    Toast.makeText(getBaseContext(), "회원 가입 및 로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                    finish();
                                }
                                Log.d("test", html);
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    });

                }
            }
        });

        aq.id(R.id.signin_title_back_but).clicked(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


}
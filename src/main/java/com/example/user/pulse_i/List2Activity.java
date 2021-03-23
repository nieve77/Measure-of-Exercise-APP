package com.example.user.pulse_i;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class List2Activity extends ActionBarActivity {


    ListView listView;
    ListAdapter adapter;
    ArrayList<ListArticle> list;
    int pos=0;
    AQuery aq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        list = new ArrayList<ListArticle>();
        /*
        list = new ArrayList<ListArticle>();
        for(int i=1;i<21;i++){
            ListArticle la = new ListArticle(i+"번째 날짜입력", "걸음수 체크");
            list.add(la);

        }
        */
        /*
         *
        마구 강제 추가

        ListArticle la = new ListArticle("1");
        list.add(la);

        la = new ListArticle("3");
        list.add(la);

        */

        aq = new AQuery(this);

        load();


        aq.id(R.id.distance_next_but).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(List2Activity.this)
                        .setTitle("이동거리측정")
                        .setMessage("이동거리측정 하시겠습니까?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                Intent distanceintent = new Intent(List2Activity.this, BluetoothActivity_distance.class);
                                // startActivity(stepintent);
                                startActivityForResult(distanceintent, 1);

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.ic_launcher)
                        .show();
            }
        });




        /*
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Toast.makeText(getBaseContext(), "바깥 터치", Toast.LENGTH_SHORT).show();
            }

        });
*/
    }

    private void load(){
        list = new ArrayList<ListArticle>();
        String url = "http://arcreation.xyz/pulsei/select_distance.php";
        Map<String, Object> params = new HashMap<String, Object>();

        aq.ajax(url, params, String.class, new AjaxCallback<String>() {

            @Override
            public AjaxCallback<String> progress(Dialog dialog) {
                return super.progress(dialog);
            }

            @Override
            public void callback(String url, String html, AjaxStatus status) {
                try {
                    JSONArray mArray = new JSONArray(html);
                    for (int i = 0; i < mArray.length(); i++) {
                        JSONObject obj = mArray.getJSONObject(i);
                        String key = obj.getString("distance_key");
                        String count = obj.getString("distance_count");
                        String date = obj.getString("reg_date");
                        ListArticle la = new ListArticle(key, count, date);
                        list.add(la);
                    }


                    listView = (ListView) findViewById(R.id.listView2);
                    adapter = new ListAdapter(List2Activity.this, R.layout.list, list);
                    listView.setAdapter(adapter);

                    Log.d("test", html);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
    }

    private class ListAdapter extends ArrayAdapter<ListArticle> {


        Context context;
        ArrayList<ListArticle> list = null;



        public ListAdapter(Context context, int ResourceId,
                           ArrayList<ListArticle> objects) {
            super(context, ResourceId, objects);
            this.context = context;
            this.list = objects;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list2, null);
            }

            ListArticle la = list.get(position);

            if(la!=null){

                TextView left2_tv = (TextView)convertView.findViewById(R.id.list_left2_tv);
                TextView right2_tv = (TextView)convertView.findViewById(R.id.list_right2_tv);

                left2_tv.setText("이동거리 : "+ la.count +"m");
                right2_tv.setText(la.date);

                /*
                TextView tv = (TextView)convertView.findViewById(R.id.list_tv);
                tv.setText(la.text);



                Button but = (Button)convertView.findViewById(R.id.list_but);
                but.setText(la.but);

                but.setTag(position);

                tv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getBaseContext(), "제목 터치", Toast.LENGTH_SHORT).show();


                    }
                });

                but.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                         pos = (Integer)v.getTag();

                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("걸음측정")
                                    .setMessage("운동측정 하시겠습니까?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                            Intent stepintent = new Intent(MainActivity.this, BluetoothActivity.class);
                                           // startActivity(stepintent);
                                            startActivityForResult(stepintent, 1);


                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do nothing
                                        }
                                    })
                                    .setIcon(R.drawable.ic_launcher)
                                    .show();


                    }

                });
                */
            }
            return convertView;
        }




    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 1:
                load();
                /*
                    String key = data.getStringExtra("data_digit");
                    list.set(pos, new ListArticle(key, "걸음수 체크"));
                    adapter = new ListAdapter(MainActivity.this, R.layout.list, list);
                    listView.setAdapter(adapter);
                    */
                break;

            default:
                break;
        }
    }

}
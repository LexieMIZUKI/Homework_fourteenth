package com.lexieluv.homeworkfourteenth2;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.lexieluv.homeworkfourteenth2.bean.Children;
import com.lexieluv.homeworkfourteenth2.bean.Parent;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity{

    ContentResolver resolver;
    private Spinner spin;
    private EditText et_name;
    private Button btn_save;
    Parent p;
    List<String> list = new ArrayList<>();
    Cursor c;
    String spinSelected = null;
    ArrayAdapter adapter;

//    Uri uri = Uri.parse("content://com.imooc.menuprovider/dish_tb");
    Uri uri = Uri.parse("content://com.imooc.menuprovider");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_layout);

        spin = findViewById(R.id.spin);
        et_name = findViewById(R.id.et_name);
        btn_save = findViewById(R.id.btn_save);

        resolver = getContentResolver();
        init();//spinner获取数据
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list);
        spin.setAdapter(adapter);

        //获取contentresolver
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();//插入表中数据
//                resolver.delete(uri,"dish_id = 66",null);//本来想把之前添加错误的内容删除，但是删除失败,如果可以的话希望老师告诉我原因

                Log.d("********新增*********",et_name.getText().toString());
                startActivity(new Intent(AddActivity.this,MainActivity.class));
                finish();
            }
        });

    }

    private void addData() {
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinSelected = String.valueOf(adapter.getItem(position));
                Log.d("********菜单*********",spinSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ContentValues values = new ContentValues();
        //这里添加数据每次都添加到了父类，不知道怎么回事，请老师指导！
        values.put("dish_type",spinSelected);
        values.put("dish_name",et_name.getText().toString());



        //根据uri向contentprovider中插入数据,不需要跳转界面获取，因为是插入表格中，直接到时候刷新adapter就行
        resolver.insert(uri,values);
        resolver.update(uri,values,null,null);
    }

    private void init() {
        c = null;
        try{
            c = resolver.query(uri,null,null,null,null);
            if(c != null){
                while(c.moveToNext()){
                    String type_name = c.getString(2);
                    if(!list.contains(type_name)) {
                        Log.d("========7==========",type_name);
                        list.add(type_name);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(c != null){
                c.close();
            }
        }
    }


}

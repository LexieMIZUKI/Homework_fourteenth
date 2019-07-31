package com.lexieluv.homeworkfourteenth2;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CursorAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.lexieluv.homeworkfourteenth2.bean.Children;
import com.lexieluv.homeworkfourteenth2.bean.Parent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ContentResolver resolver;
    private ExpandableListView exlv;
    private ExAdapter adapter;
    private List<Parent> pList = new ArrayList<>();
//    private List<Children> cList = new ArrayList<>();
//    private List<List<Children>> cList = new ArrayList<>();

    Parent p;
    Children c;
//    Uri uri = Uri.parse("content://com.imooc.menuprovider/dish_tb");
    Uri uri = Uri.parse("content://com.imooc.menuprovider");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exlv = findViewById(R.id.exlv);
        resolver = getContentResolver();
        //进行数据绑定
        readData();
        //绑定适配器
        adapter = new ExAdapter(this,pList);
        adapter.notifyDataSetChanged();
        exlv.setAdapter(adapter);
        exlv.setOnItemLongClickListener(onItemLongClickListener);
    }

    //标题栏添加菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.adding,menu);
        return true;
    }
    //监听菜单按钮,跳转到添加界面
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                startActivity(new Intent(MainActivity.this,AddActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void readData() {
        //根据provider来只来绑定子类，请根据提供的代码变化
        Cursor cc = null;
        int num = 0;//为了逻辑方便的数字
        try {
            cc = resolver.query(uri, null, null, null, null);
            if (cc != null) {
                cc.moveToFirst();
                while (cc.moveToNext()) {
                    /*
                    没有错，以下的逻辑问题，我花了一整个下午的时间才调试出来，感觉超级有成就感！
                     */
                    //由于只能使用子表，所以，只有这三个数据可以拿到，那么这也决定了List应该定义几个以及其类别
                    String dish_name = cc.getString(1);
                    int dish_id = cc.getInt(cc.getColumnIndex("dish_id"));
                    String dish_type = cc.getString(2);

                    c = new Children(dish_id, dish_name);
                    c.setpType(dish_type);
//                    Log.d("=====c=====",c.getId()+"");//本来想删除添加错误额，所以打印了一下c的id看下错的那条的id
                    //第一次不需要判断列表里面有没有p，直接新建添加
                    if(num == 0) {
                        p = new Parent(dish_type);
                        p.addChild(c);
                    }
                    //除了第一次，每次的第二次过度的地方都需要先建立且定义了，下面一步才可以判断是否存在p
                    if((num > 0)&(!p.getName().equals(dish_type))){
                        p = new Parent(dish_type);
                    }
                    //父类不重复写法（因为从子表拿数据）
                    if (!(pList.contains(p))) {
                        Log.d("=============",p.toString());
                        pList.add(p);
                        num++;//数字自加一方便逻辑处理
                    }
                    //第一次由于已经新建且对第一个数据添加，就不需要再添加了
                    if (num > 1) {
                        p.addChild(c);
                    }
                    num++;//这里加一是由于如果只有中间判断父类不重复那里自加一，上面将会处于整组数据的num都是1，没办法加入子类，因此为了逻辑上加一，并且除了第一次之后，后面的num作用都不大了
                }
            }
            Log.d("======psize======", String.valueOf(pList.size()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cc != null) {
                cc.close();
            }
        }
    }
    //自定义长按监听类
    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final long packedPosition = exlv.getExpandableListPosition(position);
        final int parentPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
        final int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);

        //删除子项
        if(childPosition != -1){
            //显示消息框
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("警告");
            builder.setMessage("您在试图破坏子类，确定吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //更新数据库

//                    ContentResolver resolver = getContentResolver();
                    //这里delete的条件不知道怎么写？请老师指导
//                    Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("取消",null);
            builder.show();
        } else { //删除父项
            //显示消息框
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("警告");
            builder.setMessage("您在试图破坏父类，确定吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //更新数据库

//                    ContentResolver resolver = getContentResolver();
                    //这里delete的条件不知道怎么写？
//                    Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("取消",null);
            builder.show();
        }
        return true;
    }
};

}

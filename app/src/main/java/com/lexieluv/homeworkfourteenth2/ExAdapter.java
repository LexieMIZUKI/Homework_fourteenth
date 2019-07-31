package com.lexieluv.homeworkfourteenth2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lexieluv.homeworkfourteenth2.R;
import com.lexieluv.homeworkfourteenth2.bean.Children;
import com.lexieluv.homeworkfourteenth2.bean.Parent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Parent> pData = null;//由于父类里面可以添加子类，所以只需要根据父类制作适配器就行
    private LayoutInflater inflater;

    public ExAdapter(Context context,List<Parent> pData) {
        this.context = context;
        this.pData = pData;
        inflater = LayoutInflater.from(context);//把实例化写在构造函数里
    }

    //父类的数量
    @Override
    public int getGroupCount() {
        return pData.size();
    }

    //子类的数量
    @Override
    public int getChildrenCount(int groupPosition) {
        return pData.get(groupPosition).getcList().size();
    }

    //获取父类的对象
    @Override
    public Object getGroup(int groupPosition) {
        return pData.get(groupPosition);
    }

    //获取子类的对象
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return pData.get(groupPosition).getcList().get(childPosition);
    }

    //获取父类id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获取子类id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //父类视图绑定
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder pvh;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_parent,parent,false);
            pvh = new ParentViewHolder();
            pvh.iv_pic = convertView.findViewById(R.id.iv_pic);
            pvh.tv_parent = convertView.findViewById(R.id.tv_parent);
            convertView.setTag(pvh);
        } else {
            pvh = (ParentViewHolder) convertView.getTag();
        }
        pvh.tv_parent.setText(pData.get(groupPosition).getName());
        pvh.iv_pic.setImageResource(R.drawable.ic_launcher_foreground);
        pvh.iv_pic.setSelected(isExpanded);//图片设置伸展
        return convertView;
    }
    //子类视图绑定
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder cvh;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_children,parent,false);
            cvh = new ChildViewHolder();
            cvh.tv_child = convertView.findViewById(R.id.tv_children);
            convertView.setTag(cvh);
        } else {
            cvh = (ChildViewHolder) convertView.getTag();
        }
        cvh.tv_child.setText(pData.get(groupPosition).getcList().get(childPosition).getName());
        return convertView;
    }

    //子类是否可以被选择
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //先创建父类和子类自定义视图
    public static class ParentViewHolder{
        TextView tv_parent;
        ImageView iv_pic;
    }
    public static class ChildViewHolder{
        TextView tv_child;
    }
}

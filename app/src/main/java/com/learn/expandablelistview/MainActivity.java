package com.learn.expandablelistview;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    private ExpandableListView mExpandableListView;

    private List<String> mGroupList;

    private List<String> mEachItemList;

    private List<List<String>> mChildList;

    private List<List<Integer>> mChildImgList;
    
    private List<Integer> mImgList;
    
    private MyExpandableListViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 随便一堆测试数据 10个分组
        mGroupList = new ArrayList<String>();
        mGroupList.add("姓氏1");
        mGroupList.add("姓氏2");
        mGroupList.add("姓氏3");
        mGroupList.add("姓氏4");
        mGroupList.add("姓氏5");
        mGroupList.add("姓氏6");
        mGroupList.add("姓氏7");
        mGroupList.add("姓氏8");
        mGroupList.add("姓氏9");
        mGroupList.add("姓氏10");

        mEachItemList = new ArrayList<String>();
        mEachItemList.add("刘一");
        mEachItemList.add("陈二");
        mEachItemList.add("张三");
        mEachItemList.add("李四");
        mEachItemList.add("王五");
        mEachItemList.add("赵六");
        mEachItemList.add("孙七");
        mEachItemList.add("周八");
        mEachItemList.add("吴九");
        mEachItemList.add("郑十");

        mChildList = new ArrayList<List<String>>();
        for(int i=0; i<10; i++){
            mChildList.add(mEachItemList);
        }

        mImgList = new ArrayList<Integer>();
        for(int i=0; i<10; i++){
            mImgList.add(R.mipmap.ic_launcher);
        }

        mChildImgList = new ArrayList<List<Integer>>();
        for(int i=0; i<10; i++){
            mChildImgList.add(mImgList);
        }

        mExpandableListView = (ExpandableListView)findViewById(R.id.expandable_list_view);
        mExpandableListView.setGroupIndicator(null);

        // 监听组点击
        mExpandableListView.setOnGroupClickListener(new OnGroupClickListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                if (item_list.get(groupPosition).isEmpty()) {
//                    return true;
//                }
//                return false;
                //返回true代表不可点击
                return true;
            }
        });

        // 监听每个分组里子控件的点击事件
        mExpandableListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Toast.makeText(MainActivity.this, "group=" + groupPosition + "---child=" + childPosition + "---" + mChildList.get(groupPosition).get(childPosition), Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        mAdapter = new MyExpandableListViewAdapter(this, mGroupList, mChildList, mChildImgList);

        mExpandableListView.setAdapter(mAdapter);

        //展开所有group
        int groupCount = mAdapter.getGroupCount();
        for(int i=0; i<groupCount; i++){
            mExpandableListView.expandGroup(i);
        }

        //测试动态插入一组数据时，是否所有view全部重新加载
        mExpandableListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.addGroup(2, "插入的groupName", mEachItemList, mImgList);
            }
        }, 3000);
    }

}
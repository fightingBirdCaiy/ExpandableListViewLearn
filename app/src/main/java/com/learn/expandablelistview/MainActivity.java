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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    private ExpandableListView expandableListView;

    private List<String> group_list;

    private List<String> item_lt;

    private List<List<String>> item_list;

    private List<List<Integer>> item_list2;

    private List<List<Integer>> gr_list2;

    private MyExpandableListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 随便一堆测试数据
        group_list = new ArrayList<String>();
        group_list.add("姓氏1");
        group_list.add("姓氏2");
        group_list.add("姓氏3");
        group_list.add("姓氏4");
        group_list.add("姓氏5");
        group_list.add("姓氏6");
        group_list.add("姓氏7");
        group_list.add("姓氏8");
        group_list.add("姓氏9");
        group_list.add("姓氏10");


        item_lt = new ArrayList<String>();
        item_lt.add("刘一");
        item_lt.add("陈二");
        item_lt.add("张三");
        item_lt.add("李四");
        item_lt.add("王五");
        item_lt.add("赵六");
        item_lt.add("孙七");
        item_lt.add("周八");
        item_lt.add("吴九");
        item_lt.add("郑十");

        item_list = new ArrayList<List<String>>();
        for(int i=0; i<10; i++){
            item_list.add(item_lt);
        }

        List<Integer> tmp_list = new ArrayList<Integer>();
        tmp_list.add(R.mipmap.ic_launcher);
        for(int i=0; i<10; i++){
            tmp_list.add(R.mipmap.ic_launcher);
        }

        item_list2 = new ArrayList<List<Integer>>();
        for(int i=0; i<10; i++){
            item_list2.add(tmp_list);
        }

        List<Integer> gr_list = new ArrayList<Integer>();
        for(int i=0; i<10; i++){
            gr_list.add(R.mipmap.ic_launcher);
        }

        gr_list2 = new ArrayList<List<Integer>>();
        for(int i=0; i<10; i++){
            gr_list2.add(gr_list);
        }

        expandableListView = (ExpandableListView)findViewById(R.id.expandable_list_view);
        expandableListView.setGroupIndicator(null);

        // 监听组点击
        expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
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
        expandableListView.setOnChildClickListener(new OnChildClickListener()
        {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {

                Toast.makeText(MainActivity.this, "group=" + groupPosition + "---child=" + childPosition + "---" + item_list.get(groupPosition).get(childPosition), Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        adapter = new MyExpandableListViewAdapter(this);

        expandableListView.setAdapter(adapter);

        //展开所有group
        int groupCount = adapter.getGroupCount();
        for(int i=0; i<groupCount; i++){
            expandableListView.expandGroup(i);
        }
    }

    // 用过ListView的人一定很熟悉，只不过这里是BaseExpandableListAdapter
    class MyExpandableListViewAdapter extends BaseExpandableListAdapter
    {

        private Context context;

        public MyExpandableListViewAdapter(Context context)
        {
            this.context = context;
        }

        /**
         *
         * 获取组的个数
         *
         * @return
         * @see android.widget.ExpandableListAdapter#getGroupCount()
         */
        @Override
        public int getGroupCount()
        {
            return group_list.size();
        }

        /**
         *
         * 获取指定组中的子元素个数
         *
         * @param groupPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
         */
        @Override
        public int getChildrenCount(int groupPosition)
        {
            return item_list.get(groupPosition).size();
        }

        /**
         *
         * 获取指定组中的数据
         *
         * @param groupPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getGroup(int)
         */
        @Override
        public Object getGroup(int groupPosition)
        {
            return group_list.get(groupPosition);
        }

        /**
         *
         * 获取指定组中的指定子元素数据。
         *
         * @param groupPosition
         * @param childPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getChild(int, int)
         */
        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            return item_list.get(groupPosition).get(childPosition);
        }

        /**
         *
         * 获取指定组的ID，这个组ID必须是唯一的
         *
         * @param groupPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getGroupId(int)
         */
        @Override
        public long getGroupId(int groupPosition)
        {
            return groupPosition;
        }

        /**
         *
         * 获取指定组中的指定子元素ID
         *
         * @param groupPosition
         * @param childPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getChildId(int, int)
         */
        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition;
        }

        /**
         *
         * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
         *
         * @return
         * @see android.widget.ExpandableListAdapter#hasStableIds()
         */
        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        /**
         *
         * 获取显示指定组的视图对象
         *
         * @param groupPosition 组位置
         * @param isExpanded 该组是展开状态还是伸缩状态
         * @param convertView 重用已有的视图对象
         * @param parent 返回的视图对象始终依附于的视图组
         * @return
         * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View,
         *      android.view.ViewGroup)
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
        {
            Log.e(TAG,"getGroupView方法调用了:groupPosition=" + groupPosition + ",isExpanded=" + isExpanded + ",convertView=" + convertView);
            GroupHolder groupHolder = null;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.expendlist_group, null);
                groupHolder = new GroupHolder();
                groupHolder.txt = (TextView)convertView.findViewById(R.id.txt);
                groupHolder.img = (ImageView)convertView.findViewById(R.id.img);
                convertView.setTag(groupHolder);
            }
            else
            {
                groupHolder = (GroupHolder)convertView.getTag();
            }

            if (!isExpanded)
            {
                groupHolder.img.setBackgroundResource(R.mipmap.ic_launcher);
            }
            else
            {
                groupHolder.img.setBackgroundResource(R.mipmap.ic_launcher);
            }

            groupHolder.txt.setText(group_list.get(groupPosition));
            groupHolder.txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"标题中的文字被点击了",Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        /**
         *
         * 获取一个视图对象，显示指定组中的指定子元素数据。
         *
         * @param groupPosition 组位置
         * @param childPosition 子元素位置
         * @param isLastChild 子元素是否处于组中的最后一个
         * @param convertView 重用已有的视图(View)对象
         * @param parent 返回的视图(View)对象始终依附于的视图组
         * @return
         * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
         *      android.view.ViewGroup)
         */
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
        {
            Log.e(TAG,"getChildView方法调用了:groupPosition=" + groupPosition + ",childPosition=" + childPosition + ",isLastChild=" + isLastChild + ",convertView=" + convertView);
            ItemHolder itemHolder = null;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.expendlist_item, null);
                itemHolder = new ItemHolder();
                itemHolder.txt = (TextView)convertView.findViewById(R.id.txt);

                itemHolder.img = (ImageView)convertView.findViewById(R.id.img);
                convertView.setTag(itemHolder);
            }
            else
            {
                itemHolder = (ItemHolder)convertView.getTag();
            }
            itemHolder.txt.setText(item_list.get(groupPosition).get(childPosition));
            itemHolder.txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "内容中的文字被点击了", Toast.LENGTH_SHORT).show();
                }
            });
            itemHolder.img.setBackgroundResource(item_list2.get(groupPosition).get(childPosition));
            return convertView;
        }

        /**
         *
         * 是否选中指定位置上的子元素。
         *
         * @param groupPosition
         * @param childPosition
         * @return
         * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
         */
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }

    }

    class GroupHolder
    {
        public TextView txt;

        public ImageView img;
    }

    class ItemHolder
    {
        public ImageView img;

        public TextView txt;
    }

}
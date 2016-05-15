package com.learn.expandablelistview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by yongc on 16/5/15.
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter
{
    private static final String TAG = MyExpandableListViewAdapter.class.getSimpleName();

    /**
     * inflate的view
     * 每次inflate的时候加1
     */
    private static int newInflateViewCount = 0;

    private Context mContext;

    private List<String> mGroupList;

    private List<List<String>> mChildList;

    private List<List<Integer>> mChildImgList;

    public MyExpandableListViewAdapter(Context context, List<String> groupList, List<List<String>> childList, List<List<Integer>> childImgList)
    {
        this.mContext = context;
        this.mGroupList = groupList;
        this.mChildList = childList;
        this.mChildImgList = childImgList;
    }


    /**
     * 在position位置插入一组数据
     * @param position
     * @param group
     * @param child
     * @param childImg
     */
    public void addGroup(int position, String group, List<String> child, List<Integer> childImg){
        mGroupList.add(position,group);
        mChildList.add(position,child);
        mChildImgList.add(position,childImg);
        notifyDataSetChanged();
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
        return mGroupList.size();
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
        return mChildList.get(groupPosition).size();
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
        return mGroupList.get(groupPosition);
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
        return mChildList.get(groupPosition).get(childPosition);
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
        Log.e(TAG, "getGroupView方法调用了:groupPosition=" + groupPosition + ",isExpanded=" + isExpanded + ",convertView=" + convertView);
        GroupHolder groupHolder = null;
        if (convertView == null)
        {
            increaAndToast();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.expendlist_group, null);
            groupHolder = new GroupHolder();
            groupHolder.txt = (TextView)convertView.findViewById(R.id.txt);
            groupHolder.img = (ImageView)convertView.findViewById(R.id.img);
            groupHolder.outerLayout = (LinearLayout)convertView.findViewById(R.id.group_root_outer);
            groupHolder.innerLayout = (RelativeLayout)convertView.findViewById(R.id.group_root_inner);
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

        groupHolder.txt.setText(mGroupList.get(groupPosition));
        groupHolder.txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "标题中的文字被点击了", Toast.LENGTH_SHORT).show();
            }
        });

//            //测试groupView如何隐藏
//            if(groupPosition == 0) {//第一个分组不显示groupView
//                groupHolder.innerLayout.setVisibility(View.GONE);
//            }else{
//                groupHolder.innerLayout.setVisibility(View.VISIBLE);
//            }

//            //测试点击groupView的时候，其他view是否重新getView
//            groupHolder.innerLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(v.getVisibility() == View.VISIBLE){
//                        v.setVisibility(View.GONE);
//                    }else{
//                        v.setVisibility(View.VISIBLE);
//                    }
//                }
//            });

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
            increaAndToast();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.expendlist_item, null);
            itemHolder = new ItemHolder();
            itemHolder.txt = (TextView)convertView.findViewById(R.id.txt);

            itemHolder.img = (ImageView)convertView.findViewById(R.id.img);
            convertView.setTag(itemHolder);
        }
        else
        {
            itemHolder = (ItemHolder)convertView.getTag();
        }
        itemHolder.txt.setText(mChildList.get(groupPosition).get(childPosition));
        itemHolder.txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "内容中的文字被点击了", Toast.LENGTH_SHORT).show();
            }
        });
        itemHolder.img.setBackgroundResource(mChildImgList.get(groupPosition).get(childPosition));
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

    private void increaAndToast(){
        newInflateViewCount ++;
        Toast.makeText(mContext,"inflate新的view,总个数:" + newInflateViewCount,Toast.LENGTH_SHORT).show();
    }
}

class GroupHolder
{
    public LinearLayout outerLayout;

    public RelativeLayout innerLayout;

    public TextView txt;

    public ImageView img;
}

class ItemHolder
{
    public ImageView img;

    public TextView txt;
}

# ExpandableListViewLearn </br>
1.学习ExpandableListView
2.测试ExapndableListView对多type的支持情况
(1)原生对ListView的多type的方式不支持。
  因为BaseExpandableListAdapter未继承BaseAdapter,所有没有getItemViewType()方法。
(2)自定义对ListView的多type的方式不支持
 关键代码如下：
 private View getChildViewByType(int groupPosition, int childPosition, View convertView) {
        ItemHolder itemHolder = null;
        Integer type = null;
        if (convertView == null){//不复用的情况1
            return inflateNewView(groupPosition, childPosition);
        }
        else
        {
            itemHolder = (ItemHolder)convertView.getTag();
            type = (Integer)convertView.getTag(R.id.key_expandable_type);
//            Log.e(TAG,"type=" + type + ",groupPosition=" + groupPosition);
            if(type != groupPosition){//不复用的情况2
                return inflateNewView(groupPosition, childPosition);
            }else{//复用的情况
                increaseUsedAndToast();
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
        }


    }
    
 日志：
 05-15 15:54:01.380 31305-31305/? I/MyExpandableListViewAdapter: 复用了view,总个数:1097
 05-15 15:54:01.380 31305-31305/? I/MyExpandableListViewAdapter: 复用了view,总个数:1098 
 05-15 15:54:01.715 31305-31305/? E/MyExpandableListViewAdapter: inflate新的view,type=0,总个数:540
 05-15 15:54:01.785 31305-31305/? E/MyExpandableListViewAdapter: inflate新的view,type=0,总个数:541
 结果：
 复用view的情况大概是inflate的view的两倍，但是inflate的view的情况始终在增长
 内存占用：
 
 

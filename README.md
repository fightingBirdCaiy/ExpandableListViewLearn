# ExpandableListViewLearn 
1.学习ExpandableListView <br/>
2.测试ExapndableListView对多type的支持情况<br/>
(1)原生对ListView的多type的方式不支持。<br/>
  因为BaseExpandableListAdapter未继承BaseAdapter,所有没有getItemViewType()方法。<br/>
(2)自定义对ListView的多type的方式不支持<br/>
 关键代码如下：<br/>
 ```java
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
 ```
 日志：<br/>
 05-15 15:54:01.380 31305-31305/? I/MyExpandableListViewAdapter: 复用了view,总个数:930 <br/>
 05-15 15:54:01.380 31305-31305/? I/MyExpandableListViewAdapter: 复用了view,总个数:931 <br/>
 05-15 15:54:01.715 31305-31305/? E/MyExpandableListViewAdapter: inflate新的view,type=0,总个数:465 <br/>
 05-15 15:54:01.785 31305-31305/? E/MyExpandableListViewAdapter: inflate新的view,type=0,总个数:466 <br/>
 结果：<br/>
 复用view的情况大概是inflate的view的两倍，但是inflate的view的情况始终在增长<br/>
 内存占用：<br/>
 ![image](https://github.com/fightingBirdCaiy/ExpandableListViewLearn/blob/master/image/memory.jpg)
 

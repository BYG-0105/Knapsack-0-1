package com.example.knapsack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.knapsack.Bean.Goods;
import com.example.knapsack.R;

import java.util.List;

public class ResultAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Goods> list;
    public ResultAdapter(Context context, List<Goods> list)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.list=list;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertview == null)
        {
            convertview=layoutInflater.inflate(R.layout.suan_item_layout,null);
            viewHolder = new ViewHolder(convertview);
            convertview.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertview.getTag();
        }

        Goods goods = (Goods) getItem(position);
        viewHolder.name.setText(goods.getId()+"");
        viewHolder.weight.setText(goods.getWeight()+"");
        viewHolder.value.setText(goods.getValue()+"");
        viewHolder.select.setText( goods.getSelect()+"");
        return convertview;
    }

    class ViewHolder {
        TextView name,weight,value,select;
        public ViewHolder( View view)
        {
            name = (TextView) view.findViewById(R.id.g_name);
            weight = (TextView) view.findViewById(R.id.g_weight);
            value = (TextView) view.findViewById(R.id.g_value);
            select = (TextView) view.findViewById(R.id.g_select);
        }
    }

}


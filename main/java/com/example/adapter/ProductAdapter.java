package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqlite_ex2.MainActivity;
import com.example.sqlite_ex2.R;

import com.example.model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    MainActivity context;

    int item_Layout;

    List<Product> products;

    public ProductAdapter(MainActivity context, int item_Layout, List<Product> products) {
        this.context = context;
        this.item_Layout = item_Layout;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_Layout,null);

            //linking views
            holder.txtInfo = view.findViewById(R.id.txtInfo);
            holder.imvEdit = view.findViewById(R.id.imvEdit);
            holder.imvDelete = view.findViewById(R.id.imvDelete);

            view.setTag(holder);

        }
        else{
            holder = (ViewHolder)view.getTag();


        }
        Product p = products.get(i);
        holder.txtInfo.setText(p.getProductName() + " gia " + p.getProductPrice());
        holder.imvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.openEditDialog(p);
            }
        });
        holder.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.openDeleteConfirmDialog(p);


            }
        });
        return view;
    }
    public static class ViewHolder{
        TextView txtInfo;
        ImageView imvEdit,imvDelete;
    }
}

package com.example.nguyen_khac_tung_duong_n22dccn018.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyen_khac_tung_duong_n22dccn018.R;
import com.example.nguyen_khac_tung_duong_n22dccn018.models.Book;

import java.util.List;

public class CartListAdapter extends BaseAdapter {
    private Context context;
    private List<Book> cartList;

    public CartListAdapter(Context context, List<Book> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public int getCount() { return cartList.size(); }

    @Override
    public Object getItem(int position) { return cartList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        }

        ImageView imgCover = convertView.findViewById(R.id.imgCartCover);
        TextView tvTitle = convertView.findViewById(R.id.tvCartTitle);
        TextView tvCategory = convertView.findViewById(R.id.tvCartCategory);
        TextView tvPrice = convertView.findViewById(R.id.tvCartPrice);

        Book book = cartList.get(position);
        tvTitle.setText(book.getTitle());

        tvCategory.setText(book.getAuthor());
        tvPrice.setText("$" + book.getPrice());
        imgCover.setImageResource(book.getImageResource());

        return convertView;
    }
}


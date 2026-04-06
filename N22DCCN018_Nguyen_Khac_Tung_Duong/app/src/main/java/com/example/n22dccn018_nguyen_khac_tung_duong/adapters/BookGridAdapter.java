package com.example.n22dccn018_nguyen_khac_tung_duong.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.n22dccn018_nguyen_khac_tung_duong.R;
import com.example.n22dccn018_nguyen_khac_tung_duong.models.Book;

import java.util.List;

public class BookGridAdapter extends BaseAdapter {
    private Context context;
    private List<Book> bookList;

    public BookGridAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public int getCount() { return bookList.size(); }

    @Override
    public Object getItem(int position) { return bookList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        }

        ImageView imgCover = convertView.findViewById(R.id.imgBookCover);
        TextView tvTitle = convertView.findViewById(R.id.tvBookTitle);
        TextView tvAuthor = convertView.findViewById(R.id.tvBookAuthor);
        TextView tvPrice = convertView.findViewById(R.id.tvBookPrice);

        Book book = bookList.get(position);
        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
        tvPrice.setText("$" + book.getPrice());
        imgCover.setImageResource(book.getImageResource());

        return convertView;
    }
}
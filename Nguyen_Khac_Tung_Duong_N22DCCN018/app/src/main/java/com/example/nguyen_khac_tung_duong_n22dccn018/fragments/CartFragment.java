package com.example.nguyen_khac_tung_duong_n22dccn018.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

import com.example.nguyen_khac_tung_duong_n22dccn018.R;
import com.example.nguyen_khac_tung_duong_n22dccn018.adapters.CartListAdapter;
import com.example.nguyen_khac_tung_duong_n22dccn018.models.Book;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    ListView listView;
    List<Book> cartList;
    CartListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        listView = view.findViewById(R.id.listViewCart);

        cartList = new ArrayList<>();

        cartList.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 12.99, R.drawable.book1));
        cartList.add(new Book("To Kill  Mockingbird", "Harper Lee", 11.99, R.drawable.book2));
        cartList.add(new Book("The Hobbit", "J.R.R. Tolkien", 15.99, R.drawable.book5));
        cartList.add(new Book("Harry Potter", "J. K. Rowling", 18.50, R.drawable.book6));

        adapter = new CartListAdapter(getContext(), cartList);
        listView.setAdapter(adapter);

        return view;
    }
}


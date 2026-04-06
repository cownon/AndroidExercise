package com.example.n22dccn018_nguyen_khac_tung_duong.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

import com.example.n22dccn018_nguyen_khac_tung_duong.R;
import com.example.n22dccn018_nguyen_khac_tung_duong.adapters.CartListAdapter;
import com.example.n22dccn018_nguyen_khac_tung_duong.models.Book;

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

        cartList.add(new Book("The Great Gatsby", "Fiction", 12.99, R.drawable.book1));
        cartList.add(new Book("1984", "Dystopian", 11.99, R.drawable.book3));
        cartList.add(new Book("The Hobbit", "Fantasy", 15.99, R.drawable.book5));
        cartList.add(new Book("Harry Potter", "Fantasy", 18.50, R.drawable.book6));
        adapter = new CartListAdapter(getContext(), cartList);
        listView.setAdapter(adapter);

        return view;
    }


}

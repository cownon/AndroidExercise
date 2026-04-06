package com.example.nguyen_khac_tung_duong_n22dccn018.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.fragment.app.Fragment;

import com.example.nguyen_khac_tung_duong_n22dccn018.R;
import com.example.nguyen_khac_tung_duong_n22dccn018.adapters.BookGridAdapter;
import com.example.nguyen_khac_tung_duong_n22dccn018.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookGridFragment extends Fragment {
    GridView gridView;
    List<Book> bookList;
    BookGridAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_grid, container, false);
        gridView = view.findViewById(R.id.gridViewBooks);

        bookList = new ArrayList<>();
        bookList.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 12.99, R.drawable.book1));
        bookList.add(new Book("To Kill a Mockingbird", "Harper Lee", 14.50, R.drawable.book2));
        bookList.add(new Book("1984", "George Orwell", 11.99, R.drawable.book3));
        bookList.add(new Book("Pride and Prejudice", "Jane Austen", 9.99, R.drawable.book4));
        bookList.add(new Book("The Hobbit", "J.R.R. Tolkien", 15.99, R.drawable.book5));
        bookList.add(new Book("Harry Potter", "J.K. Rowling", 18.50, R.drawable.book6));

        adapter = new BookGridAdapter(getContext(), bookList);
        gridView.setAdapter(adapter);

        return view;
    }
}


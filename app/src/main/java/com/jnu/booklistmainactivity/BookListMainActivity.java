package com.jnu.booklistmainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Book> books =getListBooks();//获取资源中的对象
        RecyclerView mainRecycleView=findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mainRecycleView.setLayoutManager(layoutManager);
        mainRecycleView.setAdapter(new MyRecyclerViewAdapter(books));
    }

    private List<Book> getListBooks(){
        List<Book> bookList= new ArrayList<>();
        bookList.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        bookList.add(new Book("创新工程实践", R.drawable.book_3));
        bookList.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
        return bookList;
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private List<Book> books;
        public MyRecyclerViewAdapter(List<Book> bookList) {
            this.books=bookList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout1,parent,false);
            return new myViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder Holder, int position) {
            myViewHolder holder=(myViewHolder)Holder;
            holder.getImageView().setImageResource(books.get(position).getimageView());
            holder.getTextView_name().setText(books.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return books.size();
        }

        private class myViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            private final TextView textView_name;

            public myViewHolder(View view) {
                super(view);
                this.imageView=view.findViewById(R.id.image_view_book_cover);
                this.textView_name =view.findViewById(R.id.text_view_book_title);
            }

            public ImageView getImageView() {
                return imageView;
            }

            public TextView getTextView_name() {
                return textView_name;
            }
        }
    }
}


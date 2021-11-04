package com.jnu.booklistmainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

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

        private class myViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
            private final ImageView imageView;
            private final TextView textView_name;

            public myViewHolder(View view) {
                super(view);
                this.imageView=view.findViewById(R.id.image_view_book_cover);
                this.textView_name =view.findViewById(R.id.text_view_book_title);
                view.setOnCreateContextMenuListener(this);
            }

            public ImageView getImageView() {
                return imageView;
            }

            public TextView getTextView_name() {
                return textView_name;
            }

            //创建菜单
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu,View view,ContextMenu.ContextMenuInfo contextMenuInfo){
                MenuItem addMt=contextMenu.add(Menu.NONE,1,1,"Add");
                MenuItem delMt=contextMenu.add(Menu.NONE,2,2,"Delete");

                //对菜单点击设置监听回调
                addMt.setOnMenuItemClickListener(this);
                delMt.setOnMenuItemClickListener(this);
            }

            //回调函数
            @Override
            public boolean onMenuItemClick(MenuItem menuItem){
                int position=getAdapterPosition();
                switch(menuItem.getItemId()){
                    case 1://添加书籍
                        View addView=LayoutInflater.from(BookListMainActivity.this).inflate(R.layout.add_item,null);
                        AlertDialog.Builder addBulier=new AlertDialog.Builder(BookListMainActivity.this);
                        addBulier.setView(addView);
                        addBulier.setTitle("Add");
                        addBulier.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText editName=addView.findViewById(R.id.edit_text_bookName);
                                EditText editImage=addView.findViewById(R.id.edit_text_imageName);
                                Context baseContext=getBaseContext();
                                int id = getResources().getIdentifier(baseContext.getPackageName() + ":drawable/" + editImage.getText().toString(), null, null);
                                if (id == 0){//资源不存在则提示错误
                                    AlertDialog.Builder error=new AlertDialog.Builder( BookListMainActivity.this);
                                    error.setTitle("Warning");
                                    error.setMessage("Error!Fail To Add");
                                    error.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    error.create().show();
                                }else{
                                    books.add(position+1,new Book(editName.getText().toString(),id));
                                    MyRecyclerViewAdapter.this.notifyItemInserted(position+1);
                                    Toast.makeText(BookListMainActivity.this,"Succeed To Add",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        addBulier.setCancelable(false).setNegativeButton("Esc", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        addBulier.create().show();
                        break;
                    case 2://删除书籍
                        AlertDialog.Builder delBuiler=new AlertDialog.Builder(BookListMainActivity.this);
                        delBuiler.setTitle("Remind");
                        delBuiler.setMessage("Sure To Delete?");
                        delBuiler.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                books.remove(position);
                                MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                                Toast.makeText(BookListMainActivity.this,"Success to Delete",Toast.LENGTH_LONG).show();
                            }
                        });
                        delBuiler.setCancelable(false).setNegativeButton("Esc", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        delBuiler.create().show();
                        break;
                }
                notifyDataSetChanged();//刷新页面
                return false;
            }
        }
    }
}


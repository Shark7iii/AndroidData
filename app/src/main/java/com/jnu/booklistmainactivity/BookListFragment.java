package com.jnu.booklistmainactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.booklistmainactivity.data.Book;
import com.jnu.booklistmainactivity.data.DataBookBank;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookListFragment extends Fragment {
    private BookListFragment.MyRecyclerViewAdapter recyclerViewAdapter;
    public static final int RESULT_CODE_ADD_DATA = 996;
    private List<Book> books;
    private DataBookBank dataBookBank;
    ActivityResultLauncher<Intent> launcherAdd=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),new ActivityResultCallback<ActivityResult>(){
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if (resultCode == RESULT_CODE_ADD_DATA) {
                if (null == data)
                    return;
                String title= data.getStringExtra("title");
                int position = data.getIntExtra("position", books.size());
                books.add(position+1, new Book(title, R.drawable.book_no_name));
                dataBookBank.saveData();
                recyclerViewAdapter.notifyItemInserted(position+1);
            }
        }
    });

    ActivityResultLauncher<Intent> launcherEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if (resultCode == RESULT_CODE_ADD_DATA) {
                if (null == data)
                    return;
                String title = data.getStringExtra("title");
                int position = data.getIntExtra("position", books.size());
                books.get(position).setTitle(title);
                dataBookBank.saveData();
                recyclerViewAdapter.notifyItemChanged(position);
            }
        }
    });

    public BookListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BookListFragment newInstance() {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_book_list,container,false);
        books=getListBooks();
        FloatingActionButton fabAdd = view1.findViewById(R.id.floating_action_button_add);
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this.getContext(), EditBookActivity.class);
            intent.putExtra("position", books.size() - 1);
            launcherAdd.launch(intent);
        });
        RecyclerView mainRecycleView=view1.findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getContext());
        mainRecycleView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new BookListFragment.MyRecyclerViewAdapter(books);
        mainRecycleView.setAdapter(recyclerViewAdapter);
        return view1;
    }
    private List<Book> getListBooks(){
        dataBookBank = new DataBookBank(BookListFragment.this.getContext());
        books= dataBookBank.loadData();
        return books;
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private List<Book> books;
        public MyRecyclerViewAdapter(List<Book> bookList) {
            this.books=bookList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout1,parent,false);
            return new BookListFragment.MyRecyclerViewAdapter.myViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder Holder, int position) {
            BookListFragment.MyRecyclerViewAdapter.myViewHolder myHolder = (BookListFragment.MyRecyclerViewAdapter.myViewHolder) Holder;

            Book book = books.get(position);
            myHolder.getImageView().setImageResource(book.getimageView());
            myHolder.getTextView_name().setText(book.getTitle());
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
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo){
                MenuItem addMt=contextMenu.add(Menu.NONE,1,1,"Add");
                MenuItem delMt=contextMenu.add(Menu.NONE,2,2,"Delete");
                MenuItem editMt=contextMenu.add(Menu.NONE,3,3,"Edit");
                //对菜单点击设置监听回调
                addMt.setOnMenuItemClickListener(this);
                delMt.setOnMenuItemClickListener(this);
                editMt.setOnMenuItemClickListener(this);
            }

            //回调函数
            @Override
            public boolean onMenuItemClick(MenuItem menuItem){
                int position=getAdapterPosition();
                Intent intent;
                switch(menuItem.getItemId()){
                    case 1://添加书籍
                        intent = new Intent(BookListFragment.this.getContext(), EditBookActivity.class);
                        intent.putExtra("position", position);
                        launcherAdd.launch(intent);
                        break;
                    case 2://删除书籍
                        AlertDialog.Builder delBuiler=new AlertDialog.Builder(BookListFragment.this.getContext());
                        delBuiler.setTitle("Remind");
                        delBuiler.setMessage("Sure To Delete?");
                        delBuiler.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                books.remove(position);
                                BookListFragment.MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                                dataBookBank.saveData();
                                Toast.makeText(BookListFragment.this.getContext(),"Succeed to Delete",Toast.LENGTH_LONG).show();
                            }
                        });
                        delBuiler.setCancelable(false).setNegativeButton("Esc", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        delBuiler.create().show();
                        break;
                    case 3://编辑
                        intent=new Intent(BookListFragment.this.getContext(),EditBookActivity.class);
                        intent.putExtra("position",position);
                        intent.putExtra("title",books.get(position).getTitle());
                        launcherEdit.launch(intent);
                        break;
                }
                notifyDataSetChanged();//刷新页面
                return false;
            }
        }
    }
}
package com.jnu.booklistmainactivity.data;

import android.content.Context;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBookBank {
    private final Context context;
    List<Book> bookItemList;
    
    public DataBookBank(Context context) {
        this.context=context;
    }

    public List<Book> loadData() {
        bookItemList=new ArrayList<>();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput("data"));
            bookItemList = (ArrayList<Book>) objectInputStream.readObject();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return bookItemList;

    }

    public void saveData() {
        ObjectOutputStream objectOutputStream=null;
        try{
            objectOutputStream = new ObjectOutputStream(context.openFileOutput("data", Context.MODE_PRIVATE));
            objectOutputStream.writeObject(bookItemList);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

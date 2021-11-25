package com.jnu.booklistmainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        Intent intent = getIntent();

        int position = intent.getIntExtra("position", 0);

        EditText editTextTitle = findViewById(R.id.edit_text_bookName);

        String title = intent.getStringExtra("title");
        if (null != title) {
            editTextTitle.setText(title);
        }

        Button button_confirm = this.findViewById(R.id.button_edit_confirm);
        Button button_cancel = this.findViewById(R.id.button_edit_cancel);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("title", editTextTitle.getText().toString());
                intent.putExtra("position", position);
                setResult(BookListFragment.RESULT_CODE_ADD_DATA, intent);
                EditBookActivity.this.finish();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBookActivity.this.finish();
            }
        });
    }
}


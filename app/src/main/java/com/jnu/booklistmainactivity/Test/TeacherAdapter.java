package com.jnu.booklistmainactivity.Test;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnu.booklistmainactivity.R;

import java.util.List;

/**
 * Created by yw on 16/2/17.
 */
public class TeacherAdapter extends ArrayAdapter<Teacher> {

    public TeacherAdapter(Context context, int resource, List<Teacher> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Teacher teacher = getItem(position);

        View oneTeacherView = LayoutInflater.from(getContext()).inflate(R.layout.teacher_item, parent, false);

        ImageView imageView = (ImageView) oneTeacherView.findViewById(R.id.teacher_small_imageView);
        TextView textView = (TextView) oneTeacherView.findViewById(R.id.teacher_name_textView);

        imageView.setImageResource(teacher.getImageId());
        textView.setText(teacher.getName());

        oneTeacherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TeacherDetailActivity.class);

                intent.putExtra("teacher_image", teacher.getImageId());
                intent.putExtra("teacher_desc", teacher.getDesc());

                getContext().startActivity(intent);
            }
        });

        return oneTeacherView;
    }
}







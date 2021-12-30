package com.jnu.booklistmainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.booklistmainactivity.fragment.BookListFragment;
import com.jnu.booklistmainactivity.fragment.GameFragment;
import com.jnu.booklistmainactivity.fragment.MapFragment;
import com.jnu.booklistmainactivity.fragment.WebViewFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPagerFragment=findViewById(R.id.viewpager2_content);
        viewPagerFragment.setAdapter(new MyFragmentAdapter(this));
        TabLayout tabLayoutHeader= findViewById(R.id.tablayout_header);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayoutHeader, viewPagerFragment, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("图书");
                        break;
                    case 1:
                        tab.setText("新闻");
                        break;
                    case 2:
                        tab.setText("地图");
                        break;
                    case 3:
                        tab.setText("游戏");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }

    private class MyFragmentAdapter extends FragmentStateAdapter {


        public MyFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position){
                case 0:
                    return BookListFragment.newInstance();
                case 1:
                    return WebViewFragment.newInstance();
                case 2:
                    return MapFragment.newInstance();
                default:
                    return GameFragment.newInstance();
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}
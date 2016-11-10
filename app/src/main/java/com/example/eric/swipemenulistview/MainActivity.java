package com.example.eric.swipemenulistview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listview = (ListView)findViewById(R.id.listview);
        ListAdapter listAdapter = new ListAdapter(this);
        listAdapter.setList(getMockData());
        listview.setAdapter(listAdapter);
    }

    public class ListAdapter extends BaseListAdapter<String> {
        private List<DragView> views;
        public ListAdapter(Context context) {
            super(context);
            views = new ArrayList<>();
        }
        @Override
        protected int getLayoutId() {
            return R.layout.list_item;
        }

        @Override
        protected void setView(final int position, View convertView, ViewGroup parent, boolean isNew) {
            TextView txt = getViewById(R.id.txt);
            DragView delBtn = getViewById(R.id.drag_view);

            delBtn.setTag(position);
            if (isNew) {
                views.add(delBtn);
            }
            if (delBtn.isOpen()) {
                delBtn.closeAnim();
            }

            txt.setText(list.get(position));

            delBtn.setOnDragStateListener(new DragView.DragStateListener() {
                @Override
                public void onOpened(DragView dragView) {
                    for (int i = 0; i < views.size(); i++){
                        if ((int)views.get(i).getTag() != position) {
                            views.get(i).closeAnim();
                        }
                    }
                }
                @Override
                public void onClosed(DragView dragView) {

                }

                @Override
                public void onForegroundViewClick(DragView dragView , View v) {
                    notifyDataSetChanged();
                }

                @Override
                public void onBackgroundViewClick(DragView dragView , View v) {
                    notifyDataSetChanged();
                    int pos = (int) dragView.getTag();
                    list.remove(pos);
                    notifyDataSetChanged();
                }
            });
        }
    }

    private List<String> getMockData() {
        List<String> list = new ArrayList<>();
        for (int i = 0;i < 10;i++) {
            list.add("测试数据--》"+i);
        }
        return list;
    }
}

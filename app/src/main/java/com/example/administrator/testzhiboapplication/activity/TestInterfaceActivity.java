package com.example.administrator.testzhiboapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.example.administrator.testzhiboapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestInterfaceActivity extends AppCompatActivity {

    @BindView(R.id.lv_interfaces)
    ListView lvInterfaces;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.lv_contents)
    ListView lvContents;
    List<String> interfacelists=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_interface);
        ButterKnife.bind(this);
      initdata();
      addData();
    }

    private void addData() {

        lvInterfaces.setAdapter(new CommonAdapter<String>(TestInterfaceActivity.this,R.layout.left_item_list,interfacelists) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, String item, int position) {
                helper.setText(R.id.tv_name,item);
            }
        });

        lvInterfaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDataFromNet(position);
            }
        });

    }

    private void getDataFromNet(int position) {

        switch (position){
            case 0:
                Toast.makeText(this, "0000000", Toast.LENGTH_SHORT).show();



                break;
            case 1:
                Toast.makeText(this, "111111", Toast.LENGTH_SHORT).show();

                break;
            case 2:
                Toast.makeText(this, "2222222", Toast.LENGTH_SHORT).show();

                break;
            case 3:
                Toast.makeText(this, "33333", Toast.LENGTH_SHORT).show();

                break;
            case 4:

                break;


        }
    }

    private void initdata() {
        interfacelists.add("创建直播频道");
        interfacelists.add("查询直播频道");
        interfacelists.add("修改直播频道");
        interfacelists.add("查询直播并发数");
        interfacelists.add("删除直播频道");

    }

    @OnClick(R.id.tv_result)
    public void onClick() {
    }
}

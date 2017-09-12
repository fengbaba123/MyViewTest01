package com.jiyun.vae.myviewtest01;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 此代码不出效果
 */
public class MainActivity extends AppCompatActivity {

    private TextView tv_remind;

    private FlowLayout tcy_my_label, tcy_hot_label;
    private FlowLayoutAdapter mMyLabelAdapter, mHotLabelAdapter;
    private List<String> MyLabelLists, HotLabelLists;

    private static int TAG_REQUESTCODE = 0x101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        tv_remind = (TextView) findViewById(R.id.tv_remind);
        tcy_my_label = (FlowLayout) findViewById(R.id.tcy_my_label);
        tcy_hot_label = (FlowLayout) findViewById(R.id.tcy_hot_label);
    }

    private void initData() {
        String[] date = getResources().getStringArray(R.array.tags);
        HotLabelLists = new ArrayList<>();
        for (int i = 0; i < date.length; i++) {
            HotLabelLists.add(date[i]);
        }
        mHotLabelAdapter = new FlowLayoutAdapter(this, HotLabelLists);
        tcy_hot_label.setAdapter(mHotLabelAdapter);
        tcy_hot_label.setItemClickListener(new TagCloudLayoutItemOnClick(1));

        MyLabelLists = new ArrayList<>();
        mMyLabelAdapter = new FlowLayoutAdapter(this, MyLabelLists);
        tcy_my_label.setAdapter(mMyLabelAdapter);
        tcy_my_label.setItemClickListener(new TagCloudLayoutItemOnClick(0));

        String labels = String.valueOf(getIntent().getStringExtra("labels"));
        if (!TextUtils.isEmpty(labels) && labels.length() > 0
                && !labels.equals("null")) {
            String[] temp = labels.split(",");
            for (int i = 0; i < temp.length; i++) {
                MyLabelLists.add(temp[i]);
            }
            ChangeMyLabels();
        }

    }

    /**
     * 刷新我的标签数据
     */
    private void ChangeMyLabels() {
        tv_remind.setVisibility(MyLabelLists.size() > 0 ? View.GONE
                : View.VISIBLE);
        tcy_my_label.setVisibility(MyLabelLists.size() > 0 ? View.VISIBLE
                : View.GONE);
        mMyLabelAdapter.notifyDataSetChanged();
    }

    /**
     * 标签的点击事件
     *
     * @author lijuan
     */
    class TagCloudLayoutItemOnClick implements FlowLayout.TagItemClickListener {
        int index;

        public TagCloudLayoutItemOnClick(int index) {
            this.index = index;
        }

        @Override
        public void itemClick(int position) {
            switch (index) {
                case 0:
                    MyLabelLists.remove(MyLabelLists.get(position));
                    ChangeMyLabels();
                    break;
                case 1:
                    if (MyLabelLists.size() < 5) {
                        if (HotLabelLists.get(position).equals("自定义")) {
                            startActivityForResult(
                                    new Intent(MainActivity.this,
                                            AddTagActivity.class),
                                    TAG_REQUESTCODE);
                        } else {
                            Boolean isExits = isExist(MyLabelLists,
                                    HotLabelLists.get(position));
                            if (isExits) {
                                Toast.makeText(MainActivity.this, "此标签已经添加啦", Toast.LENGTH_LONG).show();
                                return;
                            }
                            MyLabelLists.add(HotLabelLists.get(position));
                            ChangeMyLabels();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "最多只能添加5个标签", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 将数组里面的字符串遍历一遍，看是否存在相同标签
     *
     * @param str
     * @param compareStr
     * @return
     */
    public static Boolean isExist(List<String> str, String compareStr) {
        Boolean isExist = false;//默认沒有相同标签
        for (int i = 0; i < str.size(); i++) {
            if (compareStr.equals(str.get(i))) {
                isExist = true;
            }
        }
        return isExist;
    }

    /**
     * 回传数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (TAG_REQUESTCODE == requestCode) {
            if (resultCode == AddTagActivity.TAG_RESULTCODE) {
                String label = data.getStringExtra("tags");
                MyLabelLists.add(label);
                ChangeMyLabels();
            }
        }
    }
}

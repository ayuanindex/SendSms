package com.ayuan.sendsms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private List<Persons> personsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //ListView控件
        ListView contact_ListView = (ListView) findViewById(R.id.lv_contactitem);
        personsList = new ArrayList<Persons>();
        for (int i = 0; i < 20; i++) {
            Persons persons = new Persons("张三" + i, "13890880" + i);
            personsList.add(persons);
        }
        //给ListView设置数据适配器
        contact_ListView.setAdapter(new MyContactAdapter());
        //个体ListView设置点击事件
        contact_ListView.setOnItemClickListener(new getContentItem());
    }

    private class MyContactAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return personsList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.item_contact, null);
            } else {
                view = convertView;
            }
            TextView contactName = (TextView) view.findViewById(R.id.tv_name);
            TextView contactPhone = (TextView) view.findViewById(R.id.tv_phone);
            Persons personItem = personsList.get(position);
            contactName.setText(personItem.getContactName());
            contactPhone.setText(personItem.getContactPhone());
            return view;
        }
    }

    //条目点击事件
    private class getContentItem implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Persons persons = personsList.get(position);
            Intent intent = new Intent();
            intent.putExtra("contactPhone", persons.getContactPhone());
            //把数据返回给调用者
            setResult(10, intent);
            //关闭当前页面
            finish();
        }
    }
}


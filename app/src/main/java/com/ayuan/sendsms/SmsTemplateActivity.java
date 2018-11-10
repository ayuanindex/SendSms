package com.ayuan.sendsms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SmsTemplateActivity extends AppCompatActivity {

    String Objects[] = {"我在吃饭请稍后联系", "我在开会", "我在上课", "我在敲代码", "我在约会"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smstemplate);

        ListView smstemp = (ListView) findViewById(R.id.lv_template);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_smstemplate, Objects);
        smstemp.setAdapter(adapter);
        //设置条目的点击事件
        smstemp.setOnItemClickListener(new MyItemClick());
    }

    //这里是条目的点击事件
    private class MyItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String sms = Objects[position];
            Intent intent = new Intent();
            intent.putExtra("sms", sms);
            setResult(20, intent);
            finish();
        }
    }
}

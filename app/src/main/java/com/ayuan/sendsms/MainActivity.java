package com.ayuan.sendsms;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText contact;
    private EditText messageContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //这是联系人的控件
        contact = (EditText) findViewById(R.id.et_number);
        //这是添加按钮的控件
        Button addContact = (Button) findViewById(R.id.btn_add);
        //这是文本编辑栏的控件
        messageContent = (EditText) findViewById(R.id.et_content);
        //这是发送按钮的控件
        Button sendMessage = (Button) findViewById(R.id.btn_send);
        //这是插入模板的控件
        Button messageTemplate = (Button) findViewById(R.id.btn_template);
        //给添加按钮设置点击事件
        addContact.setOnClickListener(new MyAddOnClick());

        //给发送按钮设置点击事件
        sendMessage.setOnClickListener(new MySendMessage());

        //给模板按钮设置点击事件
        messageTemplate.setOnClickListener(new InsertTemplate());
    }

    /**
     * 当开启的Activity页面关闭的时候自动调用此方法
     *
     * @param requestCode 请求码 这个参数是此页面的请求码
     * @param resultCode  结果码 这个参数是上一个页面返回的结果码
     * @param data        是上一个页面传递的Intent对象
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            switch (resultCode) {
                case 10:
                    SetContact(data);
                    break;
                case 20:
                    GetMessageTemplate(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void GetMessageTemplate(@Nullable Intent data) {
        String sms = data.getStringExtra("sms");
        messageContent.setText(sms);
    }

    private void SetContact(@Nullable Intent data) {
        String phone = data.getStringExtra("contactPhone");
        if (!TextUtils.isEmpty(phone)) {
            //String trim = "";
            //if (contact.getText().toString().trim() != null) {
            //    trim = contact.getText().toString().trim();
            //}
            contact.setText(phone);
        }
    }

    //添加发送人的按钮点击事件
    private class MyAddOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
            //startActivity(intent);
            //如果点击按钮开启了另外一个Activity，并且当开启的Activity关闭的时候  并开启意图跳转到指定页面
            startActivityForResult(intent, 1);
        }
    }

    //发送短信的点击事件
    private class MySendMessage implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String message = messageContent.getText().toString().trim();
            String number = contact.getText().toString().trim();
            if (TextUtils.isEmpty(number)) {
                Toast.makeText(MainActivity.this, "请选择联系人", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(message)) {
                Toast.makeText(MainActivity.this, "请输入短信内容", Toast.LENGTH_SHORT).show();
                return;
            }
            //获取SmsManager的实例
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> strings = smsManager.divideMessage(message);
            for (String a : strings) {
                /**
                 * destinationAddress 目标的地址:发送短息的目标联系人
                 * scAddress 短信服务中心的地址:默认情况为null会自动识别移动、联通、和电信
                 * text 要发送的内容
                 * sentIntent 广播接收者:可代表发送成功的标志
                 * deliveryIntent 广播接收者:代表发送失败的标志
                 */
                smsManager.sendTextMessage(number, null, a, null, null);
            }
        }
    }

    //插入模板的事件
    private class InsertTemplate implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, SmsTemplateActivity.class);
            startActivityForResult(intent, 2);
        }
    }
}

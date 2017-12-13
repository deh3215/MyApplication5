package com.example.a32150.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btn_read, btn_write, btn_set, btn_share;
    TextView tv;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_read = (Button)findViewById(R.id.btn_read);
        btn_write = (Button)findViewById(R.id.btn_write);
        btn_set = (Button)findViewById(R.id.btn_set);
        btn_share = (Button)findViewById(R.id.btn_share);
        tv=(TextView)findViewById(R.id.textView);
        et=(EditText)findViewById(R.id.editText);

        btn_read.setOnClickListener(listener);
        btn_write.setOnClickListener(listener);
        btn_set.setOnClickListener(listener);
        btn_share.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener()    {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if(id == R.id.btn_read) {
                fileRead();
            }   else if(id == R.id.btn_write)   {
                fileWrite();
            }   else if(id == R.id.btn_set) {
                setting();
            }   else if(id == R.id.btn_share) {
                shareRead();
            }
        }
    };

    public void setting()  {
        Intent it = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(it);
    }

    void shareRead() {
        SharedPreferences sp = getSharedPreferences(getPackageName()+"_preferences", MODE_PRIVATE);
        String str = sp.getString("example_text", "");
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    void fileRead() {

        char ch[] = new char[100];

        try {
            File f = getFilesDir();
            File path = new File(f.getAbsolutePath() + f.separator + "mydata.txt");
            if(path.exists()) {
                int len = 0;
                StringBuilder sb = new StringBuilder();
                FileReader fr = new FileReader(path);
                while ((len = fr.read(ch)) > 0) {
                    sb.append(new String(ch, 0, len));
                }
                System.out.println("讀出的字串" + sb);
                tv.setText(sb);
                fr.close();
            }   else    {
                System.out.println("檔案不存在");
                tv.setText("檔案不存在");
                Toast.makeText(MainActivity.this, "檔案不存在", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found !");
            tv.setText("File not found !");
        } catch (IOException ex) {
            System.out.println("IO error !");
            tv.setText("IO error !");
        }
    }

    void fileWrite()   {

        File f = getFilesDir();
        File myfile = new File(f.getAbsolutePath() + f.separator + "mydata.txt");
        try {
            FileWriter fw = new FileWriter(myfile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(et.getText().toString());
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

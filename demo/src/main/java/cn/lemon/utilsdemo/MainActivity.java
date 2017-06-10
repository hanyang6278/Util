package cn.lemon.utilsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.lemon.util.SQLHelper;
import cn.lemon.util.Utils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.Toast("哇卡卡");
        Utils.Log("哇卡卡");

        SQLHelper sqlHelper = new SQLHelper(this,"DB");
    }
}

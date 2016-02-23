package com.example.testdll;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Java 调用 C 例子 
 * 
 * @author luxiaofeng <454162034@qq.com>
 *
 */
public class MainActivity extends Activity {
    //也就是你mk配置文件中的  LOCAL_MODULE    := NDK_03
    private static final String libSoName = "NDK_03";
    private static final String tag = "MainActivity";
    
    private Context mContext = null;
    private Button btnClick = null;
    private String mStrMSG = null;
    private EditText etX = null;
    private EditText etY = null;
    private EditText etResult = null;
    
    private int x = 0 ;
    private int y = 0 ;
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mContext = this;
        //初始化控件
        initViews();
    }
    
    /**
     * 初始化控件
     */
    private void initViews() {
        etX = (EditText)findViewById(R.id.et_x);
        etY = (EditText)findViewById(R.id.et_y);
        etResult = (EditText)findViewById(R.id.et_result);
        btnClick = (Button) findViewById(R.id.btn_click);
        btnClick.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {

                if(getX() && getY()){
                    int result = Java_com_duicky_MainActivity_add(x,y);
                    LogUtils.printWithLogCat(tag,x+" + " + y + " = " +result);
                    etResult.setText(String.valueOf(result));
                } else {
                    etX.setText("");
                    etY.setText("");
                    etResult.setText("");
                    LogUtils.toastMessage(mContext, "请输入正确的值");
                }
                
            }
        });
        
    }

    /**
     * 获取x
     */
    private boolean getX() {
        String str = etX.getText().toString().trim();
        try {
            x = Integer.valueOf(str);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    /**
     * 获取y
     */
    private boolean getY() {
        String str = etY.getText().toString().trim();
        try {
            y = Integer.valueOf(str);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    /**
     * 该方法为native方法.
     * 
     *         实现加法功能
     * 
     * @param x    加数
     * @param y 加数
     * 
     * @return x+y 的结果
     */
    //public native int add(int x, int y);
    public native int Java_com_duicky_MainActivity_add(int x, int y);
    
    /**
     * 载入JNI生成的so库文件
     */
    static {
        System.loadLibrary("NDK_03");
    }
    
}
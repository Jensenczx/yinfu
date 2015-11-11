package com.example.chenjensen.yinfu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.voicecollectutile.AudioUtil;


public class MainActivity extends Activity implements View.OnClickListener{

    private AudioUtil mUtil;
    private boolean isRecording = false;
    private boolean ifFirst = true;
    private Button btn_rec;
    private EditText edtxt_rec;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        btn_rec = (Button)findViewById(R.id.record);
        btn_rec.setOnClickListener(this);
        edtxt_rec = (EditText)findViewById(R.id.input_fileName);
        Button btn_convert = (Button)findViewById(R.id.convert);
        btn_convert.setOnClickListener(this);
    }

    /*
    当录音工具类为空的时候，创建一个录音工具类的对象，然后开始录音，将状态改为正在录音
    如果是第一次录音，则要开启一个记录数据的线程，通过这个线程，判断当前的记录状态，然后进行
    数据的写入
     */

    public void startRecord(){
        if(mUtil==null){
            mUtil = AudioUtil.getInstance();
        }
        mUtil.startRecord();
        isRecording = true;
        if(ifFirst){
            mUtil.recordData();
        }
    }

    public void stopRecord(){
        if(mUtil!=null)
            mUtil.stopRecord();
        isRecording = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.record:
                if(isRecording){
                    stopRecord();
                    btn_rec.setText("开始录音");
                }
                else{
                    startRecord();
                    btn_rec.setText("录音中...");
                }
                break;
            case R.id.convert:
            if(mUtil!=null){
                getFileName();
                if(fileName=="")
                    Toast.makeText(getBaseContext(),"请输入文件名",Toast.LENGTH_SHORT).show();
                else{
                    mUtil.convertWaveFile(fileName);
                    Toast.makeText(getBaseContext(),"文件创建成功",Toast.LENGTH_SHORT).show();
                }
            }
                break;
            default:
                break;

        }
    }

    public void getFileName(){
        fileName = edtxt_rec.getText().toString();
    }

}

package com.voicecollectutile;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by chenjensen on 15/7/10.
 */
public class AudioUtil implements AudioRecord.OnRecordPositionUpdateListener{

    private static AudioUtil mInstance;
    private AudioRecord recorder;
    //录音源
    private static int audioSource = MediaRecorder.AudioSource.MIC;
    //录音的采样频率
    private static int audioRate = 44100;
    //录音的声道
    private static int audioChannel = AudioFormat.CHANNEL_IN_MONO;
    //量化的深度
    private static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    //缓存的大小
    private static int bufferSize = AudioRecord.getMinBufferSize(audioRate,audioChannel,audioFormat);
    //记录播放状态
    private boolean isRecording = false;
    //数字信号数组
    private byte [] noteArray;
    //文件输出流
    private OutputStream os;

    public String basePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/yinfu/";

    private String inFileName = basePath+"/yinfu.pcm";

    private AudioUtil(){
        recorder = new AudioRecord(audioSource,audioRate,audioChannel,audioFormat,bufferSize);
    }

    //得到录音的实例
    public synchronized static AudioUtil getInstance(){
        if(mInstance == null){
            mInstance = new AudioUtil();
        }
        return mInstance;
    }

    //读取录音数字数据线程
    class WriteThread implements Runnable{
        public void run(){
            writeData();
        }
    }

    //开始录音
    public void startRecord(){
        isRecording = true;
        recorder.startRecording();
    }

    //停止录音
    public void stopRecord(){
        isRecording = false;
        recorder.stop();
    }

    //记录数据
    public void recordData(){
        new Thread(new WriteThread()).start();
    }


    //将数据写入文件夹
    public void writeData(){
        File pcmFile = createFile(inFileName);
        noteArray = new byte[bufferSize];
        //建立文件输出流
        try {
            os = new BufferedOutputStream(new FileOutputStream(pcmFile));
        }catch (IOException e){

        }
        while(isRecording == true){
            int recordSize = recorder.read(noteArray,0,bufferSize);
            if(recordSize>0){
                //音频处理
                try{
                    os.write(noteArray);
                }catch(IOException e){

                }
            }
        }
        if (os != null) {
            try {
                    os.close();
                }catch (IOException e){

                }
        }
    }

    // 这里得到可播放的音频文件
    public void convertWaveFile(String outFileName) {
        String outFilePath = basePath+"/"+outFileName+".wav";
        AudioTransform mTransform = new AudioTransform();
        createFile(outFilePath);
        mTransform.convertWaveFile(AudioUtil.audioRate,bufferSize,inFileName,outFilePath);
    }


    //创建文件夹,首先创建目录，然后创建对应的文件，分清楚目录和实际文件
    public File createFile(String filePath){
        File baseFile = new File(basePath);
        if(!baseFile.exists())
            baseFile.mkdirs();
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
        try{
            file.createNewFile();
        }catch(IOException e){

        }
        return file;
    }


    @Override
    public void onMarkerReached(AudioRecord recorder) {
    }

    @Override
    public void onPeriodicNotification(AudioRecord recorder) {
    }
}

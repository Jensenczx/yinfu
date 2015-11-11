package com.chenjensen.voicedisposeutile;

/**
 * Created by chenjensen on 15/7/27.
 */
public class VoiceComparion {

    public VoiceComparion(){

    }

    //当返回null的时候，表示在口琴的频谱表中，没有频率不在这一个阶段内
    public String comparion(int frequency){
        for(int i=0; i<VoiceFrequencyData.FREQUENCY_ARRAY.length; i++){
            if(frequency==VoiceFrequencyData.FREQUENCY_ARRAY[i])
                return VoiceFrequencyData.HARMONICA_MUSIC_ARRAY[i];
        }
        return null;
    }
}

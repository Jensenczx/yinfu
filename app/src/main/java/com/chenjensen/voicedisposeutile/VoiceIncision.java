package com.chenjensen.voicedisposeutile;

/**
 * Created by chenjensen on 15/7/27.
 */
public class VoiceIncision {

    private final static long ENERGY_MIN=33;

    private final static long ENERGY_MAX=900;

    public VoiceIncision(){

    }

    public boolean ifEntrtIntoTone(byte [] voice){
        long energy = energyJudge(voice);
        energyJudge(voice);
        return false;
    }

    public long energyJudge(byte [] voice){
        long energy=0;
        for(byte i:voice){
            energy = energy+Math.abs(i);
        }
        return energy;
    }

    public long exceedZeroRateJudge(){
        long exceedZeroRate = 0 ;
        return exceedZeroRate;
    }


}

package com.example.aiko.flushcalculator;

import android.os.CountDownTimer;
import android.support.v4.view.AsyncLayoutInflater;

/**
 * Created by aiko on 2017/12/24.
 */

public class CountDown extends CountDownTimer {

    //ここにリスナーに変数を入れるための変数を宣言する
    OnFinishListener onFinishListener;
    OnTickListener onTickListener;

    public CountDown(long millisInFuture, long countdownInterval) {
        super(millisInFuture, countdownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //毎秒呼ばれる(呼ばれる間隔は指定できる)
        if (onTickListener != null) onTickListener.onTick(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        //数え終わった時に呼ばれる
        if (onFinishListener != null) onFinishListener.onFinish();
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public void setOnTickListerner(OnTickListener onTickListener) {
        this.onTickListener = onTickListener;

    }

    //EventListeners

    public interface OnFinishListener{
        void onFinish();
    }

    public interface OnTickListener{
        void onTick(long millisUntilFinished);
    }


}

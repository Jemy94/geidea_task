package com.jemy.geideaapp

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import com.jemy.geideaapp.utils.Constants

class TimerService : Service() {


    val counterIntent = Intent(Constants.Key.COUNT_DOWN_BR)

    override fun onCreate() {
        super.onCreate()
        setupCountDownTimer()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    private fun setupCountDownTimer() {
        object : CountDownTimer(5000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                counterIntent.putExtra(Constants.Key.COUNT_DOWN,(millisUntilFinished/1000))
                sendBroadcast(counterIntent)
            }

            override fun onFinish() {
            }
        }.start()
    }

}
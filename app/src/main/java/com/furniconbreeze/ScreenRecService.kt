package com.furniconbreeze

import android.app.Service
import android.content.Intent
import android.icu.util.TimeUnit
import android.os.IBinder
import android.os.SystemClock
import android.widget.Chronometer
import com.furniconbreeze.app.Pref
import com.furniconbreeze.features.dashboard.presentation.DashboardActivity
import com.furniconbreeze.features.dashboard.presentation.DashboardFragment
import java.util.*

class ScreenRecService: Service() {

    var sec:Int = 0
    var mi:Int = 0
    var ho:Int = 0
    var timer : Timer? = null

    companion object{
        var isPause:Boolean = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //DashboardFragment.screen_timer.start()

        timer = Timer()
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                //xx()


                if(isPause==false){
                    sec++
                    if(sec == 60){
                        mi++
                        sec=0
                    }
                    if(mi==60){
                        mi=0
                        ho++
                    }

                    //DashboardFragment.tv_timer.text="sf"

                }

                //code start Mantis- 27419 by puja screen recorder off 07.05.2024 v4.2.7
               // DashboardFragment.tv_timer.text= String.format("%02d",ho)+":"+String.format("%02d",mi)+":"+String.format("%02d",sec)
                //code end Mantis- 27419 by puja screen recorder off 07.05.2024 v4.2.7

            }
        }
        timer!!.schedule(task, 0, 1000)

        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        timer!!.cancel()
    }



/*    fun xx(){
        var elapsed = (SystemClock.elapsedRealtime() - DashboardFragment.screen_timer.base)
        println("timer "+elapsed.toString());

        val hms = String.format("%02d:%02d:%02d", java.util.concurrent.TimeUnit.MILLISECONDS.toHours(elapsed),
                java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(elapsed) - java.util.concurrent.TimeUnit.HOURS.toMinutes(java.util.concurrent.TimeUnit.MILLISECONDS.toHours(elapsed)),
                java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(elapsed) - java.util.concurrent.TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(elapsed)))

        //println("timerrrrrr "+hms.toString()+" : "+Pref.screenTime.toString());
        println("timerrrrrr "+ho.toString()+" : "+mi.toString()+" : "+sec.toString());


    }*/

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not Yet Implemented")
    }




}
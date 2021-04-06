package com.example.alarammanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
/*
* this class use to generate notification at particular time
* - set alarm manager
* */
class NotificationGenerate {
    companion object {
        const val  REPEAT_15_MIN = 15*60*1000L

        fun setEventAlarm(context: Context, eventTitle: String?, scheduleTime: Long, isRepeatInEvery15Min: Boolean? =false) {
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            val intent = Intent(context, NotificationBroascastReceiver::class.java)
            if(isRepeatInEvery15Min?: false) {
                intent.putExtra("message", "REPEATING EVENT IN 15 MIN")
            }else{
                intent.putExtra("message", eventTitle)

            }
            val pendingIntent =
                PendingIntent.getBroadcast(context, 4543, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            Log.e("setEventAlarm ", "setEventAlarm")

            if(isRepeatInEvery15Min?: false){
                am?.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(),
                    REPEAT_15_MIN,
                    pendingIntent);
            }else{
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> am?.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, scheduleTime, pendingIntent
                )
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> am?.setExact(
                    AlarmManager.RTC_WAKEUP, scheduleTime, pendingIntent
                )
                else -> am?.set(AlarmManager.RTC_WAKEUP, scheduleTime, pendingIntent)

            }
            }
        }
    }
}
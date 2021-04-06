package com.example.alarammanager

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationBroascastReceiver : BroadcastReceiver() {
    //region- companion
    companion object {
        const val NOTIFICATION_CHANNEL_ID = "4545"
    }
//endregion

    //region- override function
    override fun onReceive(p0: Context?, intent: Intent?) {
        var message = intent?.getStringExtra("message") ?: ""
        createNotification(p0, message)
    }
//endregion

    //region- private function
    private fun createNotification(
        context: Context?,
        title: String
    ) {
        val mNotificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel =
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, "App Notification",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply { setShowBadge(true) }
            mNotificationManager?.createNotificationChannel(mChannel)
        }
        // mId allows you to update the notification later on.
        mNotificationManager?.notify(
            45,
            getNotificationBuilder(context, title).build()
        )
    }

    private fun getNotificationBuilder(
        context: Context?, title: String
    ): NotificationCompat.Builder {
// Remove alarm icon if server notification
        val largeIcon = BitmapFactory.decodeResource(context?.resources, R.drawable.ic_launcher)

        return NotificationCompat.Builder(context!!, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher, 5)
            .setColor(context?.resources.getColor(R.color.colorPrimary))
            .setLargeIcon(largeIcon)
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Notification"))
            .setContentText("Notification")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_LIGHTS and Notification.DEFAULT_SOUND).apply {
                setContentIntent(getResultPendingIntent(context))
            }.setAutoCancel(true)
    }

    private fun getResultPendingIntent(context: Context): PendingIntent? {
// Create an Intent for the activity you want to start
        val resultIntent = Intent(context, MainActivity::class.java)
// Create the TaskStackBuilder
        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(resultIntent)
// Get the PendingIntent containing the entire back stack
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }
    //endregion
}
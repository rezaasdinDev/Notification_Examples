package com.example.notificationtest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val channelID = "com.example.notificationtest.channel1"
    private var notificationManager: NotificationManager? = null;
    private val KEY_REPLY = "key_reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, "DemoChannel", "This is a demo!!!")

        btn_make_notif.setOnClickListener {
            displayNotification()
        }
    }

    private fun displayNotification() {

        val notifivationId = 45

        //action after click on notification
        val tapResultIntent = Intent(this, SecondActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK

        }


        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        //reply action
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_REPLY).run{
            setLabel("Insert your name here")
            build()
        }

        val replyAction: NotificationCompat.Action = NotificationCompat.Action.Builder(
            0,
            "reply",
            pendingIntent
        ).addRemoteInput(remoteInput)
            .build()

        //action button detail
        val toDetailsIntent = Intent(this, DetailsActivity::class.java)


        val toDetailPendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            toDetailsIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val toDetailAction: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "Details", toDetailPendingIntent).build()

        //action button settings
        val toSettingsIntent = Intent(this, SettingsActivity::class.java)


        val toSettingsPendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            toSettingsIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val toSettingsAction: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "Settings", toSettingsPendingIntent).build()


        //
        val notification = NotificationCompat.Builder(this@MainActivity, channelID)
            .setContentTitle("Demo Title")
            .setContentTitle("This is a demo notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .addAction(toDetailAction)
            .addAction(toSettingsAction)
            .addAction(replyAction)
            .build()

        notificationManager?.notify(notifivationId, notification)

    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }

            notificationManager?.createNotificationChannel(channel)
        }
    }
}
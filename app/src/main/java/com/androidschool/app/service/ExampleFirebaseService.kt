package com.androidschool.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.androidschool.app.PushApplication
import com.androidschool.app.presentation.MainActivity
import com.androidschool.app.R
import com.androidschool.app.data.PushExampleApiService
import com.androidschool.app.data.model.DeviceIdApiModel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ExampleFirebaseService : FirebaseMessagingService() {

    @Inject
    lateinit var pushApiService: PushExampleApiService

    override fun onCreate() {
        PushApplication.instance.components.getFirebaseServiceComponent().inject(this)
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        PushApplication.instance.components.clearFirebaseServiceComponent()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        pushApiService.addDeviceId(
            DeviceIdApiModel(
                "Max",
                token))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {it.printStackTrace()})
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (message.data.containsKey("screen")) {
            Log.d("InputData", message.data.getValue("screen"))
        }

        message.notification?.let {
            sendNotification(it.body!!, it.title!!)
        }
    }

    private fun sendNotification(messageBody: String, messageTitle: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var notificationBuilder: NotificationCompat.Builder? = null
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                packageName,
                packageName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = packageName
            notificationManager.createNotificationChannel(channel)
            if (notificationBuilder == null) {
                notificationBuilder = NotificationCompat.Builder(application, packageName)
            }
        } else {
            if (notificationBuilder == null) {
                notificationBuilder = NotificationCompat.Builder(application, packageName)
            }
        }
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}
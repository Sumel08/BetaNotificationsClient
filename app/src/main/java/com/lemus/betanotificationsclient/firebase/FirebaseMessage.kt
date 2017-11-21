package com.lemus.betanotificationsclient.firebase

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.content.ContentValues.TAG
import android.support.v7.app.NotificationCompat
import com.lemus.betanotificationsclient.R
import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context
import android.app.PendingIntent
import android.R.attr.label
import android.app.Notification
import android.content.Intent
import android.provider.AlarmClock
import com.lemus.betanotificationsclient.MainActivity


/**
 * Created by lemus on 11/21/17.
 */
class FirebaseMessage: FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage!!.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob()
                Log.d(TAG, "ScheduleJob!!")
            } else {
                // Handle message within 10 seconds
                //handleNow()
                Log.d(TAG, "HandleNow!!")
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification.body!!)

            // Key for the string that's delivered in the action's intent.
            val KEY_TEXT_REPLY = "key_text_reply"
            val replyLabel = resources.getString(R.string.reply_label)
            val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
                    .setLabel(replyLabel)
                    .build()

            // Build a PendingIntent for the reply action to trigger.

            val notificationIntent = Intent(applicationContext, MainActivity::class.java)

            val contentIntent = PendingIntent.getActivity(applicationContext,
                    0, notificationIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT)

            // Create the reply action and add the remote input.
            val action = Notification.Action.Builder(R.drawable.ic_menu_gallery,
                    getString(R.string.label), contentIntent)
                    .addRemoteInput(remoteInput)
                    .build()

            val mBuilder = Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_menu_camera)
                    .setContentTitle("My notification")
                    .setContentText("Hello World!")
                    .addAction(action)

            // Sets an ID for the notification
            val mNotificationId = 1
// Gets an instance of the NotificationManager service
            val mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
// Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build())

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
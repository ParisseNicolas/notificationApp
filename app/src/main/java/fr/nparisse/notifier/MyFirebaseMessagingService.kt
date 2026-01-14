package fr.nparisse.notifier

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.i("FCM_TOKEN", token)
        // TODO: Optionnel - remonter ce token à ton worker/serveur
    }

    
    
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // 1) Canal & son pilotés par FCM (data) — sinon valeurs par défaut
        val channelId = message.data["channel_id"] ?: "alerts_channel_2"
        val soundName = message.data["sound"] ?: "alert_sound"

        // 2) Assure la présence du canal (USAGE_ALARM, importance haute)
        NotificationHelper.ensureChannel(
            context = this,
            channelId = channelId,
            soundName = soundName
        )

        // 3) Récupère titre/corps (notification ou data) et affiche
        val title = message.notification?.title ?: message.data["title"] ?: "Notification"
        val body  = message.notification?.body  ?: message.data["body"]  ?: ""
        NotificationHelper.show(this, title, body, channelId)
    }



}

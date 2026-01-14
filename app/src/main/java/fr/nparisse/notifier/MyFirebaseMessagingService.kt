package fr.nparisse.notifier

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.i("FCM_TOKEN", token)
        // TODO: Optionnel - remonter ce token à ton worker/serveur
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // Notification payload
        message.notification?.let {
            NotificationHelper.show(this, it.title ?: "Notification", it.body ?: "")
        }
        // Data payload
        if (message.data.isNotEmpty()) {
            val title = message.data["title"] ?: "Data"
            val body = message.data["body"] ?: message.data.toString()
            NotificationHelper.show(this, title, body)
        }
    }
}

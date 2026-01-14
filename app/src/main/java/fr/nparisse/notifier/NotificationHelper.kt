
package fr.nparisse.notifier

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

object NotificationHelper {

    /**
     * Crée (si nécessaire) un canal avec le son et l'importance voulus.
     * Par défaut : USAGE_ALARM pour maximiser l'audibilité (DND/volume bas).
     */
    fun ensureChannel(
        context: Context,
        channelId: String,
        soundName: String = "alert_sound",
        importance: Int = NotificationManager.IMPORTANCE_HIGH,
        usage: Int = AudioAttributes.USAGE_ALARM
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mgr = context.getSystemService(NotificationManager::class.java)
            if (mgr?.getNotificationChannel(channelId) == null) {
                val soundUri = Uri.parse("android.resource://${context.packageName}/raw/$soundName")
                val attrs = AudioAttributes.Builder()
                    .setUsage(usage)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()

                val channel = NotificationChannel(channelId, "Alertes", importance).apply {
                    setSound(soundUri, attrs)
                    enableVibration(true)
                    description = "Notifications d’alerte avec son personnalisé"
                }
                mgr?.createNotificationChannel(channel)
            }
        }
    }

    /**
     * Affiche une notification sur le canal indiqué.
     */
    fun show(context: Context, title: String, body: String, channelId: String) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val mgr = ContextCompat.getSystemService(context, NotificationManager::class.java)
        mgr?.notify(System.currentTimeMillis().toInt(), builder.build())
    }
}

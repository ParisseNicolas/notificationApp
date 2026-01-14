
package fr.nparisse.notifier

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Crée un canal prêt à l’emploi (son + vibration)
        NotificationHelper.ensureChannel(
            context = this,
            channelId = "alerts_channel_2",
            soundName = "alert_sound"
        )
    }
}

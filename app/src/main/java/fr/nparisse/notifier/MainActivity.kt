package fr.nparisse.notifier

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var tokenView: TextView
    private lateinit var permButton: Button

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        Log.i("PERM", "POST_NOTIFICATIONS granted=$granted")
        fetchFcmToken()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tokenView = findViewById(R.id.tokenView)
        permButton = findViewById(R.id.permButton)
        permButton.setOnClickListener { askNotificationPermission() }
        askNotificationPermission()
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            val perm = Manifest.permission.POST_NOTIFICATIONS
            when {
                ContextCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED -> fetchFcmToken()
                shouldShowRequestPermissionRationale(perm) -> requestPermissionLauncher.launch(perm)
                else -> requestPermissionLauncher.launch(perm)
            }
        } else {
            fetchFcmToken()
        }
    }

    private fun fetchFcmToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    tokenView.text = "Token error"
                    Log.e("FCM", "Token fetch failed", task.exception)
                    return@addOnCompleteListener
                }
                val token = task.result
                tokenView.text = token
                Log.i("FCM_TOKEN", token)
            }
    }
}

package com.frezzcoding.bolosassuncao.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.view.privileged.PrivilegedUserActivity
import com.google.firebase.messaging.RemoteMessage
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class FirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        //Log.e("new token", token)
        //CoroutineScope(IO).launch {
            storeToken(token)
        //}
        super.onNewToken(token)
    }
    private fun storeToken(token : String) {

        if (token.isNotEmpty()) {
            var client = OkHttpClient()
            var body = FormBody.Builder()
                .add("fcm_token", token)
                .build()

            var request = Request.Builder()
                .url("http://www.bolosassuncao.com.br/get_notification.php")
                .post(body)
                .build()
            try {
                client.newCall(request).execute()
            } catch (e: IOException) {

            }
        }
    }


    @Override
    override fun onMessageReceived(message: RemoteMessage) {
        showNotification(message.data["message"])
    }

    private fun showNotification(message : String?){
        var intent = Intent(this, PrivilegedUserActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        var pending = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        var builder = NotificationCompat.Builder(this)
            .setAutoCancel(true)
            .setContentTitle("FCM Test")
            .setContentText(message)
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
            .setContentIntent(pending)

        var manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(0,builder.build())
    }

}
package th.thebox.sayhichat.application

import android.app.Application
import com.google.firebase.FirebaseApp

class SayHiChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

}
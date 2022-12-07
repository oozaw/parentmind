package com.capstone.parentmind.view.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.parentmind.R

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ParentMind)
        setContentView(R.layout.activity_notification)
    }
}
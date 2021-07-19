package com.example.notificationtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.RemoteInput
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        receiveInput()

    }

    private fun receiveInput() {

        val KEY_REPLY = "key_reply"

        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)

        if (remoteInput != null) {
            val inputString = remoteInput.getCharSequence(KEY_REPLY).toString()
            txt_second_activity.text = inputString
        }

    }

}
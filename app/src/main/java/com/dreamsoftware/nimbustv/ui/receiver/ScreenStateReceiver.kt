package com.dreamsoftware.nimbustv.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.dreamsoftware.fudge.utils.FudgeTvEventBus
import com.dreamsoftware.nimbustv.AppEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ScreenStateReceiver : BroadcastReceiver() {

    @Inject
    lateinit var appEventBus: FudgeTvEventBus

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_SCREEN_ON -> {
                appEventBus.send(AppEvent.ComeFromStandby)
            }
            Intent.ACTION_SCREEN_OFF -> {
                appEventBus.send(AppEvent.GoToStandby)
            }
        }
    }

    companion object {
        fun register(context: Context): ScreenStateReceiver {
            val receiver = ScreenStateReceiver()
            val filter = IntentFilter().apply {
                addAction(Intent.ACTION_SCREEN_ON)
                addAction(Intent.ACTION_SCREEN_OFF)
            }
            context.registerReceiver(receiver, filter)
            return receiver
        }

        fun unregister(context: Context, receiver: ScreenStateReceiver) {
            context.unregisterReceiver(receiver)
        }
    }
}
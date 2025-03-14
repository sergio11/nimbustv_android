package com.dreamsoftware.nimbustv.utils.network

import android.net.ConnectivityManager
import android.net.Network
import com.dreamsoftware.nimbustv.AppEvent
import com.dreamsoftware.fudge.utils.FudgeTvEventBus

class NetworkConnectivityCallback(private val appEventBus: FudgeTvEventBus) : ConnectivityManager.NetworkCallback() {

    private var lastState: Boolean = true

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        updateConnectivityState(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        updateConnectivityState(false)
    }

    private fun updateConnectivityState(newState: Boolean) {
        if (newState != lastState) {
            appEventBus.send(
                AppEvent.NetworkConnectivityStateChanged(
                    lastState = lastState,
                    newState = newState
                )
            ).also {
                lastState = newState
            }
        }
    }

    companion object {
        private const val TAG = "NetworkConnectivity"
    }
}
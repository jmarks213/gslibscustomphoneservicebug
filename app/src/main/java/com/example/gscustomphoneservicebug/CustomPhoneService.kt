package com.example.gscustomphoneservicebug

import android.util.Log
import com.gs.phone.service.BasePhoneService

class CustomPhoneService: BasePhoneService() {

    companion object {
        private const val TAG = "CustomPhoneService"
    }

    override fun onHookEvent(offHook: Boolean) {
        super.onHookEvent(offHook)

        Log.v(TAG, "onHookEvent, offHook = $offHook")
    }


    override fun onEHSHookEvent(offHook: Boolean) {
        super.onEHSHookEvent(offHook)

        Log.v(TAG, "onEHSHookEvent, offHook = $offHook")
    }


    override fun onLineStateChanged(lineId: Int, status: Int) {
        super.onLineStateChanged(lineId, status)

        Log.v(TAG, "onLineStateChanged, lineId = $lineId , status = $status")
    }


    /**
     * return true means this click event was cost by this service.
     * else this event will call the system emergency dialer.
     */
    override fun onEmergencyCallButtonClicked(): Boolean {

        Log.v(TAG, "onEmergencyCallButtonClicked")

        return super.onEmergencyCallButtonClicked()
    }
}
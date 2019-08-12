package com.example.gscustomphoneservicebug

import android.util.Log
import com.gs.phone.service.BasePhoneService

class CustomPhoneService: BasePhoneService() {

    companion object {
        private const val TAG = "CustomPhoneService"
    }

    override fun onHookEvent(offHook: Boolean) {
        super.onHookEvent(offHook)

        log("onHookEvent, offHook = $offHook")
    }


    override fun onEHSHookEvent(offHook: Boolean) {
        super.onEHSHookEvent(offHook)

        log("onEHSHookEvent, offHook = $offHook")
    }


    override fun onLineStateChanged(lineId: Int, status: Int) {
        super.onLineStateChanged(lineId, status)

        log("onLineStateChanged, lineId = $lineId , status = $status")
    }


    /**
     * return true means this click event was cost by this service.
     * else this event will call the system emergency dialer.
     */
    override fun onEmergencyCallButtonClicked(): Boolean {
        log("onEmergencyCallButtonClicked")

        return super.onEmergencyCallButtonClicked()
    }

    private fun log(msg: String) {
        Log.v(TAG, msg)
        FileUtils.writeToExternalNotificationsLog(this, msg)
    }
}
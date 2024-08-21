// SPDX-FileCopyrightText: 2024 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.features.input

import android.view.InputDevice
import androidx.annotation.Keep
import org.uzuy.uzuy_emu.UzuyApplication
import org.uzuy.uzuy_emu.R
import org.uzuy.uzuy_emu.utils.InputHandler.getGUID

@Keep
interface UzuyInputDevice {
    fun getName(): String

    fun getGUID(): String

    fun getPort(): Int

    fun getSupportsVibration(): Boolean

    fun vibrate(intensity: Float)

    fun getAxes(): Array<Int> = arrayOf()
    fun hasKeys(keys: IntArray): BooleanArray = BooleanArray(0)
}

class UzuyPhysicalDevice(
    private val device: InputDevice,
    private val port: Int,
    useSystemVibrator: Boolean
) : UzuyInputDevice {
    private val vibrator = if (useSystemVibrator) {
        UzuyVibrator.getSystemVibrator()
    } else {
        UzuyVibrator.getControllerVibrator(device)
    }

    override fun getName(): String {
        return device.name
    }

    override fun getGUID(): String {
        return device.getGUID()
    }

    override fun getPort(): Int {
        return port
    }

    override fun getSupportsVibration(): Boolean {
        return vibrator.supportsVibration()
    }

    override fun vibrate(intensity: Float) {
        vibrator.vibrate(intensity)
    }

    override fun getAxes(): Array<Int> = device.motionRanges.map { it.axis }.toTypedArray()
    override fun hasKeys(keys: IntArray): BooleanArray = device.hasKeys(*keys)
}

class UzuyInputOverlayDevice(
    private val vibration: Boolean,
    private val port: Int
) : UzuyInputDevice {
    private val vibrator = UzuyVibrator.getSystemVibrator()

    override fun getName(): String {
        return UzuyApplication.appContext.getString(R.string.input_overlay)
    }

    override fun getGUID(): String {
        return "00000000000000000000000000000000"
    }

    override fun getPort(): Int {
        return port
    }

    override fun getSupportsVibration(): Boolean {
        if (vibration) {
            return vibrator.supportsVibration()
        }
        return false
    }

    override fun vibrate(intensity: Float) {
        if (vibration) {
            vibrator.vibrate(intensity)
        }
    }
}

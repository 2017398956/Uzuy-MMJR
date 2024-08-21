// SPDX-FileCopyrightText: 2024 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.features.input

import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.InputDevice
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import org.uzuy.uzuy_emu.UzuyApplication

@Keep
@Suppress("DEPRECATION")
interface UzuyVibrator {
    fun supportsVibration(): Boolean

    fun vibrate(intensity: Float)

    companion object {
        fun getControllerVibrator(device: InputDevice): UzuyVibrator =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                UzuyVibratorManager(device.vibratorManager)
            } else {
                UzuyVibratorManagerCompat(device.vibrator)
            }

        fun getSystemVibrator(): UzuyVibrator =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = UzuyApplication.appContext
                    .getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                UzuyVibratorManager(vibratorManager)
            } else {
                val vibrator = UzuyApplication.appContext
                    .getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                UzuyVibratorManagerCompat(vibrator)
            }

        fun getVibrationEffect(intensity: Float): VibrationEffect? {
            if (intensity > 0f) {
                return VibrationEffect.createOneShot(
                    50,
                    (255.0 * intensity).toInt().coerceIn(1, 255)
                )
            }
            return null
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
class UzuyVibratorManager(private val vibratorManager: VibratorManager) : UzuyVibrator {
    override fun supportsVibration(): Boolean {
        return vibratorManager.vibratorIds.isNotEmpty()
    }

    override fun vibrate(intensity: Float) {
        val vibration = UzuyVibrator.getVibrationEffect(intensity) ?: return
        vibratorManager.vibrate(CombinedVibration.createParallel(vibration))
    }
}

class UzuyVibratorManagerCompat(private val vibrator: Vibrator) : UzuyVibrator {
    override fun supportsVibration(): Boolean {
        return vibrator.hasVibrator()
    }

    override fun vibrate(intensity: Float) {
        val vibration = UzuyVibrator.getVibrationEffect(intensity) ?: return
        vibrator.vibrate(vibration)
    }
}

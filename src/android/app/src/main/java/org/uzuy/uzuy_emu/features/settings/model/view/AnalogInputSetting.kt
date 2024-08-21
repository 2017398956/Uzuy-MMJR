// SPDX-FileCopyrightText: 2024 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.features.settings.model.view

import androidx.annotation.StringRes
import org.uzuy.uzuy_emu.features.input.NativeInput
import org.uzuy.uzuy_emu.features.input.model.AnalogDirection
import org.uzuy.uzuy_emu.features.input.model.InputType
import org.uzuy.uzuy_emu.features.input.model.NativeAnalog
import org.uzuy.uzuy_emu.utils.ParamPackage

class AnalogInputSetting(
    override val playerIndex: Int,
    val nativeAnalog: NativeAnalog,
    val analogDirection: AnalogDirection,
    @StringRes titleId: Int = 0,
    titleString: String = ""
) : InputSetting(titleId, titleString) {
    override val type = TYPE_INPUT
    override val inputType = InputType.Stick

    override fun getSelectedValue(): String {
        val params = NativeInput.getStickParam(playerIndex, nativeAnalog)
        val analog = analogToText(params, analogDirection.param)
        return getDisplayString(params, analog)
    }

    override fun setSelectedValue(param: ParamPackage) =
        NativeInput.setStickParam(playerIndex, nativeAnalog, param)
}

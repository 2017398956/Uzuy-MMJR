// SPDX-FileCopyrightText: 2023 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.features.settings.model

import org.uzuy.uzuy_emu.utils.NativeConfig

enum class StringSetting(override val key: String) : AbstractStringSetting {
    DRIVER_PATH("driver_path"),
    DEVICE_NAME("device_name");

    override fun getString(needsGlobal: Boolean): String = NativeConfig.getString(key, needsGlobal)

    override fun setString(value: String) {
        if (NativeConfig.isPerGameConfigLoaded()) {
            global = false
        }
        NativeConfig.setString(key, value)
    }

    override val defaultValue: String by lazy { NativeConfig.getDefaultToString(key) }

    override fun getValueAsString(needsGlobal: Boolean): String = getString(needsGlobal)

    override fun reset() = NativeConfig.setString(key, defaultValue)
}

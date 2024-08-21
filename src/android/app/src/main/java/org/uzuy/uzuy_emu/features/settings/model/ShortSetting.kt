// SPDX-FileCopyrightText: 2023 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.features.settings.model

import org.uzuy.uzuy_emu.utils.NativeConfig

enum class ShortSetting(override val key: String) : AbstractShortSetting {
    RENDERER_SPEED_LIMIT("speed_limit");

    override fun getShort(needsGlobal: Boolean): Short = NativeConfig.getShort(key, needsGlobal)

    override fun setShort(value: Short) {
        if (NativeConfig.isPerGameConfigLoaded()) {
            global = false
        }
        NativeConfig.setShort(key, value)
    }

    override val defaultValue: Short by lazy { NativeConfig.getDefaultToString(key).toShort() }

    override fun getValueAsString(needsGlobal: Boolean): String = getShort(needsGlobal).toString()

    override fun reset() = NativeConfig.setShort(key, defaultValue)
}

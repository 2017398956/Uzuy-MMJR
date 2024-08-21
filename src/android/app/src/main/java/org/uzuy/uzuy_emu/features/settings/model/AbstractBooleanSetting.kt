// SPDX-FileCopyrightText: 2023 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.features.settings.model

interface AbstractBooleanSetting : AbstractSetting {
    fun getBoolean(needsGlobal: Boolean = false): Boolean
    fun setBoolean(value: Boolean)
}

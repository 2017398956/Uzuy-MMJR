// SPDX-FileCopyrightText: 2023 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.features.settings.model

interface AbstractShortSetting : AbstractSetting {
    fun getShort(needsGlobal: Boolean = false): Short
    fun setShort(value: Short)
}

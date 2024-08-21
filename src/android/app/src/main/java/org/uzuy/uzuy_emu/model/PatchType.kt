// SPDX-FileCopyrightText: 2024 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.model

enum class PatchType(val int: Int) {
    Update(0),
    DLC(1),
    Mod(2);

    companion object {
        fun from(int: Int): PatchType = entries.firstOrNull { it.int == int } ?: Update
    }
}

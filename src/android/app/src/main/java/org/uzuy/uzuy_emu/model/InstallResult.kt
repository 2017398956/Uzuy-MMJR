// SPDX-FileCopyrightText: 2024 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.model

enum class InstallResult(val int: Int) {
    Success(0),
    Overwrite(1),
    Failure(2),
    BaseInstallAttempted(3);

    companion object {
        fun from(int: Int): InstallResult = entries.firstOrNull { it.int == int } ?: Success
    }
}

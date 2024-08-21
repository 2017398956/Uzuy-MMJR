// SPDX-FileCopyrightText: 2023 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.model

import androidx.annotation.Keep

@Keep
data class Patch(
    var enabled: Boolean,
    val name: String,
    val version: String,
    val type: Int,
    val programId: String,
    val titleId: String
)

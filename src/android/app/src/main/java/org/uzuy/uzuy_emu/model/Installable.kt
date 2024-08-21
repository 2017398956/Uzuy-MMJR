// SPDX-FileCopyrightText: 2023 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.model

import androidx.annotation.StringRes

data class Installable(
    @StringRes val titleId: Int,
    @StringRes val descriptionId: Int,
    val install: (() -> Unit)? = null,
    val export: (() -> Unit)? = null
)

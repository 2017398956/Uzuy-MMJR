// SPDX-FileCopyrightText: 2024 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.model

import org.uzuy.uzuy_emu.utils.GpuDriverMetadata

data class Driver(
    override var selected: Boolean,
    val title: String,
    val version: String = "",
    val description: String = ""
) : SelectableItem {
    override fun onSelectionStateChanged(selected: Boolean) {
        this.selected = selected
    }

    companion object {
        fun GpuDriverMetadata.toDriver(selected: Boolean = false): Driver =
            Driver(
                selected,
                this.name ?: "",
                this.version ?: "",
                this.description ?: ""
            )
    }
}

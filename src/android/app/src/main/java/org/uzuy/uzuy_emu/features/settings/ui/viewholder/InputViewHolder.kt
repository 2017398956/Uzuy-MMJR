// SPDX-FileCopyrightText: 2024 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.features.settings.ui.viewholder

import android.view.View
import org.uzuy.uzuy_emu.databinding.ListItemSettingInputBinding
import org.uzuy.uzuy_emu.features.input.NativeInput
import org.uzuy.uzuy_emu.features.settings.model.view.AnalogInputSetting
import org.uzuy.uzuy_emu.features.settings.model.view.ButtonInputSetting
import org.uzuy.uzuy_emu.features.settings.model.view.InputSetting
import org.uzuy.uzuy_emu.features.settings.model.view.ModifierInputSetting
import org.uzuy.uzuy_emu.features.settings.model.view.SettingsItem
import org.uzuy.uzuy_emu.features.settings.ui.SettingsAdapter
import org.uzuy.uzuy_emu.utils.ViewUtils.setVisible

class InputViewHolder(val binding: ListItemSettingInputBinding, adapter: SettingsAdapter) :
    SettingViewHolder(binding.root, adapter) {
    private lateinit var setting: InputSetting

    override fun bind(item: SettingsItem) {
        setting = item as InputSetting
        binding.textSettingName.text = setting.title
        binding.textSettingValue.text = setting.getSelectedValue()

        when (item) {
            is AnalogInputSetting -> {
                val param = NativeInput.getStickParam(item.playerIndex, item.nativeAnalog)
                binding.buttonOptions.setVisible(
                    param.get("engine", "") == "analog_from_button" ||
                        param.has("axis_x") || param.has("axis_y")
                )
            }

            is ButtonInputSetting -> {
                val param = NativeInput.getButtonParam(item.playerIndex, item.nativeButton)
                binding.buttonOptions.setVisible(
                    param.has("code") || param.has("button") || param.has("hat") ||
                        param.has("axis")
                )
            }

            is ModifierInputSetting -> {
                val params = NativeInput.getStickParam(item.playerIndex, item.nativeAnalog)
                binding.buttonOptions.setVisible(params.has("modifier"))
            }
        }

        binding.buttonOptions.setOnClickListener(null)
        binding.buttonOptions.setOnClickListener {
            adapter.onInputOptionsClick(binding.buttonOptions, setting, bindingAdapterPosition)
        }
    }

    override fun onClick(clicked: View) =
        adapter.onInputClick(setting, bindingAdapterPosition)

    override fun onLongClick(clicked: View): Boolean =
        adapter.onLongClick(setting, bindingAdapterPosition)
}

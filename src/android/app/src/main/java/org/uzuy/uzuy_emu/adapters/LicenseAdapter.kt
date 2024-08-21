// SPDX-FileCopyrightText: 2023 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import org.uzuy.uzuy_emu.databinding.ListItemSettingBinding
import org.uzuy.uzuy_emu.fragments.LicenseBottomSheetDialogFragment
import org.uzuy.uzuy_emu.model.License
import org.uzuy.uzuy_emu.utils.ViewUtils.setVisible
import org.uzuy.uzuy_emu.viewholder.AbstractViewHolder

class LicenseAdapter(private val activity: AppCompatActivity, licenses: List<License>) :
    AbstractListAdapter<License, LicenseAdapter.LicenseViewHolder>(licenses) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LicenseViewHolder {
        ListItemSettingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .also { return LicenseViewHolder(it) }
    }

    inner class LicenseViewHolder(val binding: ListItemSettingBinding) :
        AbstractViewHolder<License>(binding) {
        override fun bind(model: License) {
            binding.apply {
                textSettingName.text = root.context.getString(model.titleId)
                textSettingDescription.text = root.context.getString(model.descriptionId)
                textSettingValue.setVisible(false)

                root.setOnClickListener { onClick(model) }
            }
        }

        private fun onClick(license: License) {
            LicenseBottomSheetDialogFragment.newInstance(license)
                .show(activity.supportFragmentManager, LicenseBottomSheetDialogFragment.TAG)
        }
    }
}

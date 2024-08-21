// SPDX-FileCopyrightText: 2023 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

package org.uzuy.uzuy_emu.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.uzuy.uzuy_emu.HomeNavigationDirections
import org.uzuy.uzuy_emu.R
import org.uzuy.uzuy_emu.UzuyApplication
import org.uzuy.uzuy_emu.databinding.CardGameBinding
import org.uzuy.uzuy_emu.model.Game
import org.uzuy.uzuy_emu.model.GamesViewModel
import org.uzuy.uzuy_emu.utils.GameIconUtils
import org.uzuy.uzuy_emu.utils.ViewUtils.marquee
import org.uzuy.uzuy_emu.viewholder.AbstractViewHolder

class GameAdapter(private val activity: AppCompatActivity) :
    AbstractDiffAdapter<Game, GameAdapter.GameViewHolder>(exact = false) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        CardGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .also { return GameViewHolder(it) }
    }

    inner class GameViewHolder(val binding: CardGameBinding) :
        AbstractViewHolder<Game>(binding) {
        override fun bind(model: Game) {
            binding.imageGameScreen.scaleType = ImageView.ScaleType.CENTER_CROP
            GameIconUtils.loadGameIcon(model, binding.imageGameScreen)

            binding.textGameTitle.text = model.title.replace("[\\t\\n\\r]+".toRegex(), " ")

            binding.textGameTitle.marquee()
            binding.cardGame.setOnClickListener { onClick(model) }
            binding.cardGame.setOnLongClickListener { onLongClick(model) }
        }

        fun onClick(game: Game) {
            val gameExists = DocumentFile.fromSingleUri(
                UzuyApplication.appContext,
                Uri.parse(game.path)
            )?.exists() == true
            if (!gameExists) {
                Toast.makeText(
                    UzuyApplication.appContext,
                    R.string.loader_error_file_not_found,
                    Toast.LENGTH_LONG
                ).show()

                ViewModelProvider(activity)[GamesViewModel::class.java].reloadGames(true)
                return
            }

            val preferences =
                PreferenceManager.getDefaultSharedPreferences(UzuyApplication.appContext)
            preferences.edit()
                .putLong(
                    game.keyLastPlayedTime,
                    System.currentTimeMillis()
                )
                .apply()

            activity.lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val shortcut =
                        ShortcutInfoCompat.Builder(UzuyApplication.appContext, game.path)
                            .setShortLabel(game.title)
                            .setIcon(GameIconUtils.getShortcutIcon(activity, game))
                            .setIntent(game.launchIntent)
                            .build()
                    ShortcutManagerCompat.pushDynamicShortcut(UzuyApplication.appContext, shortcut)
                }
            }

            val action = HomeNavigationDirections.actionGlobalEmulationActivity(game, true)
            binding.root.findNavController().navigate(action)
        }

        fun onLongClick(game: Game): Boolean {
            val action = HomeNavigationDirections.actionGlobalPerGamePropertiesFragment(game)
            binding.root.findNavController().navigate(action)
            return true
        }
    }
}

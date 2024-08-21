// SPDX-FileCopyrightText: 2023 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

#pragma once

#include <QWindow>
#include "core/frontend/emu_window.h"

namespace QtCommon {

Core::Frontend::WindowSystemType GetWindowSystemType();

Core::Frontend::EmuWindow::WindowSystemInfo GetWindowSystemInfo(QWindow* window);

} // namespace QtCommon

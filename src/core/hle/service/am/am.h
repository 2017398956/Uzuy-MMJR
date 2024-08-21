// SPDX-FileCopyrightText: Copyright 2024 uzuy Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

#pragma once

namespace Core {
class System;
}

namespace Service::AM {

void LoopProcess(Core::System& system);

} // namespace Service::AM

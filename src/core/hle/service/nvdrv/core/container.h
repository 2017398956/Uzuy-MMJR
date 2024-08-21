// SPDX-FileCopyrightText: 2022 uzuy Emulator Project
// SPDX-FileCopyrightText: 2022 Skyline Team and Contributors
// SPDX-License-Identifier: GPL-3.0-or-later

#pragma once

#include <deque>
#include <memory>
#include <unordered_map>

#include "core/device_memory_manager.h"
#include "core/hle/service/nvdrv/nvdata.h"

namespace Kernel {
class KProcess;
}

namespace Tegra::Host1x {
class Host1x;
} // namespace Tegra::Host1x

namespace Service::Nvidia::NvCore {

class HeapMapper;
class NvMap;
class SyncpointManager;

struct ContainerImpl;

struct SessionId {
    size_t id;
};

struct Session {
    Session(SessionId id_, Kernel::KProcess* process_, Core::Asid asid_);
    ~Session();

    Session(const Session&) = delete;
    Session& operator=(const Session&) = delete;
    Session(Session&&) = default;
    Session& operator=(Session&&) = default;

    SessionId id;
    Kernel::KProcess* process;
    Core::Asid asid;
    bool has_preallocated_area{};
    std::unique_ptr<HeapMapper> mapper{};
    bool is_active{};
    s32 ref_count{};
};

class Container {
public:
    explicit Container(Tegra::Host1x::Host1x& host1x);
    ~Container();

    SessionId OpenSession(Kernel::KProcess* process);
    void CloseSession(SessionId id);

    Session* GetSession(SessionId id);

    NvMap& GetNvMapFile();

    const NvMap& GetNvMapFile() const;

    SyncpointManager& GetSyncpointManager();

    const SyncpointManager& GetSyncpointManager() const;

    struct Host1xDeviceFileData {
        std::deque<u32> syncpts_accumulated{};
    };

    Host1xDeviceFileData& Host1xDeviceFile();

    const Host1xDeviceFileData& Host1xDeviceFile() const;

private:
    std::unique_ptr<ContainerImpl> impl;
};

} // namespace Service::Nvidia::NvCore

# SPDX-FileCopyrightText: 2017 Citra Emulator Project
# SPDX-License-Identifier: GPL-2.0-or-later

add_executable(uzuy-room
    precompiled_headers.h
    uzuy_room.cpp
    uzuy_room.rc
)

target_link_libraries(uzuy-room PRIVATE common network)
if (ENABLE_WEB_SERVICE)
    target_compile_definitions(uzuy-room PRIVATE -DENABLE_WEB_SERVICE)
    target_link_libraries(uzuy-room PRIVATE web_service)
endif()

target_link_libraries(uzuy-room PRIVATE mbedtls mbedcrypto)
if (MSVC)
    target_link_libraries(uzuy-room PRIVATE getopt)
endif()
target_link_libraries(uzuy-room PRIVATE ${PLATFORM_LIBRARIES} Threads::Threads)

if(UNIX AND NOT APPLE)
    install(TARGETS uzuy-room)
endif()

if (UZUY_USE_PRECOMPILED_HEADERS)
    target_precompile_headers(uzuy-room PRIVATE precompiled_headers.h)
endif()

create_target_directory_groups(uzuy-room)

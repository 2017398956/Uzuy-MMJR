# SPDX-FileCopyrightText: 2018 uzuy Emulator Project
# SPDX-License-Identifier: GPL-2.0-or-later

# Credits to Samantas5855 and others for this function.
function(create_resource file output filename)
    # Read hex data from file
    file(READ ${file} filedata HEX)
    # Convert hex data for C compatibility
    string(REGEX REPLACE "([0-9a-f][0-9a-f])" "0x\\1," filedata ${filedata})
    # Write data to output file
    set(RESOURCES_DIR "${PROJECT_BINARY_DIR}/dist" PARENT_SCOPE)
    file(WRITE "${PROJECT_BINARY_DIR}/dist/${output}" "const unsigned char ${filename}[] = {${filedata}};\nconst unsigned ${filename}_size = sizeof(${filename});\n")
endfunction()

add_executable(uzuy-cmd
    emu_window/emu_window_sdl2.cpp
    emu_window/emu_window_sdl2.h
    emu_window/emu_window_sdl2_gl.cpp
    emu_window/emu_window_sdl2_gl.h
    emu_window/emu_window_sdl2_null.cpp
    emu_window/emu_window_sdl2_null.h
    emu_window/emu_window_sdl2_vk.cpp
    emu_window/emu_window_sdl2_vk.h
    precompiled_headers.h
    sdl_config.cpp
    sdl_config.h
    uzuy.cpp
    uzuy.rc
)

target_link_libraries(uzuy-cmd PRIVATE common core input_common frontend_common)
target_link_libraries(uzuy-cmd PRIVATE glad)
if (MSVC)
    target_link_libraries(uzuy-cmd PRIVATE getopt)
endif()
target_link_libraries(uzuy-cmd PRIVATE ${PLATFORM_LIBRARIES} Threads::Threads)

create_resource("../../dist/uzuy.bmp" "uzuy_cmd/uzuy_icon.h" "uzuy_icon")
target_include_directories(uzuy-cmd PRIVATE ${RESOURCES_DIR})

target_link_libraries(uzuy-cmd PRIVATE SDL2::SDL2 Vulkan::Headers)

if(UNIX AND NOT APPLE)
    install(TARGETS uzuy-cmd)
endif()

if(WIN32)
    # compile as a win32 gui application instead of a console application
    if(MSVC)
        set_target_properties(uzuy-cmd PROPERTIES LINK_FLAGS_RELEASE "/SUBSYSTEM:WINDOWS /ENTRY:mainCRTStartup")
    elseif(MINGW)
        set_target_properties(uzuy-cmd PROPERTIES LINK_FLAGS_RELEASE "-Wl,--subsystem,windows")
    endif()
endif()

if (MSVC)
    include(CopyUzuySDLDeps)
    copy_uzuy_SDL_deps(uzuy-cmd)
endif()

if (UZUY_USE_PRECOMPILED_HEADERS)
    target_precompile_headers(uzuy-cmd PRIVATE precompiled_headers.h)
endif()

create_target_directory_groups(uzuy-cmd)

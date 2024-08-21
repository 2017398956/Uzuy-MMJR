// SPDX-FileCopyrightText: 2013 Dolphin Emulator Project
// SPDX-FileCopyrightText: 2014 Citra Emulator Project
// SPDX-License-Identifier: GPL-2.0-or-later

#include <algorithm>
#include <cctype>
#include <locale>
#include <sstream>
#include <string_view>

#include "common/string_util.h"

#ifdef _WIN32
#include <windows.h>
#endif

#ifdef ANDROID
#include <common/fs/fs_android.h>
#endif

namespace Common {

    std::string ToLower(std::string str) {
        std::transform(str.begin(), str.end(), str.begin(),
                       [](unsigned char c) { return static_cast<char>(std::tolower(c)); });
        return str;
    }

    std::string ToUpper(std::string str) {
        std::transform(str.begin(), str.end(), str.begin(),
                       [](unsigned char c) { return static_cast<char>(std::toupper(c)); });
        return str;
    }

    std::string StringFromBuffer(std::span<const u8> data) {
        return std::string(data.begin(), std::find(data.begin(), data.end(), '\0'));
    }

    std::string StringFromBuffer(std::span<const char> data) {
        return std::string(data.begin(), std::find(data.begin(), data.end(), '\0'));
    }

    std::string StripSpaces(const std::string& str) {
        const std::size_t s = str.find_first_not_of(" \t\r\n");
        if (str.npos != s)
            return str.substr(s, str.find_last_not_of(" \t\r\n") - s + 1);
        else
            return "";
    }

    std::string StripQuotes(const std::string& s) {
        if (s.size() && '\"' == s[0] && '\"' == *s.rbegin())
            return s.substr(1, s.size() - 2);
        else
            return s;
    }

    std::string StringFromBool(bool value) {
        return value ? "True" : "False";
    }

    bool SplitPath(const std::string& full_path, std::string* _pPath, std::string* _pFilename,
                   std::string* _pExtension) {
        if (full_path.empty())
            return false;

#ifdef ANDROID
        if (full_path[0] != '/') {
        *_pPath = Common::FS::Android::GetParentDirectory(full_path);
        *_pFilename = Common::FS::Android::GetFilename(full_path);
        return true;
    }
#endif

        std::size_t dir_end = full_path.find_last_of("/"
#ifdef _WIN32
                "\\:"
#endif
        );
        if (std::string::npos == dir_end)
            dir_end = 0;
        else
            dir_end += 1;

        std::size_t fname_end = full_path.rfind('.');
        if (fname_end < dir_end || std::string::npos == fname_end)
            fname_end = full_path.size();

        if (_pPath)
            *_pPath = full_path.substr(0, dir_end);

        if (_pFilename)
            *_pFilename = full_path.substr(dir_end, fname_end - dir_end);

        if (_pExtension)
            *_pExtension = full_path.substr(fname_end);

        return true;
    }

    void SplitString(const std::string& str, const char delim, std::vector<std::string>& output) {
        std::istringstream iss(str);
        output.resize(1);
        while (std::getline(iss, *output.rbegin(), delim)) {
            output.emplace_back();
        }
        output.pop_back();
    }

    std::string TabsToSpaces(int tab_size, std::string in) {
        std::size_t i = 0;
        while ((i = in.find('\t')) != std::string::npos) {
            in.replace(i, 1, tab_size, ' ');
        }
        return in;
    }

    std::string ReplaceAll(std::string result, const std::string& src, const std::string& dest) {
        std::size_t pos = 0;
        if (src == dest)
            return result;

        while ((pos = result.find(src, pos)) != std::string::npos) {
            result.replace(pos, src.size(), dest);
            pos += dest.length();
        }
        return result;
    }

// Use standard library functions to replace the deprecated codecvt-based conversions.
    std::string UTF16ToUTF8(std::u16string_view input) {
        std::string output;
        for (char16_t c : input) {
            if (c <= 0x7F) {
                output.push_back(static_cast<char>(c));
            } else if (c <= 0x7FF) {
                output.push_back(static_cast<char>((c >> 6) | 0xC0));
                output.push_back(static_cast<char>((c & 0x3F) | 0x80));
            } else {
                output.push_back(static_cast<char>((c >> 12) | 0xE0));
                output.push_back(static_cast<char>(((c >> 6) & 0x3F) | 0x80));
                output.push_back(static_cast<char>((c & 0x3F) | 0x80));
            }
        }
        return output;
    }

    std::u16string UTF8ToUTF16(std::string_view input) {
        std::u16string output;
        for (size_t i = 0; i < input.size();) {
            char32_t c = static_cast<unsigned char>(input[i++]);
            if (c <= 0x7F) {
                output.push_back(static_cast<char16_t>(c));
            } else if (c <= 0xDF) {
                c = ((c & 0x1F) << 6) | (static_cast<unsigned char>(input[i++]) & 0x3F);
                output.push_back(static_cast<char16_t>(c));
            } else {
                c = ((c & 0x0F) << 12) | ((static_cast<unsigned char>(input[i++]) & 0x3F) << 6);
                c |= (static_cast<unsigned char>(input[i++]) & 0x3F);
                output.push_back(static_cast<char16_t>(c));
            }
        }
        return output;
    }

    std::u32string UTF8ToUTF32(std::string_view input) {
        std::u32string output;
        for (size_t i = 0; i < input.size();) {
            char32_t c = static_cast<unsigned char>(input[i++]);
            if (c <= 0x7F) {
                output.push_back(c);
            } else if (c <= 0xDF) {
                c = ((c & 0x1F) << 6) | (static_cast<unsigned char>(input[i++]) & 0x3F);
                output.push_back(c);
            } else if (c <= 0xEF) {
                c = ((c & 0x0F) << 12) | ((static_cast<unsigned char>(input[i++]) & 0x3F) << 6);
                c |= (static_cast<unsigned char>(input[i++]) & 0x3F);
                output.push_back(c);
            } else {
                c = ((c & 0x07) << 18) | ((static_cast<unsigned char>(input[i++]) & 0x3F) << 12);
                c |= ((static_cast<unsigned char>(input[i++]) & 0x3F) << 6);
                c |= (static_cast<unsigned char>(input[i++]) & 0x3F);
                output.push_back(c);
            }
        }
        return output;
    }

#ifdef _WIN32
    static std::wstring CPToUTF16(u32 code_page, std::string_view input) {
    const auto size =
        MultiByteToWideChar(code_page, 0, input.data(), static_cast<int>(input.size()), nullptr, 0);

    if (size == 0) {
        return {};
    }

    std::wstring output(size, L'\0');

    if (size != MultiByteToWideChar(code_page, 0, input.data(), static_cast<int>(input.size()),
                                    &output[0], static_cast<int>(output.size()))) {
        output.clear();
    }

    return output;
}

std::string UTF16ToUTF8(std::wstring_view input) {
    const auto size = WideCharToMultiByte(CP_UTF8, 0, input.data(), static_cast<int>(input.size()),
                                          nullptr, 0, nullptr, nullptr);
    if (size == 0) {
        return {};
    }

    std::string output(size, '\0');

    if (size != WideCharToMultiByte(CP_UTF8, 0, input.data(), static_cast<int>(input.size()),
                                    &output[0], static_cast<int>(output.size()), nullptr,
                                    nullptr)) {
        output.clear();
    }

    return output;
}

std::wstring UTF8ToUTF16W(std::string_view input) {
    return CPToUTF16(CP_UTF8, input);
}

#endif

    std::u16string U16StringFromBuffer(const u16* input, std::size_t length) {
        return std::u16string(reinterpret_cast<const char16_t*>(input), length);
    }

    std::string StringFromFixedZeroTerminatedBuffer(std::string_view buffer, std::size_t max_len) {
        std::size_t len = 0;
        while (len < buffer.length() && len < max_len && buffer[len] != '\0') {
            ++len;
        }
        return std::string(buffer.begin(), buffer.begin() + len);
    }

    std::u16string UTF16StringFromFixedZeroTerminatedBuffer(std::u16string_view buffer,
                                                            std::size_t max_len) {
        std::size_t len = 0;
        while (len < buffer.length() && len < max_len && buffer[len] != '\0') {
            ++len;
        }
        return std::u16string(buffer.begin(), buffer.begin() + len);
    }

} // namespace Common

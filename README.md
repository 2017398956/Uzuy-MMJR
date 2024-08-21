| Pull Request | Commit | Title | Author | Merged? |
|----|----|----|----|----|
| [12461](https://github.com/uzuy-emu/uzuy//pull/12461) | [`7464cae24`](https://github.com/uzuy-emu/uzuy//pull/12461/files) | Rework Nvdec and VIC to fix out-of-order videos, and speed up decoding. | [Kelebek1](https://github.com/Kelebek1/) | Yes |
| [13018](https://github.com/uzuy-emu/uzuy//pull/13018) | [`01cbc638a`](https://github.com/uzuy-emu/uzuy//pull/13018/files) | am: rewrite part 2 | [liamwhite](https://github.com/liamwhite/) | Yes |
| [13174](https://github.com/uzuy-emu/uzuy//pull/13174) | [`7d1284826`](https://github.com/uzuy-emu/uzuy//pull/13174/files) | glue/time: Remove global variables | [FearlessTobi](https://github.com/FearlessTobi/) | Yes |
| [13177](https://github.com/uzuy-emu/uzuy//pull/13177) | [`f5cc94f05`](https://github.com/uzuy-emu/uzuy//pull/13177/files) | vfs: misc performance improvements | [liamwhite](https://github.com/liamwhite/) | Yes |


End of merge log. You can find the original README.md below the break.

-----

<!--
SPDX-FileCopyrightText: 2018 uzuy Emulator Project
SPDX-License-Identifier: GPL-2.0-or-later
-->

<h1 align="center">
  <br>
  <a href="https://uzuy-emu.org/"><img src="https://raw.githubusercontent.com/uzuy-emu/uzuy-assets/master/icons/icon.png" alt="uzuy" width="200"></a>
  <br>
  <b>uzuy</b>
  <br>
</h1>

<h4 align="center"><b>uzuy</b> is the world's most popular, open-source, Nintendo Switch emulator — started by the creators of <a href="https://citra-emu.org" target="_blank">Citra</a>.
<br>
It is written in C++ with portability in mind, and we actively maintain builds for Windows, Linux and Android.
</h4>

<p align="center">
    <a href="https://dev.azure.com/uzuy-emu/uzuy/">
        <img src="https://dev.azure.com/uzuy-emu/uzuy/_apis/build/status/uzuy%20mainline?branchName=master"
            alt="Azure Mainline CI Build Status">
    </a>
    <a href="https://discord.com/invite/u77vRWY">
        <img src="https://img.shields.io/discord/398318088170242053?color=5865F2&label=uzuy&logo=discord&logoColor=white"
            alt="Discord">
    </a>
</p>

<p align="center">
  <a href="#compatibility">Compatibility</a> |
  <a href="#development">Development</a> |
  <a href="#building">Building</a> |
  <a href="#download">Download</a> |
  <a href="#support">Support</a> |
  <a href="#license">License</a>
</p>

## Compatibility

The emulator is capable of running most commercial games at full speed, provided you meet the [necessary hardware requirements](https://uzuy-emu.org/help/quickstart/#hardware-requirements).

For a full list of games uzuy supports, please visit our [Compatibility page](https://uzuy-emu.org/game/).

Check out our [website](https://uzuy-emu.org/) for the latest news on exciting features, monthly progress reports, and more!

## Development

Most of the development happens on GitHub. It's also where [our central repository](https://github.com/uzuy-emu/uzuy) is hosted. For development discussion, please join us on [Discord](https://discord.com/invite/u77vRWY).

If you want to contribute, please take a look at the [Contributor's Guide](https://github.com/uzuy-emu/uzuy/wiki/Contributing) and [Developer Information](https://github.com/uzuy-emu/uzuy/wiki/Developer-Information).
You can also contact any of the developers on Discord in order to know about the current state of the emulator.

If you want to contribute to the user interface translation project, please check out the [uzuy project on transifex](https://www.transifex.com/uzuy-emulator/uzuy). We centralize translation work there, and periodically upstream translations.

## Building

* __Windows__: [Windows Build](https://github.com/uzuy-emu/uzuy/wiki/Building-For-Windows)
* __Linux__: [Linux Build](https://github.com/uzuy-emu/uzuy/wiki/Building-For-Linux)

## Download

You can download the latest releases automatically via the installer on our [downloads](https://uzuy-emu.org/downloads/) page.


## Support

If you enjoy the project and want to support us financially, check out our Patreon!

<a href="https://www.patreon.com/uzuyteam">
    <img src="https://c5.patreon.com/external/logo/become_a_patron_button@2x.png" width="160">
</a>

Any donations received will go towards things like:
* Switch consoles to explore and reverse-engineer the hardware
* Switch games for testing, reverse-engineering, and implementing new features
* Web hosting and infrastructure setup
* Software licenses (e.g. Visual Studio, IDA Pro, etc.)
* Additional hardware (e.g. GPUs as-needed to improve rendering support, other peripherals to add support for, etc.)

If you wish to support us a different way, please join our [Discord](https://discord.gg/u77vRWY) and talk to bunnei. You may also contact: donations@uzuy-emu.org.

## License

uzuy is licensed under the GPLv3 (or any later version). Refer to the [LICENSE.txt](https://github.com/uzuy-emu/uzuy/blob/master/LICENSE.txt) file.

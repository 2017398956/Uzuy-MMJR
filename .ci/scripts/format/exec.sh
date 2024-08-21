#!/bin/bash -ex

# SPDX-FileCopyrightText: 2019 uzuy Emulator Project
# SPDX-License-Identifier: GPL-2.0-or-later

chmod a+x ./.ci/scripts/format/docker.sh
# the UID for the container uzuy user is 1027
sudo chown -R 1027 ./
docker run -v "$(pwd):/uzuy" -w /uzuy uzuyemu/build-environments:linux-clang-format /bin/bash -ex /uzuy/.ci/scripts/format/docker.sh
sudo chown -R $UID ./

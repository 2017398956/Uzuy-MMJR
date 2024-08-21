#!/bin/bash -ex

# SPDX-FileCopyrightText: 2021 uzuy Emulator Project
# SPDX-License-Identifier: GPL-2.0-or-later

mkdir -p "ccache"  || true
chmod a+x ./.ci/scripts/clang/docker.sh
# the UID for the container uzuy user is 1027
sudo chown -R 1027 ./
docker run -e ENABLE_COMPATIBILITY_REPORTING -e CCACHE_DIR=/uzuy/ccache -v "$(pwd):/uzuy" -w /uzuy uzuyemu/build-environments:linux-fresh /bin/bash /uzuy/.ci/scripts/clang/docker.sh "$1"
sudo chown -R $UID ./

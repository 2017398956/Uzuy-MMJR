#!/bin/bash -ex

# SPDX-FileCopyrightText: 2019 uzuy Emulator Project
# SPDX-License-Identifier: GPL-2.0-or-later

mkdir -p "ccache"  || true
chmod a+x ./.ci/scripts/linux/docker.sh
# the UID for the container uzuy user is 1027
sudo chown -R 1027 ./

# The environment variables listed below:
# AZURECIREPO TITLEBARFORMATIDLE TITLEBARFORMATRUNNING DISPLAYVERSION
#  are requested in src/common/CMakeLists.txt and appear to be provided somewhere in Azure DevOps

docker run -e AZURECIREPO -e TITLEBARFORMATIDLE -e TITLEBARFORMATRUNNING -e DISPLAYVERSION -e ENABLE_COMPATIBILITY_REPORTING -e CCACHE_DIR=/uzuy/ccache -v "$(pwd):/uzuy" -w /uzuy uzuyemu/build-environments:linux-fresh /bin/bash /uzuy/.ci/scripts/linux/docker.sh "$1"
sudo chown -R $UID ./


# Base: Java 17 + devcontainer user
FROM mcr.microsoft.com/devcontainers/java:17

# ----- Essentials -----
USER root
RUN apt-get update && apt-get install -y --no-install-recommends \
    wget unzip ca-certificates gnupg curl \
  && rm -rf /var/lib/apt/lists/*

# ----- Android SDK root -----
ENV ANDROID_SDK_ROOT=/opt/android-sdk
ENV ANDROID_HOME=${ANDROID_SDK_ROOT}
RUN mkdir -p ${ANDROID_SDK_ROOT}

# ----- Install Command-line Tools (bootstrap) -----
# Always fetch from the official Android "Command-line tools only" distribution.
# (Version number may change; this is just the latest public endpoint.)
# See "Command-line tools" docs and sdkmanager docs.
# https://developer.android.com/tools/  |  https://developer.android.com/tools/sdkmanager
# We place them under cmdline-tools/latest as required by sdkmanager.
WORKDIR /tmp
RUN wget -q https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip -O /tmp/cmdline-tools.zip \
  && mkdir -p ${ANDROID_SDK_ROOT}/cmdline-tools \
  && unzip -q /tmp/cmdline-tools.zip -d ${ANDROID_SDK_ROOT}/cmdline-tools \
  && rm /tmp/cmdline-tools.zip \
  && mv ${ANDROID_SDK_ROOT}/cmdline-tools/cmdline-tools ${ANDROID_SDK_ROOT}/cmdline-tools/latest

# Make sdkmanager/avdmanager available
ENV PATH=${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools:${PATH}

# ----- Accept SDK licenses & install packages -----
# Note: sdkmanager requires Java and the latest cmdline-tools layout above.
RUN yes | sdkmanager --licenses || true

# Install specific packages needed to build with Gradle:
# - platform-tools (adb, etc.)
# - a stable Android platform (API 34 as example)
# - matching build-tools (adjust if your Gradle plugin requires another)
RUN sdkmanager "platform-tools" \
               "platforms;android-34" \
               "build-tools;34.0.0"

# Optional: Pre-create Gradle cache dirs faster builds (kept minimal here)

# Back to vscode user
USER vscode
WORKDIR /workspaces

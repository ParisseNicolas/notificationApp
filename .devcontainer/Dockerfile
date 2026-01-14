FROM mcr.microsoft.com/devcontainers/java:17

# Installer dépendances
RUN apt-get update && apt-get install -y unzip wget gradle

# Installer Android SDK Command Line Tools
RUN mkdir -p /android-sdk && cd /android-sdk && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip && \
    unzip commandlinetools-linux-9477386_latest.zip -d cmdline-tools && \
    rm commandlinetools-linux-9477386_latest.zip

ENV ANDROID_HOME=/android-sdk
ENV PATH=$ANDROID_HOME/cmdline-tools/bin:$ANDROID_HOME/platform-tools:$PATH

# Installer composants Android SDK
RUN yes | sdkmanager --licenses && \
    sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

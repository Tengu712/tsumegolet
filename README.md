# Tsumegolet

自分用の詰碁練習Androidアプリ

## Build

Android Studioは使い——ません。
CLI信者なのでね仕方ないね。

次をインストール:

- JDK
- Android Studio Commandline Tools
- proto
- Taskfile

次を実行してセットアップ:

```sh
proto install

ANDROID_SDK_ROOT=<ANDROID_SDK_ROOT>
EMULATOR_ARCH=(x86_64|arm64-v8a)
sdkmanager --sdk_root=$ANDROID_SDK_ROOT --licenses
sdkmanager --sdk_root=$ANDROID_SDK_ROOT "platform-tools" "platforms;android-35" "build-tools;35.0.0" "emulator" "system-images;android-35;google_apis;$EMULATOR_ARCH"

echo "sdk.dir=$ANDROID_SDK_ROOT" > local.properties
```

ビルド:

```sh
task build
```

> [!WARNING]
> `ANDROID_SDK_ROOT`はAndroid Studio Commandline Toolsのツールから見て`../../../`でなければならない。

## Emulate

仮想デバイスを作成:

```sh
VIRTUAL_DEVICE_NAME=<FAVORITE_NAME>
VIRTUAL_DEVICE_PROFILE_NAME=<CORRECT_NAME>
avdmanager create avd --name "$VIRTUAL_DEVICE_NAME" --package "system-images;android-35;google_apis;$EMULATOR_ARCH" --device "$VIRTUAL_DEVICE_PROFILE_NAME"
```

仮想デバイスを起動:

```sh
$ANDROID_SDK_ROOT/emulator/emulator -avd $VIRTUAL_DEVICE_NAME
```

実行:

```sh
task run
```

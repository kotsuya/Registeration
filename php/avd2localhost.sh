#!/bin/sh

# [環境]
# MacOSX
# AVD (Android Virtual Device) から接続させたいデスクトップ上のローカルホストへの接続設定
#
# MacOSX上で構築した接続先ローカルホストを `local-pl-t-dev.jp` としています。

# AVDから接続させたい接続先ローカルホスト
CONNECT_LOCALHOST="local-pl-t-dev.jp"

# AVD上のLOCAL IP
AVD_LOCAL_IP="10.0.2.2"

# 接続先ローカルホストが設定されていない場合エラー発生し停止.
if [ "${CONNECT_LOCALHOST}" == "" ]; then
        echo 'Please set CONNECT_LOCALHOST'
        exit
fi

/Users/yoo/Library/Android/sdk/platform-tools/adb root

# 起動中AVDをマウント
/Users/yoo/Library/Android/sdk/platform-tools/adb remount

# AVD上のhostsをlocalにダウンロード
/Users/yoo/Library/Android/sdk/platform-tools/adb pull /system/etc/hosts ~/hosts

SET=$(grep ${AVD_LOCAL_IP} ~/hosts)

if [ "${SET}" == "" ]; then
        echo 'do not set! '
        # 接続先ローカルホスト追加
        echo "${AVD_LOCAL_IP}  ${CONNECT_LOCALHOST}" >> ~/hosts
fi
# AVDにhostsをpush
/Users/yoo/Library/Android/sdk/platform-tools/adb push ~/hosts /system/etc/hosts

db pull /system/etc/hosts ~/_avd_hosts

# 念のための設定確認
echo '
[emulator hosts configure]'
cat ~/_avd_hosts
rm ~/_avd_hosts

# AVD上のhosts設定確認
# adb shell
# cat /system/etc/hosts

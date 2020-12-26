# MyTiles-android
Add tile(s) to Android status bar. API 24 and higher supported.

Sorry for my poor English.

## NOTE
I need "ALWAYS ALLOWED" permission for android.permission.MODIFY_AUDIO_SETTINGS to show volume panel. It doesn't work under "Only in use" permission. And I don't know how to check it via code. System always returns PERMISSION_GRANTED for me. Tested with my Xiaomi Redmi K20 Pro.

## Reason
My phone's volume button is too soft; I can't feel my press action though the volume panel UI shows.

I wanna control volume without volume button.

I search some app in app market, and I found some:
- "com.mqc.tmzoihm" does help, but it is based on old version of Android, and it's too slow to start via system's floating ball. What's more, my phone's security warns me of it's unsafety.
- "com.ksxkq.floating" has lots of functions, but the floating ball is too large and can't collapse aside as system's do.

Then I do the job myself. Currently the app only shows the Volume UI by clicking tile. At least I'm free with the button.

## Develop Environment
- gradle. Maybe you can change the settings to use lower version.
- Android Studio (optional). It's okey to use gradlew command line only to build.

## 使用注意
我需要“始终允许”修改媒体音量的权限来显示音量面板。“只在使用中允许”搞不定。而且通过代码检查不出来权限问题，一直都是返回PERMISSION_GRANTED。我是拿自己的红米K20Pro测的。

## 原因
手机音量键坏了，按下去没感觉但是还能显示音量条。

想直接触摸手机修改音量。

奈何应用市场里相关应用不多:
- “独立音量调节器”是老版本Android上开发的，用悬浮球打开很慢。而且手机会提示有安全风险。
- “悬浮菜单”功能很强，不过它的悬浮球太大了，不能像系统的悬浮球一样靠边折叠。

干脆自己做一个。目前只有打开音量面板，至少我不要音量键了。

## 开发环境
- gradle。可以试试修改配置用低版本的gradle和插件。
- Android Studio（可选）。只用gradlew命令行编译也可以。

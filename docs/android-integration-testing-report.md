## 本次项目实践

源码目录：

```text
D:\Codex_talk\AS\Notes-main
```

新增 instrumentation 测试配置：

- 在 `defaultConfig` 中设置 `testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"`。
- 为 `androidTest` 添加 Kotlin 源集：`src/androidTest/kotlin`。
- 新增 AndroidX Test、Espresso Core、Espresso Intents 依赖。

新增测试文件：

```text
app/src/androidTest/kotlin/org/fossify/notes/activities/SplashActivityRoutingTest.kt
app/src/androidTest/kotlin/org/fossify/notes/activities/SettingsActivityIntegrationTest.kt
```

### 实践一：SplashActivity 路由集成测试

测试目标：

- 启动 `SplashActivity`。
- 传入 `OPEN_NOTE_ID`。
- 验证它启动 `MainActivity` 时继续携带同一个 note id。

涉及的真实协作对象：

- `SplashActivity`
- Android `Intent`
- `ActivityScenario`
- Espresso Intents

这个测试对应课件中的 Activity 启动和 Intent 验证场景，重点不是测试某个函数返回值，而是测试组件协作后的可观察行为。

### 实践二：SettingsActivity 设置页交互测试

测试目标：

- 启动真实 `SettingsActivity`。
- 使用 Espresso 滚动并点击 `settings_enable_line_wrap_holder`。
- 验证界面 Switch 状态改变。
- 验证 `Config.enableLineWrap` 持久化配置同步改变。

涉及的真实协作对象：

- `SettingsActivity`
- XML layout/ViewBinding 生成的真实视图层级
- Espresso 点击和滚动动作
- SharedPreferences-backed `Config`

这个测试覆盖了“界面点击 -> Activity 回调 -> 配置写入 -> UI 状态更新”的集成链路。


## 运行命令

编译 instrumentation 测试：

```powershell
.\gradlew.bat :app:compileFossDebugAndroidTestKotlin --console=plain --no-daemon
```

在已启动的虚拟机上运行集成测试：

```powershell
.\gradlew.bat :app:connectedFossDebugAndroidTest --console=plain --no-daemon
```

本次在已有虚拟机 `Pixel_10` 上执行结果：

```text
Starting 2 tests on Pixel_10(AVD) - 17
Finished 2 tests on Pixel_10(AVD) - 17
BUILD SUCCESSFUL
```

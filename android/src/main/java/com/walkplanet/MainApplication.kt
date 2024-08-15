package com.walkplanet

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactHost
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.load
import com.facebook.react.defaults.DefaultReactHost.getDefaultReactHost
import com.facebook.react.defaults.DefaultReactNativeHost
import com.facebook.soloader.SoLoader
import com.walkplanet.step.StepCounterPackage

class MainApplication : Application(), ReactApplication {

    override val reactNativeHost: ReactNativeHost =
            object : DefaultReactNativeHost(this) {
                override fun getPackages(): List<ReactPackage> =
                        PackageList(this).packages.apply {
                            // Packages that cannot be autolinked yet can be added manually here, for example:
                            // add(MyReactNativePackage())
                            add(MyPackage())  // 여기에 추가
                            add(StepCounterPackage())  // 여기에 추가
                        }

                override fun getJSMainModuleName(): String = "index"

                override fun getUseDeveloperSupport(): Boolean = BuildConfig.DEBUG

                override val isNewArchEnabled: Boolean = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED
                override val isHermesEnabled: Boolean = BuildConfig.IS_HERMES_ENABLED
            }

    override val reactHost: ReactHost
        get() = getDefaultReactHost(applicationContext, reactNativeHost)

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)
        if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
            // If you opted-in for the New Architecture, we load the native entry point for this app.
            load()
        }
    }

    fun openBatteryOptimizationSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent()
            val packageName = packageName
            intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
            intent.data = Uri.parse("package:$packageName")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}

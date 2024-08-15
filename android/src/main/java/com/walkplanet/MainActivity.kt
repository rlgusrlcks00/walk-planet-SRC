package com.walkplanet

import android.Manifest
import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate
import com.walkplanet.notification.service.NotificationService
import com.walkplanet.step.service.StepCounterService

class MainActivity : ReactActivity() {

  companion object {
    const val TAG = "MainActivity"
    const val REQUEST_CODE = 123
    const val NOTIFICATION_REQUEST_CODE = 124
    const val PREFS_NAME = "StepCounterPrefs"
    const val STEPS_KEY = "steps"
  }

  override fun getMainComponentName(): String = "walk-planet-app"

  override fun createReactActivityDelegate(): ReactActivityDelegate =
          DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    try {
      Log.d(TAG, "MainActivity onCreate")

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        requestPermission(Manifest.permission.POST_NOTIFICATIONS, NOTIFICATION_REQUEST_CODE)
      }

      if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), REQUEST_CODE)
      } else {
        startStepCounterService()
        startNotificationService()
      }

      // Load and display initial step count
      val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
      val initialStepCount = prefs.getInt(STEPS_KEY, 0)
      Log.d(TAG, "Initial step count: $initialStepCount")
      // Use initialStepCount to update the UI
    } catch (e: Exception) {
      Log.e(TAG, "Exception in onCreate", e)
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == REQUEST_CODE) {
      if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
        startStepCounterService()
      } else {
        Log.e(TAG, "ACTIVITY_RECOGNITION permission denied")
      }
    }

    if (requestCode == NOTIFICATION_REQUEST_CODE) {
      if ((grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
        Log.e(TAG, "POST_NOTIFICATIONS permission denied")
      }
    }
  }

  private fun requestPermission(permission: String, requestCode: Int) {
    if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
    }
  }

  private fun startNotificationService() {
    try {
        val serviceIntent = Intent(this, NotificationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
        } catch (e: Exception) {
        Log.e(TAG, "Exception in startNotificationService", e)
    }
  }

  private fun startStepCounterService() {
    try {
      val serviceIntent = Intent(this, StepCounterService::class.java)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(serviceIntent)
      } else {
        startService(serviceIntent)
      }
    } catch (e: Exception) {
      Log.e(TAG, "Exception in startStepCounterService", e)
    }
  }

  private fun isServiceRunning(serviceClass: Class<out Service>): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
      if (serviceClass.name == service.service.className) {
        return true
      }
    }
    return false
  }

  override fun onDestroy() {
    super.onDestroy()

    try {
      Log.d(TAG, "MainActivity onDestroy")

      //val serviceIntent = Intent(this, StepCounterService::class.java)
      //stopService(serviceIntent)
    } catch (e: Exception) {
      Log.e(TAG, "Exception in onDestroy", e)
    }
  }
}

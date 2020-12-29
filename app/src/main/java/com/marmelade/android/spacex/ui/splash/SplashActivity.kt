package com.marmelade.android.spacex.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marmelade.android.spacex.ui.main.MainActivity


/**
 * Activity for app splash screen
 *
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }
}
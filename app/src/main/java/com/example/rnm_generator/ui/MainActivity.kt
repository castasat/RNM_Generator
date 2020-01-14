package com.example.rnm_generator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.rnm_generator.BuildConfig
import com.example.rnm_generator.R

class MainActivity : AppCompatActivity() {

    private val rnmFragment: RnmFragment by lazy {
        RnmFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startRnmFragment()
    }

    private fun startRnmFragment() {
        rnmFragment.apply {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, this)
                .commit()
        }
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        findViewById<Toolbar>(R.id.toolbar)?.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onBackPressed() {
        log("MainActivity.onBackPressed()")
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                log("MainActivity.onOptionsItemSelected() home")
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val TAG = "RnmGenerator"
        fun log(message: String) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, message)
            }
        }
    }
}

package io.github.wotaslive.utils

import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

fun Fragment.setupActionBar(toolbar: Toolbar, action: ActionBar.() -> Unit) {
    with(activity as AppCompatActivity) {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            action()
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
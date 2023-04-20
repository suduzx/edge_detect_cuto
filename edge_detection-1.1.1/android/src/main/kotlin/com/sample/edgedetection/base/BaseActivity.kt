package com.sample.edgedetection.base

import android.app.ActionBar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.sample.edgedetection.R

abstract class BaseActivity : AppCompatActivity() {

    protected val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideContentViewId())
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

//        supportActionBar.setDisplayHomeAsUpEnabled(false)
//        supportActionBar?.setDisplayShowCustomEnabled(true)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//        val customView = layoutInflater.inflate(R.layout.action_bar, null)
//        customView.layoutParams = ActionBar.LayoutParams(
//            FrameLayout.LayoutParams.MATCH_PARENT,
//            FrameLayout.LayoutParams.MATCH_PARENT
//        )
//        actionBar!!.customView = customView
//        val parent: Toolbar = customView.parent as Toolbar
//        parent.setContentInsetsAbsolute(0, 0)
        initPresenter()
        transparentStatusBar()
        prepare()
    }

    fun transparentStatusBar(
        statusBarColor: Int = resources.getColor(R.color.colorPrimaryLight)
    ) {
        var systemUiVisibility = 0
        // Use a dark scrim by default since light status is API 23+
        //  Use a dark scrim by default since light nav bar is API 27+
        val winParams = window.attributes

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            systemUiVisibility = systemUiVisibility or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.decorView.systemUiVisibility = systemUiVisibility
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            winParams.flags = winParams.flags or
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            winParams.flags = winParams.flags and
                (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS).inv()
            window.statusBarColor = statusBarColor
        }

        window.attributes = winParams
    }

    fun showMessage(id: Int) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
    }

    abstract fun provideContentViewId(): Int

    abstract fun initPresenter()

    abstract fun prepare()
}

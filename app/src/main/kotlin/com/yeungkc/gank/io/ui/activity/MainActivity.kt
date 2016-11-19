package com.yeungkc.gank.io.ui.activity

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.view.WindowInsetsCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.databinding.ActivityDrawerBaseBinding
import com.yeungkc.gank.io.fragmentnavigatior.FragmentNavigator
import com.yeungkc.gank.io.ui.IScrollFragment
import com.yeungkc.gank.io.ui.adapter.FragmentAdapter


class MainActivity : BaseToolBarActivity() {
    private val binding by lazy { ActivityDrawerBaseBinding.inflate(layoutInflater) }
    override fun getToolBar(): Toolbar = binding.tlBaseToolbar
    var navigator: FragmentNavigator? = null
    val appName by lazy { getString(R.string.app_name) }


    override fun onCreateView() {
        setContentView(binding.root)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        onToolBarCreate(savedInstanceState)
    }

    override fun initEvent(savedInstanceState: Bundle?) {
        super.initEvent(savedInstanceState)
        binding.navView.setNavigationItemSelectedListener {
            val itemId = it.itemId
            val currentPosition = getPosition(itemId)

            binding.drawerLayout.closeDrawer(GravityCompat.END)
            showFragment(currentPosition)
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onApplyWindowInsets(savedInstanceState: Bundle?, insets: WindowInsetsCompat) {
        super.onApplyWindowInsets(savedInstanceState, insets)
        val categorical = resources.getStringArray(R.array.categorical)
        val adapterAdapter = FragmentAdapter(categorical, insets.systemWindowInsetTop, insets.systemWindowInsetBottom)

        navigator = FragmentNavigator(supportFragmentManager, adapterAdapter, binding.flBaseContent.id)
        navigator?.let {
            it.setDefaultPosition(0)
            it.onCreate(savedInstanceState)
            val position = it.getCurrentPosition()
            binding.navView.setCheckedItem(getItemId(position))
            showFragment(position)
        }
    }

    val itemData = arrayOf(
            R.id.nav_home,
            R.id.nav_category,
            R.id.nav_android,
            R.id.nav_ios,
            R.id.nav_前端,
            R.id.nav_apps,
            R.id.nav_瞎推荐,
            R.id.nav_拓展资源,
            R.id.nav_movie
    )

    fun getPosition(itemId: Int) = itemData.indexOf(itemId)

    fun getItemId(position: Int): Int = itemData[position]

    fun showFragment(position: Int) {
        navigator?.let {
            if (position == 0) {
                animateTitleChange(appName)
            } else {
                animateTitleChange(binding.navView.menu.getItem(position).title)
            }

            it.showFragment(position
                    , enter = R.animator.fade_in, exit = R.animator.fade_out
            )
            val fragment = it.getFragment(position)
            if (fragment is IScrollFragment) {
                fragment.checkPaddingTop()
                enableDoubleClickToEvent {
                    fragment.onDoubleClickToolBar()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_category ->
                if (!binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    binding.drawerLayout.openDrawer(GravityCompat.END)
                } else {
                    binding.drawerLayout.closeDrawer(GravityCompat.END)
                }

            R.id.action_search -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END))
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        else
            super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        onSaveToolbarInstanceState(outState)
        navigator?.onSaveInstanceState(outState)
    }
}

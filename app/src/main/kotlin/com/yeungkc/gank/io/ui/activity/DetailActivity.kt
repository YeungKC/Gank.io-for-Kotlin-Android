package com.yeungkc.gank.io.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.WindowInsetsCompat
import android.support.v7.widget.Toolbar
import com.yeungkc.gank.io.databinding.ActivityToolbarBaseBinding
import com.yeungkc.gank.io.model.DataLayer
import com.yeungkc.gank.io.ui.fragment.DetailFragment
import java.util.*

class DetailActivity : BaseToolBarActivity() {
    private val binding by lazy { ActivityToolbarBaseBinding.inflate(layoutInflater) }
    override fun getToolBar(): Toolbar = binding.tlBaseToolbar
    private var insets: WindowInsetsCompat? = null

    override fun onCreateView() {
        setContentView(binding.root)
    }

    lateinit var date: Date

    companion object {
        const val DATE = "DATE"
        const val TAG = "DetailActivity"
    }

    override fun initArgs(savedInstanceState: Bundle?) {
        super.initArgs(savedInstanceState)
        date = intent.getSerializableExtra(DATE) as Date
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            date = it.getSerializableExtra(DATE) as Date
            animateTitleChange(DataLayer.simpleDateFormat.format(date))

            showFragment(insets, true)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        onToolBarCreate(savedInstanceState)
        this.title = DataLayer.simpleDateFormat.format(date)
    }

    override fun initEvent(savedInstanceState: Bundle?) {
        super.initEvent(savedInstanceState)
        enableHomeAsUp { onBackPressed() }
    }


    override fun onApplyWindowInsets(savedInstanceState: Bundle?, insets: WindowInsetsCompat) {
        super.onApplyWindowInsets(savedInstanceState, insets)
        this.insets = insets

        showFragment(this.insets)
    }

    private fun showFragment(insets: WindowInsetsCompat?,reset:Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()

        val fragment = supportFragmentManager.findFragmentByTag(TAG)
        if (reset) {
            remove(fragment, transaction)
            add(insets, transaction, binding.flBaseContent.id, date)
        } else {
            show(fragment, insets, transaction, binding.flBaseContent.id, date)
        }

        transaction.commit()
    }

    private fun show(findFragmentByTag: Fragment?, insets: WindowInsetsCompat?, transaction: FragmentTransaction, @IdRes containerViewId: Int, date: Date) {
        if (findFragmentByTag != null) {
            transaction.show(findFragmentByTag)
        } else {
            add(insets, transaction, containerViewId, date)
        }
    }

    private fun add(insets: WindowInsetsCompat?, transaction: FragmentTransaction, @IdRes containerViewId: Int, date: Date) {
        transaction.add(
                containerViewId,
                DetailFragment.newInstance(date, insets?.systemWindowInsetTop ?: 0, insets?.systemWindowInsetBottom ?: 0),
                TAG
        )
    }

    private fun remove(findFragmentByTag: Fragment?, transaction: FragmentTransaction) {
        if (findFragmentByTag != null) {
            transaction.remove(findFragmentByTag)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        onSaveToolbarInstanceState(outState)
    }
}
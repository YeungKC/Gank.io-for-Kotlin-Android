package com.yeungkc.gank.io.fragmentnavigatior

import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.yeungkc.gank.io.R

class FragmentNavigator(
        private val mFragmentManager: FragmentManager,
        private val mAdapter: FragmentNavigatorAdapter,
        @IdRes private val mContainerViewId: Int) {

    companion object {
        private const val EXTRA_CURRENT_POSITION = "extra_current_position"
    }

    private var mCurrentPosition = -1

    private var mDefaultPosition: Int = 0

    fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(EXTRA_CURRENT_POSITION, mDefaultPosition)
        }
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(EXTRA_CURRENT_POSITION, mCurrentPosition)
    }

    fun showFragment(position: Int, reset: Boolean = false, allowingStateLoss: Boolean = false, @AnimRes enter:Int = R.animator.fade_in, @AnimRes exit:Int = R.animator.fade_out) {
        this.mCurrentPosition = position
        val transaction = mFragmentManager.beginTransaction()
        transaction.setCustomAnimations(enter,exit)
        val count = mAdapter.getCount()
        for (i in 0..count - 1) {
            if (position == i) {
                if (reset) {
                    remove(position, transaction)
                    add(position, transaction)
                } else {
                    show(i, transaction)
                }
            } else {
                hide(i, transaction)
            }
        }
        if (allowingStateLoss) {
            transaction.commitAllowingStateLoss()
        } else {
            transaction.commit()
        }
    }

    /**
     * reset all the fragments and show current fragment

     * @see .resetFragments
     */
    fun resetFragments() {
        resetFragments(mCurrentPosition)
    }

    /**
     * @see .resetFragments
     */
    fun resetFragments(position: Int) {
        resetFragments(position, false)
    }

    /**
     * reset all the fragment and show given position fragment

     * @param position fragment position
     * *
     * @param allowingStateLoss true if allowing state loss otherwise false
     */
    fun resetFragments(position: Int, allowingStateLoss: Boolean) {
        this.mCurrentPosition = position
        val transaction = mFragmentManager.beginTransaction()
        removeAll(transaction)
        add(position, transaction)
        if (allowingStateLoss) {
            transaction.commitAllowingStateLoss()
        } else {
            transaction.commit()
        }
    }

    /**
     * @see .removeAllFragment
     */
    fun removeAllFragment() {
        removeAllFragment(false)
    }

    /**
     * remove all fragment in the [FragmentManager]

     * @param allowingStateLoss true if allowing state loss otherwise false
     */
    fun removeAllFragment(allowingStateLoss: Boolean) {
        val transaction = mFragmentManager.beginTransaction()
        removeAll(transaction)
        if (allowingStateLoss) {
            transaction.commitAllowingStateLoss()
        } else {
            transaction.commit()
        }
    }

    /**
     * @return current showing fragment's position
     */
    fun getCurrentPosition(): Int {
        return mCurrentPosition
    }

    /**
     * Also @see #getFragment(int)

     * @return current position fragment
     */
    fun getCurrentFragment(): Fragment? {
        return getFragment(mCurrentPosition)
    }

    /**
     * Get the fragment has been added in the given position. Return null if the fragment
     * hasn't been added in [FragmentManager] or has been removed already.

     * @param position position of fragment in [FragmentNavigatorAdapter.onCreateFragment]}
     * *                 and [FragmentNavigatorAdapter.getTag]
     * *
     * @return The fragment if found or null otherwise.
     */
    fun getFragment(position: Int): Fragment? {
        val tag = mAdapter.getTag(position)
        return mFragmentManager.findFragmentByTag(tag)
    }

    private fun show(position: Int, transaction: FragmentTransaction) {
        val tag = mAdapter.getTag(position)
        val fragment = mFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            add(position, transaction)
        } else {
            transaction.show(fragment)
        }
    }

    private fun hide(position: Int, transaction: FragmentTransaction) {
        val tag = mAdapter.getTag(position)
        val fragment = mFragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            transaction.hide(fragment)
        }
    }

    private fun add(position: Int, transaction: FragmentTransaction) {
        val fragment = mAdapter.onCreateFragment(position)
        val tag = mAdapter.getTag(position)
        transaction.add(mContainerViewId, fragment, tag)
    }

    private fun removeAll(transaction: FragmentTransaction) {
        val count = mAdapter.getCount()
        for (i in 0..count - 1) {
            remove(i, transaction)
        }
    }

    private fun remove(position: Int, transaction: FragmentTransaction) {
        val tag = mAdapter.getTag(position)
        val fragment = mFragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            transaction.remove(fragment)
        }
    }

    fun setDefaultPosition(defaultPosition: Int) {
        this.mDefaultPosition = defaultPosition
        if (mCurrentPosition == -1) {
            this.mCurrentPosition = defaultPosition
        }
    }
}
package com.yeungkc.gank.io.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yeungkc.gank.io.CustomApplication
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.contract.DetailContract
import com.yeungkc.gank.io.databinding.FragmentGankBinding
import com.yeungkc.gank.io.extensions.getToolBarHeight
import com.yeungkc.gank.io.extensions.isOrientationPortrait
import com.yeungkc.gank.io.extensions.isTranslucentNavigation
import com.yeungkc.gank.io.extensions.onScrollShowHideAppBar
import com.yeungkc.gank.io.model.bean.AutoBean
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.ui.IScrollFragment
import com.yeungkc.gank.io.ui.IToolbarManager
import com.yeungkc.gank.io.ui.activity.DetailActivity
import com.yeungkc.gank.io.ui.adapter.GankAdapter
import com.yeungkc.gank.io.ui.item_animator.SlideInItemAnimator
import java.util.*


class DetailFragment : BaseFragment(), IScrollFragment, DetailContract.DetailView {
    override fun onLoading() {
        if (gankAdapter.isHaveDataSets()) {
            binding.srlRefresh.isRefreshing = true
        } else {
            gankAdapter.showLoading()
        }
    }

    override fun onError(error: Throwable) {
        gankAdapter.showError()
    }

    override fun setData(data: List<AutoBean>) {
        gankAdapter.replaceWith(data) {
            binding.srlRefresh.isRefreshing = false
        }
    }

    lateinit var binding: FragmentGankBinding
    val layoutManager by lazy {
        LinearLayoutManager(context)
                .apply { recycleChildrenOnDetach = true }
    }
    val gankAdapter by lazy { GankAdapter() }
    val toolbarManager: IToolbarManager?
        get() {
            if (activity != null && activity is IToolbarManager) {
                return activity as IToolbarManager
            }
            return null
        }

    lateinit var presenter: DetailContract.DetailPresenter

    var scrollOffset = 0

    companion object {
        const val paddingBottomDp = 16
        fun newInstance(date: Date? = null,statusBarHeight: Int, navigationBarHeight: Int): DetailFragment {

            val args = Bundle()
            args.putSerializable(DetailActivity.DATE,date)
            args.putInt(STATUS_BAR_HEIGHT, statusBarHeight)
            args.putInt(NAVIGATION_BAR_HEIGHT, navigationBarHeight)

            val fragment = DetailFragment()
            fragment.arguments = args

            return fragment
        }
    }

     var date: Date? = null


    override fun initArgs(arguments: Bundle) {
        super.initArgs(arguments)
        arguments.getSerializable(DetailActivity.DATE)?.let {
            if (it !is Date) return

            date = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGankBinding.inflate(inflater)
        return binding.root
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        scrollOffset = savedInstanceState?.getInt(IScrollFragment.SCROLL_OFF_SET_KEY, scrollOffset) ?: scrollOffset

        setContentPadding()

        binding.rvContent.recycledViewPool = CustomApplication.recycledViewPool
        binding.rvContent.layoutManager = layoutManager
        binding.rvContent.itemAnimator = SlideInItemAnimator()
        binding.rvContent.adapter = gankAdapter

        binding.srlRefresh.setColorSchemeResources(R.color.colorAccent)
    }

    override fun initEvent(savedInstanceState: Bundle?) {
        super.initEvent(savedInstanceState)
        toolbarManager?.enableDoubleClickToEvent { onDoubleClickToolBar() }
        binding.srlRefresh.setOnRefreshListener {
            if (gankAdapter.isCanLoading())
                presenter.getContent()
        }
        gankAdapter.setOnClickErrorItemListener { presenter.getContent() }
        binding.rvContent.onScrollShowHideAppBar(
                { toolbarManager?.hideToolBar() },
                { toolbarManager?.showToolBar() },
                { scrollOffset = it }
        )
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        presenter = DetailContract.DetailPresenter(date)
        presenter.bind(this)

        savedInstanceState?.let {
            val dataSets = it.getSerializable(DATASETS_KEY)

            dataSets?.let {
                gankAdapter.replaceWith(it as ArrayList<Result>)
                return
            }
        }
        presenter.getContent()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(IScrollFragment.SCROLL_OFF_SET_KEY, scrollOffset)
        if (!gankAdapter.isHaveDataSets()) return

        outState.putSerializable(DATASETS_KEY, ArrayList(gankAdapter.dataSets))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unBind(isDetached)
    }

    private fun setContentPadding() {
        val orientationPortrait = context.isOrientationPortrait()
        if (!orientationPortrait || !context.isTranslucentNavigation()) {
            navigationBarHeight = 0
        }

        val toolBarHeight = context.getToolBarHeight()
        binding.rvContent.run {
            clipToPadding = false

            val scale = resources.displayMetrics.density
            val pixel = (paddingBottomDp * scale + 0.5f).toInt()

            setPadding(paddingLeft, toolBarHeight + statusBarHeight, paddingRight, pixel + navigationBarHeight)
        }

        binding.srlRefresh.setProgressViewOffset(false, 0, toolBarHeight + statusBarHeight * 2)
    }

    override fun checkPaddingTop() {
        val paddingTop = binding.rvContent.paddingTop
        if (scrollOffset < paddingTop) toolbarManager?.showToolBar()
    }

    override fun onDoubleClickToolBar() {
        binding.rvContent.smoothScrollToPosition(0)
    }
}

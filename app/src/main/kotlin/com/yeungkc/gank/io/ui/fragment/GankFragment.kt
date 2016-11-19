package com.yeungkc.gank.io.ui.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yeungkc.gank.io.CustomApplication
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.contract.GankContract
import com.yeungkc.gank.io.databinding.FragmentGankBinding
import com.yeungkc.gank.io.extensions.*
import com.yeungkc.gank.io.model.bean.AutoBean
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.ui.IScrollFragment
import com.yeungkc.gank.io.ui.IScrollFragment.Companion.SCROLL_OFF_SET_KEY
import com.yeungkc.gank.io.ui.IToolbarManager
import com.yeungkc.gank.io.ui.adapter.GankAdapter
import com.yeungkc.gank.io.ui.item_animator.SlideInItemAnimator
import java.util.*


class GankFragment : BaseFragment(), IScrollFragment, GankContract.GankView {
    override fun onLoading() {
        if (gankAdapter.isHaveDataSets()) {
            if (requestPage == 0) {
                binding.srlRefresh.isRefreshing = true
                gankAdapter.hideLoadingMore()
            } else {
                gankAdapter.showLoadingMore()
            }
        } else {
            gankAdapter.showLoading()
        }
    }

    override fun onError(error: Throwable) {
        binding.srlRefresh.isRefreshing = false

        if (gankAdapter.isHaveDataSets()) {
            if (requestPage == 0) {
                Snackbar.make(binding.rvContent, R.string.connection_fail, LENGTH_LONG)
                        .setAction(R.string.retry) {
                            presenter.getRemoteContent(requestPage)
                        }
                        .show(navigationBarHeight)
            } else {
                gankAdapter.showLoadMoreError()
            }
        } else {
            gankAdapter.showError()
        }
    }

    override fun setData(data: List<Result>) {
        val list: List<AutoBean>

        if (currentPage > 0) {
            list = gankAdapter.dataSets + data
        } else {
            list = data
        }

        gankAdapter.replaceWith(list) {
            binding.srlRefresh.isRefreshing = false

            if (gankAdapter.isHaveDataSets()) {
                if (isNoData) {
                    gankAdapter.showLoadMoreNoData()
                } else {
                    gankAdapter.showLoadingMore()
                }
            }
        }
    }

    override var requestPage: Int = 0
    override var currentPage: Int = -1
    override var isNoData: Boolean = false
    lateinit var categorical: String
    var scrollOffset = 0

    companion object {
        const val CURRENT_PAGE_KEY: String = "CURRENT_PAGE_KEY"
        const val IS_NO_DATA_KEY: String = "IS_NO_DATA_KEY"

        const val CATEGORICAL = "CATEGORICAL"
        fun newInstance(categorical: String, statusBarHeight: Int, navigationBarHeight: Int): GankFragment {

            val args = Bundle()
            args.putString(CATEGORICAL, categorical)
            args.putInt(STATUS_BAR_HEIGHT, statusBarHeight)
            args.putInt(NAVIGATION_BAR_HEIGHT, navigationBarHeight)

            val fragment = GankFragment()
            fragment.arguments = args

            return fragment
        }
    }

    lateinit var presenter: GankContract.GankPresenter
    val isFuLi by lazy { categorical == "福利" }
    val layoutManager by lazy<RecyclerView.LayoutManager> {
        if (isFuLi) {
            StaggeredGridLayoutManager(
                    resources.getInteger(R.integer.spanCount), StaggeredGridLayoutManager.VERTICAL)
        } else {
            LinearLayoutManager(context).apply { recycleChildrenOnDetach = true }
        }
    }
    val placeholderColors: IntArray by lazy { resources.getIntArray(R.array.loadingPlaceholdersDark) }
    val gankAdapter by lazy { GankAdapter(placeholderColors) }

    val toolbarManager: IToolbarManager?
        get() {
            if (activity != null && activity is IToolbarManager) {
                return activity as IToolbarManager
            }
            return null
        }

    override fun onStart() {
        super.onStart()
        checkPaddingTop()
    }

    override fun initArgs(arguments: Bundle) {
        super.initArgs(arguments)
        categorical = arguments.getString(CATEGORICAL)
        presenter = GankContract.GankPresenter(categorical)
    }

    lateinit var binding: FragmentGankBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGankBinding.inflate(inflater)
        return binding.root
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        scrollOffset = savedInstanceState?.getInt(SCROLL_OFF_SET_KEY, scrollOffset) ?: scrollOffset

        setContentPadding()

        if (!isFuLi) {
            binding.rvContent.recycledViewPool = CustomApplication.recycledViewPool
        }
        binding.rvContent.layoutManager = layoutManager
        binding.rvContent.itemAnimator = SlideInItemAnimator()
        binding.rvContent.adapter = gankAdapter

        binding.srlRefresh.setColorSchemeResources(R.color.colorAccent)
    }

    override fun initEvent(savedInstanceState: Bundle?) {
        super.initEvent(savedInstanceState)
        toolbarManager?.enableDoubleClickToEvent { onDoubleClickToolBar() }
        binding.srlRefresh.setOnRefreshListener {
            if (!gankAdapter.isCanLoading()) {
                binding.srlRefresh.isRefreshing = false
                return@setOnRefreshListener
            }

            presenter.getRemoteContent()
        }

        binding.rvContent.setOnLoadMore {
            if (presenter.isRemoteLoading()) return@setOnLoadMore
            presenter.getRemoteContent(requestPage + 1)
        }

        gankAdapter.setOnClickErrorItemListener { presenter.getRemoteContent(requestPage) }
        binding.rvContent.onScrollShowHideAppBar(
                { toolbarManager?.hideToolBar() },
                { toolbarManager?.showToolBar() },
                { scrollOffset = it }
        )
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        presenter.bind(this)

        savedInstanceState?.let {
            val dataSets = it.getSerializable(DATASETS_KEY)
            currentPage = it.getInt(CURRENT_PAGE_KEY, currentPage)
            isNoData = it.getBoolean(IS_NO_DATA_KEY, isNoData)

            dataSets?.let {
                setData(it as ArrayList<Result>)
                return
            }
        }
        presenter.getContent()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCROLL_OFF_SET_KEY, scrollOffset)

        if (gankAdapter.dataSets.isEmpty()) return

        outState.putInt(CURRENT_PAGE_KEY, currentPage)
        outState.putBoolean(IS_NO_DATA_KEY, isNoData)
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
            setPadding(paddingLeft, toolBarHeight + statusBarHeight, paddingRight, navigationBarHeight)
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
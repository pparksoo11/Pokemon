package com.soo.presentation.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: ViewDataBinding>(@LayoutRes private val layoutId: Int): Fragment() {

    protected lateinit var binding: T
        private set

    private var rootView: View? = null

    /**
     * Fragment가 처음 초기화 되었는지 여부
     * fragment의 초기화가 필요하다면 onCreate에서 true로 초기화
     * */
    var isFirstInit = true

    abstract fun initView()
    protected open fun observeViewModel() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("onCraete :: ", javaClass.simpleName)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return rootView ?: DataBindingUtil.inflate<T>(inflater, layoutId, container, false).apply {
            binding = this
            rootView = this.root
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        if(isFirstInit) {
            isFirstInit = false
            initView()
            observeViewModel()
        }
    }
}
package com.soo.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T: ViewDataBinding>(@LayoutRes private val layoutResId: Int): AppCompatActivity() {
    protected lateinit var binding: T
        private set

    abstract fun initView()
    protected open fun observeViewModel() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)

        initView()
        observeViewModel()
    }
}
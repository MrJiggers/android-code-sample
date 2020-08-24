package com.marmelade.android.spacex.data.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/**
 * Base parent for fragments
 */
abstract class BaseFragment<T, V : ViewBinding> : DaggerFragment() where T : ViewModel {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var viewModel: T
    protected abstract val vmClass: Class<T>
    protected var binding: V? = null
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(this.vmClass)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = this.setViewBinding(inflater, container)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    abstract fun setViewBinding(inflater: LayoutInflater, container: ViewGroup?): V
}
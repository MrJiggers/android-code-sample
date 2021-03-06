package com.marmelade.android.spacex.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


/**
 * Base parent for activities
 *
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
abstract class BaseActivity<V, T> : DaggerAppCompatActivity() where V : ViewModel, T : ViewBinding {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var viewModel: V
    protected abstract val vmClassToken: Class<V>
    protected var binding: T? = null
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setViewBinding(layoutInflater)
        val view = binding?.root
        setContentView(view)
        viewModel = createViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    abstract fun setViewBinding(inflater: LayoutInflater): T

    private fun createViewModel() = ViewModelProviders.of(this, viewModelFactory).get(this.vmClassToken)
}
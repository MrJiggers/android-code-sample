package com.marmelade.android.spacex.ui.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marmelade.android.spacex.ui.base.BaseFragment
import com.marmelade.android.spacex.data.entities.company.Company
import com.marmelade.android.spacex.databinding.FragmentCompanyBinding


/**
 * Fragment for information about SpaceX company
 *
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
class CompanyFragment : BaseFragment<CompanyViewModel, FragmentCompanyBinding>() {
    companion object {
        fun newInstance() = CompanyFragment()
    }

    override val vmClass: Class<CompanyViewModel> = CompanyViewModel::class.java


    override fun setViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
            FragmentCompanyBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.company.observe(viewLifecycleOwner, {
            it.firstOrNull()?.let { setUpLayout(it) }
        })
    }

    private fun setUpLayout(company: Company) {
        binding?.companyName?.text = company.name ?: ""
        binding?.companyDescription?.text = company.summary ?: ""
    }
}
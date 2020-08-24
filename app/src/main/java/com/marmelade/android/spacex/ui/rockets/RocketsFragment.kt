package com.marmelade.android.spacex.ui.rockets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.marmelade.android.spacex.R
import com.marmelade.android.spacex.data.base.BaseFragment
import com.marmelade.android.spacex.data.entities.ErrorStatus
import com.marmelade.android.spacex.data.entities.LoadingStatus
import com.marmelade.android.spacex.data.entities.SuccessStatus
import com.marmelade.android.spacex.data.entities.rocket.Rocket
import com.marmelade.android.spacex.databinding.FragmentRocketsBinding
import com.marmelade.android.spacex.databinding.RowRocketBinding
import com.marmelade.android.spacex.logic.util.setVisible
import com.marmelade.android.spacex.logic.util.showSnackBar
import com.marmelade.android.spacex.ui.detail.DetailActivity
import com.marmelade.android.spacex.ui.main.FilterOnClickListener
import com.marmelade.android.spacex.ui.main.MainActivity
import com.marmelade.android.spacex.ui.main.bottom_sheets.FilterBottomSheet


/**
 * Fragment for list of rockets
 */
class RocketsFragment : BaseFragment<RocketsViewModel, FragmentRocketsBinding>() {
    companion object {
        fun newInstance() = RocketsFragment()

        private const val ROW_IMAGE_FADE_DURATION = 100
    }

    override val vmClass: Class<RocketsViewModel> = RocketsViewModel::class.java

    private var rocketsAdapter: RocketsAdapter? = null
    private var filterBottomSheet: FilterBottomSheet? = null


    override fun setViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
            FragmentRocketsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        binding?.rocketsRefresh?.setOnRefreshListener {
            viewModel.getListOfRockets()
        }
        viewModel.rockets.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                showLoading(false)
                showError(false)
            }
            rocketsAdapter?.submitList(it)
        })
        viewModel.refreshState.observe(viewLifecycleOwner, {
            when {
                it.status is LoadingStatus -> {
                    showLoading(true)
                    showError(false)
                }
                it.status is SuccessStatus -> {
                    showLoading(false)
                    showError(false)
                }
                it.status is ErrorStatus && viewModel.rockets.value?.isEmpty() == true -> {
                    showLoading(false)
                    showError(true)
                }
                it.status is ErrorStatus -> {
                    binding?.root?.let { rootLayout ->
                        showSnackBar(rootLayout, it.errorIdentification.message)
                    }
                }
            }
            binding?.rocketsRefresh?.isRefreshing = false
        })

        setUpRecyclerView()

        (activity as? MainActivity)?.getFilterMenuItem(object : FilterOnClickListener {
            override fun filterOnClick(filter: MenuItem) {
                showFilterBottomSheet()
            }
        })
    }

    override fun onDestroyView() {
        (activity as? MainActivity)?.getFilterMenuItem(null)
        super.onDestroyView()
    }

    private fun showLoading(showLoading: Boolean) {
        if (showLoading) {
            binding?.rocketsProgressText?.text = getString(R.string.detail_loading)
            binding?.rocketsProgressAnimation?.setAnimation("rocket_launched_into_space.json")
            binding?.rocketsProgressAnimation?.playAnimation()
        }
        binding?.detailLoadingGroup?.setVisible(showLoading)
    }

    private fun showError(showError: Boolean, errorMessage: String? = null) {
        if (showError) {
            binding?.rocketsProgressText?.text =
                    if (errorMessage?.isNotEmpty() == true) errorMessage
                    else getString(R.string.common_error_unknown_error)
            binding?.rocketsProgressAnimation?.setAnimation("delivery_with_plane_concept.json")
            binding?.rocketsProgressAnimation?.playAnimation()
        }
        binding?.detailLoadingGroup?.setVisible(showError)
    }

    private fun setUpRecyclerView() {
        rocketsAdapter = RocketsAdapter()
        binding?.rocketsList?.apply {
            adapter = rocketsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showFilterBottomSheet() {
        if (filterBottomSheet?.isVisible == true) return
        filterBottomSheet = FilterBottomSheet(viewModel.filter)
        filterBottomSheet?.show(childFragmentManager, filterBottomSheet?.tag)
    }


    inner class RocketsAdapter : ListAdapter<Rocket, RecyclerView.ViewHolder>(
            object : DiffUtil.ItemCallback<Rocket>() {
                override fun areItemsTheSame(oldItem: Rocket, newItem: Rocket): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Rocket, newItem: Rocket): Boolean {
                    return oldItem.name == newItem.name &&
                            oldItem.company == newItem.company &&
                            oldItem.firstFlight == newItem.firstFlight
                }
            }) {

        private lateinit var binding: RowRocketBinding

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            binding = RowRocketBinding.inflate(layoutInflater, parent, false)
            return RocketViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is RocketViewHolder) {
                holder.bind(getItem(position))
            }
        }

        private inner class RocketViewHolder(private val binding: RowRocketBinding) :
                RecyclerView.ViewHolder(binding.root) {
            fun bind(rocket: Rocket) {
                binding.root.setOnClickListener {
                    startActivity(DetailActivity.getStartIntent(requireContext(), rocket.id))
                }
                binding.rowRocketTitle.text = rocket.name ?: ""
                binding.rowRocketCompany.text = rocket.company ?: ""
                binding.rowRocketFirstFlight.text = rocket.firstFlight ?: ""

                Glide.with(requireContext())
                        .load(rocket.flickrImages?.firstOrNull())
                        .apply(RequestOptions.circleCropTransform())
                        .transition(DrawableTransitionOptions.withCrossFade(ROW_IMAGE_FADE_DURATION))
                        .into(binding.rowRocketImage)
            }
        }
    }
}
package com.marmelade.android.spacex.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.marmelade.android.spacex.BuildConfig
import com.marmelade.android.spacex.R
import com.marmelade.android.spacex.data.base.BaseActivity
import com.marmelade.android.spacex.data.entities.ErrorStatus
import com.marmelade.android.spacex.data.entities.rocket.Rocket
import com.marmelade.android.spacex.databinding.ActivityDetailBinding
import com.marmelade.android.spacex.logic.util.showSnackBar
import com.marmelade.android.spacex.ui.detail.adapter.DetailPhotosPagerAdapter
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


/**
 * Activity for rocket detail
 */
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {
    companion object {
        fun getStartIntent(context: Context, rocketId: String): Intent {
            return Intent(context, DetailActivity::class.java)
                    .putExtra(ROCKET_ID_EXTRA, rocketId)
        }

        private const val ROCKET_ID_EXTRA = "${BuildConfig.APPLICATION_ID}.rocket_id"
    }

    override val vmClassToken: Class<DetailViewModel> = DetailViewModel::class.java


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getStringExtra(ROCKET_ID_EXTRA)?.let {
            viewModel.getRocketById(it)
            viewModel.rocketById(it).observe(this, {
                binding?.detailRefresh?.isRefreshing = false
                it.firstOrNull()?.let { setUpDetailUi(it) }
            })
        }

        binding?.detailToolbar?.setNavigationOnClickListener {
            finish()
        }
        binding?.detailRefresh?.setOnRefreshListener {
            intent.getStringExtra(ROCKET_ID_EXTRA)?.let { viewModel.getRocketById(it) }
        }
        viewModel.refreshState.observe(this, {
            if (it.status is ErrorStatus) {
                binding?.root?.let { rootLayout ->
                    showSnackBar(rootLayout, it.errorIdentification.message)
                }
            }
            binding?.detailRefresh?.isRefreshing = false
        })
    }

    override fun setViewBinding(inflater: LayoutInflater): ActivityDetailBinding =
            ActivityDetailBinding.inflate(layoutInflater)

    private fun setUpDetailUi(rocket: Rocket) {
        binding?.detailTitle?.text = rocket.name ?: ""
        binding?.detailDescription?.text = rocket.description ?: ""
        binding?.detailHeightText?.text =
                getString(R.string.detail_unit_m, rocket.height?.meters?.toString() ?: "0")
        binding?.detailDiameterText?.text =
                getString(R.string.detail_unit_m, rocket.diameter?.meters?.toString() ?: "0")

        val weightSymbol = DecimalFormatSymbols().apply { decimalSeparator = '.' }
        val weightFormatter = DecimalFormat("#0.0", weightSymbol)
        val massInTones: String = weightFormatter.format((rocket.mass?.kg ?: 0) / 1000.0)
        binding?.detailMassText?.text = getString(R.string.detail_unit_tones, massInTones)

        rocket.flickrImages?.let { setUpPhotosPagerAdapter(it) }
    }

    private fun setUpPhotosPagerAdapter(photos: List<String>) {
        binding?.detailPhotosViewPager?.adapter = DetailPhotosPagerAdapter(this, photos)
        binding?.detailIndicatorView?.count = photos.size
    }
}
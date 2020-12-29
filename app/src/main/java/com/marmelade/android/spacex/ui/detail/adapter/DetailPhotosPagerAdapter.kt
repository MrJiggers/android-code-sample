package com.marmelade.android.spacex.ui.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.marmelade.android.spacex.R


/**
 * ViewPager adapter for list of photos
 *
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
class DetailPhotosPagerAdapter(
        private val context: Context,
        private val items: List<String>
) : PagerAdapter() {
    companion object {
        private const val ROW_IMAGE_FADE_DURATION = 100
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val pageView = inflater.inflate(R.layout.item_detail_view_pager, container, false)

        val itemAtPosition = items[position]
        val detailImage = pageView.findViewById<ImageView>(R.id.detailImage)

        Glide.with(context)
                .load(itemAtPosition)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(ROW_IMAGE_FADE_DURATION))
                .into(detailImage)

        container.addView(pageView)
        return pageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun getCount(): Int = items.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
}
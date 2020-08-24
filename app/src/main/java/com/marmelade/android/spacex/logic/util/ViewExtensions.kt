package com.marmelade.android.spacex.logic.util

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun View.setVisible(visible: Boolean) {
    if (visible) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

fun showSnackBar(rootView: View, text: String) {
    val snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_SHORT)
    snackbar.show()
}
package com.marmelade.android.spacex.ui.main.bottom_sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.marmelade.android.spacex.R
import com.marmelade.android.spacex.data.entities.RocketFilter


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
class FilterBottomSheet() : BottomSheetDialogFragment() {

    private lateinit var filter: MutableLiveData<RocketFilter>

    private lateinit var switch: SwitchMaterial
    private lateinit var seekBar: SeekBar
    private lateinit var seekBarValue: TextView

    private var tempRocketFilter = RocketFilter()


    constructor(filter: MutableLiveData<RocketFilter>) : this() {
        this.filter = filter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.bottom_sheet_filter, container, false)

        view.findViewById<ImageView>(R.id.close).setOnClickListener {
            dismiss()
        }
        switch = view.findViewById(R.id.filterActiveRocket)
        switch.setOnCheckedChangeListener { _, isChecked ->
            tempRocketFilter.active = isChecked
        }
        seekBar = view.findViewById(R.id.filterSuccessRate)
        seekBarValue = view.findViewById(R.id.filterSuccessRateValue)
        seekBar.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        tempRocketFilter.successRatio = progress
                        seekBarValue.text = progress.toString()
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
                })
        view.findViewById<MaterialButton>(R.id.filterAction).setOnClickListener {
            tempRocketFilter.enabledFilter = true
            filter.postValue(tempRocketFilter)
            dismiss()
        }
        view.findViewById<MaterialButton>(R.id.filterClearAction).setOnClickListener {
            tempRocketFilter = RocketFilter()
            filter.postValue(tempRocketFilter)
            dismiss()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filter.observe(viewLifecycleOwner, {
            switch.isChecked = it.active ?: false
            seekBar.progress = it.successRatio ?: 0
            seekBarValue.text = (it.successRatio ?: 0).toString()
        })
    }
}
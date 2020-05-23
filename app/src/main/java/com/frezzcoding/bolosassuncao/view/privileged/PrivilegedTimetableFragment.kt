package com.frezzcoding.bolosassuncao.view.privileged

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentTimetableBinding
import com.frezzcoding.bolosassuncao.di.PrivilegedInjection
import com.frezzcoding.bolosassuncao.models.Privileged
import com.frezzcoding.bolosassuncao.viewmodel.PrivilegedViewModel
import java.lang.Integer.parseInt

class PrivilegedTimetableFragment : Fragment() {

    private lateinit var binding : FragmentTimetableBinding
    private var monPressed = false
    private var tuePressed = false
    private var wedPressed = false
    private var thuPressed = false
    private var friPressed = false
    private var satPressed = false
    private var sunPressed = false
    private lateinit var viewModel : PrivilegedViewModel
    private var updated = false
    private val minuteChoices = arrayOf("00", "30")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_timetable, container, false
        )

        initializeViewModel()
        /*
        todo
        mysql database should either contain column for each week day and the time of start and end of shift
        store the timetable entries on room for caching, but if user submits new entry then send it to main db
         */

        setListeners()
        initializeTimePickers()
        viewModel.getPrivileged()
        return binding.root
    }

    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, PrivilegedInjection.provideViewModelFactory()).get(PrivilegedViewModel::class.java)
        viewModel.priv.observe(viewLifecycleOwner, getPrivData)
    }

    private val getPrivData = Observer<Privileged>{
        updateTimetableUI(it)
        updated = true
    }

    private fun updateTimetableUI(priv : Privileged){
        monPressed = priv.monday.toBoolean()
        tuePressed = priv.tuesday.toBoolean()
        wedPressed = priv.wednesday.toBoolean()
        thuPressed = priv.thursday.toBoolean()
        friPressed = priv.friday.toBoolean()
        satPressed = priv.saturday.toBoolean()
        sunPressed = priv.sunday.toBoolean()
        if(monPressed)
            binding.tvMonday.setBackgroundResource(R.drawable.ic_dayselected)
        if(tuePressed)
            binding.tvTuesday.setBackgroundResource(R.drawable.ic_dayselected)
        if(wedPressed)
            binding.tvWednesday.setBackgroundResource(R.drawable.ic_dayselected)
        if(thuPressed)
            binding.tvThursday.setBackgroundResource(R.drawable.ic_dayselected)
        if(friPressed)
            binding.tvFriday.setBackgroundResource(R.drawable.ic_dayselected)
        if(satPressed)
            binding.tvSaturday.setBackgroundResource(R.drawable.ic_dayselected)
        if(sunPressed)
            binding.tvSunday.setBackgroundResource(R.drawable.ic_dayselected)
        binding.timePickerStartHour.value = parseInt(priv.start_time.substring(0,2))
        binding.timePickerStartMin.value = parseInt(priv.start_time.substring(3,5))

        binding.timePickerEndHour.value = parseInt(priv.end_time.substring(0,2))
        binding.timePickerEndMin.value = parseInt(priv.end_time.substring(3,5))
    }

    private fun initializeTimePickers(){
        binding.timePickerStartHour.minValue = 0
        binding.timePickerStartHour.maxValue = 23
        binding.timePickerStartMin.minValue = 0
        binding.timePickerStartMin.maxValue = 1
        binding.timePickerStartMin.displayedValues = minuteChoices

        binding.timePickerEndHour.minValue = 0
        binding.timePickerEndHour.maxValue = 23
        binding.timePickerEndMin.minValue = 0
        binding.timePickerEndMin.maxValue = 1
        binding.timePickerEndMin.displayedValues = minuteChoices
    }

    private fun setListeners(){
        //state of date selected should be stored on viewmodel and result should be sent to the database
        binding.btnConfirm.setOnClickListener {
            viewModel.updateTimetable(Privileged(0, monPressed.toString(), tuePressed.toString(), wedPressed.toString(),
                thuPressed.toString(), friPressed.toString(), satPressed.toString(), sunPressed.toString(),
                (binding.timePickerStartHour.value.toString() + ":" + minuteChoices[binding.timePickerStartMin.value]),
                (binding.timePickerEndHour.value.toString() + ":" + minuteChoices[binding.timePickerEndMin.value])))
        }

        binding.tvMonday.setOnClickListener {
            if(monPressed) {
                binding.tvMonday.background = null
                monPressed = false
            }else {
                binding.tvMonday.setBackgroundResource(R.drawable.ic_dayselected)
                monPressed = true
            }
        }
        binding.tvTuesday.setOnClickListener {
            if(tuePressed) {
                binding.tvTuesday.background = null
                tuePressed = false
            }else {
                binding.tvTuesday.setBackgroundResource(R.drawable.ic_dayselected)
                tuePressed = true
            }
        }

        binding.tvWednesday.setOnClickListener {
            if(wedPressed) {
                binding.tvWednesday.background = null
                wedPressed = false
            }else {
                binding.tvWednesday.setBackgroundResource(R.drawable.ic_dayselected)
                wedPressed = true
            }
        }
        binding.tvThursday.setOnClickListener {
            if(thuPressed) {
                binding.tvThursday.background = null
                thuPressed = false
            }else {
                binding.tvThursday.setBackgroundResource(R.drawable.ic_dayselected)
                thuPressed = true
            }
        }
        binding.tvFriday.setOnClickListener {
            if(friPressed) {
                binding.tvFriday.background = null
                friPressed = false
            }else {
                binding.tvFriday.setBackgroundResource(R.drawable.ic_dayselected)
                friPressed = true
            }
        }
        binding.tvSaturday.setOnClickListener {
            if(satPressed) {
                binding.tvSaturday.background = null
                satPressed = false
            }else {
                binding.tvSaturday.setBackgroundResource(R.drawable.ic_dayselected)
                satPressed = true
            }
        }
        binding.tvSunday.setOnClickListener {
            if(sunPressed) {
                binding.tvSunday.background = null
                sunPressed = false
            }else {
                binding.tvSunday.setBackgroundResource(R.drawable.ic_dayselected)
                sunPressed = true
            }
        }


    }

}
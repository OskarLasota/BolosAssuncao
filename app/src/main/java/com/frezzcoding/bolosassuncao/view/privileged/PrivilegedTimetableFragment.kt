package com.frezzcoding.bolosassuncao.view.privileged

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentTimetableBinding

class PrivilegedTimetableFragment : Fragment() {

    private lateinit var binding : FragmentTimetableBinding
    private var monPressed = false
    private var tuePressed = false
    private var wedPressed = false
    private var thuPressed = false
    private var friPressed = false
    private var satPressed = false
    private var sunPressed = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_timetable, container, false
        )

        //initializeViewModel()
        /*
        todo
        mysql database should either contain column for each week day and the time of start and end of shift
        store the timetable entries on room for caching, but if user submits new entry then send it to main db
         */

        setListeners()
        initializeTimePickers()

        return binding.root
    }

    private fun initializeTimePickers(){
        val mins = arrayOf("0", "30")
        binding.timePickerStartHour.minValue = 0
        binding.timePickerStartHour.maxValue = 23
        binding.timePickerStartMin.minValue = 0
        binding.timePickerStartMin.maxValue = 1
        binding.timePickerStartMin.displayedValues = mins

        binding.timePickerEndHour.minValue = 0
        binding.timePickerEndHour.maxValue = 23
        binding.timePickerEndMin.minValue = 0
        binding.timePickerEndMin.maxValue = 1
        binding.timePickerEndMin.displayedValues = mins
    }

    private fun setListeners(){
        //state of date selected should be stored on viewmodel and result should be sent to the database
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
package com.frezzcoding.bolosassuncao.view.neutral

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentCollectionBinding
import com.frezzcoding.bolosassuncao.di.PrivilegedInjection
import com.frezzcoding.bolosassuncao.models.Privileged
import com.frezzcoding.bolosassuncao.utils.InputValidator
import com.frezzcoding.bolosassuncao.viewmodel.PrivilegedViewModel
import java.util.*

class NeutralCollectionFragment : Fragment(), InputValidator {

    private lateinit var binding : FragmentCollectionBinding
    private val SELECT_DATE = 2
    private val SELECT_TIME = 1
    private var TIME_SELECTED = false
    private var DATE_SELECTED = false
    private var PAYMENT_SELECTED = false
    private var MIN_NAME_LENGTH = 2
    private var inProcess = false
    private lateinit var viewModel : PrivilegedViewModel
    private var daysAvailable = emptyArray<String>()
    private var timesAvailable = emptyArray<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_collection, container, false
        )
        if(activity is NeutralUserActivity){
            (activity as NeutralUserActivity)?.hideBottombar(true)
        }
        setListeners()
        initializeViewModel()
        viewModel.getPrivileged()
        return binding.root

    }

    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, PrivilegedInjection.provideViewModelFactory()).get(PrivilegedViewModel::class.java)
        viewModel.priv.observe(viewLifecycleOwner, getPrivData)
    }

    private val getPrivData = Observer<Privileged>{
        daysAvailable = emptyArray()
        findAvailableDates(it)
        findAvailableTimes(it)
    }

    //todo if statements could be avoided by changing api response and storing in an array
    private fun findAvailableDates(it : Privileged){
        var dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        for(i in 1..7){
            c.add(Calendar.DATE, 1)
            dt = c.time
            if(dt.toString().substring(0,3) == "Mon"){
                if(it.monday == "true") {
                    daysAvailable = daysAvailable.plus(dt.toString().substring(0, 10))
                    continue
                }
            }
            if(dt.toString().substring(0,3) == "Tue"){
                if(it.tuesday == "true") {
                    daysAvailable = daysAvailable.plus(dt.toString().substring(0, 10))
                    continue
                }
            }
            if(dt.toString().substring(0,3) == "Wed"){
                if(it.wednesday == "true") {
                    daysAvailable = daysAvailable.plus(dt.toString().substring(0, 10))
                    continue
                }
            }
            if(dt.toString().substring(0,3) == "Thu"){
                if(it.thursday == "true") {
                    daysAvailable = daysAvailable.plus(dt.toString().substring(0, 10))
                    continue
                }
            }
            if(dt.toString().substring(0,3) == "Fri"){
                if(it.friday == "true") {
                    daysAvailable = daysAvailable.plus(dt.toString().substring(0, 10))
                    continue
                }
            }
            if(dt.toString().substring(0,3) == "Sat"){
                if(it.saturday == "true") {
                    daysAvailable = daysAvailable.plus(dt.toString().substring(0, 10))
                    continue
                }
            }
            if(dt.toString().substring(0,3) == "Sun"){
                if(it.sunday == "true") {
                    daysAvailable = daysAvailable.plus(dt.toString().substring(0, 10))
                    continue
                }
            }
        }
    }

    private fun findAvailableTimes(it : Privileged){
        var startHour = Integer.parseInt(it.start_time.substring(0,2))
        var startMin = Integer.parseInt(it.start_time.substring(3,5))

        var endHour = Integer.parseInt(it.end_time.substring(0,2))
        var endMin = Integer.parseInt(it.end_time.substring(3,5))

        var tempMin = ""

        while(startHour < endHour) {
            if(startHour == endHour && endMin == 30){
                timesAvailable = timesAvailable.plus("$startHour:$endMin")
            }
            if (startMin == 30) {
                startMin = 0
                tempMin = startMin.toString()+"0"
                startHour ++
            }else{
                startMin += 30
            }
            if(startMin == 0){
                timesAvailable = timesAvailable.plus("$startHour:$tempMin")
            }else {
                timesAvailable = timesAvailable.plus("$startHour:$startMin")
            }
        }


    }

    private fun showPopup(action : Int){
        var result: Array<String> = when(action){
            1 -> timesAvailable
            2 -> daysAvailable
            else -> return
        }
        var dialog = Dialog(context!!)
        dialog.setContentView(R.layout.popup_select_time)


        var builder = AlertDialog.Builder(this.context)
        builder.setSingleChoiceItems(result, -1){
            dialog: DialogInterface?, which: Int ->
            if(action==1){
                builder.setTitle("Select Delivery Time")
                binding.tvSelecttime.text = result[which] + " ▼"
                TIME_SELECTED = true
            }else{
                builder.setTitle("Select Delivery Day")
                binding.tvSelectdate.text = result[which] + " ▼"
                DATE_SELECTED = true
            }
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()


    }

    private fun setListeners(){
        binding.tvSelectdate.setOnClickListener {
            showPopup(SELECT_DATE)
        }
        binding.tvSelecttime.setOnClickListener {
            showPopup(SELECT_TIME)
        }
        binding.btnOrder.setOnClickListener {
            if(checkInputValidity() && !inProcess){
                inProcess=true
                //loading animation here + call the api with retrofit
            }
            inProcess = false
        }
        binding.etName.doAfterTextChanged {
            checkCurrentValidity("name")
        }
    }

    override fun checkCurrentValidity(resource: String) {
        when(resource){
            "name" -> if(binding.etName.text.toString().length >= MIN_NAME_LENGTH) {binding.tilName.error = null; }
        }
    }

    override fun checkInputValidity(): Boolean {
        if (binding.etName.text.toString().length < MIN_NAME_LENGTH) {
            binding.tilName.error = getString(R.string.product_name_error)
            return false
        }
        if(!TIME_SELECTED){
            var animation = AnimationUtils.loadAnimation(this.context, R.anim.shake)
            binding.tvSelecttime?.startAnimation(animation)
            return false
        }
        if(!DATE_SELECTED){
            var animation = AnimationUtils.loadAnimation(this.context, R.anim.shake)
            binding.tvSelectdate?.startAnimation(animation)
            return false
        }
        if(binding.radioGroup.checkedRadioButtonId < 0 ){
            Toast.makeText(this.context, "Select payment method", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

}
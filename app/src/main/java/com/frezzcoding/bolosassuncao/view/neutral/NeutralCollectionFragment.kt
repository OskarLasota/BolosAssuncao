package com.frezzcoding.bolosassuncao.view.neutral

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentCollectionBinding
import com.frezzcoding.bolosassuncao.utils.InputValidator

class NeutralCollectionFragment : Fragment(), InputValidator {

    private lateinit var binding : FragmentCollectionBinding
    private val SELECT_DATE = 2
    private val SELECT_TIME = 1
    private var TIME_SELECTED = false
    private var DATE_SELECTED = false
    private var PAYMENT_SELECTED = false
    private var MIN_NAME_LENGTH = 2
    private var inProcess = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_collection, container, false
        )
        if(activity is NeutralUserActivity){
            (activity as NeutralUserActivity)?.hideBottombar(true)
        }
        setListeners()

        return binding.root

    }

    //todo admin should be able to select which dates and times are available
    private fun getAvailableDates() : Array<String>{
        var result = arrayOf("")
        //this should be called from api (manager should provide which times are available)
        return result
    }

    private fun getAvailableTimes() : Array<String>{
        var result = arrayOf("")
        //this should be called from api (manager should provide which times are available)
        return result
    }

    private fun showPopup(action : Int){
        var result: Array<String> = when(action){
            1 -> getAvailableTimes()
            2 -> getAvailableDates()
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
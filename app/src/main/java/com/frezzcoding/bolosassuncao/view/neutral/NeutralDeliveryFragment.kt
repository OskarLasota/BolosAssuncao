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
import com.frezzcoding.bolosassuncao.databinding.FragmentDeliveryBinding
import com.frezzcoding.bolosassuncao.utils.InputValidator

class NeutralDeliveryFragment : Fragment(), InputValidator {

    private lateinit var binding : FragmentDeliveryBinding
    private val MIN_NAME_LENGTH = 2
    private val MIN_NUMBER_LENGTH = 8
    private val MIN_ADDRESS_LENGTH = 6
    private val POSTCODE_LENGTH = 6
    private var inProcess = false
    private var TIME_SELECTED = false
    private var DATE_SELECTED = false
    private val SELECT_DATE = 2
    private val SELECT_TIME = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_delivery, container, false
        )
        if(activity is NeutralUserActivity){
            (activity as NeutralUserActivity)?.hideBottombar(true)
        }

        //call api to create an order in the main database ( not local database ) & remove all basket items

        setObservers()

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



    override fun checkCurrentValidity(resource: String) {
        when(resource){
            "name" -> if(binding.etName.text.toString().length >= MIN_NAME_LENGTH) {binding.tilName.error = null; }
            "number" -> if(binding.etMobile.text.toString().length >= MIN_NUMBER_LENGTH) {binding.tilMobile.error = null; }
            "address" -> if(binding.etAddress1.text.toString().length >= MIN_ADDRESS_LENGTH) {binding.tilAddress1.error = null; }
            "postcode" -> if(binding.etPostcode.text.toString().length != POSTCODE_LENGTH) {binding.tilPostcode.error = null; }
        }
    }

    override fun checkInputValidity(): Boolean {

        if (binding.etName.text.toString().length < MIN_NAME_LENGTH) {
            binding.tilName.error = getString(R.string.product_name_error)
            return false
        }
        if(binding.etMobile.text.toString().length < MIN_NUMBER_LENGTH){
            binding.tilMobile.error = getString(R.string.incorrect_mobile)
            return false
        }
        if(binding.etAddress1.text.toString().length < MIN_ADDRESS_LENGTH){
            binding.tilAddress1.error = getString(R.string.incorrect_address)
            return false
        }
        if(binding.etPostcode.text.toString().length != POSTCODE_LENGTH){
            binding.tilPostcode.error = getString(R.string.incorrect_postcode )
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
        if(binding.radioGrp.checkedRadioButtonId < 0 ){
            Toast.makeText(this.context, "Select payment method", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }



    private fun setObservers(){
        binding.btnOrderdelivery.setOnClickListener {
            if(checkInputValidity() && inProcess){
                inProcess = true
            }
            inProcess = false
        }
        binding.tvSelectdate.setOnClickListener {
            showPopup(SELECT_DATE)
        }
        binding.tvSelecttime.setOnClickListener {
            showPopup(SELECT_TIME)
        }

        binding.etName.doAfterTextChanged {
            checkCurrentValidity("name")
        }
        binding.etMobile.doAfterTextChanged{
            checkCurrentValidity("number")
        }
        binding.etAddress1.doAfterTextChanged {
            checkCurrentValidity("address")
        }
        binding.etPostcode.doAfterTextChanged {
            checkCurrentValidity("postcode")
        }
    }

}
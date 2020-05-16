package com.frezzcoding.bolosassuncao.view.neutral

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentDeliveryBinding
import com.frezzcoding.bolosassuncao.utils.InputValidator
import retrofit2.http.POST

class NeutralDeliveryFragment : Fragment(), InputValidator {

    private lateinit var binding : FragmentDeliveryBinding
    private val MIN_NAME_LENGTH = 2
    private val MIN_NUMBER_LENGTH = 8
    private val MIN_ADDRESS_LENGTH = 6
    private val POSTCODE_LENGTH = 6
    private var inProcess = false

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

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkCurrentValidity("name")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        binding.etMobile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkCurrentValidity("number")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        binding.etAddress1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkCurrentValidity("address")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        binding.etPostcode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkCurrentValidity("postcode")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

}
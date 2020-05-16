package com.frezzcoding.bolosassuncao.view.neutral

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentCollectionBinding
import com.frezzcoding.bolosassuncao.utils.InputValidator

class NeutralCollectionFragment : Fragment(), InputValidator {

    private lateinit var binding : FragmentCollectionBinding
    private val SELECT_DATE = 1
    private val SELECT_TIME = 2
    private var TIME_SELECTED = false
    private var DATE_SELECTED = false
    private var PAYMENT_SELECTED = false
    private var MIN_NAME_LENGTH = 2


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
        builder.setTitle("Select Delivery Day")
        builder.setSingleChoiceItems(result, -1){
            dialog: DialogInterface?, which: Int ->
            binding.tvSelectdate.text = result[which] + " ▼"
            if(action==1){
                TIME_SELECTED = true
            }else{
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
            checkInputValidity()
        }
    }

    override fun checkCurrentValidity(resource: String) {
        TODO("Not yet implemented")
    }

    override fun checkInputValidity(): Boolean {
        if (binding.etName.text.toString().length < MIN_NAME_LENGTH) {
            binding.tilName.error = getString(R.string.product_name_error)
            return false
        }
        if(!DATE_SELECTED){
            var animation = AnimationUtils.loadAnimation(this.context, R.anim.shake)
            binding.tvSelectdate?.startAnimation(animation)
            return false
        }

        return true
    }

}
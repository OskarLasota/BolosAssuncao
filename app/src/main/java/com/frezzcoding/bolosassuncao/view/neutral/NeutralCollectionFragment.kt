package com.frezzcoding.bolosassuncao.view.neutral

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return result
    }

    private fun getAvailableTimes() : Array<String>{
        var result = arrayOf("")
        return result
    }

    private fun showPopup(action : Int){
        var result = arrayOf("")
        when(action){
            1 -> result = getAvailableTimes()
            2 -> result = getAvailableDates()
            else -> return
        }
        var dialog = Dialog(context!!)
        dialog.setContentView(R.layout.popup_select_time)


        var builder = AlertDialog.Builder(this.context)
        builder.setTitle("Select Delivery Day")
        builder.setSingleChoiceItems(result, -1){
            dialog: DialogInterface?, which: Int ->
            binding.tvSelectdate.text = result[which] + " ▼"
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
    }

    override fun checkCurrentValidity(resource: String) {
        TODO("Not yet implemented")
    }

    override fun checkInputValidity(): Boolean {
        TODO("Not yet implemented")
    }

}
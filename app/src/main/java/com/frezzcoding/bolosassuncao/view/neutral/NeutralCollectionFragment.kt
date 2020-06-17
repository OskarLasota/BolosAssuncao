package com.frezzcoding.bolosassuncao.view.neutral

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentCollectionBinding
import com.frezzcoding.bolosassuncao.di.OrderInjection
import com.frezzcoding.bolosassuncao.di.PrivilegedInjection
import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.models.Privileged
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.models.User
import com.frezzcoding.bolosassuncao.utils.InputValidator
import com.frezzcoding.bolosassuncao.utils.NetworkChecker
import com.frezzcoding.bolosassuncao.viewmodel.CachingViewModel
import com.frezzcoding.bolosassuncao.viewmodel.OrderViewModel
import com.frezzcoding.bolosassuncao.viewmodel.PrivilegedViewModel
import maes.tech.intentanim.CustomIntent
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
    private lateinit var productList : List<Product>
    private lateinit var cachingViewModel : CachingViewModel
    private lateinit var orderViewModel : OrderViewModel
    private lateinit var currentUser : User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_collection, container, false
        )
        if(activity is NeutralUserActivity){
            (activity as NeutralUserActivity)?.hideBottombar(true)
        }
        if(arguments!!.get("products") != null) {
            productList = arguments!!.get("products") as List<Product>
        }else{
            //handle miss communication
        }

        if(!NetworkChecker.isNetworkAvailable(this.context!!)){
            Toast.makeText(this.context, "Por favor verifique sua conexao", Toast.LENGTH_SHORT).show()
        }

        setListeners()
        initializeViewModel()
        viewModel.getPrivileged()
        return binding.root

    }

    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, PrivilegedInjection.provideViewModelFactory()).get(PrivilegedViewModel::class.java)
        viewModel.priv.observe(viewLifecycleOwner, getPrivData)
        cachingViewModel = ViewModelProvider.AndroidViewModelFactory(activity!!.application).create(
            CachingViewModel(activity!!.application).javaClass)
        cachingViewModel.init()
        cachingViewModel.user.observe(viewLifecycleOwner, getCachedUser)

        orderViewModel = ViewModelProvider(this, OrderInjection.provideViewModelFactory()).get(
            OrderViewModel::class.java)
        orderViewModel.upload.observe(viewLifecycleOwner, getUploadStatus)
        orderViewModel.productupload.observe(viewLifecycleOwner, getProductUploadStatus)
    }

    private val getProductUploadStatus = Observer<Boolean>{
        //animation here
        Navigation.findNavController(binding.root).navigate(R.id.destination_ordersuccess)
        CustomIntent.customType(this.context, "fadein-to-fadeout")
    }

    private val getUploadStatus = Observer<Int>{
        for(element in productList){
            orderViewModel.upload(element.id, it)
        }
    }

    private val getCachedUser = Observer<User>{
        currentUser = it
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
        val startMin = Integer.parseInt(it.start_time.substring(3,5))

        val endHour = Integer.parseInt(it.end_time.substring(0,2))
        val endMin = Integer.parseInt(it.end_time.substring(3,5))

        var tempMin = startMin.toString() + "0"

        while(startHour+1 <= endHour) {
            if(startHour+1 == endHour){
                if(tempMin == "30" && (endMin.toString()+"0") == "00"){
                    break
                }
            }
            if(tempMin == "00"){
                timesAvailable = timesAvailable.plus("$startHour:$tempMin - ${startHour + 1}:$tempMin")
            }else {
                timesAvailable = timesAvailable.plus("$startHour:$tempMin - ${startHour + 1}:$tempMin")
            }

            if (tempMin == "30") {
                tempMin = "00"
                startHour ++
            }else{
                tempMin = "30"
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
                builder.setTitle("Selecione a hora de entrega")
                binding.tvSelecttime.text = result[which] + " ▼"
                TIME_SELECTED = true
            }else{
                builder.setTitle("Selecione a data de entrega")
                binding.tvSelectdate.text = result[which] + " ▼"
                DATE_SELECTED = true
            }
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()


    }

    private fun setListeners(){
        binding.btnOrder.setOnClickListener {
            if(checkInputValidity() && !inProcess){
                inProcess = true
                var sum = 0.0
                for(element in productList){
                    sum += element.price
                }
                var payment_type = "Cash On Delivery"
                if(binding.radioOne.isSelected){
                    payment_type = "Debit card"
                }
                if(binding.radioThree.isSelected){
                    payment_type = "Transferência"
                }
                orderViewModel.upload(
                    Order(0, currentUser.id, sum, binding.etName.text.toString(), binding.tvSelecttime.text.toString().substring(0,5),
                        binding.tvSelectdate.text.toString().substring(0,10),
                        binding.etMobile.text.toString(), "Retirar",
                        payment_type, "Pendente")
                )


            }
            inProcess = false
        }

        binding.radioThree.setOnClickListener {
            showTransferPopup()
        }

        binding.etMobile.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(binding.etMobile.text.toString().length == 4 && keyCode == 67){
                    return true
                }
                return false
            }
        })


        binding.tvSelectdate.setOnClickListener {
            showPopup(SELECT_DATE)
        }
        binding.tvSelecttime.setOnClickListener {
            showPopup(SELECT_TIME)
        }
        binding.etName.doAfterTextChanged {
            checkCurrentValidity("name")
        }
    }

    private fun showTransferPopup(){
        var dialog = Dialog(context!!)
        dialog.setContentView(R.layout.popup_transfer_info)


        dialog.show()
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
            Toast.makeText(this.context, "Selecione forma de pagamento", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

}
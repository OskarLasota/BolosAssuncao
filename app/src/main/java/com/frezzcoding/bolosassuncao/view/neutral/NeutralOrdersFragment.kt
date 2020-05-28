import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.adapters.OrderViewAdapter
import com.frezzcoding.bolosassuncao.databinding.FragmentOrdersBinding
import com.frezzcoding.bolosassuncao.di.OrderInjection
import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.models.User
import com.frezzcoding.bolosassuncao.utils.NetworkChecker
import com.frezzcoding.bolosassuncao.viewmodel.CachingViewModel
import com.frezzcoding.bolosassuncao.viewmodel.OrderViewModel


class NeutralOrdersFragment : Fragment() {

    private lateinit var viewModel : OrderViewModel
    private lateinit var cachingViewModel : CachingViewModel
    private lateinit var binding : FragmentOrdersBinding
    private lateinit var adapterProduct : OrderViewAdapter
    private lateinit var currentUser : User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_orders, container, false
        )
        if(!NetworkChecker.isNetworkAvailable(this.context!!)){
            Toast.makeText(this.context, "Please check your internet connection", Toast.LENGTH_SHORT).show()
        }
        initializeViewModel()
        viewModel.getOrders()

        return binding.root
    }

    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, OrderInjection.provideViewModelFactory()).get(OrderViewModel::class.java)
        viewModel.orders.observe(viewLifecycleOwner, renderOrders)
        viewModel.deleted.observe(viewLifecycleOwner, refreshList)
        cachingViewModel = ViewModelProvider.AndroidViewModelFactory(activity!!.application).create(
            CachingViewModel(activity!!.application).javaClass)
        cachingViewModel.init()
        cachingViewModel.user.observe(viewLifecycleOwner, getCachedUser)
    }

    private val refreshList = Observer<Int>{
        if(it == 1 ){
            viewModel.getOrders()
        }
    }

    private val getCachedUser = Observer<User>{
        currentUser = it
        viewModel.getOrders()
    }

    private val renderOrders = Observer<ArrayList<Order>>{
        var list : List<Order> = mutableListOf()
        for(element in it){
            if(element.user_id == currentUser.id){
                list = list.plus(element)
            }
        }
        println(list.size)
        ///productList = it
        adapterProduct = OrderViewAdapter(list, viewModel)
        binding.ordersRecycler.layoutManager = GridLayoutManager(this.requireContext(), 1)
        binding.ordersRecycler.adapter = adapterProduct
        //println(it[0].customer_name)
        //make another call to get all products for that order
    }

}


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.adapters.ViewAdapter
import com.frezzcoding.bolosassuncao.databinding.FragmentHomeBinding
import com.frezzcoding.bolosassuncao.databinding.FragmentSettingsBinding
import com.frezzcoding.bolosassuncao.di.ProductInjection
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.viewmodel.ProductViewModel


class HomeFragment : Fragment(), ViewAdapter.OnItemClickListener {

    private lateinit var _view : View
    private lateinit var viewModel : ProductViewModel
    private lateinit var adapter : ViewAdapter
    private var productList : ArrayList<Product> = ArrayList<Product>()
    private lateinit var binding : FragmentHomeBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        initializeViewModel()
        viewModel.getProducts()

        //todo home fragment should carry out other api calls

        return binding.root
    }

    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, ProductInjection.provideViewModelFactory()).get(ProductViewModel::class.java)
        viewModel.products.observe(viewLifecycleOwner, renderProducts)
    }

    private val renderProducts = Observer<ArrayList<Product>>{
        productList = it
        adapter = ViewAdapter(productList, this)
        binding.recyclerView.layoutManager = GridLayoutManager(this.requireContext(), 2)
        binding.recyclerView.adapter = adapter
    }

    override fun onItemClick(product: Product) {
        Toast.makeText(this.context, "product clicked", Toast.LENGTH_SHORT).show()
    }


}

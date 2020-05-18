import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.adapters.ProductViewAdapter
import com.frezzcoding.bolosassuncao.databinding.FragmentSettingsBinding
import com.frezzcoding.bolosassuncao.di.ProductInjection
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.viewmodel.ProductViewModel


class ManageProductsFragment : Fragment() , ProductViewAdapter.OnItemClickListener {

    private lateinit var viewModel : ProductViewModel
    private lateinit var adapterProduct : ProductViewAdapter
    private var productList : ArrayList<Product> = ArrayList()
    private lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings, container, false
        )

        initializeLiteners()
        initializeViewModel()
        viewModel.getProducts()

        return binding.root
    }


    private val renderProducts = Observer<ArrayList<Product>>{
        productList = it
        adapterProduct = ProductViewAdapter(productList, this)
        binding.recyclerView.layoutManager = GridLayoutManager(this.requireContext(), 2)
        binding.recyclerView.adapter = adapterProduct
    }

    private fun initializeLiteners(){
        binding.floatingButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.destination_add)
        }
    }


    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, ProductInjection.provideViewModelFactory()).get(ProductViewModel::class.java)
        viewModel.products.observe(viewLifecycleOwner, renderProducts)
    }

    override fun onItemClick(product: Product) {
        var bundle = bundleOf("product" to product)
        Navigation.findNavController(binding.root).navigate(R.id.destination_editproduct, bundle)
    }
}
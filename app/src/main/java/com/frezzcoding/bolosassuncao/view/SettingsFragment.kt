import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.adapters.ViewAdapter
import com.frezzcoding.bolosassuncao.databinding.FragmentSettingsBinding
import com.frezzcoding.bolosassuncao.di.Injection
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.view.EditProductFragment
import com.frezzcoding.bolosassuncao.viewmodel.ProductViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SettingsFragment : Fragment() , ViewAdapter.OnItemClickListener {

    private lateinit var viewModel : ProductViewModel
    private lateinit var adapter : ViewAdapter
    private var productList : ArrayList<Product> = ArrayList<Product>()
    private lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings, container, false
        )

        initializeLiteners();
        //todo this fragment is exclusive for the privilaged users, but com.frezzcoding.bolosassuncao.viewmodel should be reused for normal users and retrieval of images
        //todo the images will be stored in a Room database to allow caching as well as reducing amount of http requests made
        //todo the user should see all the stored pictures and be able to edit the pictures as well as the description and details
        //todo this application is aimed at a bakery company
        //todo on new object create or update, the api as well as the database should be updated.
        initializeViewModel()
        viewModel.getProducts()

        return binding.root
    }


    private val renderProducts = Observer<ArrayList<Product>>{
        productList = it
        adapter = ViewAdapter(productList, this)
        binding.recyclerView.layoutManager = GridLayoutManager(this.requireContext(), 2)
        binding.recyclerView.adapter = adapter
    }

    private fun initializeLiteners(){
        binding.floatingButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.destination_add)
        }
    }


    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(ProductViewModel::class.java)
        viewModel.products.observe(viewLifecycleOwner, renderProducts)
    }

    override fun onItemClick(product: Product) {
        var bundle = bundleOf("product" to product)
        Navigation.findNavController(binding.root).navigate(R.id.destination_edit, bundle)
    }



}
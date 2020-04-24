import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.adapters.ViewAdapter
import com.frezzcoding.bolosassuncao.di.Injection
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.viewmodel.ProductViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SettingsFragment : Fragment() {

    private lateinit var _view : View
    private lateinit var viewModel : ProductViewModel
    private lateinit var btnUpload : Button
    private lateinit var floating : FloatingActionButton
    private lateinit var adapter : ViewAdapter
    private var productList : ArrayList<Product> = ArrayList<Product>()
    private lateinit var recycler : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view =  inflater.inflate(R.layout.fragment_settings, container, false)

        initializeView();
        //todo create a com.frezzcoding.bolosassuncao.viewmodel that will connect to the api using retrofit to upload images and obtain images from the server
        //todo this fragment is exclusive for the privilaged users, but com.frezzcoding.bolosassuncao.viewmodel should be reused for normal users and retrieval of images
        //todo the images will be stored in a Room database to allow caching as well as reducing amount of http requests made
        //todo the user should see all the stored pictures and be able to edit the pictures as well as the description and details
        //todo this application is aimed at a bakery company

        //todo design
        //todo design should be a cardview 3x3 or 2x2
        //todo each card should be pressable which will navigate to a new fragment, edit and create fragment can be identical
        //todo on new object create or update, the api as well as the database should be updated.
        initializeViewModel()
        viewModel.getProducts()

        return _view;
    }


    private val renderProducts = Observer<ArrayList<Product>>{
        productList = it
        initializeView()
    }

    private fun initializeView(){
        recycler = _view.findViewById(R.id.recycler_view)
        floating = _view.findViewById(R.id.floating_button)
        floating.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.destination_update)
        }
        //set up adapter here
        adapter = ViewAdapter(requireContext(), productList)
        recycler.layoutManager = GridLayoutManager(this.requireContext(), 2)
        recycler.adapter = adapter
    }




    private fun initializeViewModel(){
        //set com.frezzcoding.bolosassuncao.viewmodel with factory
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(ProductViewModel::class.java)
        //set observers
        viewModel.products.observe(viewLifecycleOwner, renderProducts)
    }



}
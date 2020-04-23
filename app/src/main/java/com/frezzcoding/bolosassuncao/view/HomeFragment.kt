
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frezzcoding.bolosassuncao.R



class HomeFragment : Fragment() {

    private lateinit var _view : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?): View? {
        _view =  inflater.inflate(R.layout.fragment_home, container, false)


        //todo home fragment should carry out other api calls

        return _view
    }

}

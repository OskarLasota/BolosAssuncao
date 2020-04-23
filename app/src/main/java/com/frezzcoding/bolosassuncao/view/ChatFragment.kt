import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.frezzcoding.bolosassuncao.R


class ChatFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        button_home.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.next_action)
        }

        arguments?.let {
            val safeArgs = PhotosFragmentArgs.fromBundle(it)
            textView_num.text = "Number of photos: ${safeArgs.numOfPhotos}"
        }

         */
    }

}

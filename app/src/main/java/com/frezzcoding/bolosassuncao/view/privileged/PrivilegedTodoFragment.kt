package com.frezzcoding.bolosassuncao.view.privileged

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentTodoBinding

class PrivilegedTodoFragment : Fragment() {
    private lateinit var binding : FragmentTodoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_todo, container, false
        )

        //initializeViewModel()
        /*
        todo
        this fragment will present the products that have a due date of the current day to inform the baker
        which cakes need to be baked
         */


        return binding.root
    }
}
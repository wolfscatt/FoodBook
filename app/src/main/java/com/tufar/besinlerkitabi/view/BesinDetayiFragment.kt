package com.tufar.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tufar.besinlerkitabi.R
import com.tufar.besinlerkitabi.databinding.FragmentBesinDetayiBinding
import com.tufar.besinlerkitabi.utilities.gorselIndir
import com.tufar.besinlerkitabi.utilities.placeHolderYap
import com.tufar.besinlerkitabi.viewmodel.BesinDetayiViewModel
import kotlinx.android.synthetic.main.fragment_besin_detayi.*


class BesinDetayiFragment : Fragment() {

    private lateinit var dataBinding : FragmentBesinDetayiBinding
    private lateinit var viewModel : BesinDetayiViewModel
    private var besinId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_besin_detayi,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            besinId = BesinDetayiFragmentArgs.fromBundle(it).besinId

        }

        viewModel = ViewModelProvider(this).get(BesinDetayiViewModel::class.java)
        viewModel.roomVerisiniAl(besinId)


        observeLiveData()
    }

    fun observeLiveData(){

        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin ->
            besin?.let {

                dataBinding.secilenBesin = it

                /*
                besinIsim.text = it.besinIsim
                besinKalori.text = it.besinKalori
                besinKarbonhidrat.text = it.besinKarbonhidrat
                besinProtein.text = it.besinProtein
                besinYag.text = it.besinYag
                context?.let { besinImage.gorselIndir(besin.besinGorsel, placeHolderYap(it)) }
                 */
            }
        })
    }
}
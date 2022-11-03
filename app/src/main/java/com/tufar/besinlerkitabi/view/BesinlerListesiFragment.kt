package com.tufar.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tufar.besinlerkitabi.R
import com.tufar.besinlerkitabi.adapter.BesinRecyclerAdapter
import com.tufar.besinlerkitabi.viewmodel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besinler_listesi.*


class BesinlerListesiFragment : Fragment() {

    private lateinit var viewModel : BesinListesiViewModel
    private val recyclerBesinAdapter = BesinRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besinler_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(BesinListesiViewModel::class.java)
        viewModel.refreshData()



        besinListRecycler.layoutManager = LinearLayoutManager(context)
        besinListRecycler.adapter = recyclerBesinAdapter

        swipeRefreshLayout.setOnRefreshListener {
            besinLoading.visibility = View.VISIBLE
            besinHataText.visibility = View.GONE
            besinListRecycler.visibility = View.GONE
            viewModel.refreshFromInternet()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    fun observeLiveData(){

        viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler ->
            besinler?.let{

                besinListRecycler.visibility = View.VISIBLE
                recyclerBesinAdapter.besinListesiniGuncelle(besinler)
            }
        })

        viewModel.besinHataMessage.observe(viewLifecycleOwner,Observer{ hata ->
            hata?.let {
                if (it){
                    besinListRecycler.visibility = View.GONE
                    besinHataText.visibility = View.VISIBLE
                }
                else{
                    besinHataText.visibility = View.GONE
                }
            }
        })

        viewModel.besinYukleniyor.observe(viewLifecycleOwner,Observer{yukleniyor ->
            yukleniyor?.let {
                if (it){
                    besinListRecycler.visibility = View.GONE
                    besinHataText.visibility = View.GONE
                    besinLoading.visibility = View.VISIBLE
                }
                else{
                    besinLoading.visibility = View.GONE
                }
            }
        })
    }
}
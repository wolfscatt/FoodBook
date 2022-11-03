package com.tufar.besinlerkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tufar.besinlerkitabi.R
import com.tufar.besinlerkitabi.databinding.BesinRecyclerRowBinding
import com.tufar.besinlerkitabi.model.Besin
import com.tufar.besinlerkitabi.utilities.gorselIndir
import com.tufar.besinlerkitabi.utilities.placeHolderYap
import com.tufar.besinlerkitabi.view.BesinlerListesiFragmentDirections
import kotlinx.android.synthetic.main.besin_recycler_row.view.*

class BesinRecyclerAdapter(val besinListesi : ArrayList<Besin> ) : RecyclerView.Adapter<BesinViewHolder>() , BesinClickListener{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.besin_recycler_row,parent,false)
        val view = DataBindingUtil.inflate<BesinRecyclerRowBinding>(inflater,R.layout.besin_recycler_row,parent,false)
        return BesinViewHolder(view)
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {

        holder.view.besin = besinListesi[position]
        holder.view.listener = this
        /*
        holder.itemView.isim.text = besinListesi.get(position).besinIsim
        holder.itemView.kalori.text = besinListesi.get(position).besinKalori
        // Görsel kısmı eklenecek -Glide ile
        holder.itemView.imageView.gorselIndir(besinListesi.get(position).besinGorsel, placeHolderYap(holder.itemView.context))

        holder.itemView.setOnClickListener {
            val action = BesinlerListesiFragmentDirections.actionBesinlerListesiFragmentToBesinDetayiFragment(besinListesi.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
         */
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    fun besinListesiniGuncelle(yeniBesinListesi : List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }

    override fun besinTiklandi(view: View) {
        val uuid = view.besin_uuid.text.toString().toIntOrNull()
        uuid?.let {
            val action = BesinlerListesiFragmentDirections.actionBesinlerListesiFragmentToBesinDetayiFragment(it)
            Navigation.findNavController(view).navigate(action)
        }
    }
}
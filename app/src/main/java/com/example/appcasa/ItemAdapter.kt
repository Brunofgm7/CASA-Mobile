package com.example.appcasa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(val listaItem: List<Item>) : RecyclerView.Adapter<ItemAdapter.ExampleViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row, parent, false)

        return ExampleViewHolder(itemView)
    }

    override fun getItemCount(): Int = listaItem.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = listaItem[position]

        holder.textViewNome.text = ("Bruno Moreira")
        holder.textViewNrRefeicoes.text = (currentItem.numero + " refeições")
        holder.textViewStock.text = currentItem.stock

        holder.itemView.setOnClickListener{

            val bundle = Bundle()
            bundle.putString("itemId", currentItem.id)
            val fragment = DetalhesItemFragment()
            fragment.arguments = bundle

            val manager: FragmentManager = (it.context as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNome: TextView = itemView.findViewById(R.id.textViewFormulario)
        val textViewNrRefeicoes: TextView = itemView.findViewById(R.id.textViewNrRefeicoes)
        val textViewStock: TextView = itemView.findViewById(R.id.textViewStock)
    }
}


package com.example.appcasa

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestTercoFragment : Fragment() {

    private val listaItem = ArrayList<Item>()
    private lateinit var itemAdapter: ItemAdapter
    lateinit var recyclerView: RecyclerView
    var listaFiltradaItem: MutableList<Item> = ArrayList()
    lateinit var botaoAdicionarItem: Button
    lateinit var adicionarItemFragment: AdicionarItemFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_rest_terco, container, false)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.show()
        val colorDrawable = ColorDrawable(Color.parseColor("#21AEFF"))
        actionBar?.setBackgroundDrawable(colorDrawable)

        val toolbar = (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.my_toolbar)
        val textViewNomeFragment = toolbar.findViewById<TextView>(R.id.textViewNomeFragment)
        textViewNomeFragment.isVisible = true
        textViewNomeFragment.text = "Terço"

        toolbar.setNavigationOnClickListener {
            fragmentManager?.popBackStack()
        }

        adicionarItemFragment = AdicionarItemFragment()

        botaoAdicionarItem = view.findViewById(R.id.botaoAdicionarItem)
        botaoAdicionarItem.setOnClickListener {
            mudarFragment(adicionarItemFragment)
        }

        recyclerView = view.findViewById(R.id.recyclerViewItensTerco)

        listaFiltradaItem.clear()
        carregarItens()

        return view
    }

    private fun carregarItens(){

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        itemAdapter = ItemAdapter(listaItem)

        val clientesService = ServiceBuilder.buildService(EndPointsService::class.java)

        val requestCall = clientesService.getItem()

        requestCall.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    val itemList = response.body()!!
                    for(i in itemList){
                        if(i.name == "Terço"){
                            listaFiltradaItem.add(i)
                            recyclerView.adapter = ItemAdapter(listaFiltradaItem)
                        }
                    }

                } else {
                    Toast.makeText(activity, "Erro a carregar os itens", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Toast.makeText(activity, "Erro: $t", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun mudarFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .addToBackStack(null)
            .commit()
    }

}
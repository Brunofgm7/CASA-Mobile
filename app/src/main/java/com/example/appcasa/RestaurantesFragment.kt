package com.example.appcasa

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class RestaurantesFragment : Fragment() {

    lateinit var botaoBaixaDoPorto: RelativeLayout
    lateinit var botaoTerco: RelativeLayout
    lateinit var botaoHospital: RelativeLayout
    lateinit var restBaixaFragment: RestBaixaFragment
    lateinit var restTercoFragment: RestTercoFragment
    lateinit var restHospitalFragment: RestJuFragment
    lateinit var perfilFragment: PerfilFragment
    lateinit var botaoPerfil: Button
    lateinit var loginFragment: LoginFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_restaurantes, container, false)

        //esconder a action bar
        (activity as AppCompatActivity).supportActionBar?.hide()

        restBaixaFragment = RestBaixaFragment()
        restTercoFragment = RestTercoFragment()
        restHospitalFragment = RestJuFragment()
        perfilFragment = PerfilFragment()
        loginFragment = LoginFragment()

        botaoBaixaDoPorto = view.findViewById(R.id.botaoBaixaDoPorto)
        botaoBaixaDoPorto.setOnClickListener {
            val preferences: SharedPreferences = requireActivity().getSharedPreferences("restaurante", MODE_PRIVATE)
            preferences.edit().putString("restaurante", "Baixa").apply()
            mudarFragment(restBaixaFragment)
        }

        botaoTerco = view.findViewById(R.id.botaoTerco)
        botaoTerco.setOnClickListener {
            val preferences: SharedPreferences = requireActivity().getSharedPreferences("restaurante", MODE_PRIVATE)
            preferences.edit().putString("restaurante", "Terço").apply()
            mudarFragment(restTercoFragment)
        }

        botaoHospital = view.findViewById(R.id.botaoHospital)
        botaoHospital.setOnClickListener {
            val preferences: SharedPreferences = requireActivity().getSharedPreferences("restaurante", MODE_PRIVATE)
            preferences.edit().putString("restaurante", "Ju").apply()
            mudarFragment(restHospitalFragment)
        }

        botaoPerfil = view.findViewById(R.id.botaoPerfil)
        botaoPerfil.setOnClickListener {
            mudarFragment(perfilFragment)
        }

        return view
    }

    private fun mudarFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .addToBackStack(null)
            .commit()
    }

}
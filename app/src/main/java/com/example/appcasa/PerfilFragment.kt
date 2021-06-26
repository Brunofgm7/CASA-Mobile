package com.example.appcasa

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilFragment : Fragment() {

    lateinit var botaoLogout: Button
    lateinit var loginFragment: LoginFragment
    lateinit var editTextNickname: EditText
    lateinit var editTextEmail: EditText
    lateinit var editTextResidence: EditText
    lateinit var editTextPhone: EditText
    lateinit var botaoChangePassword: Button
    lateinit var changePasswordFragment: Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        carregarPerfil()

        loginFragment = LoginFragment()

        editTextNickname = view.findViewById(R.id.editTextNickname)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextResidence = view.findViewById(R.id.editTextResidence)
        editTextPhone = view.findViewById(R.id.editTextPhone)
        changePasswordFragment = ChangePasswordFragment()

        botaoChangePassword = view.findViewById(R.id.botaoChangePassword)
        botaoChangePassword.setOnClickListener {
            mudarFragment(changePasswordFragment)
        }

        botaoLogout = view.findViewById(R.id.botaoLogout)
        botaoLogout.setOnClickListener {
            val preferences: SharedPreferences = activity!!.getSharedPreferences("token", Context.MODE_PRIVATE)
            preferences.edit().remove("token").apply()

            val email: SharedPreferences = activity!!.getSharedPreferences("email", Context.MODE_PRIVATE)
            email.edit().remove("email").apply()

            val count: Int =  requireActivity().supportFragmentManager.backStackEntryCount

            for (i in 0 until count) {
                requireActivity().supportFragmentManager.popBackStack()
            }

            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, loginFragment)
                .commit()


        }

        return view
    }

    private fun carregarPerfil() {

        val email = activity!!.getSharedPreferences("email", AppCompatActivity.MODE_PRIVATE)
            .getString("email", null).toString()

        val destinationService = ServiceBuilder.buildService(EndPointsService::class.java)
        val requestCall = destinationService.getProfile(email)

        requestCall.enqueue(object: Callback<List<ProfileInfo>> {

            override fun onResponse(call: Call<List<ProfileInfo>>, response: Response<List<ProfileInfo>>) {
                if (response.isSuccessful) {
                    val profileResponse = response.body()
                    if (profileResponse != null) {
                        for (i in profileResponse) {
                            editTextNickname.setText(i.nickname)
                            editTextEmail.setText(i.email)
                            editTextResidence.setText(i.residence)
                            editTextPhone.setText(i.phone)
                        }
                    }
                } else {
                    Toast.makeText(activity, "Erro a carregar o perfil", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ProfileInfo>>, t: Throwable) {
                Toast.makeText(activity, "Erro a carregar o perfil: $t", Toast.LENGTH_SHORT).show()
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
package com.example.appcasa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordFragment : Fragment() {

    lateinit var editTextPassword: TextInputEditText
    lateinit var editTextRepeatPassword: TextInputEditText
    lateinit var buttonAtualizarPassword: Button
    lateinit var perfilFragment: PerfilFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_change_password, container, false)

        perfilFragment = PerfilFragment()
        editTextPassword = view.findViewById(R.id.editTextPassword)
        editTextRepeatPassword = view.findViewById(R.id.editTextRepeatPassword)

        buttonAtualizarPassword = view.findViewById(R.id.buttonAtualizarPassword)
        buttonAtualizarPassword.setOnClickListener {
            val password: String = editTextPassword.text.toString()
            val passwordrepeated: String = editTextRepeatPassword.text.toString()

            atualizarPassword(password, passwordrepeated)
        }


        return view
    }

    private fun atualizarPassword(password: String, passwordrepeated: String) {
        if(password.isNotEmpty() && passwordrepeated.isNotEmpty()) {
            if(password == passwordrepeated) {

                val email = activity!!.getSharedPreferences("email", AppCompatActivity.MODE_PRIVATE)
                    .getString("email", null).toString()

                val endPointsService = ServiceBuilder.buildService((EndPointsService::class.java))
                val requestCall = endPointsService.changePassword(email, password)
                
                requestCall.enqueue(object : Callback<ChangePasswordResponse> {
                    override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                        if (response.isSuccessful) {

                            val changePasswordResponse = response.body()
                            if (changePasswordResponse != null) {
                                if (!changePasswordResponse.getStatus()) { //status false
                                    Toast.makeText(activity, changePasswordResponse.getMessage(), Toast.LENGTH_SHORT).show()
                                } else { //status true
                                    Toast.makeText(activity, changePasswordResponse.getMessage(), Toast.LENGTH_SHORT).show()

                                    mudarFragment(perfilFragment)
                                }
                            }
                        } else {
                            Toast.makeText(activity, "Change Password Attempt Failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                        Toast.makeText(activity, "Erro$t", Toast.LENGTH_SHORT).show()
                    }
                })

            } else {
                Toast.makeText(activity, "Palavras-passes não correspondem!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, "Por favor preencha os campos todos!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mudarFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

}
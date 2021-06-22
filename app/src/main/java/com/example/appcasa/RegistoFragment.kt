package com.example.appcasa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistoFragment : Fragment() {

    lateinit var botaoRegisto: Button
    lateinit var botaoJaTemConta: Button
    lateinit var textNicknameRegisto: EditText
    lateinit var textEmailRegisto: EditText
    lateinit var textPasswordRegisto: TextInputEditText
    lateinit var textResidenceRegisto: EditText
    lateinit var textPhoneRegisto: EditText
    lateinit var loginFragment: LoginFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_registo, container, false)

        textNicknameRegisto = view.findViewById(R.id.textNicknameRegisto)
        textEmailRegisto = view.findViewById(R.id.textEmailRegisto)
        textPasswordRegisto = view.findViewById(R.id.textSenhaRegisto)
        textResidenceRegisto = view.findViewById(R.id.textResidenceRegisto)
        textPhoneRegisto = view.findViewById(R.id.textPhoneRegisto)
        botaoRegisto = view.findViewById(R.id.botaoRegisto)

        loginFragment = LoginFragment()

        botaoJaTemConta = view.findViewById(R.id.botaoJaTemConta)
        botaoJaTemConta.setOnClickListener {
            mudarFragment(loginFragment)
        }

        botaoRegisto.setOnClickListener {
            val nickname: String = textNicknameRegisto.text.toString()
            val email: String = textEmailRegisto.text.toString()
            val password: String = textPasswordRegisto.text.toString()
            val residence: String = textResidenceRegisto.text.toString()
            val phone: String = textPhoneRegisto.text.toString()
            registo(nickname, email, password, residence, phone)
        }

        return view
    }

    private fun registo(nickname: String, email: String, password: String, residence: String, phone: String) {
        if(nickname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && residence.isNotEmpty() && phone.isNotEmpty()){
            val newUser = User(nickname, email, password, residence, phone)

            val endPointsService = ServiceBuilder.buildService((EndPointsService::class.java))
            val requestCall = endPointsService.addUser(newUser)

            requestCall.enqueue(object : Callback<RegistoResponse> {

                override fun onResponse(call: Call<RegistoResponse>, response: Response<RegistoResponse>) {
                    if (response.isSuccessful){
                        val registoResponse = response.body()
                        if (registoResponse != null) {
                            if(!registoResponse.getStatus()) { //status false
                                Toast.makeText(activity, registoResponse.getMessage(), Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(activity, registoResponse.getMessage(), Toast.LENGTH_SHORT).show()

                                //trocar de fragment
                                mudarFragment(loginFragment)
                            }
                        }
                    } else {
                        Toast.makeText(activity, "Erro a adicionar o user", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RegistoResponse>, t: Throwable) {
                    Toast.makeText(activity, "Erro$t", Toast.LENGTH_SHORT).show()
                }
            })

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
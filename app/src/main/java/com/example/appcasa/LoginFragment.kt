package com.example.appcasa

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    lateinit var textEmail: EditText
    lateinit var textPassword: EditText
    lateinit var botaoAindaNaoTemConta: TextView
    lateinit var botaoLogin: Button
    lateinit var botaoForgotYourPassword : TextView
    lateinit var restaurantesFragment: RestaurantesFragment
    lateinit var registoFragment: RegistoFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        textEmail = view.findViewById(R.id.textEmail)
        textPassword = view.findViewById(R.id.textPassword)
        botaoAindaNaoTemConta = view.findViewById(R.id.textViewNaoPossuiContaRegistar)
        botaoLogin = view.findViewById(R.id.botaoLogin)
//        botaoForgotYourPassword = view.findViewById(R.id.botaoForgotYourPassword)
        restaurantesFragment = RestaurantesFragment()
        registoFragment = RegistoFragment()

        botaoAindaNaoTemConta.setOnClickListener {
           mudarFragment(registoFragment)
        }

        botaoLogin.setOnClickListener {
            val email: String = textEmail.text.toString()
            val pass: String = textPassword.text.toString()

            login(email, pass)
        }

        return view
    }

    private fun login(email: String, password: String) {
        if(email.isNotEmpty() && password.isNotEmpty()) {

            val endPointsService = ServiceBuilder.buildService((EndPointsService::class.java))
            val requestCall = endPointsService.userLogin(email, password)

            requestCall.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {

                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            if (!loginResponse.getStatus()) { //status false
                                Toast.makeText(activity, loginResponse.getMessage(), Toast.LENGTH_SHORT).show()
                            } else { //status true
                                Toast.makeText(activity, loginResponse.getMessage(), Toast.LENGTH_SHORT).show()

                                val preferences: SharedPreferences = activity!!.getSharedPreferences("token", MODE_PRIVATE)
                                preferences.edit().putString("token", "token="+loginResponse.getToken()).apply()

                                val emailUser: SharedPreferences = activity!!.getSharedPreferences("email", MODE_PRIVATE)
                                emailUser.edit().putString("email", email).apply()

                                mudarFragment(restaurantesFragment)
                            }
                        }
                    } else {
                        Toast.makeText(activity, "Logged In Attempt Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
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
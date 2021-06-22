package com.example.appcasa

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var loginFragment: LoginFragment
    lateinit var restaurantesFragment: RestaurantesFragment
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        restaurantesFragment = RestaurantesFragment()
        loginFragment = LoginFragment()
        
        verificarToken()

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    private fun verificarToken() {

        val token = getSharedPreferences("token", MODE_PRIVATE).getString("token", null)

        if (token.isNullOrEmpty()){
                mudarFragment(loginFragment)
        } else {
            val endPointsService = ServiceBuilder.buildService(EndPointsService::class.java)
            val requestCall = endPointsService.checkToken(token)

            requestCall.enqueue(object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.isSuccessful) {
                        mudarFragment(restaurantesFragment)
                    } else {
                        mudarFragment(loginFragment)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MainActivity,"Erro a verificar o token: $t", Toast.LENGTH_SHORT).show()
                    mudarFragment(loginFragment)
                }
            })
        }
    }

    private fun mudarFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.backStackEntryCount > 0 -> {
                supportFragmentManager.popBackStack()
            }
            backPressedTime + 2000 > System.currentTimeMillis() -> {
                super.onBackPressed()
                return
            }
            else -> {
                Toast.makeText(this, "Prima BACK novamente para sair.", Toast.LENGTH_SHORT).show()
            }
        }
        backPressedTime = System.currentTimeMillis()
    }
}

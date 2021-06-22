package com.example.appcasa

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class AdicionarItemFragment : Fragment() {

    lateinit var editTextNrRefeicoes: EditText
    lateinit var editTextStock: EditText
    lateinit var editTextNotas: EditText
    lateinit var botaoSubmeter: Button
    lateinit var editTextKitDetails: EditText
    lateinit var buttonCheckIn: Button
    lateinit var buttonCheckOut: Button
    lateinit var editTextAppetizer: EditText
    lateinit var editTextSideDish: EditText
    lateinit var editTextDessert: EditText
    lateinit var editTextGaps: EditText
    lateinit var editTextSpoons: EditText
    lateinit var editTextNapkin: EditText
    lateinit var editTextCuvettes: EditText
    lateinit var editTextCover: EditText
    lateinit var editTextKitchenPaperRolls: EditText
    lateinit var editTextRollsToiletPaper: EditText
    lateinit var editTextMistolim: EditText
    lateinit var editTextDishwashingDetergent: EditText
    lateinit var editTextFloorDetergent: EditText
    lateinit var editTextGloves: EditText
    lateinit var editTextMasks: EditText
    lateinit var layoutAdicionarItem: ConstraintLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_adicionar_item, container, false)

        //toolbar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.show()
        actionBar?.setBackgroundDrawable(null)

        val toolbar = (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.my_toolbar)
        val textViewNomeFragment = toolbar.findViewById<TextView>(R.id.textViewNomeFragment)
        textViewNomeFragment.isVisible = false

        layoutAdicionarItem = view.findViewById(R.id.layoutAdicionarItem)
        editTextNrRefeicoes = view.findViewById(R.id.editTextNrRefeicoes)
        editTextStock = view.findViewById(R.id.editTextStock)
        editTextNotas = view.findViewById(R.id.editTextNotas)
        editTextKitDetails = view.findViewById(R.id.editTextKitDetails)
        buttonCheckIn = view.findViewById(R.id.buttonCheckIn)
        buttonCheckOut = view.findViewById(R.id.buttonCheckOut)
        editTextAppetizer = view.findViewById(R.id.editTextAppetizer)
        editTextSideDish = view.findViewById(R.id.editTextSideDish)
        editTextDessert = view.findViewById(R.id.editTextDessert)
        editTextGaps = view.findViewById(R.id.editTextGaps)
        editTextSpoons = view.findViewById(R.id.editTextSpoons)
        editTextNapkin = view.findViewById(R.id.editTextNapkin)
        editTextCuvettes = view.findViewById(R.id.editTextCuvettes)
        editTextCover = view.findViewById(R.id.editTextCover)
        editTextKitchenPaperRolls = view.findViewById(R.id.editTextKitchenPaperRolls)
        editTextRollsToiletPaper = view.findViewById(R.id.editTextRollsToiletPaper)
        editTextMistolim = view.findViewById(R.id.editTextMistolim)
        editTextDishwashingDetergent = view.findViewById(R.id.editTextDishwashingDetergent)
        editTextFloorDetergent = view.findViewById(R.id.editTextFloorDetergent)
        editTextGloves = view.findViewById(R.id.editTextGloves)
        editTextMasks = view.findViewById(R.id.editTextMasks)

        //nome do restaurante
        val name = activity?.getSharedPreferences("restaurante", AppCompatActivity.MODE_PRIVATE)
            ?.getString("restaurante", null)

        //mudar background dependendo do restaurante
        when {
            name.toString() == "Terço" -> {
                layoutAdicionarItem.setBackgroundResource(R.drawable.azulejo_blurred_mobile)
            }
            name.toString() == "Baixa" -> {
                layoutAdicionarItem.setBackgroundResource(R.drawable.porto_blurred_mobile)
            }
            name.toString() == "Ju" -> {
                layoutAdicionarItem.setBackgroundResource(R.drawable.hospital_blurred_mobile)
            }
        }

        buttonCheckIn.setOnClickListener {
            showDateTimeDialog(buttonCheckIn)
        }

        buttonCheckOut.setOnClickListener {
            showDateTimeDialog(buttonCheckOut)
        }

        botaoSubmeter = view.findViewById(R.id.botaoSubmeter)
        botaoSubmeter.setOnClickListener {

            var nrRefeicoes: Int? = null
            if(!editTextNrRefeicoes.text.isNullOrEmpty()) {
                nrRefeicoes = editTextNrRefeicoes.text.toString().toInt()
            }

            val stock: String = editTextStock.text.toString()
            val notas: String = editTextNotas.text.toString()
            val kitDetails: String = editTextKitDetails.text.toString()
            val checkIn: String = buttonCheckIn.text.toString()
            val checkOut: String = buttonCheckOut.text.toString()
            val appetizer: String = editTextAppetizer.text.toString()
            val sideDish: String = editTextSideDish.text.toString()
            val dessert: String = editTextDessert.text.toString()

            var gaps: Int? = null
            if(!editTextGaps.text.isNullOrEmpty()) {
                gaps = editTextGaps.text.toString().toInt()
            }

            var spoons: Int? = null
            if(!editTextSpoons.text.isNullOrEmpty()) {
                spoons = editTextSpoons.text.toString().toInt()
            }

            var napkin: Int? = null
            if(!editTextNapkin.text.isNullOrEmpty()) {
                napkin = editTextNapkin.text.toString().toInt()
            }

            var cuvettes: Int? = null
            if(!editTextCuvettes.text.isNullOrEmpty()) {
                cuvettes = editTextCuvettes.text.toString().toInt()
            }

            var cover: Int? = null
            if(!editTextCover.text.isNullOrEmpty()) {
                cover = editTextCover.text.toString().toInt()
            }

            var kitchenPaperRolls: Int? = null
            if(!editTextKitchenPaperRolls.text.isNullOrEmpty()) {
                kitchenPaperRolls = editTextKitchenPaperRolls.text.toString().toInt()
            }

            var rollsToiletPaper: Int? = null
            if(!editTextRollsToiletPaper.text.isNullOrEmpty()) {
                rollsToiletPaper = editTextRollsToiletPaper.text.toString().toInt()
            }

            var mistolim: Int? = null
            if(!editTextMistolim.text.isNullOrEmpty()) {
                mistolim = editTextMistolim.text.toString().toInt()
            }

            var dishwashingDetergent: Int? = null
            if(!editTextDishwashingDetergent.text.isNullOrEmpty()) {
                dishwashingDetergent = editTextDishwashingDetergent.text.toString().toInt()
            }

            var floorDetergent: Int? = null
            if(!editTextFloorDetergent.text.isNullOrEmpty()) {
                floorDetergent = editTextFloorDetergent.text.toString().toInt()
            }

            var gloves: Int? = null
            if(!editTextGloves.text.isNullOrEmpty()) {
                gloves = editTextGloves.text.toString().toInt()
            }

            var masks: Int? = null
            if(!editTextMasks.text.isNullOrEmpty()) {
                masks = editTextMasks.text.toString().toInt()
            }

            adicionarItem(nrRefeicoes, stock, notas, kitDetails, checkIn, checkOut, appetizer, sideDish, dessert,
                gaps, spoons, napkin, cuvettes, cover, kitchenPaperRolls, rollsToiletPaper, mistolim, dishwashingDetergent, floorDetergent, gloves, masks)
        }

        return view
    }

    private fun adicionarItem(nrRefeicoes: Int?, stock: String, notas: String, kitDetails: String, checkIn: String, checkOut: String, appetizer: String, sideDish: String,
                              dessert: String, gaps: Int?, spoons: Int?, napkin: Int?, cuvettes: Int?, cover: Int?, kitchenPaperRolls: Int?,
                              rollsToiletPaper: Int?, mistolim: Int?, dishwashingDetergent: Int?, floorDetergent: Int?, gloves: Int?, masks: Int? ) {
        if(stock.isNotEmpty() && notas.isNotEmpty() && kitDetails.isNotEmpty() && checkIn.isNotEmpty() && checkOut.isNotEmpty() && appetizer.isNotEmpty() && sideDish.isNotEmpty() && dessert.isNotEmpty()){

            //nome do restaurante
            val name = activity?.getSharedPreferences("restaurante", AppCompatActivity.MODE_PRIVATE)
                ?.getString("restaurante", null)

            val endPointsService = ServiceBuilder.buildService(EndPointsService::class.java)
            val requestCall = endPointsService.addItem(name.toString(), nrRefeicoes, stock, notas, kitDetails, checkIn, checkOut, appetizer, sideDish, dessert,
                gaps, spoons, napkin, cuvettes, cover, kitchenPaperRolls, rollsToiletPaper, mistolim, dishwashingDetergent, floorDetergent, gloves, masks)

            requestCall.enqueue(object : Callback<AddItemResponse> {
                override fun onResponse(call: Call<AddItemResponse>, response: Response<AddItemResponse>) {
                    if (response.isSuccessful) {

                        val addItemResponse = response.body()
                        if (addItemResponse != null) {
                            if (!addItemResponse.getStatus()) { //status false
                                Toast.makeText(activity, "Ocorreu um erro a tentar adicionar o item", Toast.LENGTH_SHORT).show()
                            } else { //status true
                                Toast.makeText(activity, "Item adicionado com sucesso", Toast.LENGTH_SHORT).show()

                                requireActivity().supportFragmentManager.popBackStack()
                            }
                        }
                    } else {
                        Toast.makeText(activity, "Logged In Attempt Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AddItemResponse>, t: Throwable) {
                    Toast.makeText(activity, "Erro$t", Toast.LENGTH_SHORT).show()
                }
            })

            } else {
            Toast.makeText(activity, "Por favor preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showDateTimeDialog(editText: Button) {
        val calendar: Calendar = Calendar.getInstance()
        val dateSetListener =
            OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val timeSetListener =
                    OnTimeSetListener { _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)
                        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
                        editText.text = simpleDateFormat.format(calendar.time)
                    }
                TimePickerDialog(
                    activity,
                    timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            }
        activity?.let {
            DatePickerDialog(
                it,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

}
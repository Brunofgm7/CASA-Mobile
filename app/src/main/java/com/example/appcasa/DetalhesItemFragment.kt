package com.example.appcasa

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DetalhesItemFragment : Fragment() {

    lateinit var textNrRefeicoesUpdate: EditText
    lateinit var textStockUpdate: EditText
    lateinit var textNotasUpdate: EditText
    lateinit var textKitDetailsUpdate: EditText
    lateinit var buttonUpdateCheckIn: Button
    lateinit var buttonUpdateCheckOut: Button
    lateinit var textAppetizerUpdate: EditText
    lateinit var textSideDishUpdate: EditText
    lateinit var textDessertUpdate: EditText
    lateinit var textGapsUpdate: EditText
    lateinit var textSpoonsUpdate: EditText
    lateinit var textNapkinUpdate: EditText
    lateinit var textCuvettesUpdate: EditText
    lateinit var textCoverUpdate: EditText
    lateinit var textKitchenPaperRollsUpdate: EditText
    lateinit var textRollsToiletPaperUpdate: EditText
    lateinit var textMistolimUpdate: EditText
    lateinit var textDishwashingDetergentUpdate: EditText
    lateinit var textFloorDetergentUpdate: EditText
    lateinit var textGlovesUpdate: EditText
    lateinit var textMasksUpdate: EditText
    lateinit var botaoGuardar: Button
    lateinit var botaoApagar: Button
    lateinit var layoutDetalhesItem: ConstraintLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_detalhes_item, container, false)

        val bundle = this.arguments
        if (bundle != null) {
            val itemId = bundle.get("itemId").toString()

            carregarItem(itemId)

            layoutDetalhesItem = view.findViewById(R.id.layoutDetalhesItem)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.show()
            actionBar?.setBackgroundDrawable(null)

            val toolbar = (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.my_toolbar)
            val textViewNomeFragment = toolbar.findViewById<TextView>(R.id.textViewNomeFragment)
            textViewNomeFragment.isVisible = false

            textNrRefeicoesUpdate = view.findViewById(R.id.TextNrRefeicoes)
            textStockUpdate = view.findViewById(R.id.TextStock)
            textNotasUpdate = view.findViewById(R.id.TextNotas)
            textKitDetailsUpdate = view.findViewById(R.id.TextKitDetails)
            buttonUpdateCheckIn = view.findViewById(R.id.buttonUpdateCheckIn)
            buttonUpdateCheckOut = view.findViewById(R.id.buttonUpdateCheckOut)
            textAppetizerUpdate = view.findViewById(R.id.TextAppetizer)
            textSideDishUpdate = view.findViewById(R.id.TextSideDish)
            textDessertUpdate = view.findViewById(R.id.TextDessert)
            textGapsUpdate = view.findViewById(R.id.TextGaps)
            textSpoonsUpdate = view.findViewById(R.id.TextSpoons)
            textNapkinUpdate = view.findViewById(R.id.TextNapkin)
            textCuvettesUpdate = view.findViewById(R.id.TextCuvettes)
            textCoverUpdate = view.findViewById(R.id.TextCover)
            textKitchenPaperRollsUpdate = view.findViewById(R.id.TextKitchenPaperRolls)
            textRollsToiletPaperUpdate = view.findViewById(R.id.TextRollsToiletPaper)
            textMistolimUpdate = view.findViewById(R.id.TextMistolim)
            textDishwashingDetergentUpdate = view.findViewById(R.id.TextDishwashingDetergent)
            textFloorDetergentUpdate = view.findViewById(R.id.TextFloorDetergent)
            textGlovesUpdate = view.findViewById(R.id.TextGloves)
            textMasksUpdate = view.findViewById(R.id.TextMasks)

            //nome do restaurante
            val name = activity?.getSharedPreferences("restaurante", AppCompatActivity.MODE_PRIVATE)
                ?.getString("restaurante", null)

            //mudar background dependendo do restaurante
            when {
                name.toString() == "Terço" -> {
                    layoutDetalhesItem.setBackgroundResource(R.drawable.azulejo_blurred_mobile)
                }
                name.toString() == "Baixa" -> {
                    layoutDetalhesItem.setBackgroundResource(R.drawable.porto_blurred_mobile)
                }
                name.toString() == "Ju" -> {
                    layoutDetalhesItem.setBackgroundResource(R.drawable.hospital_blurred_mobile)
                }
            }


            buttonUpdateCheckIn.setOnClickListener {
                showDateTimeDialog(buttonUpdateCheckIn)
            }

            buttonUpdateCheckOut.setOnClickListener {
                showDateTimeDialog(buttonUpdateCheckOut)
            }

            botaoGuardar = view.findViewById(R.id.botaoGuardar)
            botaoGuardar.setOnClickListener {

                var nrRefeicoesUpdate: Int? = null
                if(!textNrRefeicoesUpdate.text.isNullOrEmpty()) {
                    nrRefeicoesUpdate = textNrRefeicoesUpdate.text.toString().toInt()
                }
                val stockUpdate: String = textStockUpdate.text.toString()
                val notasUpdate: String = textNotasUpdate.text.toString()
                val kitDetailsUpdate: String = textKitDetailsUpdate.text.toString()
                val buttonUpdateCheckInUpdate: String = buttonUpdateCheckIn.text.toString()
                val buttonUpdateCheckOutUpdate: String = buttonUpdateCheckOut.text.toString()
                val appetizerUpdate: String = textAppetizerUpdate.text.toString()
                val sideDishUpdate: String = textSideDishUpdate.text.toString()
                val dessertUpdate: String = textDessertUpdate.text.toString()

                var gapsUpdate: Int? = null
                if(!textGapsUpdate.text.isNullOrEmpty()) {
                    gapsUpdate = textGapsUpdate.text.toString().toInt()
                }

                var spoonsUpdate: Int? = null
                if(!textSpoonsUpdate.text.isNullOrEmpty()) {
                    spoonsUpdate = textSpoonsUpdate.text.toString().toInt()
                }

                var napkinUpdate: Int? = null
                if(!textNapkinUpdate.text.isNullOrEmpty()) {
                    napkinUpdate = textNapkinUpdate.text.toString().toInt()
                }

                var cuvettesUpdate: Int? = null
                if(!textCuvettesUpdate.text.isNullOrEmpty()) {
                    cuvettesUpdate = textCuvettesUpdate.text.toString().toInt()
                }

                var coverUpdate: Int? = null
                if(!textCoverUpdate.text.isNullOrEmpty()) {
                    coverUpdate = textCoverUpdate.text.toString().toInt()
                }

                var kitchenPaperRollsUpdate: Int? = null
                if(!textKitchenPaperRollsUpdate.text.isNullOrEmpty()) {
                    kitchenPaperRollsUpdate = textKitchenPaperRollsUpdate.text.toString().toInt()
                }

                var rollsToiletPaperUpdate: Int? = null
                if(!textRollsToiletPaperUpdate.text.isNullOrEmpty()) {
                    rollsToiletPaperUpdate = textRollsToiletPaperUpdate.text.toString().toInt()
                }

                var mistolimUpdate: Int? = null
                if(!textMistolimUpdate.text.isNullOrEmpty()) {
                    mistolimUpdate = textMistolimUpdate.text.toString().toInt()
                }

                var dishwashingDetergentUpdate: Int? = null
                if(!textDishwashingDetergentUpdate.text.isNullOrEmpty()) {
                    dishwashingDetergentUpdate = textDishwashingDetergentUpdate.text.toString().toInt()
                }

                var floorDetergentUpdate: Int? = null
                if(!textFloorDetergentUpdate.text.isNullOrEmpty()) {
                    floorDetergentUpdate = textFloorDetergentUpdate.text.toString().toInt()
                }

                var glovesUpdate: Int? = null
                if(!textGlovesUpdate.text.isNullOrEmpty()) {
                    glovesUpdate = textGlovesUpdate.text.toString().toInt()
                }

                var masksUpdate: Int? = null
                if(!textMasksUpdate.text.isNullOrEmpty()) {
                    masksUpdate = textMasksUpdate.text.toString().toInt()
                }

                updateItem(itemId, nrRefeicoesUpdate, stockUpdate, notasUpdate, kitDetailsUpdate, buttonUpdateCheckInUpdate, buttonUpdateCheckOutUpdate, appetizerUpdate, sideDishUpdate,
                    dessertUpdate, gapsUpdate, spoonsUpdate, napkinUpdate, cuvettesUpdate, coverUpdate, kitchenPaperRollsUpdate, rollsToiletPaperUpdate, mistolimUpdate, dishwashingDetergentUpdate,
                    floorDetergentUpdate, glovesUpdate, masksUpdate )
            }

            botaoApagar = view.findViewById(R.id.botaoApagar)
            botaoApagar.setOnClickListener {
                val builder = AlertDialog.Builder(activity as AppCompatActivity)
                builder.setMessage("Tem a certeza que deseja apagar?")
                    .setCancelable(false)
                    .setPositiveButton("Sim") { _, _ ->
                        apagarItem(itemId)
                    }
                    .setNegativeButton("Não") { dialog, _ ->
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }

        return view
    }

    private fun carregarItem(itemId: String) {

        val destinationService = ServiceBuilder.buildService(EndPointsService::class.java)
        val requestCall = destinationService.getItem(itemId)

        requestCall.enqueue(object: Callback<List<Item>> {

            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    val item = response.body()
                    if (item != null) {
                        for (i in item) {
                            textNrRefeicoesUpdate.setText(i.numero)
                            textStockUpdate.setText(i.stock)
                            textNotasUpdate.setText(i.notas)
                            textKitDetailsUpdate.setText(i.kit_details)
                            buttonUpdateCheckIn.text = converterDataHora(i.check_in)
                            buttonUpdateCheckOut.text = converterDataHora(i.check_out)
                            textAppetizerUpdate.setText(i.appetizer)
                            textSideDishUpdate.setText(i.side_dish)
                            textDessertUpdate.setText(i.dessert)

                            if(i.gaps != 0) {
                                textGapsUpdate.setText(i.gaps.toString())
                            } else {
                                textGapsUpdate.setText("")
                            }

                            if(i.spoons != 0) {
                                textSpoonsUpdate.setText(i.spoons.toString())
                            } else {
                                textSpoonsUpdate.setText("")
                            }

                            if(i.napkin != 0) {
                                textNapkinUpdate.setText(i.napkin.toString())
                            } else {
                                textNapkinUpdate.setText("")
                            }

                            if(i.cuvettes != 0) {
                                textCuvettesUpdate.setText(i.cuvettes.toString())
                            } else {
                                textCuvettesUpdate.setText("")
                            }

                            if(i.cover != 0) {
                                textCoverUpdate.setText(i.cover.toString())
                            } else {
                                textCoverUpdate.setText("")
                            }

                            if(i.kitchen_paper_rolls != 0) {
                                textKitchenPaperRollsUpdate.setText(i.kitchen_paper_rolls.toString())
                            } else {
                                textKitchenPaperRollsUpdate.setText("")
                            }

                            if(i.rolls_toilet_paper != 0) {
                                textRollsToiletPaperUpdate.setText(i.rolls_toilet_paper.toString())
                            } else {
                                textRollsToiletPaperUpdate.setText("")
                            }

                            if(i.mistolim != 0) {
                                textMistolimUpdate.setText(i.mistolim.toString())
                            } else {
                                textMistolimUpdate.setText("")
                            }

                            if(i.dishwashing_detergent != 0) {
                                textDishwashingDetergentUpdate.setText(i.dishwashing_detergent.toString())
                            } else {
                                textDishwashingDetergentUpdate.setText("")
                            }

                            if(i.floor_detergent != 0) {
                                textFloorDetergentUpdate.setText(i.floor_detergent.toString())
                            } else {
                                textFloorDetergentUpdate.setText("")
                            }

                            if(i.gloves != 0) {
                                textGlovesUpdate.setText(i.gloves.toString())
                            } else {
                                textGlovesUpdate.setText("")
                            }

                            if(i.masks != 0) {
                                textMasksUpdate.setText(i.masks.toString())
                            } else {
                                textMasksUpdate.setText("")
                            }

                        }
                    }
                } else {
                    Toast.makeText(activity, "Erro a carregar o item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Toast.makeText(activity, "Erro a carregar o item: $t",Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun apagarItem(itemId: String){

        val endPointsService = ServiceBuilder.buildService(EndPointsService::class.java)
        val requestCall = endPointsService.deleteItem(itemId)

        requestCall.enqueue(object: Callback<Unit> {

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(activity,"Item eliminado com sucesso", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.popBackStack()

                } else {
                    Toast.makeText(activity,"Erro a apagar o item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(activity,"Erro a apagar o item: $t", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun updateItem(itemId: String, nrRefeicoesUpdate: Int?, stockUpdate: String, notasUpdate: String, kitDetailsUpdate: String, buttonUpdateCheckInUpdate: String, buttonUpdateCheckOutUpdate: String,
                           appetizerUpdate: String, sideDishUpdate: String, dessertUpdate: String, gapsUpdate: Int?, spoonsUpdate: Int?, napkinUpdate: Int?, cuvettesUpdate: Int?, coverUpdate: Int?,
                           kitchenPaperRollsUpdate: Int?, rollsToiletPaperUpdate: Int?, mistolimUpdate: Int?, dishwashingDetergentUpdate: Int?, floorDetergentUpdate: Int?, glovesUpdate: Int?, masksUpdate: Int?  ) {
        if(stockUpdate.isNotEmpty() && notasUpdate.isNotEmpty() && kitDetailsUpdate.isNotEmpty() && buttonUpdateCheckInUpdate.isNotEmpty() && buttonUpdateCheckOutUpdate.isNotEmpty() && appetizerUpdate.isNotEmpty() && sideDishUpdate.isNotEmpty() && dessertUpdate.isNotEmpty()){

            val endPointsService = ServiceBuilder.buildService(EndPointsService::class.java)

            val name = activity?.getSharedPreferences("restaurante", AppCompatActivity.MODE_PRIVATE)
                ?.getString("restaurante", null)

            val requestCall = endPointsService.updateItem(itemId, name.toString(), nrRefeicoesUpdate, stockUpdate, notasUpdate, kitDetailsUpdate, buttonUpdateCheckInUpdate, buttonUpdateCheckOutUpdate,
                appetizerUpdate, sideDishUpdate, dessertUpdate, gapsUpdate, spoonsUpdate, napkinUpdate, cuvettesUpdate, coverUpdate,
                kitchenPaperRollsUpdate, rollsToiletPaperUpdate, mistolimUpdate, dishwashingDetergentUpdate, floorDetergentUpdate, glovesUpdate, masksUpdate)

            requestCall.enqueue(object: Callback<AddItemResponse> {

                override fun onResponse(call: Call<AddItemResponse>, response: Response<AddItemResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(activity,"Item editado com sucesso", Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.popBackStack()
                    } else {
                        Toast.makeText(activity,"Erro a editar o item", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AddItemResponse>, t: Throwable) {
                    Toast.makeText(activity,"Erro a editar o item: $t", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(activity, "Por favor preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDateTimeDialog(editText: Button) {
        val calendar: Calendar = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
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

    private fun converterDataHora(dateStr: String): String {
        val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.UK)
        val destFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
        val convertedDate = sourceFormat.parse(dateStr)
        return destFormat.format(convertedDate)
    }
}
package com.fromjin.xmlparsing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.fromjin.xmlparsing.Util.Model.ResultFoodNutri
import com.fromjin.xmlparsing.Util.RetrofitClient
import com.fromjin.xmlparsing.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val foodNutriDecodingKey =
        "j/xkShPJBtxFbK+ahZ+zy8yx8hTGU36HJbFQ9ZK0/JNRG6yhX41qMmiyl73Z1VSpfFZUiK3DBt31s9qnfHqLEw=="
    private val retrofit = RetrofitClient.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.searchBtn.setOnClickListener {
            val foodName = binding.foodEdit.text.toString()
            val intent = Intent(this, FoodSearchActivity::class.java)
            intent.putExtra("foodName", foodName)
            startActivityForResult(intent, 200)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                val selectedFoodCode = data?.getStringExtra("selectedFoodCode").toString()
                getFoodNutriByFoodCode(selectedFoodCode)
            }
        }
    }

    private fun getFoodNutriByFoodCode(selectedFoodCode: String) {
        retrofit.getFoodNutriInfo(foodNutriDecodingKey, selectedFoodCode)
            .enqueue(object : Callback<ResultFoodNutri> {
                override fun onResponse(
                    call: Call<ResultFoodNutri>,
                    response: Response<ResultFoodNutri>
                ) {
                    Log.d("레트로핏", selectedFoodCode)
                    Log.d("레트로핏", response.code().toString())
                    binding.resultText.text = response.raw().toString()

                }

                override fun onFailure(call: Call<ResultFoodNutri>, t: Throwable) {
                    Log.d("레트로핏", "레트로핏실패: ${t.message}")
                }

            })
    }

}
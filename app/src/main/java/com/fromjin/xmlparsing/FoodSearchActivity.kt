package com.fromjin.xmlparsing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fromjin.xmlparsing.RecyclerView.FoodCodeInfo
import com.fromjin.xmlparsing.RecyclerView.FoodResultAdapter
import com.fromjin.xmlparsing.Util.Model.ResultFoodCodeList
import com.fromjin.xmlparsing.Util.RetrofitClient
import com.fromjin.xmlparsing.databinding.ActivityFoodSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodSearchActivity : AppCompatActivity(), FoodResultAdapter.OnItemClickListener {
    lateinit var binding: ActivityFoodSearchBinding

    private val foodCodeDecodingKey =
        "j/xkShPJBtxFbK+ahZ+zy8yx8hTGU36HJbFQ9ZK0/JNRG6yhX41qMmiyl73Z1VSpfFZUiK3DBt31s9qnfHqLEw=="
    private val retrofit = RetrofitClient.create()


    private val listItems = arrayListOf<FoodCodeInfo>()
    private val foodResultAdapter = FoodResultAdapter(listItems)

    private var foodName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodName = intent.getStringExtra("foodName").toString()
        binding.foodEdit.setText(foodName)

        searchFoodCodeByFoodName(foodName)

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@FoodSearchActivity, LinearLayoutManager.VERTICAL, false)
            adapter = foodResultAdapter
        }

        foodResultAdapter.setOnItemClickListener(this)

        binding.searchBtn.setOnClickListener {
            searchFoodCodeByFoodName(binding.foodEdit.text.toString())
        }

    }

    private fun addResultItem(items: ResultFoodCodeList?) {
        listItems.clear()
        if (items?.response?.list != null) {
            for (item in items.response.list) {
                val i = FoodCodeInfo(
                    item.fd_Nm, item.fd_Code
                )
                listItems.add(i)
            }

        }
        foodResultAdapter.notifyDataSetChanged()
    }

    fun searchFoodCodeByFoodName(foodName: String) {
        retrofit.getFoodCodeList(foodCodeDecodingKey, "json", 1, 20, foodName).enqueue(object :
            Callback<ResultFoodCodeList> {
            override fun onResponse(
                call: Call<ResultFoodCodeList>,
                response: Response<ResultFoodCodeList>
            ) {
                Log.d("????????????", response.code().toString())

                addResultItem(response.body())

            }

            override fun onFailure(call: Call<ResultFoodCodeList>, t: Throwable) {
                Log.d("????????????", "??????????????????: ${t.message}")
            }

        })
    }

    override fun onItemClick(view: View, data: FoodCodeInfo, position: Int) {
        val intent = Intent()
        intent.putExtra("selectedFoodCode",data.foodCode)
        setResult(RESULT_OK, intent)
        finish()
    }
}
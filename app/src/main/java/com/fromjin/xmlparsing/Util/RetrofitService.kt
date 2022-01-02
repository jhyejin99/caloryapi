package com.fromjin.xmlparsing.Util

import com.fromjin.xmlparsing.Util.Model.ResultFoodCodeList
import com.fromjin.xmlparsing.Util.Model.ResultFoodNutri
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    // 음식 및 이미지 정보 조회

    @GET("FdImage/getKoreanFoodFdImageList")
    fun getFoodCodeList(
        @Query("serviceKey") serviceKey: String, // 공공데이터포털 인증키
        @Query("service_Type") serviceType: String, // xml / json
        @Query("Page_No") pageNo: Int, // 페이지 번호
        @Query("Page_Size") pageSize: Int, // 한 페이지 결과 수
        @Query("food_Name") foodName: String? = null // 음식명
    ): Call<ResultFoodCodeList>


    @GET("MzenFoodNutri/getKoreanFoodIdntList")
    fun getFoodNutriInfo(
        @Query("serviceKey") serviceKey: String, // 공공데이터포털 인증키
        @Query("food_Code") foodName: String // 음식코드
    ): Call<ResultFoodNutri>

}
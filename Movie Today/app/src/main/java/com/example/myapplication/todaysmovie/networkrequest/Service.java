package com.example.myapplication.todaysmovie.networkrequest;

import com.example.myapplication.todaysmovie.data.ApiInterface;
import com.example.myapplication.todaysmovie.utils.CredentialsModel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(CredentialsModel.baseUrl)
            .addConverterFactory(GsonConverterFactory.create());

       private static Retrofit retrofit = builder.build();

         private static   ApiInterface apiInterface = retrofit.create(ApiInterface.class);



         public static ApiInterface getApiInterface(){
             return  apiInterface;
         }


}

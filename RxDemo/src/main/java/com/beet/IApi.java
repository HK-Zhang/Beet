package com.beet;

import com.beet.model.AllCity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * IApi
 */
public interface IApi {

    @GET("citys")
    Observable<AllCity> getAllCity(@Query("key") String key);
}
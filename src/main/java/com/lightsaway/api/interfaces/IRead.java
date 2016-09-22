package com.lightsaway.api.interfaces;

import com.jayway.restassured.response.Response;

import java.util.Map;

public interface IRead<T> {

    Response get();

    Response get(String itemName);

    Response get(T item);

    Response get(Map<String, ?> params);
}

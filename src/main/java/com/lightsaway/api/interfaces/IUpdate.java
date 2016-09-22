package com.lightsaway.api.interfaces;

import com.jayway.restassured.response.Response;

public interface IUpdate<T> {

    Response put(String itemName, String body);

    Response put(String itemName, T item);
}

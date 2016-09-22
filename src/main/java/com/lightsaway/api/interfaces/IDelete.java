package com.lightsaway.api.interfaces;

import com.jayway.restassured.response.Response;

public interface IDelete<T> {

    Response delete(String itemName);
    
    Response delete(T item);
}

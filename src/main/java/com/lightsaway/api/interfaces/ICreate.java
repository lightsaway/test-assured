package com.lightsaway.api.interfaces;

import com.jayway.restassured.response.Response;

public interface ICreate<T> {

    Response post(T items);

    Response post(String body);
}

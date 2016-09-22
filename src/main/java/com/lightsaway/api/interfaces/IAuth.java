package com.lightsaway.api.interfaces;

import com.jayway.restassured.response.Response;

public interface IAuth {

    Response authenticate(String userName, String password);

}

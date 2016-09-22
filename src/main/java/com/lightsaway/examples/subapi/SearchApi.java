package com.lightsaway.examples.subapi;

import com.jayway.restassured.response.Response;
import com.lightsaway.api.Api;
import com.lightsaway.api.Resource;
import com.lightsaway.api.interfaces.IRead;

import java.util.Map;

@Resource(path = "/search")
public class SearchApi extends Api implements IRead<String>{


    @Override
    public Response get() {
        return null;
    }

    @Override
    public Response get(String itemName) {
      return createSpec().param("a" , itemName).get(getPath());
    }

    @Override
    public Response get(Map<String, ?> params) {
        return null;
    }
}

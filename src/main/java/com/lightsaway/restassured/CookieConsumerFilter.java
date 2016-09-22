package com.lightsaway.restassured;

import com.jayway.restassured.filter.Filter;
import com.jayway.restassured.filter.FilterContext;
import com.jayway.restassured.response.Cookies;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.FilterableRequestSpecification;
import com.jayway.restassured.specification.FilterableResponseSpecification;
import com.lightsaway.api.Api;

import java.util.Map;


public class CookieConsumerFilter implements Filter {

    private Api owner;

    public  CookieConsumerFilter(Api api){
        this.owner = api;
    }

    @Override
    public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
        Response response = filterContext.next(filterableRequestSpecification, filterableResponseSpecification);

        Cookies cookies = response.getDetailedCookies();
        Map<String, String> cookiesLight = response.getCookies();
        owner.setCookies(cookiesLight);
        owner.getStore().put("cookies", cookies);
        return response;

    }
}

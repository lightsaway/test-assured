package com.lightsaway.api;

import com.jayway.restassured.filter.Filter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Store for cookies and filters.
 * To be sure that parent and children are in sync this is used
 */
public class Store {

    private Map<String, String> cookies = new HashMap<>();
    private List<Filter> filters = new LinkedList<>();
    private Map<String, Object> data = new HashMap<>();

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public void deleteCookies() {
        this.cookies = new HashMap<>();
    }

    public void addCookies(Map<String, String> cookies) {
        this.cookies.putAll(cookies);
    }

    public void addCookie(String cookieName, String cookieValue) {
        this.cookies.put(cookieName,cookieValue);
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public void deleteFilters() {
        this.filters = new LinkedList<>();
    }

    public void deleteFilter(int index) {
        this.filters.remove(index);
    }

    public void addFilters(List<Filter> filters) {
        this.filters.addAll(filters);
    }

    public void addFilter(Filter filter) {
        this.filters.add(filter);
    }

    public Object put(String key, Object val){
        return data.put(key, val);
    }

    public Object get(String key){
        return data.get(key);
    }


}

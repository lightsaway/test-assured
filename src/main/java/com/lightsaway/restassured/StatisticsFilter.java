package com.lightsaway.restassured;

import com.jayway.restassured.filter.Filter;
import com.jayway.restassured.filter.FilterContext;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.FilterableRequestSpecification;
import com.jayway.restassured.specification.FilterableResponseSpecification;
import org.apache.commons.collections.map.MultiKeyMap;

/**
 * Filter that records statistics of requests/responses
 * and represents them in table
 */
public class StatisticsFilter implements Filter {

    private static int counter = 0;

    private static MultiKeyMap map = new MultiKeyMap();

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        counter+=1;
        Integer value = (Integer) map.get(ctx.getOriginalRequestPath(), ctx.getRequestMethod(), response.getStatusCode());
        if(value != null ){
            map.put(ctx.getOriginalRequestPath(), ctx.getRequestMethod(), response.getStatusCode(), value += 1);
        }
        else {
            map.put(ctx.getOriginalRequestPath(), ctx.getRequestMethod(), response.getStatusCode(), 1);
        }
        return response;
    }


    /**
     *
     * @return total request counter, that passed this filter
     */
    public static int getTotalCounter(){
        return counter;
    }


    /**
     * Calculates total amount of requests/responses
     * @return total request counter, that passed this filter
     */
    public static int calculateTotal(){
        return (int) map.keySet().parallelStream().map(key -> map.get(key)).count();
    }


    /**
     * Returns statistics map
     * @return {MultiKeyMap}
     */
    public static MultiKeyMap getStatistics(){
        return map;
    }


    /**
     * To rewrite stats
     * @param newStats
     */
    public static void setStatistics(MultiKeyMap newStats){
        map = newStats;
    }


    /**
     * If stats comes from other place
     * @param newStats
     */
    public static void updateStatistics(MultiKeyMap newStats){
        newStats.keySet().forEach(k -> {
            if (map.get(k) != null){
                int sum = (int)map.get(k) + (int)newStats.get(k);
                map.put(k , sum);
            }
            else{
                map.put(k , newStats.get(k));
            }
        });
    }

}

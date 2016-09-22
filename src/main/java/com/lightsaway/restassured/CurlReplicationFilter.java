package com.lightsaway.restassured;

import com.jayway.restassured.filter.Filter;
import com.jayway.restassured.filter.FilterContext;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.FilterableRequestSpecification;
import com.jayway.restassured.specification.FilterableResponseSpecification;

import java.util.ArrayList;
import java.util.List;


/**
 * Replicates all commands in CURL storage
 */
public class CurlReplicationFilter implements Filter {

    private static List<String> globalStorage = new ArrayList<>();

    private String description;

    public CurlReplicationFilter(){
        this.description = "#THIS IS CURL COMMAND";
    }

    public CurlReplicationFilter(String description){
        this.description = description;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {

        StringBuilder builder = new StringBuilder();
        builder.append("#").append(description).append("\n").append("curl ")
                .append("-X ").append(ctx.getRequestMethod().toString())
                .append(" ")
                .append("$URL")
                .append(ctx.getRequestPath())
                .append(" ");

        requestSpec.getHeaders().forEach(header -> {
            builder.append("-H ")
                    .append("'")
                    .append(header.getName())
                    .append(": ")
                    .append(header.getValue())
                    .append("' ");
        });

        if(requestSpec.getBody() != null) {
            builder.append("--data-binary '")
                    .append(requestSpec.getBody().toString())
                    .append("'");
        }

        if(requestSpec.getFormParams() != null){
            //TODO
        }

        builder.append("\n");

        globalStorage.add(builder.toString());

        return ctx.next(requestSpec,responseSpec);
    }


    public List<String> getGlobalStorage(){
        return globalStorage;
    }
}

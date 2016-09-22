package com.lightsaway.raml;

import com.jayway.restassured.internal.http.Method;
import org.apache.commons.collections.map.MultiKeyMap;
import org.raml.model.Raml;
import org.raml.model.Resource;

import java.util.Map;

public class RamlCoverageCalculator {

    private MultiKeyMap coverageMap = new MultiKeyMap();

    public RamlCoverageCalculator(Raml contract, MultiKeyMap statistics){

        Map<String, Resource> resources = contract.getResources();
        MultiKeyMap flattenedRaml = flattenRaml(resources, new MultiKeyMap());
        this.coverageMap.putAll(flattenedRaml);
        this.coverageMap.putAll(statistics);

    }

    private MultiKeyMap flattenRaml(Map<String,Resource> resourceMap, MultiKeyMap accum){
        resourceMap.forEach( (name,resource) -> {
            resource.getActions().forEach((actionType,action) -> {
                action.getResponses().forEach((code, response) ->{
                    Method m = Method.valueOf(actionType.toString());
                    accum.put(resource.getUri(), m , Integer.valueOf(code.toString()), 0);
                });
            });
            if(resource.getResources() != null){
                flattenRaml(resource.getResources(), accum);
            }
        });

        return accum;
    }

    public MultiKeyMap getCoverageMap(){
        return this.coverageMap;
    }

    public float getCoveragePercentage(){
        //This is because lambda allows only finals
        final int[] covered = {0};
        coverageMap.forEach((k,v) -> {
            if((int)v > 0) covered[0] +=1;
        });
        return covered[0] *100f / this.coverageMap.size();
    }
}

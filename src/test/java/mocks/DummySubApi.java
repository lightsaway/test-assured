package mocks;

import com.jayway.restassured.response.Response;
import com.lightsaway.api.Api;
import com.lightsaway.api.Resource;
import com.lightsaway.api.interfaces.ICreate;


@Resource(path = "/dummy/subapi")
public class DummySubApi extends Api implements ICreate<Object>{
    public DummySubApi (){
        super();
    }

    public Api authenticate(String userName, String password) {
        return this;
    }

    @Override
    public Response post(Object items) {
        return null;
    }

    @Override
    public Response post(String body) {
        return null;
    }
}
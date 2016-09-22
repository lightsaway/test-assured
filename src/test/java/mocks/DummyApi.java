package mocks;

import com.lightsaway.api.Api;
import com.lightsaway.api.Resource;
import com.lightsaway.api.SubApi;

@Resource(path = "/dummy")
public class DummyApi extends Api {

    @SubApi
    public DummySubApi subApi;

    public DummyApi(){
        super();
    }

    public Api authenticate(String userName, String password) {
        return null;
    }
}
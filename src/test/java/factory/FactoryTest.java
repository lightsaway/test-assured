package factory;

import mocks.DummyApi;
import mocks.DummySubApi;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Anton Sergienko on 9/22/16.
 */
public class FactoryTest {

    @Test
    public void initOfSubApis(){
        DummyApi api = new DummyApi();
        Assert.assertNotNull(api.subApi);
        Assert.assertTrue(api.subApi instanceof DummySubApi);
    }

    @Test
    public void initOfPath(){
        DummyApi api = new DummyApi();
        Assert.assertNotNull(api.getPath());
        Assert.assertEquals(api.getPath(), "/dummy");
    }

    @Test
    public void initOfSubPath(){
        DummyApi api = new DummyApi();
        Assert.assertNotNull(api.subApi.getPath());
        Assert.assertEquals(api.subApi.getPath(), "/dummy/subapi");
    }

}

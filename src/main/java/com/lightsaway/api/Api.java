package com.lightsaway.api;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.AuthenticationScheme;
import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.filter.Filter;
import com.jayway.restassured.filter.log.LogDetail;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.internal.RequestSpecificationImpl;
import com.jayway.restassured.internal.ResponseParserRegistrar;
import com.jayway.restassured.internal.ResponseSpecificationImpl;
import com.jayway.restassured.internal.TestSpecificationImpl;
import com.jayway.restassured.internal.log.LogRepository;
import com.jayway.restassured.specification.ProxySpecification;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.jayway.restassured.config.LogConfig.logConfig;
import static java.lang.System.getProperty;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public abstract class Api {

    private String path = "";

    private ResponseParserRegistrar responseParserRegistrar = new ResponseParserRegistrar();

    private String baseURI = RestAssured.DEFAULT_URI;

    private int port = RestAssured.UNDEFINED_PORT;

    private String basePath = RestAssured.DEFAULT_PATH;

    private AuthenticationScheme authentication = RestAssured.DEFAULT_AUTH;

    private List<Filter> filters = new LinkedList<Filter>();

    private boolean urlEncodingEnabled = RestAssured.DEFAULT_URL_ENCODING_ENABLED;

    private ProxySpecification proxy = null;

    private Object requestContentType = null;

    private Object responseContentType = null;

    private String rootPath = RestAssured.DEFAULT_BODY_ROOT_PATH;

    private ResponseSpecification responseSpecification = null;

    private RequestSpecification requestSpecification = null;

    private Map<String, String> cookies;

    private boolean useCookies = true;

    private boolean useFilters = true;

    private Store store = new Store();

    private static Set<Class> filterBlackList = new HashSet<Class>();

    public Api() {
        ApiFactory.init(this);
    }

    public Api(String uri, int port, String context) {
        this();
        this.baseURI = uri;
        this.port = port;
        this.basePath = context;
    }

    /**
     * Wrapper for creating RequestSpecification
     * @return RequestSpecification
     */
    protected RequestSpecification createSpec() {
        LogRepository logRepository = new LogRepository();

        if(Boolean.getBoolean(getProperty("log.requests"))) initDefaultOutputFilters();
        initDefaultProxy();

        RestAssuredConfig restAssuredConfig = new RestAssuredConfig();
        LogConfig logConfig = logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
        restAssuredConfig.logConfig(logConfig);

        RequestSpecification spec = new TestSpecificationImpl(
                new RequestSpecificationImpl(baseURI, port, basePath, authentication, getWhiteListFilters(), requestSpecification, urlEncodingEnabled, restAssuredConfig, logRepository, proxy),
                new ResponseSpecificationImpl(rootPath, responseSpecification, responseParserRegistrar, restAssuredConfig, logRepository)).getRequestSpecification();

        spec.cookies(store.getCookies());

        return spec;
    }

    protected void setStore(Store store){
        this.store = store ;
    }

    public Store getStore(){
        return this.store ;
    }

    protected Api setAuthentication(AuthenticationScheme authentication) {
        this.authentication = authentication;
        return this;
    }

    public Api unAuthenticate() {
        this.authentication = RestAssured.DEFAULT_AUTH;
        return this;
    }


    public Api blackListFilter(Class clazz){
        this.filterBlackList.add(clazz);
        return this;
    }

    public Api setProxy(ProxySpecification proxy) {
        this.proxy = proxy;
        return this;
    }

    public Api setCookies(Map<String, String> cookies) {
        this.store.setCookies(cookies);
        return this;
    }

    public Api removeFilter(Class clazz) {
        List<Filter> filters = store.getFilters().stream()
                .filter( p -> !(clazz.isInstance(p)))
                .collect(Collectors.toList());
        this.store.setFilters(filters);
        return this;
    }

    public Map<String, String> getCookies() {
        return this.store.getCookies();
    }

    private void initDefaultProxy() {
        String proxyHost = getProperty("proxy.host");
        String proxyPort = getProperty("proxy.port");
        String proxyScheme = getProperty("proxy.scheme");
        if (!isEmpty(proxyHost) && !isEmpty(proxyPort) && !isEmpty(proxyScheme)) {
            this.setProxy(new ProxySpecification(proxyHost, Integer.parseInt(proxyPort), proxyScheme));
        }
    }

    protected void initDefaultOutputFilters(){
        this.removeFilter(RequestLoggingFilter.class);
        this.removeFilter(ResponseLoggingFilter.class);
        filters.add(new RequestLoggingFilter(System.out));
        filters.add(new ResponseLoggingFilter(System.out));
    }

    public void addFilter(Filter filter){
        this.store.addFilter(filter);
    }
    
    public List<Filter> getFilters(){
        return this.store.getFilters();
    }

    public List<Filter> getWhiteListFilters(){
        return store.getFilters().stream()
                .filter( p -> !(filterBlackList.contains(p.getClass())))
                .collect(Collectors.toList());
    }

    public void setFilters(List<Filter> filters){
        this.store.setFilters(filters);
    }


    //Used only with @Path annotation
    public void setPath(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }


}

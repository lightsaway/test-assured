package com.lightsaway.restassured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.filter.Filter;
import com.jayway.restassured.filter.FilterContext;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.FilterableRequestSpecification;
import com.jayway.restassured.specification.FilterableResponseSpecification;

/**
 * Class that overrides headers in request
 */
public class HeaderOverwriter implements Filter {

    private Headers headers;

    private ContentType DEFAULT_ENCODING = ContentType.TEXT;

    public HeaderOverwriter(Headers headers){
        this.headers = headers;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        requestSpec.headers(headers);

        for (Header header : headers) {
            if (header.getName().equals("Content-type")) {
                RestAssuredConfig config = requestSpec.getConfig().encoderConfig(new EncoderConfig().encodeContentTypeAs(header.getValue(), DEFAULT_ENCODING));
                requestSpec.config(config);
            }
        }
        Response response = ctx.next(requestSpec, responseSpec);
        return response;
    }

    public HeaderOverwriter setContentTypeEncoding(ContentType encoding){
        this.DEFAULT_ENCODING = encoding;
        return this;
    }
}

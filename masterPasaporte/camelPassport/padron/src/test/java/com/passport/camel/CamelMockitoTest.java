package com.passport.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.passport.camel.config.DirectRoutes;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@MockEndpoints("direct:end")
public class CamelMockitoTest {
	
	@EndpointInject(uri = "mock:resultFindId")
	protected MockEndpoint endPoint;
	
	@Autowired
	protected ProducerTemplate producer;

	@Autowired
	protected CamelContext context;
	
	@Before
	public void setRoutes() throws Exception {
		context.addRoutes(createRouteBuilder());
	}
	
	@Test
	public void getHttp() throws InterruptedException {
		endPoint.expectedMessageCount(1);
		producer.setDefaultEndpointUri("direct:getIdTest");
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("country", "Argentina");
		headers.put("document", "1");
		Object response = producer.requestBodyAndHeaders(null, headers);
		
		endPoint.assertIsSatisfied();
	}
	
    protected RouteBuilder createRouteBuilder() throws Exception { 
        return new RouteBuilder() { 
            public void configure() { 
                 
                from("direct:getIdTest") 
                    .to(DirectRoutes.GET_ID) 
                    .to("mock:resultFindId"); 
                 
            } 
        }; 
    }
}

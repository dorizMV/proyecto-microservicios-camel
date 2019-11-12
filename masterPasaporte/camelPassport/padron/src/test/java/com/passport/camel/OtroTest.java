package com.passport.camel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.passport.camel.exception.PersonAlreadyExistsException;
import com.passport.camel.model.Persona;
import com.passport.camel.processor.PersonExistsProcessor;

@RunWith(CamelSpringBootRunner.class)
@MockEndpoints("direct:end")
public class OtroTest extends CamelTestSupport {
	
	@EndpointInject(uri = "mock:testRoute")
	protected MockEndpoint endPointTestRoute;
	
	@EndpointInject(uri = "mock:resultFindId")
	protected MockEndpoint endPointResult;
	
	@EndpointInject(uri = "mock:resultTestProcessor")
	protected MockEndpoint endPointProcessor;
	
	@Produce
	protected ProducerTemplate producer;

	@Test
	public void testRoute() throws InterruptedException {
		endPointTestRoute.expectedMessageCount(1);
		producer.requestBody("direct:testRoute", 1);
		endPointTestRoute.assertIsSatisfied();
	}
	
	@Test
	public void getHttp() throws InterruptedException {
		endPointResult.expectedMessageCount(1);
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("document", "1");
		producer.requestBodyAndHeaders("direct:getIdOnly", null, headers);
		endPointResult.assertIsSatisfied();
	}
	
	//@Test
	public void getHttpError() throws InterruptedException {
		endPointResult.expectedMessageCount(1);
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("document", "2");
		producer.requestBodyAndHeaders("direct:getIdOnly", null, headers);
		endPointResult.assertIsNotSatisfied();
	}
	
	@Test
	public void getHttpException() throws InterruptedException {
		try {
			producer.requestBody("direct:testProcessor", new Persona());
		} catch (Exception e) {
			assertEquals(new PersonAlreadyExistsException().getMessage(), e.getCause().getMessage());
		}
	}
	
	@Test
	public void spyProcessor() {
	    PersonExistsProcessor p = new PersonExistsProcessor();
	    PersonExistsProcessor spy = Mockito.spy(p);
		
	    ExchangeBuilder builder = new ExchangeBuilder(producer.getCamelContext());
	    builder.withProperty("person", new Persona());
	    Exchange exch = builder.build();
	    try {
			spy.process(exch);
			assertNotNull(exch.getIn().getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
    protected RouteBuilder createRouteBuilder() throws Exception { 
        return new RouteBuilder() { 
            public void configure() { 
                from("direct:testRoute").to("mock:testRoute");            	
                from("direct:getIdOnly").filter(header("document").isEqualTo(1)).to("mock:resultFindId");
                from("direct:testProcessor").process(new PersonExistsProcessor()).to("mock:resultTestProcessor");
                 
            } 
        }; 
    } 

}

package com.accenture.galicia.routes.rest;


import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.accenture.galicia.utils.DirectRoutesShortcuts;

class DeleteByDocumentRestTest extends CamelTestSupport{
	
	@Before
	public
	void setUp() throws Exception {
		
	}

	@After
	public
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		getMockEndpoint(DirectRoutesShortcuts.DELETE_PASSPORT_DNI).expectedBodiesReceived();
	}

}

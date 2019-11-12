package com.passport.camel.processor;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.net.URL;
import java.util.Enumeration;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;

public class GetByIdAndCountryProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Object country = exchange.getIn().getHeader("country");
		Object document = exchange.getIn().getHeader("document");
		Bson andFilter = and(eq("country", country), eq("document", document));
		BsonDocument doc = andFilter.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry());
		BasicDBObject db = new BasicDBObject(doc);
		exchange.getIn().setBody(db);
	}

}
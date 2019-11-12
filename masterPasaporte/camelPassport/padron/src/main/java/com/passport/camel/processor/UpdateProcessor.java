package com.passport.camel.processor;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.passport.camel.model.Persona;
import com.passport.camel.util.ObjectMapper;

@Component
public class UpdateProcessor implements Processor {

	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Persona persona = exchange.getIn().getBody(Persona.class);
		Map<String, Object> map = objectMapper.convertValue(persona, Map.class);
		Bson filterField = Filters.and(Filters.eq("document", persona.getDocument()), Filters.eq("country", persona.getCountry()));
		Bson update = Updates.combine();
		/*for (String key : map.keySet()) {
			Bson temp = Updates.set(key, map.get(key));
			if (update == null) {
				update = temp;
			} else {
				update = Updates.combine(update, temp);
			}
		}*/
		BsonDocument doc = filterField.toBsonDocument(BasicDBObject.class, MongoClient.getDefaultCodecRegistry());
		BsonDocument updateDoc = update.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry());
		BasicDBObject a = new BasicDBObject(doc);
		BasicDBObject b = new BasicDBObject(updateDoc);
		exchange.getIn().setBody(new BasicDBObject[] {a,b});
	}

}
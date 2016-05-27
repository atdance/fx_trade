package com.bondsbiz.trade.business.encoders;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class JsonEncoder implements Encoder.TextStream<JsonObject>, Decoder.TextStream<JsonObject> {

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void encode(JsonObject payload, Writer writer) throws EncodeException, IOException {
        try (JsonWriter jsonWriter = Json.createWriter(writer)) {
            jsonWriter.writeObject(payload);
        }
    }
    
    @Override
    public void destroy() {
    }

   	/*
	 * Not implemented .
	 *
	 * @see javax.net.websocket.Decoder.Text#decode(java.lang.String)
	 */
	@Override
	public JsonObject decode(Reader arg0) throws DecodeException, IOException {
		// temporary workaround for a web socket implementation issue
		Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

		throw new DecodeException("", "Decode Not implemented");
	}

}

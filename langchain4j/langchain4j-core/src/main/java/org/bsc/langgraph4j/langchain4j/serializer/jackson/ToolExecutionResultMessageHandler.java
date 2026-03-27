package org.bsc.langgraph4j.langchain4j.serializer.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import dev.langchain4j.data.message.ToolExecutionResultMessage;

import java.io.IOException;

import static java.util.Optional.ofNullable;

public interface ToolExecutionResultMessageHandler {

    class Serializer extends StdSerializer<ToolExecutionResultMessage> {

        public Serializer() {
            super(ToolExecutionResultMessage.class);
        }

        @Override
        public void serialize(ToolExecutionResultMessage msg, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("@type", msg.type().name());
            gen.writeStringField("id", msg.id());
            gen.writeStringField("toolName", msg.toolName());
            gen.writeStringField("text", msg.text());
            gen.writeBooleanField("isError", ofNullable(msg.isError()).orElse(false) );
            gen.writeEndObject();
        }

    }

    class Deserializer extends StdDeserializer<ToolExecutionResultMessage> {

        protected Deserializer() {
            super(ToolExecutionResultMessage.class);
        }

        @Override
        public ToolExecutionResultMessage deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JacksonException {
            return deserialize(parser.getCodec().readTree(parser));
        }

        protected ToolExecutionResultMessage deserialize(JsonNode node) throws IOException {
            return ToolExecutionResultMessage.builder()
                    .id( node.get("id").asText() )
                    .toolName( node.get("toolName").asText() )
                    .text( node.get("text").asText() )
                    .isError( node.get("isError").asBoolean() )
                    .build();
        }

    }
}

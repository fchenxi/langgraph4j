package org.bsc.langgraph4j.serializer;

import org.bsc.langgraph4j.state.AgentState;
import org.bsc.langgraph4j.state.AgentStateFactory;
import org.bsc.langgraph4j.utils.Types;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Optional;


public abstract class PlainTextStateSerializer<State extends AgentState> extends StateSerializer<State> implements PlainTextSerializer<Map<String,Object>> {

    protected PlainTextStateSerializer(AgentStateFactory<State> stateFactory) {
        super(stateFactory);
    }

    @Override
    public final void writeData(Map<String, Object> data, ObjectOutput out) throws IOException {
        PlainTextSerializer.super.writeData(data, out);
    }

    @Override
    public final Map<String, Object> readData(ObjectInput in) throws IOException {
        return PlainTextSerializer.super.readData(in);
    }


    @SuppressWarnings("unchecked")
    public Optional<Class<State>> getStateType() {
        return Types.parameterizedType(getClass())
                .map(ParameterizedType::getActualTypeArguments)
                .filter( args -> args.length > 0 )
                .map( args -> (Class<State>)args[0] );
    }

    public State read( String data ) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bytesStream =  new ByteArrayOutputStream();

        try(ObjectOutputStream out = new ObjectOutputStream( bytesStream )) {
            Serializer.writeUTF(data, out);
            out.flush();
        }

        try(ObjectInput in = new ObjectInputStream( new ByteArrayInputStream( bytesStream.toByteArray() ) ) ) {
            return read(in);
        }

    }

    public State read( Reader reader ) throws IOException, ClassNotFoundException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        }
        return read( sb.toString() );
    }

}

package org.bsc.langgraph4j.serializer;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public interface PlainTextSerializer<T> {

    String writeDataAsString(T data) throws IOException;
    T readDataFromString(String string) throws IOException;

    default void writeData(T data, ObjectOutput out) throws IOException {
        String text = writeDataAsString(data);
        Serializer.writeUTF( text, out );
    }

    default T readData(ObjectInput in) throws IOException {
        String text = Serializer.readUTF(in);
        return readDataFromString(text);
    }

}

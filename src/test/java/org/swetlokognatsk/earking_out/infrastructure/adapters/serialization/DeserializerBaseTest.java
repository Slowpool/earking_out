package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import java.lang.reflect.InvocationTargetException;
import org.junit.*;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.json.JsonMapper;

public abstract class DeserializerBaseTest<Type, Deserializer extends StdDeserializer<Type>> {

    protected ObjectMapper objectMapper;

    protected abstract Class<Deserializer> getTestedDeserializerClass();

    protected abstract Class<Type> getTestedDeserializerType();

    @Before
    public void setup() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        var deserializer = getTestedDeserializerClass()
                .getDeclaredConstructor(Class.class)
                .newInstance(getTestedDeserializerType());
        var module = new SimpleModule()
                .addDeserializer(getTestedDeserializerType(), deserializer);

        addExtraSerializers(module);

        objectMapper = JsonMapper.builder()
                .addModule(module)
                .build();
    }

    protected void addExtraSerializers(final SimpleModule module) {
    }

    protected final <T> T readValue(final String serializedValue, final Class<T> valueClass) {
        return objectMapper.readValue(serializedValue, valueClass);
    }
}

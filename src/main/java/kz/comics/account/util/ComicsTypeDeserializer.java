package kz.comics.account.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import kz.comics.account.model.comics.ComicsType;

import java.io.IOException;
import java.util.Locale;

public class ComicsTypeDeserializer extends StdDeserializer<ComicsType> {

    public ComicsTypeDeserializer() {
        super(ComicsType.class);
    }

    @Override
    public ComicsType deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String value = jsonParser.readValueAs(String.class);
        return ComicsType.valueOf(value.toUpperCase(Locale.ROOT));
    }
}

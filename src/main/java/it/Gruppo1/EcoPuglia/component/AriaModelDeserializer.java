package it.Gruppo1.EcoPuglia.component;

import com.google.gson.*;
import it.Gruppo1.EcoPuglia.model.AriaModel;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;

public class AriaModelDeserializer implements JsonDeserializer<AriaModel> {

    @Override
    public AriaModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        AriaModel ariaModel = new AriaModel();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            if (iterator.hasNext()) {
                Object field = iterator.next();
                if (field instanceof Integer) {
                    System.out.println("Integer");
                    field = entry.getValue().getAsInt();
                } else if (field instanceof String) {
                    System.out.println("String");
                    field = entry.getValue().getAsString();
                } else if (field instanceof LocalDateTime) {
                    System.out.println("LocalDateTime");
                    field = LocalDateTime.parse(entry.getValue().getAsString());
                }
                System.out.println(field.getClass().getName());
            }
        }

        return ariaModel;
    }

}

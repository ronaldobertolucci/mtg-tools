package br.com.bertolucci.mtgtools.shared.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ListToJsonService {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> void listToJson(List<T> list, String filename) throws IOException {

        String jsonInString = gson.toJson(list);

        try (FileWriter file = new FileWriter(filename)) {
            file.write(jsonInString);
        }
    }

}

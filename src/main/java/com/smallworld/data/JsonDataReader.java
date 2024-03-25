package com.smallworld.data;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class JsonDataReader<T> implements IDataReader<T> {
    private List<T> data;
    private final String path;

    public JsonDataReader(String path) {
        this.path = path;
    }

    public List<T> getAlreadyFetchedData(Class<T[]> value) throws IOException {

        if(Objects.isNull(this.data) || this.data.isEmpty()){
            this.readData(value);
        }

        return this.data;
    }

    public void readData(Class<T[]> value) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // convert JSON array to list of transactions
        this.data = Arrays.asList(mapper.readValue(Paths.get(path).toFile(), value));

    }

}

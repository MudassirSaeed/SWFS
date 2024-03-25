package com.smallworld.data;

import java.io.IOException;
import java.util.List;

public interface IDataReader<T> {
    void readData(Class<T[]> value) throws IOException;

    List<T> getAlreadyFetchedData(Class<T[]> value) throws IOException;
}

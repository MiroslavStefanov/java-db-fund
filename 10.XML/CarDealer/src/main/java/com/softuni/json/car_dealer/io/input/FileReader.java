package com.softuni.json.car_dealer.io.input;

import java.io.InputStream;

public interface FileReader {

    String read(String path);

    InputStream readAsStream(String path);
}

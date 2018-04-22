package com.softuni.json.car_dealer.io.input;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Component
public class FileReaderImpl implements FileReader {
    @Override
    public String read(String path) {
        InputStream stream = this.getClass().getResourceAsStream(path);
        try {
            return StreamUtils.copyToString(stream, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public InputStream readAsStream(String path) {
        return this.getClass().getResourceAsStream(path);
    }
}

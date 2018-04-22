package com.softuni.json.car_dealer.io.output;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class FileWriterImpl implements FileWriter {
    @Override
    public void write(String source, String path) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(new File(path));
            writer.write(source);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

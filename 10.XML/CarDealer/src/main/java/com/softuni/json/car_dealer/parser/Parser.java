package com.softuni.json.car_dealer.parser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;

public interface Parser {

    <T> T read(Class<T> objectClass, String fileContent) throws IOException, JAXBException;

    <T> T read(Class<T> objectClass, InputStream fileContent) throws IOException, JAXBException;

    <T> String write(T object) throws IOException, JAXBException;
}

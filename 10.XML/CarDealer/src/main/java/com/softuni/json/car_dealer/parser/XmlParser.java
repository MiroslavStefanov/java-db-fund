package com.softuni.json.car_dealer.parser;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class XmlParser implements Parser {

    @Override
    public <T> T read(Class<T> objectClass, String fileContent) throws IOException, JAXBException {

        JAXBContext context = JAXBContext.newInstance(objectClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(fileContent);
        return objectClass.cast(unmarshaller.unmarshal(reader));
    }

    @Override
    public <T> T read(Class<T> objectClass, InputStream fileContent) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(objectClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return objectClass.cast(unmarshaller.unmarshal(fileContent));
    }

    @Override
    public <T> String write(T object) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);
        return writer.toString();
    }
}

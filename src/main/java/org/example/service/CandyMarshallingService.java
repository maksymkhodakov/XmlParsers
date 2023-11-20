package org.example.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.example.data.Candies;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CandyMarshallingService {
    public void marshal(final Candies candies, final String filepath) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Candies.class);
        Marshaller mar = context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(candies, new File(filepath));
        context.generateSchema(new CustomSchemaOutputResolver());
    }

    public Candies unmarshal(final String filepath) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(Candies.class);
        return (Candies) context.createUnmarshaller()
                .unmarshal(new FileReader(filepath));
    }
}

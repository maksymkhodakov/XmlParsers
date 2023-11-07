package org.example.parsers;

import org.example.data.Candy;
import org.example.data.CandyType;
import org.example.service.adapter.DateAdapter;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StAX {

    public List<Candy> parseDocument(String filePath) {
        List<Candy> candies = new ArrayList<>();
        Candy candy = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(filePath));
            Map<String, Integer> currentMap = null;
            String currentElement = "";
            String key = null;

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    currentElement = startElement.getName().getLocalPart();

                    switch (currentElement) {
                        case "Candy":
                            candy = new Candy();
                            currentMap = null;
                            break;
                        case "Ingredients":
                            currentMap = new HashMap<>();
                            candy.setIngredients(currentMap);
                            break;
                        case "Value":
                            currentMap = new HashMap<>();
                            candy.setValue(currentMap);
                            break;
                    }
                } else if (xmlEvent.isCharacters()) {
                    Characters characters = xmlEvent.asCharacters();
                    String data = characters.getData().trim();
                    if (!data.isEmpty()) {
                        switch (currentElement) {
                            case "Id":
                                candy.setId(Integer.parseInt(data));
                                break;
                            case "Name":
                                candy.setName(data);
                                break;
                            case "Energy":
                                candy.setEnergy(Integer.parseInt(data));
                                break;
                            case "Type":
                                candy.setType(CandyType.valueOf(data));
                                break;
                            case "Production":
                                candy.setProduction(data);
                                break;
                            case "ProductionDate":
                                DateAdapter adapter = new DateAdapter();
                                Date date = adapter.unmarshal(data);
                                candy.setProductionDate(date);
                                break;
                            case "key":
                                key = data;
                                break;
                            case "value":
                                if (key != null && currentMap != null) {
                                    currentMap.put(key, Integer.parseInt(data));
                                    key = null; // Reset key after using
                                }
                                break;
                        }
                    }
                } else if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if ("Candy".equals(endElement.getName().getLocalPart())) {
                        candies.add(candy);
                    }
                    // Reset currentElement at the end of an element
                    currentElement = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candies;
    }
}

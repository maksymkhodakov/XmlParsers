package org.example.parsers;

import org.example.data.Candy;
import org.example.data.CandyType;
import org.example.service.adapter.DateAdapter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SAX extends DefaultHandler {

    private List<Candy> candies;
    private Candy currentCandy;
    private StringBuilder elementValue;
    private String currentElement = "";
    private String mapKey; // Hold the key when parsing <key> within <entry>
    private Map<String, Integer> currentMap;

    public SAX() {
        candies = new ArrayList<>();
    }

    public List<Candy> getCandies() {
        return candies;
    }

    public void parseDocument(String filePath) {
        try {
            File inputFile = new File(filePath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(inputFile, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        elementValue = new StringBuilder();
        if ("Candy".equals(qName)) {
            currentCandy = new Candy();
        } else if ("Ingredients".equals(qName)) {
            currentCandy.setIngredients(new HashMap<>());
            currentMap = currentCandy.getIngredients();
        } else if ("Value".equals(qName)) {
            currentCandy.setValue(new HashMap<>());
            currentMap = currentCandy.getValue();
        }
        currentElement = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "Candy":
                candies.add(currentCandy);
                break;
            case "Id":
                currentCandy.setId(Integer.parseInt(elementValue.toString()));
                break;
            case "Name":
                currentCandy.setName(elementValue.toString());
                break;
            case "Energy":
                currentCandy.setEnergy(Integer.parseInt(elementValue.toString()));
                break;
            case "Type":
                currentCandy.setType(CandyType.valueOf(elementValue.toString()));
                break;
            case "Production":
                currentCandy.setProduction(elementValue.toString());
                break;
            case "ProductionDate":
                DateAdapter adapter = new DateAdapter();
                try {
                    Date date = adapter.unmarshal(elementValue.toString());
                    currentCandy.setProductionDate(date);
                } catch (Exception e) {
                    throw new SAXException(e);
                }
                break;
            case "key":
                mapKey = elementValue.toString();
                break;
            case "value":
                if (mapKey != null) {
                    currentMap.put(mapKey, Integer.parseInt(elementValue.toString()));
                    mapKey = null; // Reset the key after using it
                }
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue.append(new String(ch, start, length).trim());
    }
}



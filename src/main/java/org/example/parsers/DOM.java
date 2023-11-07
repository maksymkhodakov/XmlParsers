package org.example.parsers;

import org.example.data.Candy;
import org.example.data.CandyType;
import org.example.service.adapter.DateAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class DOM {
    public List<Candy> parseDocument(String filePath) {
        List<Candy> candies = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            NodeList nodeList = document.getElementsByTagName("Candy");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);
                Candy candy = new Candy();

                // Use helper method to safely retrieve text content
                candy.setId(Integer.parseInt(getElementTextContent(elem, "Id")));
                candy.setName(getElementTextContent(elem, "Name"));
                candy.setEnergy(Integer.parseInt(getElementTextContent(elem, "Energy")));
                candy.setType(CandyType.valueOf(getElementTextContent(elem, "Type")));
                candy.setIngredients(parseMap((Element) elem.getElementsByTagName("Ingredients").item(0)));
                candy.setValue(parseMap((Element) elem.getElementsByTagName("Value").item(0)));
                candy.setProduction(getElementTextContent(elem, "Production"));

                String date = getElementTextContent(elem, "ProductionDate");
                DateAdapter adapter = new DateAdapter();
                Date productionDate = adapter.unmarshal(date);
                candy.setProductionDate(productionDate);

                candies.add(candy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candies;
    }

    private String getElementTextContent(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else {
            return null; // or throw an exception if this is not a valid state for your application
        }
    }

    private Map<String, Integer> parseMap(Element element) {
        Map<String, Integer> map = new HashMap<>();
        NodeList entryNodes = element.getElementsByTagName("entry");
        for (int i = 0; i < entryNodes.getLength(); i++) {
            Element entryElement = (Element) entryNodes.item(i);
            String key = getElementTextContent(entryElement, "key");
            Integer value = Integer.parseInt(getElementTextContent(entryElement, "value"));
            if (key != null && value != null) {
                map.put(key, value);
            }
        }
        return map;
    }
}

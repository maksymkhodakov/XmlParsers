package org.example.data;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.example.service.adapter.DateAdapter;

import java.util.Date;
import java.util.Map;

@XmlRootElement(name = "Candy")
@XmlType(propOrder = {"id", "name", "energy", "type", "ingredients", "value", "production", "productionDate"})
public class Candy {
    private Integer id;
    private String name;
    private int energy;
    private CandyType type;
    private Map<String, Integer> ingredients;
    private Map<String, Integer> value;
    private String production;
    private Date productionDate;

    public Candy() {
    }

    public Integer getId() {
        return id;
    }

    @XmlElement(name = "Id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement(name = "ProductionDate")
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "Name")
    public void setName(String name) {
        this.name = name;
    }

    public int getEnergy() {
        return energy;
    }

    @XmlElement(name = "Energy")
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public CandyType getType() {
        return type;
    }

    @XmlElement(name = "Type")
    public void setType(CandyType type) {
        this.type = type;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    @XmlElementWrapper(name = "Ingredients")
    @XmlElement(name = "Entry")
    public void setIngredients(Map<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public Map<String, Integer> getValue() {
        return value;
    }

    @XmlElementWrapper(name = "Value")
    @XmlElement(name = "Entry")
    public void setValue(Map<String, Integer> value) {
        this.value = value;
    }

    public String getProduction() {
        return production;
    }

    @XmlElement(name = "Production")
    public void setProduction(String production) {
        this.production = production;
    }

    @Override
    public String toString() {
        return "Candy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", energy=" + energy +
                ", type=" + type +
                ", ingredients=" + ingredients +
                ", value=" + value +
                ", production='" + production + '\'' +
                ", productionDate=" + productionDate +
                '}';
    }
}
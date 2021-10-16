package main.java.com.fedorov.dto;
/*
 *
 *@Data 16.10.2021
 *@autor Fedorov Yuri
 *@project testJava
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс для хранения информации о пакетах и их зависимостях.
 */
public class Package{
    //уникальное имя пакета
    private final String name;
    //список пакетов, от которых зависит данный
    private final List<Package> dependencies = new ArrayList<>();

    public Package(String name)  {
        this.name = name;
    }

    public List<Package> getDependencies() {
        return dependencies;
    }

    public String getName(){
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return getName().equals(aPackage.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Package{" +
                "name='" + name + '\'' +
                '}';
    }
}

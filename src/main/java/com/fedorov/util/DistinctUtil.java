package main.java.com.fedorov.util;


import main.java.com.fedorov.dto.Package;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DistinctUtil {
    public static int[] task1(int[] a) {
        return Arrays.stream(a).peek(item -> {
            if (item < 0)
                throw new IllegalArgumentException("массив содержит значение меньше 0");
        }).distinct().toArray();
    }

    public static List<Package> task1(List<Package> packages) {
        return packages.stream().peek(item -> {
            if (item.getName().isEmpty())
                throw new IllegalArgumentException("массив содержит значение меньше 0");
        }).distinct().collect(Collectors.toList());
    }
}

package test.java;


import main.java.com.fedorov.dto.Package;
import main.java.com.fedorov.util.DistinctUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DistinctUtilTest {
    @Test
    public void testtask1Array() {
        int[] arr = new int[]{2, 1, 4, 2, 3};
        int[] expected = new int[]{2, 1, 4, 3};
        int[] actual = DistinctUtil.task1(arr);
        Assert.assertArrayEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testtask1ArrayExeption() {
        int[] arr = new int[]{2, 1, -4, 2, 3};
        int[] actual = DistinctUtil.task1(arr);
    }


    @Test
    public void testtask1List() {
        List<Package> packages = given();
        String expected = "[Package{name='d'}, Package{name='c'}, Package{name='f'}, Package{name='g'}, Package{name='i'}, Package{name='j'}]";
        List<Package> actual = DistinctUtil.task1(packages);
        Assert.assertEquals(expected, actual.toString());
    }

    private List<Package> given() {
        List<Package> packages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            char ch = (char) ('b' + (i % 3 == 0 ? 2 : i));
            packages.add(new Package("" + ch));
        }
        return packages;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testtask1ListExeption() {
        List<Package> packages = given();
        packages.add(new Package(""));
        List<Package> actual = DistinctUtil.task1(packages);
    }
}

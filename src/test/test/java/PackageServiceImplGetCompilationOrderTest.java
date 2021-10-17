package test.java;


import main.java.com.fedorov.dto.Package;
import main.java.com.fedorov.packageService.impl.PackageServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static test.java.util.CreatePackagesUtil.given;

public class PackageServiceImplGetCompilationOrderTest extends PackageServiceImpl {
    /**
     * 0  1 (2) 3   1  2  3  4
     * для проекта A->B->C->D , B->E->T->C
     */
    @Test
    public void testfillStepZero() {
        List<Package> packages = given("a", "b", "c", "d");
        packages.get(0).getDependencies().get(0).getDependencies().addAll(given("e", "t", "c"));
        Map<Package, Boolean> map = fillFreedomPackage(packages);
        String expected = "{Package{name='a'}=false, Package{name='b'}=false, Package{name='c'}=false, Package{name='t'}=false, Package{name='d'}=false, Package{name='e'}=false}";
        Assert.assertEquals(expected, map.toString());
    }


    /**
     * 0  1 (2) 3   1  2  3  4
     * для проекта A->B->C->D , B->E->T->C
     */
    @Test
    public void testfillLastStepEveryPackage() {
        List<Package> packages = given("a", "b", "c", "d");
        packages.get(0).getDependencies().get(0).getDependencies().addAll(given("e", "t", "c"));
        fillLastStepEveryPackage(0, packages);
        System.out.println(packages);
        String expected = "{Package{name='a'}=0, Package{name='b'}=1, Package{name='c'}=4, Package{name='t'}=3, Package{name='d'}=3, Package{name='e'}=2}";
        Assert.assertEquals(expected, lastSteps.toString());
    }

    /**
     * для проекта A->B->C, D->C
     * порядок компиляции может быть C, B, A, D или C, D, B, A или C, B, D, A
     */
    @Test
    public void testgetCompilationOrderTest() {
        List<Package> packages = given("a", "b", "c");
        packages.addAll(given("d", "c"));
        List<Package> actual = getCompilationOrder(packages);
        String expected = "[Package{name='c'}, Package{name='b'}, Package{name='d'}, Package{name='a'}]";
        Assert.assertEquals(expected, actual.toString());
    }

    /**
     * для проекта A->B->C->D , B->E->T->C
     * порядок компиляции может быть D,C,T,E,B,A
     */
    @Test
    public void testgetCompilationOrderTest2() {
        List<Package> packages = given("a", "b", "c", "d");
        packages.get(0).getDependencies().get(0).getDependencies().addAll(given("e", "t", "c"));
        List<Package> actual = getCompilationOrder(packages);
        String expected = "[Package{name='d'}, Package{name='c'}, Package{name='t'}, Package{name='e'}, Package{name='b'}, Package{name='a'}]";
        Assert.assertEquals(expected, actual.toString());
    }

    /**
     * для проекта A->B->E->T->C  +B->C->D
     * порядок компиляции может быть D,C,T,E,B,A
     */
    @Test
    public void testgetCompilationOrderTest3() {
        List<Package> packages = given("a", "b", "e", "t", "c");
        packages.get(0).getDependencies().get(0).getDependencies().addAll(given("c", "d"));
        List<Package> actual = getCompilationOrder(packages);
        String expected = "[Package{name='d'}, Package{name='c'}, Package{name='t'}, Package{name='e'}, Package{name='b'}, Package{name='a'}]";
        Assert.assertEquals(expected, actual.toString());
    }

    /**
     * для проекта A->B->C->D->G  +  B->S->E->G  +  C->E
     * порядок компиляции может быть G, D, E, S, C, B, A
     */
    @Test
    public void testgetCompilationOrderTest4() {
        List<Package> packages = given("a", "b", "c", "d", "g");
        packages.get(0).getDependencies().get(0).getDependencies().addAll(given("s", "e", "g"));
        packages.get(0).getDependencies().get(0).getDependencies().get(0).getDependencies().addAll(given("e"));
        List<Package> actual = getCompilationOrder(packages);
        String expected = "[Package{name='g'}, Package{name='d'}, Package{name='e'}, Package{name='s'}, Package{name='c'}, Package{name='b'}, Package{name='a'}]";
        Assert.assertEquals(expected, actual.toString());
    }
}

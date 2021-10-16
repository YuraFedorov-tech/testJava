package test.java;
/*
 *
 *@Data 16.10.2021
 *@autor Fedorov Yuri
 *@project testJava
 *
 */

import main.java.com.fedorov.dto.Package;
import main.java.com.fedorov.packageService.impl.PackageServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static test.java.util.CreatePackagesUtil.given;

public class PackageServiceImplHasCircularDependesyTest extends PackageServiceImpl {
    @Test
    public void testisCurPackageConsistOfInVisitPackage() {
        List<Package> packages = given("a", "b", "c");
        boolean actual = isCurPackageConsistOfInVisitPackage(new Package("a"), packages);
        Assert.assertEquals(true, actual);
        actual = isCurPackageConsistOfInVisitPackage(new Package("aaaa"), packages);
        Assert.assertEquals(false, actual);
    }




    /**
     * A->B->C->A – цикл есть
     * A->B->C->B – цикл есть
     * B->B – цикл есть
     * B – цикла нет
     */
    @Test
    public void testhasCyclicDependencies() {
        List<Package> packages = given("a", "b", "c", "a");
        boolean actual = hasCyclicDependencies(packages);
        Assert.assertEquals(true, actual);

        packages = given("a", "b", "c", "b");
        actual = hasCyclicDependencies(packages);
        Assert.assertEquals(true, actual);

        packages = given("b", "b");
        actual = hasCyclicDependencies(packages);
        Assert.assertEquals(true, actual);

        packages = given("b");
        actual = hasCyclicDependencies(packages);
        Assert.assertEquals(false, actual);
    }

    /**
     * A->B->C->G  + B->D->C  – цикла нет
     * A->B->C->G  + B->D->C->A  – цикл ecmь
     */
    @Test
    public void testhasCyclicDependencies2() {

        List<Package> packages = packages = given("a", "b", "c", "g");
        packages.get(0).getDependencies().get(0).getDependencies().addAll(given("d", "c"));
        boolean actual = hasCyclicDependencies(packages);
        Assert.assertEquals(false, actual);

        packages = packages = given("a", "b", "c", "g");
        packages.get(0).getDependencies().get(0).getDependencies().addAll(given("d", "c", "a"));
        actual = hasCyclicDependencies(packages);
        Assert.assertEquals(true, actual);
    }


}

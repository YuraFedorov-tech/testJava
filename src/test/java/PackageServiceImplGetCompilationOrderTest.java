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

public class PackageServiceImplGetCompilationOrderTest extends PackageServiceImpl {

    /**
     * Например, для проекта A->B->C, D->C
     * порядок компиляции может быть C, B, A, D или C, D, B, A или C, B, D, A
     */
    @Test
    public void testhasCyclicDependencies() {
        List<Package> packages =given("a", "b", "c");

        boolean actual = hasCyclicDependencies(packages);
        Assert.assertEquals(true, actual);


    }

}

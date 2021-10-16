package main.java.com.fedorov.packageService;

import main.java.com.fedorov.dto.Package;

import java.util.List;

/*
 *
 *@Data 16.10.2021
 *@autor Fedorov Yuri
 *@project testJava
 *
 */
public interface PackageService {

    Boolean hasCyclicDependencies(List<Package> packages);
}

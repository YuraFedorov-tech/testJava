package main.java.com.fedorov.packageService;

import main.java.com.fedorov.dto.Package;

import java.util.List;


public interface PackageService {

    Boolean hasCyclicDependencies(List<Package> packages);

    List<Package> getCompilationOrder(List<Package> packages);
}

package main.java.com.fedorov.packageService.impl;
/*
 *
 *@Data 16.10.2021
 *@autor Fedorov Yuri
 *@project testJava
 *
 */

import main.java.com.fedorov.dto.Package;
import main.java.com.fedorov.packageService.PackageService;
import main.java.com.fedorov.validator.Validator;
import main.java.com.fedorov.validator.impl.ValidatorImpl;

import java.util.ArrayList;
import java.util.List;

public class PackageServiceImpl implements PackageService {

    private Validator validator = new ValidatorImpl();

    @Override
    public Boolean hasCyclicDependencies(List<Package> packages) {
        validator.validate(packages);
        Package pacageRoot = new Package("root");
        pacageRoot.getDependencies().addAll(packages);
        return hasCyclicDependenciesRekursia(0, pacageRoot, new ArrayList<Package>());
    }

    private boolean hasCyclicDependenciesRekursia(int count, Package curPackage,
                                                  List<Package> visitPackage) {
        if (isCurPackageConsistOfInVisitPackage(curPackage, visitPackage)) {
            return true;
        }
        List<Package> packages = curPackage.getDependencies();
        for (int i = 0; i < packages.size(); i++) {
            Package newCurPackage = packages.get(i);
            visitPackage.add(curPackage);
            boolean ans = hasCyclicDependenciesRekursia(count + 1, newCurPackage, visitPackage);
            if (ans) {
                return true;
            }
            visitPackage.remove(visitPackage.size() - 1);
        }
        return false;
    }

    protected boolean isCurPackageConsistOfInVisitPackage(Package curPackage, List<Package> visitPackage) {
        return visitPackage.contains(curPackage);
    }
}

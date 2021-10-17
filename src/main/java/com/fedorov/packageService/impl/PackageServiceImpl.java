package main.java.com.fedorov.packageService.impl;


import main.java.com.fedorov.dto.Package;
import main.java.com.fedorov.packageService.PackageService;
import main.java.com.fedorov.util.DistinctUtil;
import main.java.com.fedorov.validator.Validator;
import main.java.com.fedorov.validator.impl.ValidatorImpl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PackageServiceImpl implements PackageService {

    private Validator validator = new ValidatorImpl();
    private Map<Package, Boolean> freedomPackage = new HashMap();
    private int countMax = 0;

    protected Map<Package, Integer> lastSteps = new HashMap();

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


    @Override
    public List<Package> getCompilationOrder(List<Package> packages) {
        lastSteps = new HashMap();
        freedomPackage = fillFreedomPackage(packages);
        countMax = 0;
        fillLastStepEveryPackage(0, packages);
        List<Package> orderPackages = new ArrayList<Package>();
        while (!isAllPackagesFreedom()) {
            getCompilationOrderRekursia(0, packages, orderPackages);
        }
        Collections.reverse(orderPackages);
        orderPackages = DistinctUtil.task1(orderPackages);
        return orderPackages;
    }

    protected Map<Package, Boolean> fillFreedomPackage(List<Package> packages) {
        Set<Package> set = new HashSet<>();
        fillStepZeroRekursia(set, packages);
        List<Package> items = new ArrayList<>(set);
        Map<Package, Boolean> map = items.stream().collect(Collectors.toMap(Function.identity(), voulue -> false));
        return map;
    }

    private void fillStepZeroRekursia(Set<Package> set, List<Package> packages) {
        if (packages.isEmpty())
            return;
        List<Package> newPackages = new ArrayList<>();
        packages.forEach(item -> {
            newPackages.addAll(item.getDependencies());
        });
        set.addAll(packages);
        fillStepZeroRekursia(set, newPackages);

    }

    protected void fillLastStepEveryPackage(int count, List<Package> packages) {
        if (packages.isEmpty())
            return;
        List<Package> newPackages = new ArrayList<>();
        packages.forEach(item -> {
            newPackages.addAll(item.getDependencies());
            lastSteps.merge(item, count, (oldValue, newValue) -> newValue);
        });
        fillLastStepEveryPackage(count + 1, newPackages);
    }

    private List<Package> getCompilationOrderRekursia(int count, List<Package> packages, List<Package> orderPackages) {
        if (isAllPackagesFreedom()) {
            return orderPackages;
        }
        for (int i = 0; i < packages.size(); i++) {
            Package item = packages.get(i);
            countMax = countMax > count ? countMax : count;
            if (isMayCountThisTop(item, count)) {
                List<Package> nextPackages = new ArrayList<>();
                nextPackages.addAll(item.getDependencies());
                if (!freedomPackage.get(item)) {
                    freedomPackage.put(item, true);
                    orderPackages.add(item);
                }
                getCompilationOrderRekursia(count + 1, nextPackages, orderPackages);
            }
        }
        return orderPackages;
    }

    private boolean isMayCountThisTop(Package item, int count) {
        return absentLessFreedomTop(item, count) && (countMax >= lastSteps.get(item) || freedomPackage.get(item));
    }

    private boolean absentLessFreedomTop(Package item, int count) {
        return lastSteps.entrySet().stream()
                .filter(itemIt -> !itemIt.equals(item))
                .filter(itemIt -> itemIt.getValue() < count)
                .allMatch(itemIt -> freedomPackage.get(itemIt.getKey()));
    }

    private boolean isAllPackagesFreedom() {
        return freedomPackage.entrySet().stream().allMatch(entry -> entry.getValue());
    }

}

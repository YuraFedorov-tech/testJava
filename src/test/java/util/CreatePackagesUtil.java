package test.java.util;


import main.java.com.fedorov.dto.Package;

import java.util.List;

public class CreatePackagesUtil {
    public static List<Package> given(String... names) {
        Package root = new Package("root");
        givenPackagesRekur(0, names, root);
        return root.getDependencies();
    }

    private static void givenPackagesRekur(int count, String[] names, Package curPackage) {
        if (count == names.length)
            return;
        Package aPackage = new Package(names[count]);
        curPackage.getDependencies().add(aPackage);
        givenPackagesRekur(count + 1, names, aPackage);
    }
}

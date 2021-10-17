package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({DistinctUtilTest.class,
        PackageServiceImplGetCompilationOrderTest.class,
        PackageServiceImplHasCircularDependesyTest.class})
public class AllTests {

}

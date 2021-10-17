package main.java.com.fedorov.dto;


import main.java.com.fedorov.packageService.PackageService;
import main.java.com.fedorov.packageService.impl.PackageServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для хранения информации о проекте и входящих в него пакетах.
 */
public class Project {
    //имя проекта
    private final String name;
    //список пакетов проекта
    private final List<Package> packages = new ArrayList<>();

    // наличие этого сервиса и его методов нарушает принцип единной ответсвенности SOLID
    private PackageService packageService = new PackageServiceImpl();

    public Project(String name) {
        this.name = name;
    }

    /**
     * Проверяет наличие циклических зависимостей между пакетами проекта.
     * Когда мы говорим "A зависит от B (или A -> B)", имеется в виду, что пакет B
     * содержится списке зависимостей (dependencies) пакета A.
     * Например, есть проект1, в нем задан список пакетов - packages = [A]
     * при зависимостях A->B->C->A – цикл есть
     * при зависимостях A->B->D, A->C->D – цикла нет
     * <p>
     * Обращаем ваше внимание, что в packages у проекта не обязательно находятся все пакеты. В данном примере там находится только один пакет A.
     */
    /**
     * Добавленно неявное условие:
     * при зависимостях A->A – цикл есть
     * * при зависимостях A->B->D->B – цикл есть
     */
    public Boolean hasCyclicDependencies() {
        return packageService.hasCyclicDependencies(packages);
    }

    /**
     * Упорядоченный список пакетов проекта для компиляции с учетом их
     * зависимостей. Скомпилировать пакет мы можем только после успешной компиляции его зависимостей.
     * Например, для проекта A->B->C, D->C
     * порядок компиляции может быть C, B, A, D или C, D, B, A или C, B, D, A
     */
    public List<Package> getCompilationOrder() {
        // эта проверка должна находиться в другом месте, где то выше
        if (hasCyclicDependencies()) {
            throw new IllegalArgumentException("Есть циклические зависимости");
        }
        return packageService.getCompilationOrder(packages);
    }
}


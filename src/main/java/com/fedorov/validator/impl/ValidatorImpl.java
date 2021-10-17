package main.java.com.fedorov.validator.impl;


import main.java.com.fedorov.dto.Package;
import main.java.com.fedorov.validator.Validator;

import java.util.List;

public class ValidatorImpl implements Validator {
    @Override
    public void validate(List<Package> packages) {
        if (validateNull(packages)) {
            throw new IllegalArgumentException("массив содержит значение меньше 0");
        }

    }

    private boolean validateNull(List<Package> packages) {
        return packages.isEmpty();
    }
}

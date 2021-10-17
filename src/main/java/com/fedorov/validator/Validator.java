package main.java.com.fedorov.validator;

import main.java.com.fedorov.dto.Package;

import java.util.List;

public interface Validator {

    public void validate(List<Package> packages);
}

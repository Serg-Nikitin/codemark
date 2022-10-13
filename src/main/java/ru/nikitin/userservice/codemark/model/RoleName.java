package ru.nikitin.userservice.codemark.model;

import java.util.HashSet;
import java.util.Set;

public enum RoleName {
    LEAD,
    OPERATOR,
    ANALYST,
    DEVELOPER,
    EMPLOYEE,
    ADMIN;

    private static Set<String> setRole = new HashSet<>();

    static {
        setRole.add(LEAD.name());
        setRole.add(OPERATOR.name());
        setRole.add(ANALYST.name());
        setRole.add(DEVELOPER.name());
        setRole.add(EMPLOYEE.name());
        setRole.add(ADMIN.name());
    }

    public static Set<String> set() {
        return new HashSet<>(setRole);
    }

}

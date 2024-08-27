package com.judele.raci.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RaciRole {
    ACCOUNTABLE("Accountable", "A"),
    ACCOUNTABLE_RESPONSIBLE("Accountable & Responsible", "AR"),
    RESPONSIBLE("Responsible", "R"),
    CONSULTED("Consulted", "C"),
    INFORMED("Informed", "I");
    private final String friendlyName;
    private final String symbol;

    public static RaciRole fromSymbol(String symbol) {
        for (RaciRole role : RaciRole.values()) {
            if (role.symbol.equals(symbol)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid RaciRole: " + symbol);
    }
}

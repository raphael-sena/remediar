package com.remediar.back_remediar.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFouCNPJValidator implements ConstraintValidator<CPFouCNPJ, String> {

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        if (valor == null || valor.isBlank()) return false;

        String doc = valor.replaceAll("\\D", "");
        if (doc.length() == 11) {
            return isValidCPF(doc);
        } else if (doc.length() == 14) {
            return isValidCNPJ(doc);
        } else {
            return false;
        }
    }

    private boolean isValidCPF(String cpf) {
        if (cpf.chars().distinct().count() == 1) return false;
        int d1 = 0, d2 = 0;
        for (int i = 0; i < 9; i++) {
            d1 += (10 - i) * Character.getNumericValue(cpf.charAt(i));
            d2 += (11 - i) * Character.getNumericValue(cpf.charAt(i));
        }
        d1 = (d1 * 10 % 11) % 10;
        d2 += d1 * 2;
        d2 = (d2 * 10 % 11) % 10;
        return d1 == Character.getNumericValue(cpf.charAt(9)) &&
                d2 == Character.getNumericValue(cpf.charAt(10));
    }

    private boolean isValidCNPJ(String cnpj) {
        if (cnpj.chars().distinct().count() == 1) return false;
        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int d1 = 0, d2 = 0;
        for (int i = 0; i < 12; i++) {
            d1 += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
            d2 += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
        }
        d1 = d1 % 11;
        d1 = d1 < 2 ? 0 : 11 - d1;
        d2 += d1 * pesos2[12];
        d2 = d2 % 11;
        d2 = d2 < 2 ? 0 : 11 - d2;
        return d1 == Character.getNumericValue(cnpj.charAt(12)) &&
                d2 == Character.getNumericValue(cnpj.charAt(13));
    }
}


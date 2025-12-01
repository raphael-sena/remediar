package com.remediar.back_remediar.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CPFouCNPJValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFouCNPJ {
    String message() default "Documento inválido (deve ser um CPF ou CNPJ válido)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


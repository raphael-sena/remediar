package com.remediar.back_remediar.model;

import com.remediar.back_remediar.model.enums.CategoriaPortaria;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicamentoControlado extends Medicamento {

    @Enumerated
    private CategoriaPortaria categoriaPortaria;
}

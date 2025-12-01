package com.remediar.back_remediar.model.enums;

public enum Genero {
    MASCULINO("Masculino"),        
    FEMININO("Feminino"),  
    OUTRO("Outro");         

    private String descricao;

    Genero(String descricao) {
           this.descricao = descricao;
        }
    
    @Override
    public String toString() {
        return descricao; 
    }
}

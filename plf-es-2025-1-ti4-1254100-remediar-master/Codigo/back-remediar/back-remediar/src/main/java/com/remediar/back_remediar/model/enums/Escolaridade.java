package com.remediar.back_remediar.model.enums;

public enum Escolaridade {

    ENSINO_FUNDAMENTAL("Ensino Fundamental"),        
    ENSINO_MEDIO("Ensino Médio"),  
    SUPERIOR_COMPLETO("Superior Completo"),
    POS_GRADUACAO("Pós Graduação");         

    private String descricao;

    Escolaridade(String descricao) {
           this.descricao = descricao;
        }
    
    @Override
    public String toString() {
        return descricao; 
    }
    
}

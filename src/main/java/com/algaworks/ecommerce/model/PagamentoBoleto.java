package com.algaworks.ecommerce.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("BOL")
@Entity
public class PagamentoBoleto extends Pagamento {
	
    @Column(name = "codigo_barras")
    private String codigoBarras;
    
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;
}

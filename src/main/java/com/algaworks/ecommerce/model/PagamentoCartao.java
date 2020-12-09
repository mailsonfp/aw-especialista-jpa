package com.algaworks.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("CRT")
@Entity
public class PagamentoCartao extends Pagamento {
	
	@Column(name = "numero_cartao")
    private String numeroCartao;
}

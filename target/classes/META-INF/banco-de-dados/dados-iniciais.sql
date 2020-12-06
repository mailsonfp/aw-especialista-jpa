insert into Produto (id, nome, preco, descricao) values (1, 'Kindle', 499.0, 'Conhe�a o novo Kindle, agora com ilumina��o embutida ajust�vel, que permite que voc� leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into Produto (id, nome, preco, descricao) values (3, 'C�mera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.');

insert into Cliente (id, nome, sexo) values (1, 'Fernando Medeiros', 'MASCULINO');
insert into Cliente (id, nome, sexo) values (2, 'Marcos Mariano', 'MASCULINO');

insert into pedido (id, cliente_id, data_pedido, total, status) values (1, 1, sysdate(), 100.0, 'AGUARDANDO');
insert into pedido_item (id, pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 1, 5.0, 2);

insert into categoria (id, nome) values (1, 'Eletr�nicos');
insert into produto (id, versao, nome, preco, data_criacao, ativo, descricao) values (1, 0, 'Kindle', 799.0, date_sub(sysdate(), interval 1 day), 'SIM', 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto (id, versao, nome, preco, data_criacao, ativo, descricao) values (3, 0, 'Câmera GoPro Hero 7', 1500.0, date_sub(sysdate(), interval 1 day), 'SIM', 'Desempenho 2x melhor.');
insert into produto (id, versao, nome, preco, data_criacao, ativo, descricao) values (4, 0, 'Câmera Canon 80D', 3500.0, sysdate(), 'SIM', 'O melhor ajuste de foco.');
insert into produto (id, versao, nome, preco, data_criacao, ativo, descricao) values (5, 0, 'Microfone de Lapela', 50.0, sysdate(), 'NAO', 'Produto massa');

insert into Cliente (id, nome, sexo) values (1, 'Fernando Medeiros', 'MASCULINO');
insert into Cliente (id, nome, sexo) values (2, 'Marcos Mariano', 'MASCULINO');

insert into pedido (id, cliente_id, data_criacao, total, status) values (1, 1,  date_sub(sysdate(), interval 5 day), 2398, 'AGUARDANDO');
insert into pedido_item (pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 499, 2);
insert into pedido_item (pedido_id, produto_id, preco_produto, quantidade) values (1, 3, 1400, 1);
insert into pagamento (pedido_id, status, tipo_pagamento, numero_cartao, codigo_barras) values (1, 'RECEBIDO', 'BOL', '0123', null);

insert into pedido (id, cliente_id, data_criacao, total, status) values (2, 1, sysdate(), 499.0, 'AGUARDANDO');
insert into pedido_item (pedido_id, produto_id, preco_produto, quantidade) values (2, 1, 499, 1);
insert into nota_fiscal (pedido_id, xml, data_emissao) values (2, '<xml />', sysdate());
insert into pagamento (pedido_id, status, numero_cartao, tipo_pagamento) values (2, 'PROCESSANDO', '123', 'CRT');

insert into pedido (id, cliente_id, data_criacao, total, status) values (3, 1, date_sub(sysdate(), interval 4 day), 3500.0, 'PAGO');
insert into pedido_item (pedido_id, produto_id, preco_produto, quantidade) values (3, 4, 3500, 1);
insert into pagamento (pedido_id, status, tipo_pagamento, numero_cartao, codigo_barras) values (3, 'RECEBIDO', 'BOL', null, '8910');

insert into pedido (id, cliente_id, data_criacao, total, status) values (4, 2, date_sub(sysdate(), interval 2 day), 499.0, 'PAGO');
insert into pedido_item (pedido_id, produto_id, preco_produto, quantidade) values (4, 1, 499, 1);
insert into pagamento (pedido_id, status, tipo_pagamento, numero_cartao, codigo_barras) values (4, 'PROCESSANDO', 'CRT', '1112', null);

insert into pedido (id, cliente_id, data_criacao, total, status) values (5, 1, date_sub(sysdate(), interval 2 day), 799.0, 'PAGO');
insert into pedido_item (pedido_id, produto_id, preco_produto, quantidade) values (5, 1, 799, 1);

insert into pedido (id, cliente_id, data_criacao, total, status) values (6, 2, sysdate(), 799.0, 'AGUARDANDO');
insert into pedido_item (pedido_id, produto_id, preco_produto, quantidade) values (6, 1, 799, 1);
insert into pagamento (pedido_id, status, tipo_pagamento, numero_cartao, codigo_barras, data_vencimento) values (6, 'PROCESSANDO', 'BOL', null, '456', date_add(sysdate(), interval 2 day));

insert into categoria (id, nome) values (1, 'Eletrodomésticos');
insert into categoria (id, nome) values (2, 'Livros');
insert into categoria (id, nome) values (3, 'Esportes');
insert into categoria (id, nome) values (4, 'Futebol');
insert into categoria (id, nome) values (5, 'Natação');
insert into categoria (id, nome) values (6, 'Notebooks');
insert into categoria (id, nome) values (7, 'Smartphones');
insert into categoria (id, nome) values (8, 'Câmeras');

insert into produto_categoria (produto_id, categoria_id) values (1, 2);
insert into produto_categoria (produto_id, categoria_id) values (3, 8);
insert into produto_categoria (produto_id, categoria_id) values (4, 8);

drop table produto_loja;
create table produto_loja (id integer not null auto_increment, nome varchar(100), descricao longtext, preco decimal(19, 2), data_criacao datetime(6), data_ultima_atualizacao datetime(6), foto longblob, primary key (id)) engine=InnoDB;

drop table ecm_produto;
create table ecm_produto (prd_id integer not null auto_increment, prd_nome varchar(100), prd_descricao longtext, prd_preco decimal(19, 2), prd_data_criacao datetime(6), prd_data_ultima_atualizacao datetime(6), prd_foto longblob, primary key (prd_id)) engine=InnoDB;

drop table erp_produto;
create table erp_produto (id integer not null auto_increment, nome varchar(100), descricao longtext, preco decimal(19, 2), primary key (id)) engine=InnoDB;

insert into produto_loja (id, nome, preco, data_criacao, descricao) values (101, 'Kindle', 799.0, date_sub(sysdate(), interval 1 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto_loja (id, nome, preco, data_criacao, descricao) values (103, 'Câmera GoPro Hero 7', 1500.0, date_sub(sysdate(), interval 1 day), 'Desempenho 2x melhor.');
insert into produto_loja (id, nome, preco, data_criacao, descricao) values (104, 'Câmera Canon 80D', 3500.0, sysdate(), 'O melhor ajuste de foco.');
insert into produto_loja (id, nome, preco, data_criacao, descricao) values (105, 'Microfone de Lapela', 50.0, sysdate(), 'Produto massa');

insert into ecm_produto (prd_id, prd_nome, prd_preco, prd_data_criacao, prd_descricao) values (201, 'Kindle', 799.0, date_sub(sysdate(), interval 1 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into ecm_produto (prd_id, prd_nome, prd_preco, prd_data_criacao, prd_descricao) values (203, 'Câmera GoPro Hero 7', 1500.0, date_sub(sysdate(), interval 1 day), 'Desempenho 2x melhor.');
insert into ecm_produto (prd_id, prd_nome, prd_preco, prd_data_criacao, prd_descricao) values (204, 'Câmera Canon 80D', 3500.0, sysdate(), 'O melhor ajuste de foco.');
insert into ecm_produto (prd_id, prd_nome, prd_preco, prd_data_criacao, prd_descricao) values (205, 'Microfone de Lapela', 50.0, sysdate(), 'Produto massa');

insert into erp_produto (id, nome, preco, descricao) values (301, 'Kindle', 799.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into erp_produto (id, nome, preco, descricao) values (303, 'Câmera GoPro Hero 7', 1500.0, 'Desempenho 2x melhor.');
insert into erp_produto (id, nome, preco, descricao) values (304, 'Câmera Canon 80D', 3500.0, 'O melhor ajuste de foco.');
insert into erp_produto (id, nome, preco, descricao) values (305, 'Microfone de Lapela', 50.0, 'Produto massa');

drop table ecm_categoria;
create table ecm_categoria (cat_id integer not null auto_increment, cat_nome varchar(100), cat_categoria_pai_id integer, primary key (cat_id)) engine=InnoDB;

insert into ecm_categoria (cat_id, cat_nome) values (201, 'Eletrodomésticos');
insert into ecm_categoria (cat_id, cat_nome) values (202, 'Livros');
insert into ecm_categoria (cat_id, cat_nome) values (203, 'Esportes');
insert into ecm_categoria (cat_id, cat_nome) values (204, 'Futebol');
insert into ecm_categoria (cat_id, cat_nome) values (205, 'Natação');
insert into ecm_categoria (cat_id, cat_nome) values (206, 'Notebooks');
insert into ecm_categoria (cat_id, cat_nome) values (207, 'Smartphones');
insert into ecm_categoria (cat_id, cat_nome) values (208, 'Câmeras');

drop function acima_media_faturamento;
create function acima_media_faturamento(valor double) returns boolean reads sql data return valor > (select avg(total) from pedido);

drop procedure buscar_nome_produto;
create procedure buscar_nome_produto(in produto_id int, out produto_nome varchar(255)) begin select nome into produto_nome from produto where id = produto_id; end

drop procedure compraram_acima_media;
create procedure compraram_acima_media(in ano integer) begin select cli.*, clid.* from cliente cli left join cliente_detalhe clid on clid.cliente_id = cli.id join pedido ped on ped.cliente_id = cli.id where ped.status = 'PAGO' and year(ped.data_criacao) = ano group by ped.cliente_id having sum(ped.total) >= (select avg(total_por_cliente.sum_total) from (select sum(ped2.total) sum_total from pedido ped2 where ped2.status = 'PAGO' and year(ped2.data_criacao) = ano group by ped2.cliente_id) as total_por_cliente); end

drop procedure ajustar_preco_produto;
create procedure ajustar_preco_produto(in produto_id int, in percentual_ajuste double, out preco_ajustado double) begin declare produto_preco double; select preco into produto_preco from produto where id = produto_id; set preco_ajustado = produto_preco + (produto_preco * percentual_ajuste); update produto set preco = preco_ajustado where id = produto_id; end

drop view view_clientes_acima_media;
create view view_clientes_acima_media as select cli.*, clid.* from cliente cli left join cliente_detalhe clid on clid.cliente_id = cli.id join pedido ped on ped.cliente_id = cli.id where ped.status = 'PAGO' and year(ped.data_criacao) = year(current_date) group by ped.cliente_id having sum(ped.total) >= (select avg(total_por_cliente.sum_total) from (select sum(ped2.total) sum_total from pedido ped2 where ped2.status = 'PAGO' and year(ped2.data_criacao) = year(current_date) group by ped2.cliente_id) as total_por_cliente);


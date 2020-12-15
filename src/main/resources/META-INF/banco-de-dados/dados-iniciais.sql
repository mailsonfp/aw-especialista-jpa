insert into produto (id, nome, preco, data_criacao, descricao) values (1, 'Kindle', 799.0, date_sub(sysdate(), interval 1 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto (id, nome, preco, data_criacao, descricao) values (3, 'Câmera GoPro Hero 7', 1400.0, date_sub(sysdate(), interval 1 day), 'Desempenho 2x melhor.');
insert into produto (id, nome, preco, data_criacao, descricao) values (4, 'Câmera Canon 80D', 3500.0, sysdate(), 'O melhor ajuste de foco.');

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
drop database if exists newway2;
create database newway2;
use newway2;

create table agencia(
    idAgencia int primary key auto_increment,
    nomeAgencia varchar(255) not null,
    cnpj char(41) not null unique,
    telefone char(111) not null unique,
    email varchar(255) not null unique,
    senha varchar(255) not null,
    dataCadastro datetime default current_timestamp,
    dataAtualizacao datetime default current_timestamp on update current_timestamp
);

create table endereco(
    idEndereco int primary key auto_increment,
    cep varchar(8) not null,
    logradouro varchar(255) not null,
    numero varchar(10) not null,
    complemento varchar(255),
    bairro varchar(255) not null,
    cidade varchar(255) not null,
    estado char(2) not null,
    idAgencia int not null,
    constraint fk_endereco_agencia
        foreign key (idAgencia) references agencia(idAgencia),
    dataCadastro datetime default current_timestamp,
    dataAtualizacao datetime default current_timestamp on update current_timestamp
);

create table logs(
    idLogs int primary key auto_increment,
    tipo varchar(255) not null,
    dateTimeLog datetime,
    descricao varchar(255) not null,
    idAgencia int not null,
    constraint fk_logs_agencia
        foreign key (idAgencia) references agencia(idAgencia)
);

create table eventos(
    idEvento int primary key auto_increment,
    nomeEvento varchar(255) not null,
    municipio varchar(255) not null,
    dtInicial date not null,
    dtTermino date not null,
    tipoEvento varchar(255) not null,
    publicoEsperado int not null
);

create table motivo(
    idMotivo int primary key auto_increment,
    porcentagem int not null,
    tipo varchar(255) not null
);

create table lazer(
    idLazer int primary key auto_increment,
    tipoLazer varchar(255) not null,
    porcentagem int not null,
    idMotivo int not null,
    constraint fk_lazer_motivo
        foreign key (idMotivo) references motivo(idMotivo)
);

create table hospedagem(
    idHospedagem int primary key auto_increment,
    tipo varchar(255) not null,
    porcentagem int not null
);

create table gasto(
    idGasto int primary key auto_increment,
    tipo varchar(255) not null,
    porcentagem int not null
);

create table grupo(
    idGrupo int primary key auto_increment,
    tipo varchar(255) not null,
    porcentagem int not null
);

create table grupo_idade(
    idGrupoIdade int primary key auto_increment,
    tipo varchar(255) not null,
    porcentagem int not null
);

create table fonte(
    idFonte int primary key auto_increment,
    tipo varchar(255) not null,
    porcentagem int not null
);

create table servico_agencia(
    idServicoAgencia int primary key auto_increment,
    tipo varchar(255) not null,
    porcentagem int not null
);

create table permanencia(
    idPermanencia int primary key auto_increment,
    tipo varchar(255) not null,
    qtdDias int not null
);

create table localizacao(
    idLocalizacao int primary key auto_increment,
    uf varchar(2) not null,
    cidade varchar(255) not null
);

create table perfil(
    idPerfil int primary key auto_increment,
    idMotivo int not null,
    constraint fk_perfil_motivo
        foreign key (idMotivo) references motivo(idMotivo),
    idGasto int not null,
    constraint fk_perfil_gasto
        foreign key (idGasto) references gasto(idGasto),
    idGrupo int not null,
    constraint fk_perfil_grupo
        foreign key (idGrupo) references grupo(idGrupo),
    idHospedagem int not null,
    constraint fk_perfil_hospedagem
        foreign key (idHospedagem) references hospedagem(idHospedagem),
    idPermanencia int not null,
    constraint fk_perfil_permanencia
        foreign key (idPermanencia) references permanencia(idPermanencia),
    idServicoAgencia int not null,
    constraint fk_perfil_servico_agencia
        foreign key (idServicoAgencia) references servico_agencia(idServicoAgencia),
    idGrupoIdade int not null,
    constraint fk_perfil_grupo_idade
        foreign key (idGrupoIdade) references grupo_idade(idGrupoIdade),
    idFonte int not null,
    constraint fk_perfil_fonte
        foreign key (idFonte) references fonte(idFonte)
);

create table pacote(
    idPacote int primary key auto_increment,
    nomePacote varchar(255) not null,
    categoria varchar(255) not null,
    destino varchar(50) not null,
    duracao int not null,
    preco int not null,
    descricao varchar(350) not null,
    vagas int not null,
    dataInicio varchar(45) not null
);

create table pacotes_agencias(
    idAgencia int not null,
    constraint fk_pacotes_agencias_agencia
        foreign key (idAgencia) references agencia(idAgencia),
    idPacote int not null,
    constraint fk_pacotes_agencias_pacote
        foreign key (idPacote) references pacote(idPacote)
);

create table vendas(
    idVenda int primary key auto_increment,
    dataVenda date not null,
    valor decimal(10,2) not null,
    qtdVenda int not null,
    idAgencia int not null,
    constraint fk_vendas_agencia
        foreign key (idAgencia) references agencia(idAgencia),
    idPacote int not null,
    constraint fk_vendas_pacote
        foreign key (idPacote) references pacote(idPacote),
    dataCadastro datetime default current_timestamp
);

-- ========================
-- INSERTS
-- ========================

INSERT INTO agencia (nomeAgencia, cnpj, telefone, email, senha)
VALUES ('Teste', '12345678901234', '11999999999', 'teste@email.com', '123');

INSERT INTO eventos (nomeEvento, municipio, dtInicial, dtTermino, tipoEvento, publicoEsperado) VALUES
('Rock in Rio',             'Rio de Janeiro',  '2025-09-19', '2025-09-28', 'Festival Musical',  100000),
('Virada Cultural',         'São Paulo',       '2025-05-17', '2025-05-18', 'Cultural',           80000),
('Festa Junina Paulista',   'São Paulo',       '2025-06-12', '2025-06-15', 'Festa Tradicional',  30000),
('Expo BH Negócios',        'Belo Horizonte',  '2025-08-05', '2025-08-08', 'Feira de Negócios',  20000),
('Festival de Inverno',     'Petrópolis',      '2025-07-10', '2025-07-20', 'Cultural',           15000),
('Oktoberfest',             'Blumenau',        '2025-10-03', '2025-10-19', 'Festa Tradicional', 700000),
('Festa da Uva',            'Caxias do Sul',   '2025-02-14', '2025-03-02', 'Festa Tradicional', 400000),
('Festival de Cinema',      'Florianópolis',   '2025-04-07', '2025-04-13', 'Cultural',           12000),
('Anime Friends Sul',       'Curitiba',        '2025-07-25', '2025-07-27', 'Entretenimento',     25000),
('Gramado em Gramado',      'Gramado',         '2025-08-15', '2025-08-24', 'Festival Musical',   50000),
('Carnaval de Salvador',    'Salvador',        '2025-03-01', '2025-03-05', 'Carnaval',         2000000),
('Forró Caju',              'Aracaju',         '2025-06-20', '2025-06-30', 'Festa Tradicional', 200000),
('Micareta de Feira',       'Feira de Santana','2025-04-24', '2025-04-27', 'Carnaval',          500000),
('Maceió Fest',             'Maceió',          '2025-10-17', '2025-10-26', 'Festival Musical',  300000),
('Festival Gastronômico',   'Recife',          '2025-09-05', '2025-09-14', 'Gastronomia',        40000),
('Festival Folclórico',     'Manaus',          '2025-06-27', '2025-07-06', 'Cultural',          150000),
('Círio de Nazaré',         'Belém',           '2025-10-12', '2025-10-12', 'Religioso',        2000000),
('Boi Bumbá',               'Parintins',       '2025-06-27', '2025-06-29', 'Festa Tradicional', 100000),
('Expo Amazônia',           'Manaus',          '2025-08-20', '2025-08-25', 'Feira de Negócios',  30000),
('Festival de Ópera',       'Manaus',          '2025-04-01', '2025-04-30', 'Cultural',           10000),
('Festa do Peão',           'Barretos',        '2025-08-14', '2025-08-24', 'Rodeio',           1000000),
('Festival Gastronômico',   'Campo Grande',    '2025-09-12', '2025-09-21', 'Gastronomia',        50000),
('Cavalgada de Brasília',   'Brasília',        '2025-07-05', '2025-07-06', 'Rodeio',             80000),
('Expo Agronegócio',        'Goiânia',         '2025-05-20', '2025-05-25', 'Feira de Negócios',  60000),
('Festival Cultura Brasil', 'Cuiabá',          '2025-06-01', '2025-06-07', 'Cultural',           20000),
('Carnaval de Porto Seguro',     'Porto Seguro',       '2025-03-01', '2025-03-05', 'Carnaval',           400000),
('Festival de Verão Salvador',   'Salvador',           '2025-01-17', '2025-01-18', 'Festival Musical',   100000),
('São João de Amargosa',         'Amargosa',           '2025-06-20', '2025-06-24', 'Festa Tradicional',   80000),
('Festival da Banana',           'Itaberaba',          '2025-07-10', '2025-07-15', 'Gastronomia',         20000),
('Lavagem do Bonfim',            'Salvador',           '2025-01-16', '2025-01-16', 'Religioso',          800000),
('Carnaval do Recife',           'Recife',             '2025-03-01', '2025-03-05', 'Carnaval',          1500000),
('Festa de São João de Caruaru', 'Caruaru',            '2025-06-01', '2025-06-30', 'Festa Tradicional',  900000),
('Festival Rec-Beat',            'Recife',             '2025-02-28', '2025-03-04', 'Festival Musical',    40000),
('Festa da Pitomba',             'Bezerros',           '2025-07-01', '2025-07-07', 'Gastronomia',         15000),
('Semana da Cultura Nordestina', 'Olinda',             '2025-10-20', '2025-10-26', 'Cultural',            35000),
('Carnaval de Florianópolis',    'Florianópolis',      '2025-03-01', '2025-03-05', 'Carnaval',           150000),
('Bloco da Barra',               'Florianópolis',      '2025-03-01', '2025-03-03', 'Carnaval',            20000),
('Bloco do Suvaco do Cristo',    'Florianópolis',      '2025-03-02', '2025-03-02', 'Carnaval',            15000),
('Sumol Summer Fest',            'Florianópolis',      '2025-01-10', '2025-01-11', 'Festival Musical',    40000),
('Festival Mundo de Música',     'Florianópolis',      '2025-02-14', '2025-02-15', 'Festival Musical',    25000),
('Planeta Atlântida SC',         'Florianópolis',      '2025-02-07', '2025-02-08', 'Festival Musical',    80000),
('Rock na Ilha',                 'Florianópolis',      '2025-04-05', '2025-04-06', 'Festival Musical',    30000),
('Festival de Jazz e Blues',     'Florianópolis',      '2025-07-18', '2025-07-20', 'Festival Musical',    18000),
('Floripa Music Week',           'Florianópolis',      '2025-10-08', '2025-10-12', 'Festival Musical',    22000),
('Festival de Verão Floripa',    'Florianópolis',      '2025-01-24', '2025-01-25', 'Festival Musical',    35000),
('Festival de Cinema de Florianópolis', 'Florianópolis','2025-04-07','2025-04-13', 'Cultural',            12000),
('Floripa Teatro Festival',      'Florianópolis',      '2025-05-15', '2025-05-25', 'Cultural',            10000),
('Festival de Dança de Florianópolis','Florianópolis', '2025-06-06', '2025-06-15', 'Cultural',            20000),
('Arte na Praça',                'Florianópolis',      '2025-09-20', '2025-09-28', 'Cultural',             8000),
('Exposição Açoriana',           'Florianópolis',      '2025-08-01', '2025-08-31', 'Cultural',             5000),
('Festival Internacional de Arte','Florianópolis',     '2025-11-14', '2025-11-23', 'Cultural',            15000),
('Festival Sabores da Ilha',     'Florianópolis',      '2025-09-05', '2025-09-07', 'Gastronomia',         18000),
('Ostravaganza',                 'Florianópolis',      '2025-03-14', '2025-03-16', 'Gastronomia',         12000),
('Festival do Camarão',          'Florianópolis',      '2025-07-25', '2025-07-27', 'Gastronomia',         10000),
('Floripa Beer Fest',            'Florianópolis',      '2025-10-17', '2025-10-19', 'Gastronomia',         20000),
('Festival Gastronômico da Lagoa','Florianópolis',     '2025-11-07', '2025-11-09', 'Gastronomia',          8000),
('Ironman Florianópolis',        'Florianópolis',      '2025-05-25', '2025-05-25', 'Esportivo',           15000),
('Maratona de Florianópolis',    'Florianópolis',      '2025-04-27', '2025-04-27', 'Esportivo',           10000),
('Floripa Surf Pro',             'Florianópolis',      '2025-01-15', '2025-01-19', 'Esportivo',           20000),
('Beach Tennis Open Floripa',    'Florianópolis',      '2025-02-21', '2025-02-23', 'Esportivo',            8000),
('Corrida da Ilha',              'Florianópolis',      '2025-08-10', '2025-08-10', 'Esportivo',            6000),
('Campeonato Catarinense de Vôlei','Florianópolis',    '2025-09-12', '2025-09-14', 'Esportivo',           12000),
('Floripa Tech Summit',          'Florianópolis',      '2025-06-20', '2025-06-21', 'Feira de Negócios',   10000),
('Startup Weekend Floripa',      'Florianópolis',      '2025-08-22', '2025-08-24', 'Feira de Negócios',    3000),
('Expo Turismo SC',              'Florianópolis',      '2025-05-08', '2025-05-11', 'Feira de Negócios',   15000),
('Summit de Inovação Catarinense','Florianópolis',     '2025-10-03', '2025-10-04', 'Feira de Negócios',    8000),
('Festa do Divino Espírito Santo','Florianópolis',     '2025-06-01', '2025-06-08', 'Religioso',           30000),
('Festa Açoriana da Ilha',       'Florianópolis',      '2025-07-04', '2025-07-06', 'Festa Tradicional',   25000),
('Festa de Nossa Senhora do Desterro','Florianópolis', '2025-08-15', '2025-08-15', 'Religioso',           20000),
('Réveillon da Beira Mar',       'Florianópolis',      '2025-12-31', '2026-01-01', 'Festival Musical',   300000),
('Réveillon da Jurerê Internacional','Florianópolis',  '2025-12-31', '2026-01-01', 'Festival Musical',    80000);

INSERT INTO motivo (porcentagem, tipo) VALUES
  (45, 'Lazer'),
  (30, 'Negócios'),
  (15, 'Eventos'),
  (10, 'Saúde');

INSERT INTO hospedagem (tipo, porcentagem) VALUES
  ('Hotel',        50),
  ('Pousada',      25),
  ('Airbnb',       15),
  ('Casa Própria', 10);

INSERT INTO gasto (tipo, porcentagem) VALUES
  ('Baixo (até R$500)',       20),
  ('Médio (R$500 a R$2000)', 50),
  ('Alto (acima de R$2000)', 30);

INSERT INTO grupo (tipo, porcentagem) VALUES
  ('Individual', 30),
  ('Casal',      40),
  ('Família',    20),
  ('Grupo',      10);

INSERT INTO grupo_idade (tipo, porcentagem) VALUES
  ('18 a 25 anos', 20),
  ('26 a 35 anos', 35),
  ('36 a 50 anos', 30),
  ('Acima de 50',  15);

INSERT INTO fonte (tipo, porcentagem) VALUES
  ('Redes Sociais',      40),
  ('Indicação',          30),
  ('Buscadores',         20),
  ('Agência Presencial', 10);

INSERT INTO servico_agencia (tipo, porcentagem) VALUES
  ('Pacote Completo', 55),
  ('Só Hospedagem',   25),
  ('Só Transporte',   20);

INSERT INTO permanencia (tipo, qtdDias) VALUES
  ('1 a 2 dias',    2),
  ('3 a 5 dias',    4),
  ('6 a 10 dias',   8),
  ('Mais de 10 dias', 14);

INSERT INTO localizacao (uf, cidade) VALUES
  ('SP', 'São Paulo'),
  ('RJ', 'Rio de Janeiro'),
  ('BA', 'Salvador'),
  ('SC', 'Florianópolis');

INSERT INTO perfil (idMotivo, idGasto, idGrupo, idHospedagem, idPermanencia, idServicoAgencia, idGrupoIdade, idFonte) VALUES
  (1, 2, 2, 1, 2, 1, 2, 1),
  (1, 3, 3, 2, 3, 1, 3, 2),
  (2, 2, 1, 1, 1, 2, 2, 3),
  (3, 3, 4, 1, 3, 1, 2, 1),
  (1, 1, 2, 3, 2, 3, 1, 1);

INSERT INTO pacote (nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio) VALUES
  ('Pacote Praias RJ',         'Praia',    'Rio de Janeiro', 7,  1600, 'Pacote completo com hospedagem e passeios pelas principais praias do Rio de Janeiro.', 50, '2026-01-10'),
  ('Pacote Família Salvador',  'Família',  'Salvador',       10, 2500, 'Pacote familiar com acomodação em pousada, visitas históricas e experiências culturais em Salvador.', 30, '2026-01-15'),
  ('Pacote Negócios SP',       'Negócios', 'São Paulo',      3,  1900, 'Pacote executivo com hotel 4 estrelas, transfer aeroporto e acesso a centros de convenções em São Paulo.', 20, '2026-02-05'),
  ('Pacote Carnaval Salvador', 'Carnaval', 'Salvador',       5,  1200, 'Pacote especial de Carnaval com camarote, hospedagem central e abadá incluído em Salvador.', 40, '2026-02-20'),
  ('Pacote Econômico Floripa', 'Praia',    'Florianópolis',  4,  1050, 'Pacote econômico com hospedagem em Florianópolis e acesso às melhores praias da ilha.', 25, '2026-03-08');

INSERT INTO vendas (dataVenda, valor, qtdVenda, idAgencia, idPacote) VALUES
  ('2026-01-10', 3200.00, 2, 1, 1),
  ('2026-01-15', 7500.00, 3, 1, 2),
  ('2026-02-05', 1900.00, 1, 1, 3),
  ('2026-02-20', 6000.00, 5, 1, 4),
  ('2026-03-08', 3150.00, 3, 1, 5),
  ('2026-03-18', 1600.00, 1, 1, 1),
  ('2026-04-02', 2500.00, 1, 1, 2),
  ('2026-04-22', 3800.00, 2, 1, 3),
  ('2026-05-10', 4800.00, 3, 1, 1),
  ('2026-05-25', 5000.00, 2, 1, 2),
  ('2026-06-03', 1900.00, 1, 1, 3),
  ('2026-06-18', 2400.00, 2, 1, 4),
  ('2026-07-07', 2100.00, 2, 1, 5),
  ('2026-07-20', 3200.00, 2, 1, 1),
  ('2026-08-05', 7500.00, 3, 1, 2),
  ('2026-08-22', 1900.00, 1, 1, 3),
  ('2026-09-11', 3600.00, 3, 1, 4),
  ('2026-09-28', 2100.00, 2, 1, 5),
  ('2026-10-14', 4800.00, 3, 1, 1),
  ('2026-11-03', 2500.00, 1, 1, 2);

  INSERT INTO eventos (nomeEvento, municipio, dtInicial, dtTermino, tipoEvento, publicoEsperado) VALUES
('Expo Turismo Maio',     'São Paulo',       '2026-05-03', '2026-05-05', 'Feira de Negócios', 25000),
('Festival Cultural SP',  'São Paulo',       '2026-05-10', '2026-05-11', 'Cultural',          18000),
('Maratona Rio',          'Rio de Janeiro',  '2026-05-15', '2026-05-15', 'Esportivo',         12000),
('Festa Junina Antecip.', 'Fortaleza',       '2026-05-24', '2026-05-25', 'Festa Tradicional', 30000);


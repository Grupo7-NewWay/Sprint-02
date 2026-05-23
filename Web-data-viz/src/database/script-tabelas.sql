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
    dataNascimento date null,
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
    idAgencia int not null unique,
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
    uf char(2) not null,
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

INSERT INTO motivo (porcentagem, tipo) VALUES
  (45, 'Lazer'),
  (30, 'Negocios'),
  (15, 'Eventos'),
  (10, 'Saude');

INSERT INTO hospedagem (tipo, porcentagem) VALUES
  ('Hotel',        50),
  ('Pousada',      25),
  ('Airbnb',       15),
  ('Casa Propria', 10);

INSERT INTO gasto (tipo, porcentagem) VALUES
  ('Baixo (ate R$500)',       20),
  ('Medio (R$500 a R$2000)', 50),
  ('Alto (acima de R$2000)', 30);

INSERT INTO grupo (tipo, porcentagem) VALUES
  ('Individual', 30),
  ('Casal',      40),
  ('Familia',    20),
  ('Grupo',      10);

INSERT INTO grupo_idade (tipo, porcentagem) VALUES
  ('18 a 25 anos', 20),
  ('26 a 35 anos', 35),
  ('36 a 50 anos', 30),
  ('Acima de 50',  15);

INSERT INTO fonte (tipo, porcentagem) VALUES
  ('Redes Sociais',      40),
  ('Indicacao',          30),
  ('Buscadores',         20),
  ('Agencia Presencial', 10);

INSERT INTO servico_agencia (tipo, porcentagem) VALUES
  ('Pacote Completo', 55),
  ('So Hospedagem',   25),
  ('So Transporte',   20);

INSERT INTO permanencia (tipo, qtdDias) VALUES
  ('1 a 2 dias',      2),
  ('3 a 5 dias',      4),
  ('6 a 10 dias',     8),
  ('Mais de 10 dias', 14);

INSERT INTO localizacao (uf, cidade) VALUES
  ('SP', 'Sao Paulo'),
  ('RJ', 'Rio de Janeiro'),
  ('BA', 'Salvador'),
  ('SC', 'Florianopolis');

INSERT INTO perfil (idMotivo, idGasto, idGrupo, idHospedagem, idPermanencia, idServicoAgencia, idGrupoIdade, idFonte) VALUES
  (1, 2, 2, 1, 2, 1, 2, 1),
  (1, 3, 3, 2, 3, 1, 3, 2),
  (2, 2, 1, 1, 1, 2, 2, 3),
  (3, 3, 4, 1, 3, 1, 2, 1),
  (1, 1, 2, 3, 2, 3, 1, 1);

INSERT INTO pacote (nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio) VALUES
  ('Pacote Praias RJ',         'Praia',    'Rio de Janeiro', 7,  1600, 'Pacote completo com hospedagem e passeios pelas principais praias do Rio de Janeiro.', 50, '2026-01-10'),
  ('Pacote Familia Salvador',  'Familia',  'Salvador',       10, 2500, 'Pacote familiar com acomodacao em pousada, visitas historicas e experiencias culturais em Salvador.', 30, '2026-01-15'),
  ('Pacote Negocios SP',       'Negocios', 'Sao Paulo',      3,  1900, 'Pacote executivo com hotel 4 estrelas, transfer aeroporto e acesso a centros de convencoes em Sao Paulo.', 20, '2026-02-05'),
  ('Pacote Carnaval Salvador', 'Carnaval', 'Salvador',       5,  1200, 'Pacote especial de Carnaval com camarote, hospedagem central e abada incluido em Salvador.', 40, '2026-02-20'),
  ('Pacote Economico Floripa', 'Praia',    'Florianopolis',  4,  1050, 'Pacote economico com hospedagem em Florianopolis e acesso as melhores praias da ilha.', 25, '2026-03-08'),
  ('Pacote Pascoa Gramado',    'Familia',  'Gramado',        5,  1800, 'Pacote especial de Pascoa com chocolate e clima serrano em Gramado.',                    35, '2026-04-10'),
  ('Pacote Praias Natal',      'Praia',    'Natal',          6,  1400, 'Pacote de praias paradisiacas em Natal com passeios de buggy.',                          40, '2026-05-12');

INSERT INTO vendas (dataVenda, valor, qtdVenda, idAgencia, idPacote) VALUES
  ('2026-01-10', 3200.00, 2, 1, 1),
  ('2026-01-15', 7500.00, 3, 1, 2),
  ('2026-02-05', 1900.00, 1, 1, 3),
  ('2026-02-20', 6000.00, 5, 1, 4),
  ('2026-03-08', 3150.00, 3, 1, 5),
  ('2026-03-18', 1600.00, 1, 1, 1),
  ('2026-04-02', 2500.00, 1, 1, 2),
  ('2026-04-10', 5400.00, 3, 1, 6),
  ('2026-04-22', 3800.00, 2, 1, 3),
  ('2026-05-10', 4800.00, 3, 1, 1),
  ('2026-05-12', 4200.00, 3, 1, 7),
  ('2026-05-25', 5000.00, 2, 1, 2);

-- ========================
-- EVENTOS 2025 (historico)
-- coluna uf: sigla do estado do municipio
-- ========================

INSERT INTO eventos (nomeEvento, municipio, uf, dtInicial, dtTermino, tipoEvento, publicoEsperado) VALUES
  ('Rock in Rio',                       'Rio de Janeiro',   'RJ', '2025-09-19', '2025-09-28', 'Festival Musical',   100000),
  ('Virada Cultural',                   'Sao Paulo',        'SP', '2025-05-17', '2025-05-18', 'Cultural',            80000),
  ('Festa Junina Paulista',             'Sao Paulo',        'SP', '2025-06-12', '2025-06-15', 'Festa Tradicional',   30000),
  ('Expo BH Negocios',                  'Belo Horizonte',   'MG', '2025-08-05', '2025-08-08', 'Feira de Negocios',   20000),
  ('Festival de Inverno',               'Petropolis',       'RJ', '2025-07-10', '2025-07-20', 'Cultural',            15000),
  ('Oktoberfest',                       'Blumenau',         'SC', '2025-10-03', '2025-10-19', 'Festa Tradicional',  700000),
  ('Festa da Uva',                      'Caxias do Sul',    'RS', '2025-02-14', '2025-03-02', 'Festa Tradicional',  400000),
  ('Festival de Cinema',                'Florianopolis',    'SC', '2025-04-07', '2025-04-13', 'Cultural',            12000),
  ('Anime Friends Sul',                 'Curitiba',         'PR', '2025-07-25', '2025-07-27', 'Entretenimento',      25000),
  ('Gramado em Gramado',                'Gramado',          'RS', '2025-08-15', '2025-08-24', 'Festival Musical',    50000),
  ('Carnaval de Salvador',              'Salvador',         'BA', '2025-03-01', '2025-03-05', 'Carnaval',           200000),
  ('Forro Caju',                        'Aracaju',          'SE', '2025-06-20', '2025-06-30', 'Festa Tradicional',  200000),
  ('Micareta de Feira',                 'Feira de Santana', 'BA', '2025-04-24', '2025-04-27', 'Carnaval',           500000),
  ('Maceio Fest',                       'Maceio',           'AL', '2025-10-17', '2025-10-26', 'Festival Musical',   300000),
  ('Festival Gastronomico Recife',      'Recife',           'PE', '2025-09-05', '2025-09-14', 'Gastronomia',         40000),
  ('Festival Folklorico',               'Manaus',           'AM', '2025-06-27', '2025-07-06', 'Cultural',           150000),
  ('Cirio de Nazare',                   'Belem',            'PA', '2025-10-12', '2025-10-12', 'Religioso',          200000),
  ('Boi Bumba',                         'Parintins',        'AM', '2025-06-27', '2025-06-29', 'Festa Tradicional',  100000),
  ('Expo Amazonia',                     'Manaus',           'AM', '2025-08-20', '2025-08-25', 'Feira de Negocios',   30000),
  ('Festival de Opera',                 'Manaus',           'AM', '2025-04-01', '2025-04-30', 'Cultural',            10000),
  ('Festa do Peao',                     'Barretos',         'SP', '2025-08-14', '2025-08-24', 'Rodeio',             100000),
  ('Festival Gastronomico CG',          'Campo Grande',     'MS', '2025-09-12', '2025-09-21', 'Gastronomia',         50000),
  ('Cavalgada de Brasilia',             'Brasilia',         'DF', '2025-07-05', '2025-07-06', 'Rodeio',              80000),
  ('Expo Agronegocio',                  'Goiania',          'GO', '2025-05-20', '2025-05-25', 'Feira de Negocios',   60000),
  ('Festival Cultura Brasil',           'Cuiaba',           'MT', '2025-06-01', '2025-06-07', 'Cultural',            20000),
  ('Carnaval de Porto Seguro',          'Porto Seguro',     'BA', '2025-03-01', '2025-03-05', 'Carnaval',           400000),
  ('Festival de Verao Salvador',        'Salvador',         'BA', '2025-01-17', '2025-01-18', 'Festival Musical',   100000),
  ('Sao Joao de Amargosa',              'Amargosa',         'BA', '2025-06-20', '2025-06-24', 'Festa Tradicional',   80000),
  ('Festival da Banana',                'Itaberaba',        'BA', '2025-07-10', '2025-07-15', 'Gastronomia',         20000),
  ('Lavagem do Bonfim',                 'Salvador',         'BA', '2025-01-16', '2025-01-16', 'Religioso',           80000),
  ('Carnaval do Recife',                'Recife',           'PE', '2025-03-01', '2025-03-05', 'Carnaval',           150000),
  ('Festa de Sao Joao de Caruaru',      'Caruaru',          'PE', '2025-06-01', '2025-06-30', 'Festa Tradicional',   90000),
  ('Festival Rec-Beat',                 'Recife',           'PE', '2025-02-28', '2025-03-04', 'Festival Musical',    40000),
  ('Festa da Pitomba',                  'Bezerros',         'PE', '2025-07-01', '2025-07-07', 'Gastronomia',         15000),
  ('Semana da Cultura Nordestina',      'Olinda',           'PE', '2025-10-20', '2025-10-26', 'Cultural',            35000),
  ('Carnaval de Florianopolis',         'Florianopolis',    'SC', '2025-03-01', '2025-03-05', 'Carnaval',           150000),
  ('Bloco da Barra',                    'Florianopolis',    'SC', '2025-03-01', '2025-03-03', 'Carnaval',            20000),
  ('Bloco do Suvaco do Cristo',         'Florianopolis',    'SC', '2025-03-02', '2025-03-02', 'Carnaval',            15000),
  ('Sumol Summer Fest',                 'Florianopolis',    'SC', '2025-01-10', '2025-01-11', 'Festival Musical',    40000),
  ('Festival Mundo de Musica',          'Florianopolis',    'SC', '2025-02-14', '2025-02-15', 'Festival Musical',    25000),
  ('Planeta Atlantida SC',              'Florianopolis',    'SC', '2025-02-07', '2025-02-08', 'Festival Musical',    80000),
  ('Rock na Ilha',                      'Florianopolis',    'SC', '2025-04-05', '2025-04-06', 'Festival Musical',    30000),
  ('Festival de Jazz e Blues',          'Florianopolis',    'SC', '2025-07-18', '2025-07-20', 'Festival Musical',    18000),
  ('Floripa Music Week',                'Florianopolis',    'SC', '2025-10-08', '2025-10-12', 'Festival Musical',    22000),
  ('Festival de Verao Floripa',         'Florianopolis',    'SC', '2025-01-24', '2025-01-25', 'Festival Musical',    35000),
  ('Festival de Cinema de Floripa',     'Florianopolis',    'SC', '2025-04-07', '2025-04-13', 'Cultural',            12000),
  ('Floripa Teatro Festival',           'Florianopolis',    'SC', '2025-05-15', '2025-05-25', 'Cultural',            10000),
  ('Festival de Danca de Floripa',      'Florianopolis',    'SC', '2025-06-06', '2025-06-15', 'Cultural',            20000),
  ('Arte na Praca',                     'Florianopolis',    'SC', '2025-09-20', '2025-09-28', 'Cultural',             8000),
  ('Exposicao Acoriana',                'Florianopolis',    'SC', '2025-08-01', '2025-08-31', 'Cultural',             5000),
  ('Festival Internacional de Arte',    'Florianopolis',    'SC', '2025-11-14', '2025-11-23', 'Cultural',            15000),
  ('Festival Sabores da Ilha',          'Florianopolis',    'SC', '2025-09-05', '2025-09-07', 'Gastronomia',         18000),
  ('Ostravaganza',                      'Florianopolis',    'SC', '2025-03-14', '2025-03-16', 'Gastronomia',         12000),
  ('Festival do Camarao',               'Florianopolis',    'SC', '2025-07-25', '2025-07-27', 'Gastronomia',         10000),
  ('Floripa Beer Fest',                 'Florianopolis',    'SC', '2025-10-17', '2025-10-19', 'Gastronomia',         20000),
  ('Festival Gastronomico da Lagoa',    'Florianopolis',    'SC', '2025-11-07', '2025-11-09', 'Gastronomia',          8000),
  ('Ironman Florianopolis',             'Florianopolis',    'SC', '2025-05-25', '2025-05-25', 'Esportivo',           15000),
  ('Maratona de Florianopolis',         'Florianopolis',    'SC', '2025-04-27', '2025-04-27', 'Esportivo',           10000),
  ('Floripa Surf Pro',                  'Florianopolis',    'SC', '2025-01-15', '2025-01-19', 'Esportivo',           20000),
  ('Beach Tennis Open Floripa',         'Florianopolis',    'SC', '2025-02-21', '2025-02-23', 'Esportivo',            8000),
  ('Corrida da Ilha',                   'Florianopolis',    'SC', '2025-08-10', '2025-08-10', 'Esportivo',            6000),
  ('Campeonato Catarinense de Volei',   'Florianopolis',    'SC', '2025-09-12', '2025-09-14', 'Esportivo',           12000),
  ('Floripa Tech Summit',               'Florianopolis',    'SC', '2025-06-20', '2025-06-21', 'Feira de Negocios',   10000),
  ('Startup Weekend Floripa',           'Florianopolis',    'SC', '2025-08-22', '2025-08-24', 'Feira de Negocios',    3000),
  ('Expo Turismo SC',                   'Florianopolis',    'SC', '2025-05-08', '2025-05-11', 'Feira de Negocios',   15000),
  ('Summit de Inovacao Catarinense',    'Florianopolis',    'SC', '2025-10-03', '2025-10-04', 'Feira de Negocios',    8000),
  ('Festa do Divino Espirito Santo',    'Florianopolis',    'SC', '2025-06-01', '2025-06-08', 'Religioso',           30000),
  ('Festa Acoriana da Ilha',            'Florianopolis',    'SC', '2025-07-04', '2025-07-06', 'Festa Tradicional',   25000),
  ('Festa de Nossa Sra do Desterro',    'Florianopolis',    'SC', '2025-08-15', '2025-08-15', 'Religioso',           20000),
  ('Reveillon da Beira Mar',            'Florianopolis',    'SC', '2025-12-31', '2026-01-01', 'Festival Musical',   220000),
  ('Reveillon da Jurere Internacional', 'Florianopolis',    'SC', '2025-12-31', '2026-01-01', 'Festival Musical',    90000),
  ('Contagem Regressiva SP',            'Sao Paulo',        'SP', '2025-12-31', '2026-01-01', 'Festival Musical',    70000);

-- ========================
-- EVENTOS 2026 - ultimos 6 meses (grafico de visitas)
-- Totais por mes: Dez~380k | Jan~370k | Fev~405k | Mar~390k | Abr~360k | Mai~350k
-- ========================

INSERT INTO eventos (nomeEvento, municipio, uf, dtInicial, dtTermino, tipoEvento, publicoEsperado) VALUES
  -- Janeiro 2026  ~370.000
  ('Carnaval Antecipado RJ',          'Rio de Janeiro',  'RJ', '2026-01-10', '2026-01-12', 'Carnaval',          180000),
  ('Festival de Verao Salvador 2026', 'Salvador',        'BA', '2026-01-17', '2026-01-19', 'Festival Musical',  120000),
  ('Floripa Summer Open',             'Florianopolis',   'SC', '2026-01-22', '2026-01-26', 'Esportivo',          70000),

  -- Fevereiro 2026  ~405.000
  ('Carnaval de Salvador 2026',       'Salvador',        'BA', '2026-02-14', '2026-02-18', 'Carnaval',          200000),
  ('Carnaval do Recife 2026',         'Recife',          'PE', '2026-02-14', '2026-02-18', 'Carnaval',          135000),
  ('Planeta Atlantida RS 2026',       'Porto Alegre',    'RS', '2026-02-07', '2026-02-08', 'Festival Musical',   70000),

  -- Marco 2026  ~390.000
  ('Festa da Uva Caxias 2026',        'Caxias do Sul',   'RS', '2026-03-13', '2026-03-22', 'Festa Tradicional', 200000),
  ('Festival Rec-Beat 2026',          'Recife',          'PE', '2026-03-06', '2026-03-10', 'Festival Musical',  125000),
  ('Expo Turismo SC 2026',            'Florianopolis',   'SC', '2026-03-20', '2026-03-22', 'Feira de Negocios',  65000),

  -- Abril 2026  ~360.000
  ('Rock na Ilha 2026',               'Florianopolis',   'SC', '2026-04-25', '2026-04-26', 'Festival Musical',  160000),
  ('Micareta Fortaleza',              'Fortaleza',       'CE', '2026-04-10', '2026-04-13', 'Carnaval',          145000),
  ('Festival de Cinema SP 2026',      'Sao Paulo',       'SP', '2026-04-03', '2026-04-09', 'Cultural',           55000),

  -- Maio 2026  ~350.000
  ('Expo Turismo Maio SP',            'Sao Paulo',       'SP', '2026-05-03', '2026-05-05', 'Feira de Negocios', 150000),
  ('Festival Cultural Recife',        'Recife',          'PE', '2026-05-10', '2026-05-12', 'Cultural',          130000),
  ('Ironman Florianopolis 2026',      'Florianopolis',   'SC', '2026-05-17', '2026-05-17', 'Esportivo',          70000);

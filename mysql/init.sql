create database if not exists newway;
use newway;

-- tabelas flutuantes (Base de dados)

create table logs(
    idLogs int primary key auto_increment,
    tipo varchar(255) not null,
    dateTimeLog datetime not null default current_timestamp,
    descricao varchar(255) not null
);
 
create table chegada(
    idChegada int primary key auto_increment,
    ano int not null
);
 
create table chegada_mes(
    idChegadaMes int primary key auto_increment,
    qtdChegadaMes int not null,
    mes varchar(20) not null,
    fkChegada int not null,
    constraint fk_chegada_mes_chegada
        foreign key (fkChegada)
            references chegada(idChegada)
);
 
create table chegada_localidade(
    idChegadaLocalidade int primary key auto_increment,
    qtdChegadaLocalidade int not null,
    localidade varchar(255) not null,
    fkChegada int not null,
    constraint fk_chegada_localidade_chegada
        foreign key (fkChegada)
            references chegada(idChegada)
);
 
create table gasto(
    idGasto int primary key auto_increment,
    tipo varchar(255) not null,
    valor decimal(10,2) not null
);
 
create table permanencia(
    idPermanencia int primary key auto_increment,
    tipo varchar(255) not null,
    qtdDias int not null
);
 
create table motivo(
    idMotivo int primary key auto_increment,
    porcentagem int not null,
    tipo varchar(255) not null
);
 
create table motivoLazer(
    idMotivoLazer int primary key auto_increment,
    tipoMotivoLazer varchar(255) not null,
    porcentagem int not null,
    fkMotivo int not null,
    constraint fk_motivolazer_motivo
        foreign key (fkMotivo)
            references motivo(idMotivo)
);
 
-- core
 
create table agencia(
    idAgencia int primary key auto_increment,
    nome varchar(255) not null,
    telefone char(11) unique,
    email varchar(255) not null unique,
    codigoAcesso char(5) not null unique,
    createdAt datetime default current_timestamp,
    updatedAt datetime default current_timestamp on update current_timestamp
);
 
create table usuario(
    idUsuario int primary key auto_increment,
    nome varchar(255) not null,
    email varchar(255) not null unique,
    dtNascimento date,
    telefone varchar(30),
    senhaHash varchar(255) not null,
    tipoAcesso enum('admin', 'operador', 'visualizador') not null default 'operador',
    notificacao tinyint(1) not null default 1,
    createdAt datetime default current_timestamp,
    updatedAt datetime default current_timestamp on update current_timestamp,
    fkAgencia int not null,
    constraint fk_usuario_agencia
        foreign key (fkAgencia)
            references agencia(idAgencia)
);

create table endereco(
    idEndereco int primary key auto_increment,
    fkUsuario int not null,
        constraint fk_usuario_endereco
        foreign key (fkUsuario)
            references usuario(idUsuario),
    cep char(8) not null,
    logradouro varchar(255) not null,
    numero varchar(10) not null,
    complemento varchar(255),
    bairro varchar(255) not null,
    cidade varchar(255) not null,
    estado char(2) not null
);

create table slack(
    idSlack int primary key auto_increment,
    canal varchar(255) not null,
    idMensagem varchar(100) not null,
    conteudo text not null,
    dataEnvio datetime not null,
    status enum('enviado', 'falha', 'pendente') not null default 'pendente',
    createdAt datetime default current_timestamp,
    fkUsuario int not null,
    constraint fk_slack_usuario
        foreign key (fkUsuario)
            references usuario(idUsuario)
);
 
create table evento(
    idEvento int primary key auto_increment,
    nomeEvento varchar(255) not null,
    localidade varchar(255) not null,
    dataEvento date not null,
    createdAt datetime default current_timestamp,
    updatedAt datetime default current_timestamp on update current_timestamp,
    fkAgencia int not null,
    constraint fk_evento_agencia
        foreign key (fkAgencia)
            references agencia(idAgencia)
);
 
create table pacote(
    idPacote int primary key auto_increment,
    nomePacote varchar(255) not null,
    categoria varchar(255) not null,
    destino varchar(255) not null,
    duracao int not null,
    preco decimal(10,2) not null,
    descricao text,
    vagas int not null default 0,
    dataInicio date not null,
    createdAt datetime default current_timestamp,
    updatedAt datetime default current_timestamp on update current_timestamp,
    fkAgencia int not null,
    constraint fk_pacote_agencia
        foreign key (fkAgencia)
            references agencia(idAgencia)
);

-- Tabela de analytics de eventos turísticos (populada pelo LeitorExcel)
create table if not exists eventos(
    idEventos       int primary key auto_increment,
    nomeEvento      varchar(255) not null,
    municipio       varchar(255),
    estado          varchar(100),
    dtInicial       date,
    dtTermino       date,
    tipoEvento      varchar(100),
    publicoEsperado int
);

INSERT INTO agencia (nome, telefone, email, codigoAcesso) VALUES
('Quickly Travel','11987654321','contato@quicklytravel.com','QT123'),
('Horizonte Turismo','11991234567','atendimento@horizonteturismo.com','HZ456');
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
        foreign key (idAgencia)
            references agencia(idAgencia),
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
        foreign key (idAgencia)
            references agencia(idAgencia)
);

INSERT INTO agencia (nomeAgencia, cnpj, telefone, email, senha)
                VALUES ('Teste', '12345678901234', '11999999999', 'teste@email.com', '123');

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
        foreign key (idMotivo)
            references motivo(idMotivo)
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
        foreign key (idMotivo)
            references motivo(idMotivo),
    idGasto int not null,
    constraint fk_perfil_gasto
        foreign key (idGasto)
            references gasto(idGasto),
    idGrupo int not null,
    constraint fk_perfil_grupo
        foreign key (idGrupo)
            references grupo(idGrupo),
    idHospedagem int not null,
    constraint fk_perfil_hospedagem
        foreign key (idHospedagem)
            references hospedagem(idHospedagem),
    idPermanencia int not null,
    constraint fk_perfil_permanencia
        foreign key (idPermanencia)
            references permanencia(idPermanencia),
    idServicoAgencia int not null,
    constraint fk_perfil_servico_agencia
        foreign key (idServicoAgencia)
            references servico_agencia(idServicoAgencia),
    idGrupoIdade int not null,
    constraint fk_perfil_grupo_idade
        foreign key (idGrupoIdade)
            references grupo_idade(idGrupoIdade),
    idFonte int not null,
    constraint fk_perfil_fonte
        foreign key (idFonte)
            references fonte(idFonte)
);

create table pacote(
    idPacote int primary key auto_increment,
    nomePacote varchar(255) not null,
    qtdDisponivel int not null,
    idPerfil int not null,
    constraint fk_pacote_perfil
        foreign key (idPerfil)
            references perfil(idPerfil),
    idLocalizacao int not null,
    constraint fk_pacote_localizacao
        foreign key (idLocalizacao)
            references localizacao(idLocalizacao),
    idEvento int,
    constraint fk_pacote_evento
        foreign key (idEvento)
            references eventos(idEvento),
    dataCadastro datetime default current_timestamp,
    dataAtualizacao datetime default current_timestamp on update current_timestamp
);

create table pacotes_agencias(
    idAgencia int not null,
    constraint fk_pacotes_agencias_agencia
        foreign key (idAgencia)
            references agencia(idAgencia),
    idPacote int not null,
    constraint fk_pacotes_agencias_pacote
        foreign key (idPacote)
            references pacote(idPacote)
);

create table vendas(
    idVenda int primary key auto_increment,
    dataVenda date not null,
    valor decimal(10,2) not null,
    qtdVenda int not null,
    idAgencia int not null,
    constraint fk_vendas_agencia
        foreign key (idAgencia)
            references agencia(idAgencia),
    idPacote int not null,
    constraint fk_vendas_pacote
        foreign key (idPacote)
            references pacote(idPacote),
    dataCadastro datetime default current_timestamp
);
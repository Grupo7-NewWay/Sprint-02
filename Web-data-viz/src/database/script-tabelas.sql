create database newway;

use newway;

create table agencia(
idAgencia int primary key auto_increment,
nomeAgencia varchar(255) not null,
cnpj varchar(14) not null unique,
telefone varchar(15) not null unique,
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
idAgencia int not null, -- Coloquei a FK aqui porque se fosse ao contrário, uma agência ia ter um único endereço, e a informação do endereço passa a existir sem a agência (na vida real faz sentido, mas como dados a serem gravados para uma agência, deixa de fazer).
	constraint fk_endereco_agencia
		foreign key (idAgencia) 
			references agencia(idAgencia),
dataCadastro datetime default current_timestamp,
dataAtualizacao datetime default current_timestamp on update current_timestamp
);

create table logs(
idLogs int primary key auto_increment,
dataLog datetime default current_timestamp, -- Data é uma palavra reservada no mysql, cuidado
descricao varchar(300),
idAgencia int not null, -- Mesma coisa da tabela endereço, de nada adianta a existência de um log sem que uma agência exista, por isso a inversão da posição da FK.
	constraint fk_logs_agencia
		foreign key (idAgencia)
			references agencia(idAgencia)
);

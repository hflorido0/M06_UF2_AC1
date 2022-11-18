create schema dam2tm06uf2p1;
use dam2tm06uf2p1;

create table jugador (
	id int primary key auto_increment,
    usuario varchar(100),
    password varchar(100),
    nombre varchar(100),
    partidas int,
    ganadas int
);

create table carta (
	id int primary key auto_increment,
    id_jugador int,
    numero varchar(15),
    color varchar(15),
    constraint carta_jugador foreign key (id_jugador) references jugador (id)
);

create table partida (
	id int primary key auto_increment,
    id_carta int,
    constraint carta_partida foreign key (id_carta) references carta (id)
    
);

insert into jugador values (1,'hector','123','hector',0,0);
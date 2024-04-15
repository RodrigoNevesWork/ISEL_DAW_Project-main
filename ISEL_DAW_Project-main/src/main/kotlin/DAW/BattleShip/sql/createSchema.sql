
create schema if not exists dbo;

create table if not exists dbo.Players(
                            id serial primary key,
                            username varchar(64) unique not null,
                            password varchar(64) not null,
                            points numeric not null,
                            token varchar(255)
);


create table if not exists dbo.Games(
                         id UUID not null primary key,
                         playerA int references dbo.Players(id),
                         playerB int references dbo.Players(id),
                         state varchar(64) not null,
                         boardA varchar(1000) not null,
                         boardB varchar(1000) not null,
                         created bigint not null,
                         updated bigint not null,
                         deadline bigint
);

create table if not exists dbo.Lobby(
    user_id int not null references dbo.Players(id),
    board_size int,
    deadline bigint
);
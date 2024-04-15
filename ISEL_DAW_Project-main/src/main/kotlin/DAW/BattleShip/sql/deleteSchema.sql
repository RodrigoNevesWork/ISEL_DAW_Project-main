ALTER sequence  dbo.players_id_seq RESTART WITH 1;
truncate dbo.players, dbo.games, dbo.lobby;

delete from dbo.games;
delete from dbo.lobby;
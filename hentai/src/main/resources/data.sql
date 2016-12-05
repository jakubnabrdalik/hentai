insert into film(id, title, description, film_type) VALUES (1, 'Pirate Tentacle attacks', 'Standard Japan family movie', 'NEW_RELEASE');
insert into film(id, title, description, film_type) VALUES (2, 'Godzilla laser tentacle from mega-tokyo', 'Sci-fi, Akira wannabe', 'REGULAR');
insert into film(id, title, description, film_type) VALUES (3, 'It came in the sea', 'Remake of an European classic', 'OLD');
insert into rent(id, username, film_Id, rent_Date, due_By, film_type) VALUES (1, 'Johnny', 1, '2015-01-01', '2015-01-05', 'NEW_RELEASE');
insert into rent(id, username, film_Id, rent_Date, due_By, film_type) VALUES (2, 'Jakub', 2, '2015-01-02', '2015-01-03', 'REGULAR');
insert into users(username, password, bonus_Points) VALUES ('test', 'test', 0);


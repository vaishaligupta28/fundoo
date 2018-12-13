create table Collaborator(
collab_id int primary key not null auto_increment,
shared_from_id int,
note_id int,
shared_to_id int,
FOREIGN KEY(shared_to_id) REFERENCES User(user_id) on delete cascade ,
FOREIGN KEY(note_id) REFERENCES Note(note_id) on delete cascade,
FOREIGN KEY(shared_from_id) REFERENCES User(user_id) on delete cascade
);

create table Note(
note_id int primary key not null auto_increment,
created_on varchar(45),
title varchar(45),
description varchar(120),
user_id int,
archive bit(1) default false,
pinned bit(1) default false,
reminder bit(1) default false,
trash bit(1) default false,
FOREIGN KEY(user_id) REFERENCES User(user_id) on delete cascade
);

drop table Note;
drop table Collaborator;
SELECT * FROM User;
SELECT * FROM demo.Note;
select * from Collaborator;
select * from User;

drop table user;

CREATE TABLE Collaborator (
    user_id INT AUTO_INCREMENT,
    user_name VARCHAR(45) NOT NULL,
    passd VARCHAR(45) NOT NULL,
    first_name VARCHAR(45) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    email VARCHAR(45) NOT NULL,
    PRIMARY KEY (user_id)
);



SELECT user_id as userId,full_name as fullName, mob_num as mobileNumber,email FROM User WHERE user_id = 1;
select * from Note where user_id = 1 union
select * from Note where note_id IN (SELECT note_id FROM Collaborator where shared_to_id=1);


DELETE FROM Note WHERE note_id = 5;
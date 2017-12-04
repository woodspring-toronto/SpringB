# SpringB
Spring Boot many-to-many
select * from Book;
select * from Publisher;
select * from Book_Publisher;

DROP TABLE IF EXISTS `Book_Publisher`;
DROP TABLE IF EXISTS `Book`;
DROP TABLE IF EXISTS `Publisher`;

create table Book (
id INT(10) NOT NULL AUTO_INCREMENT,
title varchar(256),
author varchar(256),
created_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (id)
);


create table Publisher (
id INT(10) NOT NULL AUTO_INCREMENT,
company varchar(256),
EUI varchar(256),
created_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (id)
);


CREATE TABLE Book_Publisher (
  id int(10) NOT NULL AUTO_INCREMENT,
  book_id int(10)  NOT NULL,
  publisher_id int(10)  NOT NULL,
  created_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  --KEY fk_bookpublisher_publisher_idx (publisher_id),
  CONSTRAINT fk_bookpublisher_book FOREIGN KEY (book_id) REFERENCES Book (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_bookpublisher_publisher FOREIGN KEY (publisher_id) REFERENCES Publisher (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


SHOW ENGINE INNODB STATUS;



insert into Book( title, author) values ('Deep Learning', 'John Patterson');
insert into Book( title, author) values ('Big Data Fundationtals', 'Thomas Erl');
insert into Book( title, author) values ('Reactive Microsystem', 'Jonas Bonder');

insert into Publisher ( company, EUI) values ('Prentice Hall','978-0-13');
insert into Publisher ( company, EUI) values ('O RELLYE','226-09-12432');
insert into Publisher ( company, EUI) values ('Willey','532-698-345');
insert into Publisher ( company, EUI) values ('OXFRD','346-762-481');


insert into Book_Publisher ( book_id, publisher_id) values( 1,1);
insert into Book_Publisher ( book_id, publisher_id) values( 1,4);
insert into Book_Publisher ( book_id, publisher_id) values( 2,2);
insert into Book_Publisher ( book_id, publisher_id) values( 3, 3);
insert into Book_Publisher ( book_id, publisher_id) values( 3,1);



select * from Book;
select * from Publisher;
select * from Book_Publisher;


select * 
FROM Book bk, Publisher pu, Book_Publisher bp
WHERE bk.id = bp.book_id
AND pu.id = bp.publisher_id
order by bp.created_DT desc;

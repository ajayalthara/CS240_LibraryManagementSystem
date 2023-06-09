CREATE TABLE book_details (
  title VARCHAR(50),
  genre VARCHAR(30),
  author VARCHAR(30),
  PRIMARY KEY (title, genre, author)
);
create table inventory_details (
	title VARCHAR(50),
	number_of_copies integer,
	available_copies	integer
);

create table user_details (
	user_id	integer PRIMARY KEY,
	name varchar(50),
	active char(1),
	role varchar(20)
);

create table checkout_info (
	user_id integer,
	title VARCHAR(50),
	constraint fk_user_id_checkout foreign key (user_id) references user_details(user_id)
);

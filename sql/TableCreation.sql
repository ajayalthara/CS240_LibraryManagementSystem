create table book_details (
  title VARCHAR(50) PRIMARY KEY,
  genre VARCHAR(30),
  author VARCHAR(30)
);

create table inventory_details (
	title VARCHAR(50),
	number_of_copies integer,
	available_copies	integer,
	constraint fk_title_inventory foreign key (title) references book_details(title)
);

create table user_details (
	user_id	integer PRIMARY KEY,
	name varchar(50),
);

create table checkout_info (
	user_id integer,
	title VARCHAR(50),
	constraint fk_title_checkout foreign key (title) references book_details(title),
	constraint fk_user_id_checkout foreign key (user_id) references user_details(user_id)
);

/* PostgreSQL */

create table tb_author
(
	pk_author serial PRIMARY KEY,
	email VARCHAR(250) UNIQUE NOT NULL,
	password VARCHAR(250) NOT NULL,
	username VARCHAR(250) UNIQUE NOT NULL,
	gender VARCHAR(20) NOT NULL,
	creation_date TIMESTAMP NOT NULL,
	update_date TIMESTAMP
);
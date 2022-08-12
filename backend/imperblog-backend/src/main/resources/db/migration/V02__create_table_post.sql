/* PostgreSQL */

create table tb_post
(
	pk_post serial PRIMARY KEY,
	fk_author INTEGER NOT NULL,
	post_content TEXT NOT NULL,
	title VARCHAR(250) NOT NULL,
	creation_date TIMESTAMP NOT NULL,
	update_date TIMESTAMP,
	FOREIGN KEY (fk_author) REFERENCES tb_author(pk_author)
);
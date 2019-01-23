BBDD:
		- Aplication --> REDoA2
		- API 		 --> APIoA2
		
		Creamos las tablas correspondientes:
			create table REDoA2_USERS (
				id integer,
				nombre varchar(30),
				usuario varchar(30),
				password varchar(40),
				email varchar(25)
			);
			
			create table APIoA2_USERS (
				id integer,
				nombre varchar(30),
				usuario varchar(30),
				password varchar(40),
				email varchar(25)
			);
			
			create table APIoA2_APPLICATION_REQUEST (
				url_redirect	varchar(100),
				code_secret		varchar(80),
				client_id		varchar(80),
				client_secret	varchar(80)
			);
			create table REDoA2_APPLICATION_REQUEST (
				url_redirect	varchar(100),
				code_secret		varchar(80),
				client_id		varchar(80),
				client_secret	varchar(80)
			);
			
			create table APIoA2_AUTHORIZATION_BASE (
				client_id		varchar(80),
				authorization_code	varchar(80)
			);
			
			create table REDoA2_APPLICATION_RECORD (
				url_redirect varchar(50),
				access_token varchar(30),
				token_type varchar(30),
				expires_in bigint,
				refresh_token varchar(30)
			);

			create table APIoA2_APPLICATION_RECORD (
				client_id varchar(30),
				client_secret varchar(30),
				access_token varchar(30),
				refresh_token varchar(30),
				expires_in bigint
			);
			
INSERT INTO public.redoa2_users(id, nombre, usuario, password, email) VALUES (1, 'ptellos', 'Pedro', '1234', 'ptellos@gmail.com');
 
INSERT INTO redoa2_users(id, nombre, usuario, password, email) VALUES (2, 'pacol', 'pacol', '1234', 'ptellos@gmail.com');
/* Carga de usuarios en la Application*/
INSERT INTO public.redoa2_users
(id, nombre, usuario, "password", email)
VALUES(2, 'pacol', 'pacol', '1234', 'ptellos@gmail.com');
INSERT INTO public.redoa2_users
(id, nombre, usuario, "password", email)
VALUES(3, 'Alex', 'Alex', '9999', 'alexandradlml@gmail.com');
INSERT INTO public.redoa2_users
(id, nombre, usuario, "password", email)
VALUES(5, 'Claudia', 'Claudia', '1234', 'claudia@gmail.com');
INSERT INTO public.redoa2_users
(id, nombre, usuario, "password", email)
VALUES(6, 'JPedro', 'JPedro', '1234', 'ptellos@gmail.com');
INSERT INTO public.redoa2_users
(id, nombre, usuario, "password", email)
VALUES(7, 'JPedro1', 'JPedro1', '1234', 'ptellos@gmail.com');
INSERT INTO public.redoa2_users
(id, nombre, usuario, "password", email)
VALUES(8, 'JPedro2', 'JPedro2', '1234', 'ptellos@gmail.com');
INSERT INTO public.redoa2_users
(id, nombre, usuario, "password", email)
VALUES(9, 'ptellos1', 'ptellos1', '1234', 'ptellos@gmail.com');
INSERT INTO public.redoa2_users
(id, nombre, usuario, "password", email)
VALUES(10, 'JPedroT', 'JPedroT', '2222', 'ptellos@gmail.com');
INSERT INTO public.redoa2_users
(id, nombre, usuario, "password", email)
VALUES(4, 'admin', 'admin', '1234', 'ptellos@gmail.com');
INSERT INTO public.redoa2_users
(id, nombre, usuario, "password", email)
VALUES(1, 'ptellos', 'ptellos', '1234', 'ptellos@gmail.com');
/* Carga de usuarios en la API*/
INSERT INTO public.apioa2_users
(id, nombre, usuario, "password", email)
VALUES(1, 'ptellos', 'ptellos', '1234', 'ptellos@gmail.com');
INSERT INTO public.apioa2_users
(id, nombre, usuario, "password", email)
VALUES(2, 'admin', 'admin', '1234', 'ptellos@gmail.com');

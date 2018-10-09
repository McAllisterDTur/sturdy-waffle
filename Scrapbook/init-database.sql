# Create Database
drop database if exists `Acme-Shout`;
create database `Acme-Shout`;

# Drop users if exist and create them
drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

create user 'acme-user'@'%' 
	identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' 
	identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

# Grant privileges to users
grant select, insert, update, delete 
	on `Acme-Shout`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Acme-Shout`.* to 'acme-manager'@'%';

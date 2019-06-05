# Initial version

# --- !Ups
CREATE TABLE USERS(
    id int(7) NOT NULL,
    firstName VARCHAR(40),
    lastName VARCHAR (40),
    email VARCHAR(50),
    admin BIT,
    address VARCHAR(50),
    phone VARCHAR(15),
    userPassword VARCHAR (255),
    PRIMARY KEY (id),
    UNIQUE KEY unique_email (email)
);

CREATE TABLE CLIENTS (
    id int(7) NOT NULL,
    firstName VARCHAR(40),
    lastName VARCHAR (40),
    gender VARCHAR (15),
    comments VARCHAR(200),
    birthday DATE, active int(1),
    PRIMARY KEY (id)
);
CREATE TABLE SERVICES (
    serviceId int(7) NOT NULL,
    serviceName VARCHAR(50),
    serviceDescription VARCHAR(50),
    days VARCHAR(50),
    time VARCHAR(50),
    active int(1),
    PRIMARY KEY (serviceId)
);

INSERT INTO USERS (id,firstName,lastName,email,admin,address,phone,userPassword)
VALUES(7795631,'John','Doe','admin@gmail.com',1,'785 W Street Brownsville, Tx', '7853359856','qwaszx');

-- !Downs

DROP TABLE USERS;
DROP TABLE CLIENTS;
DROP TABLE SERVICES;


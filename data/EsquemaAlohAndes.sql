
-- CREACION DE LA TABLA OPERADOR
CREATE TABLE OPERADOR
   (ID NUMBER, 
	NOMBRE VARCHAR2(40), 
	EMAIL VARCHAR2(40),
    TELEFONO VARCHAR2(15)
    );
	
ALTER TABLE OPERADOR
	ADD CONSTRAINT PK_OPERADOR 
	PRIMARY KEY (ID)
ENABLE;

ALTER TABLE OPERADOR
	ADD CONSTRAINT UN_OPERADOR_EMAIL 
	UNIQUE (EMAIL)
ENABLE;

ALTER TABLE OPERADOR
	MODIFY NOMBRE NOT NULL
ENABLE;

ALTER TABLE OPERADOR
	MODIFY EMAIL NOT NULL
ENABLE;

ALTER TABLE OPERADOR
	MODIFY TELEFONO NOT NULL
ENABLE;


-- CREACION DE LA TABLA USUARIO
CREATE TABLE USUARIO
    (ID NUMBER, 
     NOMBRE VARCHAR2(40),
     EMAIL VARCHAR2(40),
     TELEFONO VARCHAR2(15) ,
     TIPO VARCHAR2(40)
    );
    
ALTER TABLE USUARIO
	ADD CONSTRAINT PK_USUARIO 
	PRIMARY KEY (ID)
ENABLE;

ALTER TABLE USUARIO
	MODIFY NOMBRE NOT NULL
ENABLE;

ALTER TABLE USUARIO
	MODIFY EMAIL NOT NULL
ENABLE;

ALTER TABLE USUARIO
	MODIFY TELEFONO NOT NULL
ENABLE;

ALTER TABLE USUARIO
	MODIFY TIPO NOT NULL
ENABLE;

ALTER TABLE USUARIO
    ADD CONSTRAINT CK_USUARIO_TIPO
	CHECK (TIPO='Profesor' OR TIPO='Profesor Invitado' OR TIPO='Estudiante'  OR TIPO='Egresado'  OR TIPO='Empleado'  OR TIPO='Padre Estudiante' OR TIPO='Invitado')
ENABLE;


-- CREACION DE TABLA PERSONA JURIDICA
CREATE TABLE PERSONA_JURIDICA(
    ID NUMBER,
    ID_SUPERINTENDENCIA_TURISMO NUMBER,
    ID_CAMARA_COMERCIO NUMBER,
    CATEGORIA NUMBER(1),
    PRECIO_NOCHE NUMBER,
    TIPO VARCHAR2(9)
);
ALTER TABLE PERSONA_JURIDICA
	ADD CONSTRAINT PK_PERSONA_JURIDICA
	PRIMARY KEY (ID)
ENABLE;

ALTER TABLE PERSONA_JURIDICA
ADD CONSTRAINT FK_PJ_ID
    FOREIGN KEY (ID)
    REFERENCES OPERADOR(ID)
ENABLE;

ALTER TABLE PERSONA_JURIDICA
	MODIFY ID_SUPERINTENDENCIA_TURISMO NOT NULL
ENABLE;

ALTER TABLE PERSONA_JURIDICA
	MODIFY ID_CAMARA_COMERCIO NOT NULL
ENABLE;


ALTER TABLE PERSONA_JURIDICA
	MODIFY TIPO NOT NULL
ENABLE;

ALTER TABLE PERSONA_JURIDICA
	ADD CONSTRAINT UN_PJ_IDSUPER 
	UNIQUE (ID_SUPERINTENDENCIA_TURISMO)
ENABLE;

ALTER TABLE PERSONA_JURIDICA
	ADD CONSTRAINT UN_PJ_IDCOMERCIO
	UNIQUE (ID_CAMARA_COMERCIO)
ENABLE;

ALTER TABLE PERSONA_JURIDICA
    ADD CONSTRAINT CK_PJ_PRECIO_NOCHE
	CHECK (PRECIO_NOCHE>0)
ENABLE;

ALTER TABLE PERSONA_JURIDICA
    ADD CONSTRAINT CK_PJ_TIPO
	CHECK (TIPO='Hotel' OR TIPO= 'Hostal' OR TIPO='Vivienda')
ENABLE;

--CREAR TABLA HORARIO
CREATE TABLE HORARIO(
    ID_HOSTAL NUMBER,
    DIA VARCHAR2(1),
    HORA_ABRE NUMBER,
    HORA_CIERRA NUMBER
);

ALTER TABLE HORARIO
    ADD CONSTRAINT CK_HORARIO_HORAA
	CHECK (HORA_ABRE BETWEEN 0 AND 23)
ENABLE;

ALTER TABLE HORARIO
    ADD CONSTRAINT CK_HORARIO_HORAC
	CHECK (HORA_CIERRA BETWEEN 0 AND 23)
ENABLE;

ALTER TABLE HORARIO
	ADD CONSTRAINT PK_HORARIO
	PRIMARY KEY (ID_HOSTAL,DIA)
ENABLE;

ALTER TABLE HORARIO
ADD CONSTRAINT FK_HORARIO_ID
    FOREIGN KEY (ID_HOSTAL)
    REFERENCES OPERADOR(ID)
ENABLE;

ALTER TABLE HORARIO
	MODIFY DIA NOT NULL
ENABLE;

ALTER TABLE HORARIO
	MODIFY HORA_ABRE NOT NULL
ENABLE;
ALTER TABLE HORARIO
	MODIFY HORA_CIERRA NOT NULL
ENABLE;

-- CREACION TABLA INMUEBLE
CREATE TABLE INMUEBLE(
    ID NUMBER,
    DIRECCION VARCHAR2(40),
    TIPO VARCHAR2(20),
    CAPACIDAD NUMBER,
    DISPONIBLE NUMBER,
    FECHA_RESERVA_FINAL DATE
);

ALTER TABLE INMUEBLE
	ADD CONSTRAINT PK_INMUEBLE
	PRIMARY KEY (ID)
ENABLE;

ALTER TABLE INMUEBLE
	MODIFY DIRECCION NOT NULL
ENABLE;

ALTER TABLE INMUEBLE
	MODIFY TIPO NOT NULL
ENABLE;

ALTER TABLE INMUEBLE
	MODIFY CAPACIDAD NOT NULL
ENABLE;

ALTER TABLE INMUEBLE
	MODIFY DISPONIBLE NOT NULL
ENABLE;

ALTER TABLE INMUEBLE
    ADD CONSTRAINT CK_INMUEBLE_TIPO
	CHECK (TIPO='Vivienda' OR TIPO= 'Habitacion' OR TIPO='Apartamento' OR TIPO='Habitacion Hotel'
    OR TIPO='Habitacion Hostal' OR TIPO='Habitacion Vivienda')
ENABLE;

-- CREAR TABLA HABITACION HOTEL

CREATE TABLE HABITACION_HOTEL(
    ID NUMBER,
    ID_HOTEL NUMBER,
    NUMERO NUMBER,
    TIPO VARCHAR2(9),
    PRECIO_NOCHE NUMBER,
    TAMANHO NUMBER,
    UBICACION VARCHAR2(40)
);

ALTER TABLE HABITACION_HOTEL
	ADD CONSTRAINT PK_HABITACION_HOTEL
	PRIMARY KEY (ID)
ENABLE;

ALTER TABLE HABITACION_HOTEL
ADD CONSTRAINT FK_HAB_HOTEL_ID
    FOREIGN KEY (ID)
    REFERENCES INMUEBLE(ID)
ENABLE;

ALTER TABLE HABITACION_HOTEL
ADD CONSTRAINT FK_HAB_HOTEL_ID_HOTEL
    FOREIGN KEY (ID_HOTEL)
    REFERENCES PERSONA_JURIDICA(ID)
ENABLE;

ALTER TABLE HABITACION_HOTEL
	MODIFY ID_HOTEL NOT NULL
ENABLE;

ALTER TABLE HABITACION_HOTEL
	MODIFY NUMERO NOT NULL
ENABLE;

ALTER TABLE HABITACION_HOTEL
	MODIFY TIPO NOT NULL
ENABLE;

ALTER TABLE HABITACION_HOTEL
	MODIFY PRECIO_NOCHE NOT NULL
ENABLE;

ALTER TABLE HABITACION_HOTEL
	MODIFY TAMANHO NOT NULL
ENABLE;

ALTER TABLE HABITACION_HOTEL
	MODIFY UBICACION NOT NULL
ENABLE;

ALTER TABLE HABITACION_HOTEL
    ADD CONSTRAINT CK_HAB_HOTEL_TIPO
	CHECK (TIPO='Estandar' OR TIPO= 'Semisuite' OR TIPO='Suite' )
ENABLE;

ALTER TABLE HABITACION_HOTEL
    ADD CONSTRAINT CK_HAB_HOTEL_PRECIO
	CHECK (PRECIO_NOCHE>=0 )
ENABLE;

ALTER TABLE HABITACION_HOTEL
    ADD CONSTRAINT CK_HAB_HOTEL_TAMANHO
	CHECK (TAMANHO>0 )
ENABLE;

-- CREACION TABLA HABITACION VIVIENDA
CREATE TABLE HABITACION_VIVIENDA(
    ID NUMBER,
    ID_VIVIENDA NUMBER,
    NUMERO NUMBER,
    PRECIO_SEMESTRE NUMBER,
    PRECIO_MES NUMBER,
    PRECIO_NOCHE NUMBER,
    UBICACION VARCHAR2(40),
    INDIVIDUAL VARCHAR2(1)
);

ALTER TABLE HABITACION_VIVIENDA
	ADD CONSTRAINT PK_HABITACION_VIVIENDA
	PRIMARY KEY (ID)
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
ADD CONSTRAINT FK_HAB_VIV_ID
    FOREIGN KEY (ID)
    REFERENCES INMUEBLE(ID)
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
ADD CONSTRAINT FK_HAB_VIV_ID_VIV
    FOREIGN KEY (ID_VIVIENDA)
    REFERENCES PERSONA_JURIDICA(ID)
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
	MODIFY ID_VIVIENDA NOT NULL
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
	MODIFY NUMERO NOT NULL
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
	MODIFY PRECIO_SEMESTRE NOT NULL
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
	MODIFY PRECIO_MES NOT NULL
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
	MODIFY PRECIO_NOCHE NOT NULL
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
	MODIFY INDIVIDUAL NOT NULL
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
	MODIFY UBICACION NOT NULL
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
    ADD CONSTRAINT CK_HAB_VIV_NUMERO
	CHECK (NUMERO>0)
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
    ADD CONSTRAINT CK_HAB_VIV_PRECIO_SEM
	CHECK (PRECIO_SEMESTRE>=0 )
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
    ADD CONSTRAINT CK_HAB_VIV_PRECIO_MES
	CHECK (PRECIO_MES>=0 )
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
    ADD CONSTRAINT CK_HAB_VIV_PRECIO_NOCHE
	CHECK (PRECIO_NOCHE>=0 )
ENABLE;

ALTER TABLE HABITACION_VIVIENDA
    ADD CONSTRAINT CK_HAB_VIV_INDIVIDUAL
	CHECK (INDIVIDUAL='Y' OR INDIVIDUAL='N')
ENABLE;


-- CREACION TABLA HABITACION HOSTAL
CREATE TABLE HABITACION_HOSTAL(
    ID NUMBER,
    ID_HOSTAL NUMBER,
    NUMERO NUMBER
);

ALTER TABLE HABITACION_HOSTAL
	ADD CONSTRAINT PK_HABITACION_HOSTAL
	PRIMARY KEY (ID)
ENABLE;

ALTER TABLE HABITACION_HOSTAL
ADD CONSTRAINT FK_HAB_HOS_ID
    FOREIGN KEY (ID)
    REFERENCES INMUEBLE(ID)
ENABLE;

ALTER TABLE HABITACION_HOSTAL
ADD CONSTRAINT FK_HAB_HOS_ID_HOS
    FOREIGN KEY (ID_HOSTAL)
    REFERENCES PERSONA_JURIDICA(ID)
ENABLE;

ALTER TABLE HABITACION_HOSTAL
	MODIFY ID_HOSTAL NOT NULL
ENABLE;

ALTER TABLE HABITACION_HOSTAL
	MODIFY NUMERO NOT NULL
ENABLE;

-- CREACION DE TABLA SERVICIO MENAJE
CREATE TABLE SERVICIO_MENAJE(
    NOMBRE VARCHAR2(40),
    TIPO VARCHAR2(8)
);

ALTER TABLE SERVICIO_MENAJE
	ADD CONSTRAINT PK_SERVICIO_MENAJE
	PRIMARY KEY (NOMBRE)
ENABLE;

ALTER TABLE SERVICIO_MENAJE
    ADD CONSTRAINT CK_SM_TIPO
	CHECK (TIPO='Menaje' OR TIPO='Servicio')
ENABLE;

ALTER TABLE SERVICIO_MENAJE
	MODIFY TIPO NOT NULL
ENABLE;


-- CREACION TABLA OFRECE SERVICIO
CREATE TABLE OFRECE_SERVICIO(
    ID_SERVICIO_MENAJE VARCHAR2(40),
    ID_INMUEBLE NUMBER,
    COSTO NUMBER,
    CANTIDAD NUMBER
);

ALTER TABLE OFRECE_SERVICIO
	ADD CONSTRAINT PK_OFRECE_SERVICIO
	PRIMARY KEY (ID_SERVICIO_MENAJE, ID_INMUEBLE)
ENABLE;

ALTER TABLE OFRECE_SERVICIO
ADD CONSTRAINT FK_OFRECE_SERVICIO_ID
    FOREIGN KEY (ID_SERVICIO_MENAJE)
    REFERENCES SERVICIO_MENAJE(NOMBRE)
ENABLE;

ALTER TABLE OFRECE_SERVICIO
ADD CONSTRAINT FK_OFRECE_SERVICIO_ID_IN
    FOREIGN KEY (ID_INMUEBLE)
    REFERENCES INMUEBLE(ID)
ENABLE;

ALTER TABLE OFRECE_SERVICIO
    ADD CONSTRAINT CK_OS_COSTO
	CHECK (COSTO>0)
ENABLE;

ALTER TABLE OFRECE_SERVICIO
    ADD CONSTRAINT CK_OS_CANTIDAD
	CHECK (CANTIDAD>0)
ENABLE;

-- CREACION TABLA RESERVA
CREATE TABLE RESERVA(
    ID  NUMBER,
    FECHA_INICIO DATE,
    FECHA_FIN DATE,
    VALOR_TOTAL NUMBER,
    FECHA_CANCELACION DATE,
    PAGADO NUMBER,
    DESCUENTO NUMBER,
    CAPACIDAD NUMBER,
    ESTADO NUMBER,
    ID_OPERADOR NUMBER,
    ID_USUARIO NUMBER,
    ID_INMUEBLE NUMBER   
);

ALTER TABLE RESERVA
	ADD CONSTRAINT PK_RESERVA
	PRIMARY KEY (ID)
ENABLE;

ALTER TABLE RESERVA
ADD CONSTRAINT FK_RESERVA_OPERADOR
    FOREIGN KEY (ID_OPERADOR)
    REFERENCES OPERADOR(ID)
ENABLE;

ALTER TABLE RESERVA
ADD CONSTRAINT FK_RESERVA_USUARIO
    FOREIGN KEY (ID_USUARIO)
    REFERENCES USUARIO(ID)
ENABLE;

ALTER TABLE RESERVA
ADD CONSTRAINT FK_RESERVA_INMUEBLE
    FOREIGN KEY (ID_INMUEBLE)
    REFERENCES INMUEBLE(ID)
ENABLE;

ALTER TABLE RESERVA
	MODIFY FECHA_INICIO NOT NULL
ENABLE;

ALTER TABLE RESERVA
	MODIFY FECHA_FIN NOT NULL
ENABLE;

ALTER TABLE RESERVA
	MODIFY VALOR_TOTAL NOT NULL
ENABLE;


ALTER TABLE RESERVA
	MODIFY PAGADO NOT NULL
ENABLE;

ALTER TABLE RESERVA
	MODIFY DESCUENTO NOT NULL
ENABLE;

ALTER TABLE RESERVA
	MODIFY CAPACIDAD NOT NULL
ENABLE;

ALTER TABLE RESERVA
	MODIFY ESTADO NOT NULL
ENABLE;

ALTER TABLE RESERVA
	MODIFY ID_OPERADOR NOT NULL
ENABLE;

ALTER TABLE RESERVA
	MODIFY ID_USUARIO NOT NULL
ENABLE;

ALTER TABLE RESERVA
	MODIFY ID_INMUEBLE NOT NULL
ENABLE;


ALTER TABLE RESERVA
    ADD CONSTRAINT CK_RESERVA_ESTADO
	CHECK (ESTADO=0 OR ESTADO=1)
ENABLE;

-- CREACION DE TABLA PERSONA NATURAL
CREATE TABLE PERSONA_NATURAL(
    ID NUMBER,
    TIPO VARCHAR2(40)
);

ALTER TABLE PERSONA_NATURAL
	ADD CONSTRAINT PK_PERSONA_NATURAL
	PRIMARY KEY (ID)
ENABLE;

ALTER TABLE PERSONA_NATURAL
	MODIFY TIPO NOT NULL
ENABLE;

-- CREACION DE TABLA HABITACION
CREATE TABLE HABITACION(
    ID NUMBER,
    TAMANHO NUMBER,
    PRECIO_MES NUMBER,
    ID_PERSONA NUMBER
);

ALTER TABLE HABITACION
	ADD CONSTRAINT PK_HABITACION
	PRIMARY KEY (ID)
ENABLE;

ALTER TABLE HABITACION
ADD CONSTRAINT FK_HABITACION_ID
    FOREIGN KEY (ID)
    REFERENCES INMUEBLE(ID)
ENABLE;

ALTER TABLE HABITACION
ADD CONSTRAINT FK_HABITACION_ID_PERSONA
    FOREIGN KEY (ID_PERSONA)
    REFERENCES PERSONA_NATURAL(ID)
ENABLE;

ALTER TABLE HABITACION
	MODIFY TAMANHO NOT NULL
ENABLE;

ALTER TABLE HABITACION
	MODIFY PRECIO_MES NOT NULL
ENABLE;

ALTER TABLE HABITACION
	MODIFY ID_PERSONA NOT NULL
ENABLE;


-- CREACION TABLA VIVIENDA
CREATE TABLE VIVIENDA(
    ID NUMBER,
    NUMERO_HABITACIONES NUMBER,
    COSTO_NOCHE NUMBER,
    DIAS_UTILIZADO NUMBER,
    ID_PERSONA NUMBER
);

ALTER TABLE VIVIENDA
	ADD CONSTRAINT PK_VIVIENDA
	PRIMARY KEY (ID)
ENABLE;

ALTER TABLE VIVIENDA
ADD CONSTRAINT FK_VIVIENDA_ID
    FOREIGN KEY (ID)
    REFERENCES INMUEBLE(ID)
ENABLE;

ALTER TABLE VIVIENDA
ADD CONSTRAINT FK_VIVIENDA_PN
    FOREIGN KEY (ID_PERSONA)
    REFERENCES PERSONA_NATURAL(ID)
ENABLE;

ALTER TABLE VIVIENDA
	MODIFY NUMERO_HABITACIONES NOT NULL
ENABLE;

ALTER TABLE VIVIENDA
	MODIFY COSTO_NOCHE NOT NULL
ENABLE;

ALTER TABLE VIVIENDA
	MODIFY DIAS_UTILIZADO NOT NULL
ENABLE;

ALTER TABLE VIVIENDA
	MODIFY ID_PERSONA NOT NULL
ENABLE;

-- CREACION TABLA APARTAMENTO
CREATE TABLE APARTAMENTO(
    ID NUMBER,
    AMOBLADO NUMBER,
    PRECIO_MES NUMBER,
    ID_PERSONA NUMBER
);

ALTER TABLE APARTAMENTO
	ADD CONSTRAINT PK_APTO
	PRIMARY KEY (ID)
ENABLE;

ALTER TABLE APARTAMENTO
ADD CONSTRAINT FK_APTO_ID
    FOREIGN KEY (ID)
    REFERENCES INMUEBLE(ID)
ENABLE;


ALTER TABLE APARTAMENTO
ADD CONSTRAINT FK_APTO_ID_PERSONA
    FOREIGN KEY (ID_PERSONA)
    REFERENCES PERSONA_NATURAL(ID)
ENABLE;

ALTER TABLE APARTAMENTO
	MODIFY AMOBLADO NOT NULL
ENABLE;

ALTER TABLE APARTAMENTO
	MODIFY PRECIO_MES NOT NULL
ENABLE;

ALTER TABLE APARTAMENTO
	MODIFY  ID_PERSONA NOT NULL
ENABLE;









--Poblar Tablas

-- Poblar tabla de Operador
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (1, 'Yard', 'ygutherson0@amazonaws.com', '6167445220');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (2, 'Ange', 'avalente1@reuters.com', '1681622545');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (3, 'Madge', 'mcyson2@tiny.cc', '3198038629');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (4, 'Huntington', 'hhanburybrown3@about.com', '5383958270');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (5, 'Maxine', 'mmartlew4@goo.gl', '6133356081');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (6, 'Corrine', 'clakenton5@ucla.edu', '4744421812');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (7, 'Kaine', 'kgarnson6@msu.edu', '9354208397');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (8, 'Gae', 'gjakubovics7@uiuc.edu', '8001054928');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (9, 'Marje', 'mbenwell8@about.com', '3503111922');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (10, 'Ole', 'opanyer9@jigsy.com', '4979928070');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (11, 'Dag', 'dmcardella@noaa.gov', '3701141763');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (12, 'Janette', 'jivoryb@sitemeter.com', '5853794301');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (13, 'Tess', 'tgervaisec@lycos.com', '7405202318');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (14, 'Lolita', 'llampbrechtd@marketwatch.com', '4425704342');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (15, 'Lenora', 'lmichelle@arstechnica.com', '9722868390');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (16, 'Kile', 'kcawkwellf@amazon.co.jp', '5856173980');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (17, 'Kylen', 'kstaresmeareg@ehow.com', '8551450109');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (18, 'Brandea', 'bglewh@about.me', '6807005454');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (19, 'Bonnie', 'bcookei@walmart.com', '3564927817');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (20, 'Cecily', 'cpettettj@prweb.com', '7641765676');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (21, 'Gabbi', 'gurionk@macromedia.com', '4742903338');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (22, 'Emilie', 'eheggmanl@businesswire.com', '7109443803');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (23, 'Knox', 'kmaitlandm@tamu.edu', '9153892774');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (24, 'Candie', 'coscanlonn@studiopress.com', '7767920494');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (25, 'Caterina', 'cwakelingo@reddit.com', '8977188814');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (26, 'Bartel', 'beversp@amazon.co.jp', '4327395472');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (27, 'Sean', 'snorrieq@google.co.uk', '9078275930');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (28, 'Larissa', 'lskaifr@ycombinator.com', '3908125400');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (29, 'Torin', 'tharlows@intel.com', '6525096008');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (30, 'Ezri', 'esimpert@marketwatch.com', '1949037679');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (31, 'Doralia', 'dgoslandu@i2i.jp', '4915101281');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (32, 'Reynolds', 'rfreezerv@eventbrite.com', '7483513396');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (33, 'Nissie', 'nfearneyw@w3.org', '8673567870');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (34, 'Claybourne', 'cfeathersx@ifeng.com', '4482216105');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (35, 'Eldridge', 'ehumbery@google.es', '5417087564');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (36, 'Rowen', 'rmckeadyz@liveinternet.ru', '7045657338');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (37, 'Lian', 'lseger10@webmd.com', '2121600843');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (38, 'Peggie', 'pmottinelli11@apple.com', '2226822276');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (39, 'Violetta', 'vvickerstaff12@nih.gov', '7408557124');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (40, 'Kaitlin', 'kheaney13@umich.edu', '2382682869');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (41, 'Bogart', 'borange14@hhs.gov', '5197055353');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (42, 'Teddi', 'tstarr15@craigslist.org', '1556712409');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (43, 'Dylan', 'dcollcutt16@cnn.com', '9252593042');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (44, 'Adella', 'anusche17@51.la', '4966382553');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (45, 'Christina', 'cledgeway18@free.fr', '6105560349');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (46, 'Terence', 'tkayes19@elpais.com', '8729109192');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (47, 'Mellie', 'mmcilwain1a@techcrunch.com', '4383991628');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (48, 'Tana', 'tmaclachlan1b@cloudflare.com', '7429012824');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (49, 'Gordie', 'gabbe1c@csmonitor.com', '1419239888');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (50, 'Val', 'vmatiashvili1d@aol.com', '9802812995');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (51, 'Cheslie', 'cjerrome1e@stumbleupon.com', '4855279494');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (52, 'Dillon', 'datlee1f@ucoz.ru', '7766339738');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (53, 'Auroora', 'asherar1g@cocolog-nifty.com', '8878076575');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (54, 'Faina', 'fyorston1h@squidoo.com', '8728030276');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (55, 'Erda', 'evecard1i@webnode.com', '2925005743');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (56, 'Hugues', 'htoe1j@spiegel.de', '2207010803');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (57, 'Ingemar', 'ibyham1k@howstuffworks.com', '5466956248');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (58, 'Philippine', 'pmaccoughen1l@blogtalkradio.com', '6951736496');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (59, 'Zandra', 'zjilliss1m@opera.com', '2899345083');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (60, 'Milli', 'mconti1n@pbs.org', '7837968504');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (61, 'Elmira', 'emuspratt1o@unblog.fr', '4979544031');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (62, 'Stephie', 'sfoxten1p@epa.gov', '4961716824');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (63, 'Ciel', 'cmacgebenay1q@samsung.com', '9585816720');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (64, 'Robinetta', 'rperroni1r@stanford.edu', '9468730306');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (65, 'Merle', 'meathorne1s@devhub.com', '7117455544');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (66, 'Ange', 'alemoir1t@nps.gov', '5776073648');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (67, 'Boycie', 'bodowd1u@statcounter.com', '6942864162');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (68, 'Evyn', 'ewarbys1v@state.gov', '4774047367');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (69, 'Zebedee', 'zlaphorn1w@twitter.com', '1095324737');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (70, 'Sallyanne', 'ssygrove1x@livejournal.com', '2572091437');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (71, 'Orazio', 'ostorry1y@wp.com', '7621056208');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (72, 'Adrea', 'apead1z@slashdot.org', '9153522637');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (73, 'Jacenta', 'jjaan20@telegraph.co.uk', '4109424678');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (74, 'Aksel', 'ayann21@noaa.gov', '9714224272');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (75, 'Christoforo', 'cgregorio22@wufoo.com', '7237606615');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (76, 'Burke', 'bbirrel23@php.net', '7037723454');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (77, 'Dorella', 'dsatterley24@imageshack.us', '2019242518');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (78, 'Reyna', 'rrodger25@dailymail.co.uk', '3293796145');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (79, 'Waverley', 'wrookledge26@etsy.com', '9941360836');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (80, 'Riccardo', 'rwhitely27@networkadvertising.org', '5388167263');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (81, 'Alfonse', 'athomke28@sfgate.com', '9255001354');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (82, 'Conni', 'cmalmar29@oaic.gov.au', '1956991737');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (83, 'Cecilio', 'civchenko2a@networkadvertising.org', '6093576491');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (84, 'Mair', 'mwheldon2b@indiatimes.com', '1879459244');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (85, 'Babita', 'bbraffington2c@cisco.com', '3147120141');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (86, 'Florri', 'fliverock2d@samsung.com', '8473606633');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (87, 'Palmer', 'pmonger2e@marketwatch.com', '6521204626');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (88, 'Orin', 'omooney2f@ifeng.com', '1329582061');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (89, 'Judy', 'josburn2g@tinyurl.com', '1041147425');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (90, 'Avrit', 'aculpan2h@usgs.gov', '4625773214');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (91, 'Celestine', 'csywell2i@nationalgeographic.com', '2893725516');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (92, 'Roscoe', 'rvayro2j@cargocollective.com', '4714423076');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (93, 'Corliss', 'ckupper2k@google.de', '7465985622');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (94, 'Bartolomeo', 'bmarioneau2l@state.gov', '9539522110');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (95, 'Adelice', 'aomara2m@go.com', '7035094724');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (96, 'Waverly', 'wmacduffie2n@com.com', '8831852647');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (97, 'Caterina', 'cdebeneditti2o@quantcast.com', '1327052860');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (98, 'Carley', 'cspellsworth2p@amazon.co.jp', '5475451048');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (99, 'Bobbye', 'bnesby2q@wikia.com', '7869737921');
INSERT INTO OPERADOR (ID, NOMBRE, EMAIL, TELEFONO) VALUES (100, 'Meriel', 'mbeves2r@discuz.net', '9677453461');




-- Poblar tabla usuario

INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (1, 'Gale', 'gdolligon0@networkadvertising.org', '8468291264', 'Profesor Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (2, 'Felike', 'fmaplethorpe1@chronoengine.com', '7439964090', 'Empleado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (3, 'Jacobo', 'jlauthian2@mapy.cz', '4491866059', 'Empleado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (4, 'Kliment', 'kwinter3@sun.com', '5874295060', 'Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (5, 'Erna', 'egolagley4@guardian.co.uk', '4671504374', 'Profesor Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (6, 'Courtney', 'cgardner5@indiegogo.com', '8508375288', 'Estudiante');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (7, 'Jamesy', 'jgeelan6@usnews.com', '5583160204', 'Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (8, 'Cathyleen', 'cmacdiarmid7@archive.org', '1512727548', 'Profesor');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (9, 'Jesselyn', 'jcucinotta8@tumblr.com', '6997597876', 'Profesor Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (10, 'Anstice', 'agendricke9@technorati.com', '3601357380', 'Egresado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (11, 'Fabiano', 'fpawletta@craigslist.org', '2518055196', 'Profesor');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (12, 'Carolus', 'cyerbyb@google.co.jp', '8222702314', 'Padre Estudiante');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (13, 'Lorrie', 'lmunginc@tiny.cc', '7354290841', 'Profesor');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (14, 'Kile', 'klegated@slate.com', '4829835759', 'Empleado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (15, 'Rocky', 'rbrignalle@blogger.com', '4076565354', 'Egresado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (16, 'Anders', 'ahenkerf@ocn.ne.jp', '4953908649', 'Profesor Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (17, 'Issi', 'iseedmang@zimbio.com', '8171852389', 'Padre Estudiante');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (18, 'Seward', 'sfishbourneh@edublogs.org', '8674042856', 'Padre Estudiante');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (19, 'Viv', 'vedwinsoni@mtv.com', '5874834767', 'Profesor Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (20, 'Dante', 'dbatistellij@answers.com', '1532917987', 'Profesor');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (21, 'Cristin', 'cfessionsk@flavors.me', '4214884427', 'Egresado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (22, 'Lillian', 'llindselll@chron.com', '2859513135', 'Estudiante');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (23, 'Boothe', 'bgreerm@tiny.cc', '7779574988', 'Profesor Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (24, 'Kathe', 'kgravestonen@tiny.cc', '5939174861', 'Empleado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (25, 'Amabel', 'abrislando@soundcloud.com', '3178870527', 'Egresado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (26, 'Kendell', 'kfishpoolp@virginia.edu', '4653290460', 'Profesor');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (27, 'Dagny', 'dstoweq@fda.gov', '7457384047', 'Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (28, 'Alastair', 'agascoyenr@1688.com', '7449205001', 'Profesor Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (29, 'Alexandre', 'awedderburns@github.io', '1584500809', 'Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (30, 'Kiersten', 'kboulet@amazon.co.uk', '3979909014', 'Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (31, 'Krissy', 'kgormanu@dailymail.co.uk', '5993136437', 'Empleado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (32, 'Julian', 'jlennardv@geocities.jp', '4208430126', 'Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (33, 'Ariana', 'aworgew@cpanel.net', '1216743718', 'Estudiante');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (34, 'Julio', 'jchildesx@xinhuanet.com', '1668347500', 'Profesor');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (35, 'Judith', 'jmulryany@hugedomains.com', '9962895285', 'Profesor');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (36, 'Alica', 'acraterez@foxnews.com', '1049572340', 'Profesor');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (37, 'Brietta', 'bcornbill10@twitter.com', '9866963909', 'Estudiante');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (38, 'Verge', 'vstapylton11@myspace.com', '7178417850', 'Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (39, 'Rodie', 'rhumbatch12@blinklist.com', '7708025946', 'Padre Estudiante');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (40, 'Rebecka', 'rannon13@microsoft.com', '5199404738', 'Profesor');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (41, 'Shamus', 'sfrisel14@cnn.com', '2678709173', 'Egresado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (42, 'Dyann', 'dmquharg15@tiny.cc', '2916200078', 'Padre Estudiante');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (43, 'Sasha', 'ssmeaton16@nasa.gov', '4676334841', 'Empleado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (44, 'Zachary', 'zvarrow17@barnesandnoble.com', '8672332211', 'Invitado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (45, 'Stuart', 'severiss18@state.tx.us', '9408879749', 'Empleado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (46, 'Khalil', 'kpitway19@theatlantic.com', '2109611656', 'Estudiante');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (47, 'Harley', 'hkervin1a@netlog.com', '2805125010', 'Padre Estudiante');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (48, 'Hank', 'halderwick1b@t-online.de', '9405310554', 'Empleado');
INSERT INTO usuario (ID, nombre, email, telefono, tipo) VALUES (49, 'Lara', 'lbrandli1c@dot.gov', '7269572259', 'Egresado');



-- poblar tabla persona juridica

INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (50, 4672, 36108, 5, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (51, 9455, 34294, 2, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (52, 9326, 56730, NULL, 105, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (53, 4084, 69297, NULL, 232.48, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (54, 9944, 53204, NULL, 405.89, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (55, 7575, 73771, NULL, 441.27, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (56, 2674, 92983, NULL, 202.07, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (57, 9679, 14013, 4, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (58, 4934, 93741, NULL, 384.54, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (59, 6927, 16526, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (60, 2394, 67233, 1, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (61, 3049, 35379, NULL, 109.82, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (62, 2292, 69142, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (63, 8069, 91417, NULL, 176.66, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (64, 4940, 81318, 1, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (65, 5130, 94515, NULL, 121.09, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (66, 8509, 92218, NULL, 462.21, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (67, 7455, 35454, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (68, 2999, 46004, NULL, 287.77, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (69, 4115, 41029, 5,NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (70, 9548, 85681, NULL, 467.36, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (71, 3549, 10584, 3, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (72, 1659, 96643, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (73, 9159, 78362, 4, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (74, 1725, 40982, NULL, 164.84, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (75, 8112, 92156, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (76, 7943, 36593, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (77, 4928, 64101, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (78, 9536, 78844, 5, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (79, 5661, 29394, NULL, 40, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (80, 5886, 73980, 1, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (81, 2408, 33238, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (82, 6232, 85148, 4, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (83, 6390, 47583, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (84, 5905, 18478, 5, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (85, 7626, 22316, NULL, 475.16, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (86, 3751, 77284, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (87, 6415, 32393, NULL, 139.39, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (88, 8716, 41068, 3, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (89, 1642, 60755, 1, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (90, 8296, 23166, 2, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (91, 9492, 28525, 5, NULL, 'Hotel');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (92, 2631, 89040, NULL, 354.86, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (93, 3727, 35005, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (94, 6361, 25921, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (95, 1484, 13687, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (96, 3626, 58288, NULL, 157.42, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (97, 1272, 79895, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (98, 8809, 66352, NULL, NULL, 'Vivienda');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (99, 1826, 37059, NULL, 129.76, 'Hostal');
INSERT INTO persona_juridica (ID, id_superintendencia_turismo, id_camara_comercio, categoria, precio_noche, tipo) VALUES (100, 5921, 82329, NULL,NULL, 'Vivienda');


-- Poblar tabla horario para hostales 99-96-92-87-85- 79 -70- 68- 66- 65
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (99, 'L', 10, 11);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (99, 'M', 1, 10);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (99, 'I', 9, 15);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (99, 'J', 10, 15);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (99, 'V', 14, 19);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (99, 'S', 5, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (99, 'D', 19, 20);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (96, 'L', 3, 22);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (96, 'M', 15, 18);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (96, 'I', 22, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (96, 'J', 3, 12);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (96, 'V', 2, 5);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (96, 'S', 6, 15);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (96, 'D', 13, 17);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (92, 'L', 12, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (92, 'M', 13, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (92, 'I', 18, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (92, 'J', 3, 12);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (92, 'V', 21, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (92, 'S', 21, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (92, 'D', 20, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (87, 'L', 11, 16);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (87, 'M', 4, 12);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (87, 'I', 20, 22);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (87, 'J', 7, 22);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (87, 'V', 0, 7);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (87, 'S', 20, 21);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (87, 'D', 10, 12);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (85, 'L', 12, 18);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (85, 'M', 7, 11);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (85, 'I', 16, 20);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (85, 'J', 11, 21);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (85, 'V', 8, 21);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (85, 'S', 21, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (85, 'D', 14, 15);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (79, 'L', 7, 12);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (79, 'M', 8, 14);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (79, 'I', 15, 19);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (79, 'J', 14, 21);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (79, 'V', 1, 22);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (79, 'S', 20, 21);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (79, 'D', 16, 17);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (70, 'L', 4, 5);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (70, 'M', 1, 13);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (70, 'I', 17, 18);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (70, 'J', 18, 21);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (70, 'V', 19, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (70, 'S', 19, 21);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (70, 'D', 1, 17);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (68, 'L', 18, 20);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (68, 'M', 3, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (68, 'I', 21, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (68, 'J', 1, 17);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (68, 'V', 11, 19);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (68, 'S', 13, 15);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (68, 'D', 19, 21);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (66, 'L', 21, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (66, 'M', 18, 19);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (66, 'I', 6, 13);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (66, 'J', 14, 18);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (66, 'V', 2, 18);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (66, 'S', 6, 17);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (66, 'D', 8, 17);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (65, 'L', 9, 19);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (65, 'M', 0, 6);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (65, 'I', 8, 11);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (65, 'J', 19, 22);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (65, 'V', 0, 7);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (65, 'S', 4, 10);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (65, 'D', 15, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (63, 'L', 21, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (63, 'M', 18, 19);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (63, 'I', 6, 13);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (63, 'J', 14, 18);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (63, 'V', 2, 18);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (63, 'S', 6, 17);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (63, 'D', 8, 17);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (61, 'L', 9, 10);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (61, 'M', 0, 6);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (61, 'I', 8, 23);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (61, 'J', 19, 20);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (61, 'V', 0, 7);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (61, 'S', 4, 10);
INSERT INTO HORARIO (ID_HOSTAL, DIA, HORA_ABRE, HORA_CIERRA) VALUES (61, 'D', 15,18);

-- POBLAR INMUEBLE
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (1, '985 Clyde Gallagher Trail', 'Vivienda', 3, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (2, '55926 Commercial Parkway', 'Habitacion', 2, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (3, '8 Namekagon Street', 'Apartamento', 2, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (4, '12 Karstens Trail', 'Habitacion Hotel', 1, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (5, '865 Rieder Park', 'Habitacion Hostal', 10, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (6, '14 High Crossing Trail', 'Habitacion Vivienda', 2, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (7, '97783 Clyde Gallagher Way', 'Vivienda', 1, 1, '15-Apr-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (8, '101 Warner Street', 'Habitacion', 2, 0, '17-Oct-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (9, '5 Clove Place', 'Apartamento', 10, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (10, '40 Tony Center', 'Habitacion Hotel', 1, 0, '15-Nov-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (11, '9904 Vernon Pass', 'Habitacion Hostal', 9, 0, '04-Jan-2022');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (12, '18 Scofield Center', 'Habitacion Vivienda', 10, 0, '25-Mar-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (13, '571 Roth Point', 'Vivienda', 3, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (14, '89440 North Hill', 'Habitacion', 4, 1, '02-Jul-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (15, '10972 North Road', 'Apartamento', 4, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (16, '5 Valley Edge Trail', 'Habitacion Hotel', 10, 0, '16-Dec-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (17, '281 Gateway Terrace', 'Habitacion Hostal', 8, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (18, '7551 Red Cloud Parkway', 'Habitacion Vivienda', 1, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (19, '673 Waxwing Crossing', 'Vivienda', 4, 0, '24-Nov-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (20, '3 Village Green Pass', 'Habitacion', 4, 1, '27-Jul-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (21, '22 American Ash Place', 'Apartamento', 1, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (22, '5 Thompson Center', 'Habitacion Hotel', 9, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (23, '1150 Scoville Terrace', 'Habitacion Hostal', 10, 0, '04-Aug-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (24, '1 Thierer Circle', 'Habitacion Vivienda', 7, 1, '08-Mar-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (25, '49 7th Drive', 'Vivienda', 5, 1, '09-Jun-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (26, '24 Crest Line Junction', 'Habitacion', 6, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (27, '02 Ronald Regan Parkway', 'Apartamento', 3, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (28, '82 Coolidge Hill', 'Habitacion Hotel', 2, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (29, '944 Kennedy Plaza', 'Habitacion Hostal', 7, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (30, '5550 Atwood Junction', 'Habitacion Vivienda', 6, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (31, '4 Schurz Avenue', 'Vivienda', 2, 1, '31-Dec-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (32, '8 Continental Parkway', 'Habitacion', 1, 1, '12-Feb-2022');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (33, '18 Hanover Terrace', 'Apartamento', 3, 0, '19-Feb-2022');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (34, '2 Browning Alley', 'Habitacion Hotel', 2, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (35, '4621 Erie Place', 'Habitacion Hostal', 4, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (36, '19 Becker Hill', 'Habitacion Vivienda', 1, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (37, '2679 Eastlawn Center', 'Vivienda', 4, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (38, '230 West Avenue', 'Habitacion', 2, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (39, '174 Lotheville Junction', 'Apartamento', 2, 0, '23-Nov-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (40, '02176 Lake View Park', 'Habitacion Hotel', 3, 1, '24-Sep-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (41, '8552 Sugar Park', 'Habitacion Hostal', 6, 1, '19-Apr-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (42, '0480 Lunder Park', 'Habitacion Vivienda', 2, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (43, '968 South Park', 'Vivienda', 8, 0, '22-Mar-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (44, '92053 Sage Way', 'Habitacion', 9, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (45, '33 Brentwood Circle', 'Apartamento', 4, 0, '22-Apr-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (46, '16948 Spenser Junction', 'Habitacion Hotel', 4, 0, '09-Jan-2022');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (47, '758 Calypso Park', 'Habitacion Hostal', 9, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (48, '380 Kenwood Lane', 'Habitacion Vivienda', 10, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (49, '47690 Arizona Crossing', 'Vivienda', 7, 1, '17-Dec-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (50, '63 Southridge Alley', 'Habitacion', 10, 0, '28-Apr-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (51, '1 Sloan Lane', 'Apartamento', 4, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (52, '162 Petterle Court', 'Habitacion Hotel', 7, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (53, '5 Continental Junction', 'Habitacion Hostal', 8, 1, '13-Aug-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (54, '029 Bay Avenue', 'Habitacion Vivienda', 4, 1, '24-Nov-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (55, '51460 Duke Terrace', 'Vivienda', 10, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (56, '14544 Merry Place', 'Habitacion', 5, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (57, '21 Graceland Street', 'Apartamento', 7, 0, '05-Aug-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (58, '05 Morningstar Alley', 'Habitacion Hotel', 8, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (59, '69213 Crest Line Point', 'Habitacion Hostal', 3, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (60, '158 Judy Crossing', 'Habitacion Vivienda', 8, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (61, '047 Carioca Court', 'Vivienda', 2, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (62, '35604 Commercial Trail', 'Habitacion', 2, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (63, '2 Packers Terrace', 'Apartamento', 6, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (64, '0024 Pepper Wood Hill', 'Habitacion Hotel', 8, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (65, '4 Kropf Drive', 'Habitacion Hostal', 7, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (66, '8738 Talmadge Way', 'Habitacion Vivienda', 8, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (67, '55 Cordelia Avenue', 'Vivienda', 4, 1, '14-Feb-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (68, '66 Surrey Lane', 'Habitacion', 4, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (69, '24531 Rigney Street', 'Apartamento', 6, 1, '11-Jun-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (70, '95566 Kennedy Road', 'Habitacion Hotel', 1, 1, '27-Jun-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (71, '67 Ronald Regan Crossing', 'Habitacion Hostal', 9, 0, '02-Oct-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (72, '066 Dottie Parkway', 'Habitacion Vivienda', 5, 0, '05-Aug-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (73, '8 Montana Avenue', 'Vivienda', 2, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (74, '5 Dunning Avenue', 'Habitacion', 6, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (75, '321 Oriole Crossing', 'Apartamento', 5, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (76, '73 Shopko Crossing', 'Habitacion Hotel', 10, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (77, '78732 Grayhawk Point', 'Habitacion Hostal', 2, 0, '20-Mar-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (78, '66 North Lane', 'Habitacion Vivienda', 7, 0, '27-Jan-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (79, '558 Duke Road', 'Vivienda', 3, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (80, '66 Troy Parkway', 'Habitacion', 8, 1, '01-Sep-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (81, '82760 Ridgeway Terrace', 'Apartamento', 10, 1, '06-Apr-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (82, '3 Elgar Court', 'Habitacion Hotel', 1, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (83, '21249 Artisan Street', 'Habitacion Hostal', 3, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (84, '8 Dixon Avenue', 'Habitacion Vivienda', 2, 0, '19-Aug-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (85, '8 Loeprich Trail', 'Vivienda', 1, 1, '14-Jul-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (86, '12 Green Ridge Road', 'Habitacion', 1, 0, '24-Oct-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (87, '0 Lakewood Gardens Drive', 'Apartamento', 8, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (88, '1063 Hoffman Lane', 'Habitacion Hotel', 7, 0, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (89, '134 Sommers Way', 'Habitacion Hostal', 7, 0, '25-Dec-2020');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (90, '13 Morning Center', 'Habitacion Vivienda', 10, 0, '17-Feb-2022');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (91, '22 Bultman Place', 'Vivienda', 3, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (92, '196 Utah Crossing', 'Habitacion', 4, 1, '12-Jun-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (93, '575 Beilfuss Place', 'Apartamento', 10, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (94, '25870 Manitowish Point', 'Habitacion Hotel', 6, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (95, '85 Boyd Park', 'Habitacion Hostal', 10, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (96, '96850 Garrison Street', 'Habitacion Vivienda', 6, 1, '09-Apr-2021');
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (97, '68 Harbort Street', 'Vivienda', 3, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (98, '01354 School Court', 'Habitacion', 7, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (99, '0 Dottie Alley', 'Apartamento', 1, 1, null);
insert into INMUEBLE (ID, DIRECCION, TIPO, CAPACIDAD, DISPONIBLE, FECHA_RESERVA_FINAL) values (100, '86656 Roth Alley', 'Habitacion Hotel', 6, 1, '30-Sep-2020');



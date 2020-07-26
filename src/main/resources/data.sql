INSERT INTO identification_schema.identification
VALUES (NEXTVAL('IDENTIFICATION_ID_GENERATOR'),true,now(),'Identificacion del Instituto Nacional Electoral','INE');
INSERT INTO identification_schema.identification
VALUES (NEXTVAL('IDENTIFICATION_ID_GENERATOR'),true,now(),'Pasaporte emitido por el estado Mexicano','PASSPORT');
INSERT INTO identification_schema.identification
VALUES (NEXTVAL('IDENTIFICATION_ID_GENERATOR'),true,now(),'Cartilla Militar emitido por el estado Mexicano','CARTILLA');

INSERT INTO person_schema.person VALUES (NEXTVAL('PERSON_ID_GENERATOR'),true,33,'SSSSSSSSSSSS',now(),'H','Test User');

INSERT INTO person_schema.person_identification VALUES (NEXTVAL('PERSON_IDENTIFICATION_ID_GENERATOR'),true,now(),'TESTTESTTEST',1,1)
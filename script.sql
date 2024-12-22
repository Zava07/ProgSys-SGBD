SHOW TABLE
-- create 
CREATE TABLE Etudiant(IdEt int,NomEt String,SemEt int)
CREATE TABLE Semestre(IdSem int,NomSem String)
CREATE TABLE Test(IdSem int,NomSem String)
-- read
SELECT * FROM etudiant
SELECT * FROM test
SELECT (IdEt) FROM etudiant
SELECT (IdSem) FROM Test
SELECT * FROM Test WHERE IdSem <= 1
SELECT (IdEt) FROM etudiant where IdEt=1
-- update 
UPDATE Test set NomSem=Kasaina
UPDATE Etudiant set NomEt=Kasaina
UPDATE Test set IdSem=okok WHERE IdSem=2
UPDATE Test set NomSem=Kasaina WHERE IdSem=0
UPDATE Test set NomSem=Kasa where IdSem=1
-- delete 
DROP TABLE Test
DROP TABLE ok
-- insert 
INSERT INTO Test (IdSem,NomSem) VALUES (1,Rakoto)
INSERT INTO Test (IdSem,NomSem) VALUES (2,Kasaina)
INSERT INTO Test (IdSem,NomSem) VALUES (3,Kas)
INSERT INTO Etudiant (IdEt,NomEt,SemEt) VALUES (3,Rakoto,2)
-- upd file 
DELETE FROM Test
DELETE FROM Test WHERE IdSem=1


-- else if (attribute.getDomaine().equals(String.class)) {
--                         // Vérification
--                         boolean isInteger = false;
--                         boolean isBoolean = false;
--                         try {
--                             Integer.parseInt(value.toString());
--                             isInteger = true;
--                         } catch (NumberFormatException ignored) {
--                             // Pas un entier
--                         }
--                         try {
--                             Boolean.parseBoolean(value.toString());
--                             isBoolean = true;
--                         } catch (Exception ignored) {
--                             // Pas un booléen
--                         }

--                         // Si convertible en Integer ou Boolean
--                         if (isInteger || isBoolean) {
--                             result += "La valeur '" + value + "' ne peut pas être un String.";
--                             return result;
--                             // throw new IllegalArgumentException(
--                             // "La valeur '" + value + "' ne peut pas être un String.");
--                         }
--                         data.set(0, value.toString());
--                     }
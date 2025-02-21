# Documentació Tècnica: Gestor d'Empresa amb Hibernate

## 1. De quina lògica d’aplicació s’encarrega el Patró DAO?

De la lògica de negoci

## 2. Per què considereu què és útil el patró DAO i en què us ha servit?

El patró DAO (Data Access Object) és útil perquè separa la lògica d'accés a les dades de la lògica de negoci de l'aplicació. En el context de l'activitat A02 de la UF2, on es va utilitzar JDBC per connectar-se a una base de dades, el patró DAO va ser especialment útil per encapsular les operacions de la base de dades (com ara inserir, actualitzar, esborrar i consultar dades) en mètodes específics. Això va permetre mantenir el codi més organitzat i va facilitar la gestió de les transaccions i les excepcions.

## 3. Heu hagut de fer cap ajust al vostre codi d’aplicació (Main, Controladors, Vistes, altres classes que no siguin DAO, etc.) ? Si és així, detalleu de forma breu quins canvis heu fet i per què?

No he hagut de fer cap ajust a cap altre classe.

## 4. Completeu el diagrama de classes de l’activitat A01 de la UF2 incorporant les interfícies, la classe abstracta i els DAOs.

## 5. Per últim valoreu el paper que hi juga la classe abstracta. És en tots els casos necessària? En el cas de l’activitat A02 de la UF2, on vau emprar JDBC, penseu que seria d’utilitat?

GenDAOImpl proporciona una implementació genèrica dels mètodes bàsics d'accés a les dades (com ara save, update, delete, get, i getAll), que poden ser heretats per classes més específiques com EquipDAO. 

### Necessitat de la classe abstracta:

No sempre és necessària una classe abstracta en la implementació del patró DAO. En alguns casos, si les operacions de la base de dades són molt simples o si només es necessita un DAO per a una única entitat, es podria implementar directament sense una classe abstracta. No obstant això, en projectes més grans o quan es treballa amb múltiples entitats, la classe abstracta és molt útil per evitar la duplicació de codi i mantenir una estructura més organitzada.

### Utilitat en l'activitat A02 de la UF2:

En el cas de l'activitat A02, on es va utilitzar JDBC per connectar-se a una base de dades, una classe abstracta com GenDAOImpl seria d'utilitat per encapsular les operacions comunes de la base de dades. Això permetria als DAOs específics (com EquipDAO) centrar-se només en la lògica específica de cada entitat, mentre que la classe abstracta gestionaria les operacions genèriques com la connexió a la base de dades, la gestió de transaccions i el control d'excepcions.
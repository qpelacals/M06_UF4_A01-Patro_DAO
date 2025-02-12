# Documentació Tècnica: Gestor d'Empresa amb Hibernate

## Configuració de Hibernate

### Fitxer de configuració (`hibernate.cfg.xml`)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <!-- Configuració de la connexió a MariaDB -->
        <property name="connection.driver_class">org.mariadb.jdbc.Driver</property>
        <property name="connection.url">jdbc:mariadb://localhost:3306/empresa</property>
        <property name="connection.username">root</property>
        <property name="connection.password">system</property>
        <property name="dialect">org.hibernate.dialect.MySQLDialect</property>
        
        <!-- Mapejament d'entitats -->
        <mapping class="com.iticbcn.quimpelacals.models.Empleat"/>
        <mapping resource="Equip.hbm.xml"/>
        <mapping class="com.iticbcn.quimpelacals.models.Tasca"/>
    </session-factory>
</hibernate-configuration>
```

## Mapejament d'Entitats

### 1. Ús d'Anotacions JPA i XML

Entitats amb anotacions (Empleat, Tasca):

```java
@Entity
@Table(name = "Empleat")
public class Empleat {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long empleat_id;

    @ManyToMany
    @JoinTable(
        name = "Empleat_Equip",
        joinColumns = @JoinColumn(name = "empleat_id"),
        inverseJoinColumns = @JoinColumn(name = "equip_id")
    )
    private List<Equip> equips;
}
```

Entitats amb XML (Equip.hbm.xml):

```xml
<hibernate-mapping>
    <class name="com.iticbcn.quimpelacals.models.Equip" table="Equip">
        <id name="id" column="id" type="long">
            <generator class="identity"/>
        </id>
        <property name="nom" column="nom" not-null="true"/>
        <bag name="empleats" table="Empleat_Equip" inverse="true">
            <key column="equip_id"/>
            <many-to-many class="com.iticbcn.quimpelacals.models.Empleat" column="empleat_id"/>
        </bag>
    </class>
</hibernate-mapping>
```

## Gestió de Relacions

### Relació Many-to-Many (Empleat ↔ Equip)
Taula intermèdia: Empleat_Equip amb dues claus foranes.

Estratègia:

- Mapejament bidireccional:

  - L'Empleat utilitza anotacions per definir la relació.

  - L'Equip utilitza XML per evitar conflictes amb la configuració de Hibernate.

```sql
-- Estructura de la taula Empleat_Equip
CREATE TABLE Empleat_Equip (
    empleat_id BIGINT,
    equip_id BIGINT,
    PRIMARY KEY (empleat_id, equip_id)
);
```

### Relació One-to-Many Bidireccional (Empleat ↔ Tasca)

Entitat `Tasca`:

```java
@ManyToOne
@JoinColumn(name = "empleat_id", nullable = false)
private Empleat empleat;
```

Entitat Empleat:
```java
@OneToMany(mappedBy = "empleat", cascade = CascadeType.ALL)
private List<Tasca> tasques;
```

## Gestió de Sessió i Transaccions

### Plantilla per a Operacions CRUD

```java
public class TascaDAO {
    public void saveTasca(Tasca tasca) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(tasca);
            session.getTransaction().commit();
        }
    }
}
```

### Tractament d'Entitats Desvinculades (Detached)
Error comú:
`detached entity passed to persist` en intentar guardar una entitat recuperada en una sessió tancada.

Solució:
```java
// Opció 1: Utilitzar merge() en lloc de persist()
session.merge(tasca);

// Opció 2: Recuperar l'entitat dins la sessió actual
Empleat empleat = session.find(Empleat.class, empleatId);
tasca.setEmpleat(empleat);
```

## Configuració Avançada

### Polítiques de Cascada

Exemple per a eliminació en cascada:

```java
@OneToMany(mappedBy = "empleat", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Tasca> tasques;
```

### Logging de Consultes SQL

```sql
<property name="show_sql">true</property>
<property name="format_sql">true</property>
```

## Exemple de Consulta HQL

### Consulta per a Estadístiques de Tasques

```java
public Map<String, Long> countTasksPerEmployee() {
    String hql = "SELECT CONCAT(e.nom, ' ', e.cognoms), COUNT(t.id) " +
                 "FROM Empleat e LEFT JOIN e.tasques t " +
                 "GROUP BY e.nom, e.cognoms";
    
    try (Session session = sessionFactory.openSession()) {
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        List<Object[]> results = query.list();
        
        Map<String, Long> resultMap = new HashMap<>();
        for (Object[] result : results) {
            resultMap.put((String) result[0], (Long) result[1]);
        }
        return resultMap;
    }
}
```

## Eines Auxiliars

Classe HibernateUtil:

```java
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
```
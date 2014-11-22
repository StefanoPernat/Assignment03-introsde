## Introsde Assigment 3: SOAP Life Coach

### Phase 1: **The database**
My project is based on a **sqlite** database as follows:

Person (idPerson, firstname, lastname)  
Measure (idMeasure, dateRegistered, measureType, measureValue, valueType, isCurrent, idPerson)

SQL Instructions:
```sql
`CREATE TABLE Person (
idPerson	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
firstname	TEXT NOT NULL,
lastname	TEXT NOT NULL,
UNIQUE(firstname, lastname)
);

```
`


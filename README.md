## Introsde Assigment 3: SOAP Life Coach

### Phase 1: **The database**
My project is based on a **sqlite** database as follows:

Person (idPerson, firstname, lastname)  
Measure (idMeasure, dateRegistered, measureType, measureValue, valueType, isCurrent, idPerson)

SQL Instructions:
```sql
CREATE TABLE Person (
idPerson	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
firstname	TEXT NOT NULL,
lastname	TEXT NOT NULL,
UNIQUE(firstname, lastname)
);

CREATE TABLE Measure (
	idMeasure		INTEGER PRIMARY KEY AUTOINCREMENT,
	dateRegistered	DATE NOT NULL,
	measureType		TEXT NOT NULL,
	measureValue	TEXT NOT NULL,
	valueType		TEXT NOT NULL,
	isCurrent		INTEGER NOT NULL DEFAULT '1',
	idPerson		INTEGER NOT NULL,
	FOREIGN KEY(`idPerson`) REFERENCES Person(idPerson)
);
```

Added the following tuples to **Person**:

|idPerson|firstname|lastname|
|--------|---------|--------|
|    1   | Stefano | Pernat |
|    2   |  Pinco  | Pallino|
|    3   |  Marco  |Pedrazzi|
|    4   |   Luka  | Modric |

Added the following tuples to **Measure**:

|idMeasure|dateRegistered|measureType|measureValue|valueType|isCurrent|idPerson|
|---------|--------------|-----------|------------|---------|---------|--------|


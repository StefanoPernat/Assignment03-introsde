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
|    1    |  2014-08-10  |   weight  |    62.5    |  Double |    1    |    1   |
|    2    |  2014-08-10  |   height  |    172     | Integer |    1    |    1   |
|    3    |  2014-11-10  |   steps   |    3000    | Integer |    1    |    1   |
|    4    |  2014-02-25  |   weight  |    85.5    |  Double |    1    |    2   |
|    5    |  2014-06-11  |   height  |    180     | Integer |    1    |    2   |
|    6    |  2014-09-01  |   steps   |   10000    | Integer |    1    |    2   |
|    7    |  2014-05-31  |   weight  |    75.7    |  Double |    1    |    3   |
|    8    |  2014-07-05  |   height  |    178     | Integer |    1    |    3   |
|    9    |  2014-03-07  |   steps   |    5500    | Integer |    1    |    3   |
|   10    |  2014-06-18  |   weight  |     65     |  Double |    1    |    4   |
|   11    |  2014-06-18  |   height  |    174     | Integer |    1    |    4   |
|   12    |  2014-11-22  |   steps   |   20700    | Integer |    1    |    4   |
|   13    |  2014-01-03  |   weight  |    57.2    |  Double |    0    |    1   |
|   14    |  2013-03-30  |   weight  |    52.5    |  Double |    0    |    1   |
|   15    |  2012-09-07  |   weight  |    70.8    |  Double |    0    |    2   |
|   16    |  2013-10-12  |   weight  |    79.5    |  Double |    0    |    2   |
|   17    |  2013-01-29  |   weight  |    69.3    |  Double |    0    |    3   |
|   18    |  2013-07-17  |   weight  |    74.7    |  Double |    0    |    3   |
|   19    |  2012-09-07  |   weight  |     60     |  Double |    0    |    4   |
|   20    |  2013-09-09  |   weight  |    62.1    |  Double |    0    |    4   |
|   21    |  2005-05-10  |   height  |    165     | Integer |    0    |    1   |
|   22    |  2010-03-04  |   height  |    171     | Integer |    0    |    2   |
|   23    |  2005-01-23  |   height  |    168     | Integer |    0    |    3   |
|   24    |  2012-05-15  |   height  |    170     | Integer |    0    |    4   |
|   25    |  2010-07-09  |   steps   |    2000    | Integer |    0    |    1   |
|   26    |  2012-10-02  |   steps   |    2560    | Integer |    0    |    1   |
|   27    |  2012-09-07  |   steps   |    4790    | Integer |    0    |    2   |
|   28    |  2013-10-15  |   steps   |    5300    | Integer |    0    |    2   |
|   29    |  2013-01-03  |   steps   |    1700    | Integer |    0    |    3   |
|   30    |  2014-01-02  |   steps   |    4820    | Integer |    0    |    3   |
|   31    |  2011-09-03  |   steps   |    7500    | Integer |    0    |    4   |
|   32    |  2012-09-05  |   steps   |    17000   | Integer |    0    |    4   |






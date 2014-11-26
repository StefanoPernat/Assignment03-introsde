## Introsde Assigment 3: SOAP Life Coach

### Phase I: **The database**
My project is based on a **sqlite** database as follows:

Person (idPerson, firstname, lastname)  
Measure (idMeasure, dateRegistered, measureType, measureValue, valueType, isCurrent, idPerson)

SQL Instructions:
```sql
CREATE TABLE Person (
idPerson	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
firstname	TEXT NOT NULL,
lastname	TEXT NOT NULL,
birthdate	DATE NOT NULL,
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
	FOREIGN KEY(idPerson) REFERENCES Person(idPerson)
);
```

Added the following tuples to **Person**:

|idPerson|firstname|lastname|birthdate |
|--------|---------|--------|----------|
|    1   | Stefano | Pernat |1989-02-07|
|    2   |  Pinco  | Pallino|1982-11-10|
|    3   |  Marco  |Pedrazzi|1989-12-13|
|    4   |   Luka  | Modric |1985-09-09|

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



## Phase II: **The Model**


```java
public class Person implements Serializable {
	private Long idPerson;
	private String firstname;
	private String lastname;
	private Date birthdate;
	private List<Measure> currentHealth;
	private List<Measure> healthHistory;
}

public class Measure implements Serializable {
	private Long idMeasure;
	private Date dateRegistered;
	private String measureType;
	private String measureValue;
	private String valueType;
	private int isCurrent; // 1 current Health, 0 History value
}
```

## Phase 3: The Request

**REQUEST 1:** readPersonList(): return all people saved into the database (personal info + healthprofile)

POST request is the following:

```xml
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
  <soap:Body xmlns:m="http://soap.assignment.introsde/">
    <m:readPeopleList />
</soap:Body>
</soap:Envelope>
```

**REQUEST 2:** readPerson(Long id): return the Person (personal info + healthprofile) of the Person identified by id

POST request is the following

```xml
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
  <soap:Body xmlns:m="http://soap.assignment.introsde/">
    <m:readPerson>
      <personId>2</personId>
    </m:readPerson>
</soap:Body>
</soap:Envelope>
```

**REQUEST 3:** updatePerson(Person p): update the personal info of the Person Passed as input

PUT request is the following:

```xml
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
  <soap:Body xmlns:m="http://soap.assignment.introsde/">
    <m:updatePerson>
      <person>
      	<personId>2</personId>
        <firstname>Antonio</firstname>
        <lastname>Pallino</lastname>
        <birthdate>1979-03-01</birthdate>
      </person>
    </m:updatePerson>
</soap:Body>
</soap:Envelope>
```

**Before**:
```xml
<person>
	<personId>2</personId>
	<firstname>Pinco</firstname>
	<lastname>Pallino</lastname>
	<birthdate>1980-01-01</birthdate>
	<currentHealth>
		<measure>
			<mid>4</mid>
			<dateRegistered>2014-02-25</dateRegistered>
			<measureType>weight</measureType>
			<measureValue>85.5</measureValue>
			<measureValueType>Double</measureValueType>
		</measure>
		<measure>
			<mid>5</mid>
			<dateRegistered>2014-06-11</dateRegistered>
			<measureType>height</measureType>
			<measureValue>180</measureValue>
			<measureValueType>Integer</measureValueType>
		</measure>
		<measure>
			<mid>6</mid>
			<dateRegistered>2014-09-01</dateRegistered>
			<measureType>steps</measureType>
			<measureValue>10000</measureValue>
			<measureValueType>Integer</measureValueType>
		</measure>
	</currentHealth>
</person>
```
**After**:
```xml
<person>
	<personId>2</personId>
	<firstname>Antonio</firstname>
	<lastname>Pallino</lastname>
	<birthdate>1979-03-01</birthdate>
	<currentHealth>
		<measure>
			<mid>4</mid>
			<dateRegistered>2014-02-25</dateRegistered>
			<measureType>weight</measureType>
			<measureValue>85.5</measureValue>
			<measureValueType>Double</measureValueType>
		</measure>
		<measure>
			<mid>5</mid>
			<dateRegistered>2014-06-11</dateRegistered>
			<measureType>height</measureType>
			<measureValue>180</measureValue>
			<measureValueType>Integer</measureValueType>
		</measure>
		<measure>
			<mid>6</mid>
			<dateRegistered>2014-09-01</dateRegistered>
			<measureType>steps</measureType>
			<measureValue>10000</measureValue>
			<measureValueType>Integer</measureValueType>
		</measure>
	</currentHealth>
</person>
```

**REQUEST 4**: createPerson(Person p): create a new person (only personal info, no currentHealth)

POST request is the following:
```xml
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
  <soap:Body xmlns:m="http://soap.assignment.introsde/">
    <m:createPerson>
      <person>
        <personId>0</personId>
        <firstname>Test</firstname>
        <lastname>Creation</lastname>
        <birthdate>1914-01-01</birthdate>
      </person>
    </m:createPerson>
</soap:Body>
</soap:Envelope>
```
The created Person is:
```xml
<person>
	<personId>5</personId>
	<firstname>Test</firstname>
	<lastname>Creation</lastname>
	<birthdate>1914-01-01</birthdate>
	<currentHealth />
</person>
```
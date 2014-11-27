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

**REQUEST 5**: deletePerson(Long id) should delete the Person identified by {id} from the system

DELETE request is the following:
```xml
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
  <soap:Body xmlns:m="http://soap.assignment.introsde/">
    <m:deletePerson>
      <personId>55</personId>
    </m:deletePerson>
</soap:Body>
</soap:Envelope>
```


**REQUEST 6**: readPersonHistory(Long id, String measureType): return the history of a **measureType** for person identified by **id**

POST request is the following:
```xml
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
  <soap:Body xmlns:m="http://soap.assignment.introsde/">
    <m:readPersonHistory>
      <personId>1</personId>
      <measureType>weight</measureType>
    </m:readPersonHistory>
</soap:Body>
</soap:Envelope>
```

**REQUEST 7**: readPersonMeasurement(Long id, String measureType, Long mid): return the selected measure of history
```xml
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
  <soap:Body xmlns:m="http://soap.assignment.introsde/">
    <m:readPersonMeasurement>
      <personId>1</personId>
      <measureType>weight</measureType>
      <mid>13</mid>
    </m:readPersonMeasurement>
</soap:Body>
</soap:Envelope>
```

**REQUEST 8**: savePersonMeasurement(Long id, Measure m): save a new **Measure** for Person identified by **id**

POST request is the following:
```xml
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
  <soap:Body xmlns:m="http://soap.assignment.introsde/">
    <m:savePersonMeasurement>
      <personId>5</personId>
      <measure>
        <mid>0</mid>
        <dateRegistered>2014-11-27</dateRegistered>
        <measureType>weight</measureType>
        <measureValue>80</measureValue>
        <measureValueType>Double</measureValueType>
      </measure>
    </m:savePersonMeasurement>
</soap:Body>
</soap:Envelope>
```

**REQUEST 9**: readMeasureTypes(): return all the **measure types**

POST request is the following:
```xml
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
  <soap:Body xmlns:m="http://soap.assignment.introsde/">
    <m:readMeasureTypes />
</soap:Body>
</soap:Envelope>
```

**EXTRA 3**: return the history of **measureType** for Person **id** with date in range

POST request is the following
```xml
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
  <soap:Body xmlns:m="http://soap.assignment.introsde/">
    <m:readPersonMeasureByDates>
      <personId>1</personId>
      <measureType>weight</measureType>
      <after>2013-01-01</after>
      <before>2014-01-05</before>
    </m:readPersonMeasureByDates>
</soap:Body>
</soap:Envelope>
```

**EXTRA 4**: readPersonListByMeasurement(String measureType, String maxValue, String minValue): list people whose measureType is in range

POST request is the following:
```xml
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
  <soap:Body xmlns:m="http://soap.assignment.introsde/">
    <m:readPersonListByMeasurement>
      <measureType>weight</measureType>
      <minValue>72</minValue>
      <maxValue>82</maxValue>
    </m:readPersonListByMeasurement>
</soap:Body>
</soap:Envelope>
```

### Phase III: **The Client**

The client write a file named **output.txt**

REQUEST 1: readPersonList()
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPeopleList xmlns:ns2="http://soap.assignment.introsde/"/></S:Body></S:Envelope>
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPeopleListResponse xmlns:ns2="http://soap.assignment.introsde/"><people><person><personId>1</personId><firstname>Stefano</firstname><lastname>Pernat</lastname><birthdate>1989-07-02</birthdate><currentHealth><measure><mid>1</mid><dateRegistered>2014-08-10</dateRegistered><measureType>weight</measureType><measureValue>62.5</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>2</mid><dateRegistered>2014-08-10</dateRegistered><measureType>height</measureType><measureValue>172</measureValue><measureValueType>Integer</measureValueType></measure><measure><mid>3</mid><dateRegistered>2014-11-10</dateRegistered><measureType>steps</measureType><measureValue>3000</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person><person><personId>2</personId><firstname>Antonio</firstname><lastname>Pallino</lastname><birthdate>1979-03-01</birthdate><currentHealth><measure><mid>4</mid><dateRegistered>2014-02-25</dateRegistered><measureType>weight</measureType><measureValue>85.5</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>5</mid><dateRegistered>2014-06-11</dateRegistered><measureType>height</measureType><measureValue>180</measureValue><measureValueType>Integer</measureValueType></measure><measure><mid>6</mid><dateRegistered>2014-09-01</dateRegistered><measureType>steps</measureType><measureValue>10000</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person><person><personId>3</personId><firstname>Marco</firstname><lastname>Pedrazzi</lastname><birthdate>1989-12-13</birthdate><currentHealth><measure><mid>7</mid><dateRegistered>2014-05-31</dateRegistered><measureType>weight</measureType><measureValue>75.7</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>8</mid><dateRegistered>2014-07-05</dateRegistered><measureType>height</measureType><measureValue>178</measureValue><measureValueType>Integer</measureValueType></measure><measure><mid>9</mid><dateRegistered>2014-03-07</dateRegistered><measureType>steps</measureType><measureValue>5500</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person><person><personId>4</personId><firstname>Luka</firstname><lastname>Modric</lastname><birthdate>1985-09-09</birthdate><currentHealth><measure><mid>10</mid><dateRegistered>2014-06-18</dateRegistered><measureType>weight</measureType><measureValue>65</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>11</mid><dateRegistered>2014-06-18</dateRegistered><measureType>height</measureType><measureValue>174</measureValue><measureValueType>Integer</measureValueType></measure><measure><mid>12</mid><dateRegistered>2014-11-22</dateRegistered><measureType>steps</measureType><measureValue>20700</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person><person><personId>5</personId><firstname>Creation Updated 2</firstname><lastname>Test</lastname><birthdate>1914-03-01</birthdate><currentHealth><measure><mid>133</mid><dateRegistered>2014-11-27</dateRegistered><measureType>weight</measureType><measureValue>80</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>183</mid><dateRegistered>2014-11-28</dateRegistered><measureType>height</measureType><measureValue>185</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person></people></ns2:readPeopleListResponse></S:Body></S:Envelope>
Person(1,Stefano,Pernat,Sun Jul 02 00:00:00 CEST 1989)
	Measure(1,Sun Aug 10 00:00:00 CEST 2014,weight,62.5,Double,1)
	Measure(2,Sun Aug 10 00:00:00 CEST 2014,height,172,Integer,1)
	Measure(3,Mon Nov 10 00:00:00 CET 2014,steps,3000,Integer,1)
Person(2,Antonio,Pallino,Thu Mar 01 00:00:00 CET 1979)
	Measure(4,Tue Feb 25 00:00:00 CET 2014,weight,85.5,Double,1)
	Measure(5,Wed Jun 11 00:00:00 CEST 2014,height,180,Integer,1)
	Measure(6,Mon Sep 01 00:00:00 CEST 2014,steps,10000,Integer,1)
Person(3,Marco,Pedrazzi,Wed Dec 13 00:00:00 CET 1989)
	Measure(7,Sat May 31 00:00:00 CEST 2014,weight,75.7,Double,1)
	Measure(8,Sat Jul 05 00:00:00 CEST 2014,height,178,Integer,1)
	Measure(9,Fri Mar 07 00:00:00 CET 2014,steps,5500,Integer,1)
Person(4,Luka,Modric,Mon Sep 09 00:00:00 CEST 1985)
	Measure(10,Wed Jun 18 00:00:00 CEST 2014,weight,65,Double,1)
	Measure(11,Wed Jun 18 00:00:00 CEST 2014,height,174,Integer,1)
	Measure(12,Sat Nov 22 00:00:00 CET 2014,steps,20700,Integer,1)
Person(5,Creation Updated 2,Test,Sun Mar 01 00:00:00 CET 1914)
	Measure(133,Thu Nov 27 00:00:00 CET 2014,weight,80,Double,1)
	Measure(183,Fri Nov 28 00:00:00 CET 2014,height,185,Integer,1)
REQUEST 2: readPerson(5)
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPerson xmlns:ns2="http://soap.assignment.introsde/"><personId>5</personId></ns2:readPerson></S:Body></S:Envelope>
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPersonResponse xmlns:ns2="http://soap.assignment.introsde/"><person><personId>5</personId><firstname>Creation Updated 2</firstname><lastname>Test</lastname><birthdate>1914-03-01</birthdate><currentHealth><measure><mid>133</mid><dateRegistered>2014-11-27</dateRegistered><measureType>weight</measureType><measureValue>80</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>183</mid><dateRegistered>2014-11-28</dateRegistered><measureType>height</measureType><measureValue>185</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person></ns2:readPersonResponse></S:Body></S:Envelope>
Person(5,Creation Updated 2,Test,Sun Mar 01 00:00:00 CET 1914)
	Measure(133,Thu Nov 27 00:00:00 CET 2014,weight,80,Double,1)
	Measure(183,Fri Nov 28 00:00:00 CET 2014,height,185,Integer,1)
REQUEST 3: updatePerson(5)
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:updatePerson xmlns:ns2="http://soap.assignment.introsde/"><person><personId>5</personId><firstname>Antonio</firstname><lastname>Rossi</lastname><birthdate>1914-03-01</birthdate><currentHealth><measure><mid>133</mid><dateRegistered>2014-11-27</dateRegistered><measureType>weight</measureType><measureValue>80</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>183</mid><dateRegistered>2014-11-28</dateRegistered><measureType>height</measureType><measureValue>185</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person></ns2:updatePerson></S:Body></S:Envelope>
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:updatePersonResponse xmlns:ns2="http://soap.assignment.introsde/"><person><personId>5</personId><firstname>Antonio</firstname><lastname>Rossi</lastname><birthdate>1914-03-01</birthdate><currentHealth><measure><mid>133</mid><dateRegistered>2014-11-27</dateRegistered><measureType>weight</measureType><measureValue>80</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>183</mid><dateRegistered>2014-11-28</dateRegistered><measureType>height</measureType><measureValue>185</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person></ns2:updatePersonResponse></S:Body></S:Envelope>
Person(5,Antonio,Rossi,Sun Mar 01 00:00:00 CET 1914)
	Measure(133,Thu Nov 27 00:00:00 CET 2014,weight,80,Double,1)
	Measure(183,Fri Nov 28 00:00:00 CET 2014,height,185,Integer,1)
REQUEST 4: createPerson
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:createPerson xmlns:ns2="http://soap.assignment.introsde/"><person><personId>0</personId><firstname>Test</firstname><lastname>VIVA</lastname><birthdate>2014-11-27</birthdate><currentHealth/></person></ns2:createPerson></S:Body></S:Envelope>
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:createPersonResponse xmlns:ns2="http://soap.assignment.introsde/"><person><personId>155</personId><firstname>Test</firstname><lastname>VIVA</lastname><birthdate>2014-11-27</birthdate><currentHealth/></person></ns2:createPersonResponse></S:Body></S:Envelope>
Person(155,Test,VIVA,Thu Nov 27 00:00:00 CET 2014)
REQUEST 5: deletePerson(5)
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:deletePerson xmlns:ns2="http://soap.assignment.introsde/"><personId>5</personId></ns2:deletePerson></S:Body></S:Envelope>
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:deletePersonResponse xmlns:ns2="http://soap.assignment.introsde/"><message>Person with id:5 deleted</message></ns2:deletePersonResponse></S:Body></S:Envelope>
REQUEST 1: readPersonList()
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPeopleList xmlns:ns2="http://soap.assignment.introsde/"/></S:Body></S:Envelope>
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPeopleListResponse xmlns:ns2="http://soap.assignment.introsde/"><people><person><personId>1</personId><firstname>Stefano</firstname><lastname>Pernat</lastname><birthdate>1989-07-02</birthdate><currentHealth><measure><mid>1</mid><dateRegistered>2014-08-10</dateRegistered><measureType>weight</measureType><measureValue>62.5</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>2</mid><dateRegistered>2014-08-10</dateRegistered><measureType>height</measureType><measureValue>172</measureValue><measureValueType>Integer</measureValueType></measure><measure><mid>3</mid><dateRegistered>2014-11-10</dateRegistered><measureType>steps</measureType><measureValue>3000</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person><person><personId>2</personId><firstname>Antonio</firstname><lastname>Pallino</lastname><birthdate>1979-03-01</birthdate><currentHealth><measure><mid>4</mid><dateRegistered>2014-02-25</dateRegistered><measureType>weight</measureType><measureValue>85.5</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>5</mid><dateRegistered>2014-06-11</dateRegistered><measureType>height</measureType><measureValue>180</measureValue><measureValueType>Integer</measureValueType></measure><measure><mid>6</mid><dateRegistered>2014-09-01</dateRegistered><measureType>steps</measureType><measureValue>10000</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person><person><personId>3</personId><firstname>Marco</firstname><lastname>Pedrazzi</lastname><birthdate>1989-12-13</birthdate><currentHealth><measure><mid>7</mid><dateRegistered>2014-05-31</dateRegistered><measureType>weight</measureType><measureValue>75.7</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>8</mid><dateRegistered>2014-07-05</dateRegistered><measureType>height</measureType><measureValue>178</measureValue><measureValueType>Integer</measureValueType></measure><measure><mid>9</mid><dateRegistered>2014-03-07</dateRegistered><measureType>steps</measureType><measureValue>5500</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person><person><personId>4</personId><firstname>Luka</firstname><lastname>Modric</lastname><birthdate>1985-09-09</birthdate><currentHealth><measure><mid>10</mid><dateRegistered>2014-06-18</dateRegistered><measureType>weight</measureType><measureValue>65</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>11</mid><dateRegistered>2014-06-18</dateRegistered><measureType>height</measureType><measureValue>174</measureValue><measureValueType>Integer</measureValueType></measure><measure><mid>12</mid><dateRegistered>2014-11-22</dateRegistered><measureType>steps</measureType><measureValue>20700</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person><person><personId>155</personId><firstname>Test</firstname><lastname>VIVA</lastname><birthdate>2014-11-27</birthdate><currentHealth/></person></people></ns2:readPeopleListResponse></S:Body></S:Envelope>
Person(1,Stefano,Pernat,Sun Jul 02 00:00:00 CEST 1989)
Person(2,Antonio,Pallino,Thu Mar 01 00:00:00 CET 1979)
Person(3,Marco,Pedrazzi,Wed Dec 13 00:00:00 CET 1989)
Person(4,Luka,Modric,Mon Sep 09 00:00:00 CEST 1985)
Person(155,Test,VIVA,Thu Nov 27 00:00:00 CET 2014)
REQUEST 6: readPersonHistory(1, weight)
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPersonHistory xmlns:ns2="http://soap.assignment.introsde/"><personId>1</personId><measureType>weight</measureType></ns2:readPersonHistory></S:Body></S:Envelope>
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPersonHistoryResponse xmlns:ns2="http://soap.assignment.introsde/"><healthProfile-history><measure><mid>13</mid><dateRegistered>2014-01-03</dateRegistered><measureType>weight</measureType><measureValue>57.2</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>14</mid><dateRegistered>2013-03-30</dateRegistered><measureType>weight</measureType><measureValue>52.5</measureValue><measureValueType>Double</measureValueType></measure></healthProfile-history></ns2:readPersonHistoryResponse></S:Body></S:Envelope>
Measure(13,Fri Jan 03 00:00:00 CET 2014,weight,57.2,Double,0)
Measure(14,Sat Mar 30 00:00:00 CET 2013,weight,52.5,Double,0)
REQUEST 7: readPersonMeasurement(1, weight, 13)
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPersonMeasurement xmlns:ns2="http://soap.assignment.introsde/"><personId>1</personId><measureType>weight</measureType><mid>13</mid></ns2:readPersonMeasurement></S:Body></S:Envelope>
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPersonMeasurementResponse xmlns:ns2="http://soap.assignment.introsde/"><healthProfile-history><measure><mid>13</mid><dateRegistered>2014-01-03</dateRegistered><measureType>weight</measureType><measureValue>57.2</measureValue><measureValueType>Double</measureValueType></measure></healthProfile-history></ns2:readPersonMeasurementResponse></S:Body></S:Envelope>
Measure(13,Fri Jan 03 00:00:00 CET 2014,weight,57.2,Double,0)
REQUEST 8: savePersonMeasurement(1, Measure m)
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:savePersonMeasurement xmlns:ns2="http://soap.assignment.introsde/"><personId>1</personId><measure><mid>0</mid><dateRegistered>2014-01-03</dateRegistered><measureType>weight</measureType><measureValue>100</measureValue><measureValueType>Double</measureValueType></measure></ns2:savePersonMeasurement></S:Body></S:Envelope>
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:savePersonMeasurementResponse xmlns:ns2="http://soap.assignment.introsde/"><mid>333</mid></ns2:savePersonMeasurementResponse></S:Body></S:Envelope>
REQUEST 9: readMeasureTypes() 
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readMeasureTypes xmlns:ns2="http://soap.assignment.introsde/"/></S:Body></S:Envelope>
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readMeasureTypesResponse xmlns:ns2="http://soap.assignment.introsde/"><measureTypes><measureType>height</measureType><measureType>steps</measureType><measureType>weight</measureType></measureTypes></ns2:readMeasureTypesResponse></S:Body></S:Envelope>
height
steps
weight
EXTRA 3: readPersonMeasureByDates(Long id, String measureType, Date before, Date after) 
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPersonMeasureByDates xmlns:ns2="http://soap.assignment.introsde/"><personId>1</personId><measureType>weight</measureType><after>2014-01-01T00:00:00+01:00</after><before>2014-12-01T00:00:00+01:00</before></ns2:readPersonMeasureByDates></S:Body></S:Envelope>
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPersonMeasureByDatesResponse xmlns:ns2="http://soap.assignment.introsde/"><healthProfile-history><measure><mid>1</mid><dateRegistered>2014-08-10</dateRegistered><measureType>weight</measureType><measureValue>62.5</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>13</mid><dateRegistered>2014-01-03</dateRegistered><measureType>weight</measureType><measureValue>57.2</measureValue><measureValueType>Double</measureValueType></measure></healthProfile-history></ns2:readPersonMeasureByDatesResponse></S:Body></S:Envelope>
Measure(1,Sun Aug 10 00:00:00 CEST 2014,weight,62.5,Double,0)
Measure(13,Fri Jan 03 00:00:00 CET 2014,weight,57.2,Double,0)
EXTRA 4: readPersonListByMeasurement(String measureType, String maxValue, String minValue)
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPersonListByMeasurement xmlns:ns2="http://soap.assignment.introsde/"><measureType>weight</measureType><maxValue>86</maxValue><minValue>75</minValue></ns2:readPersonListByMeasurement></S:Body></S:Envelope>
XML
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><S:Body><ns2:readPersonListByMeasurementResponse xmlns:ns2="http://soap.assignment.introsde/"><people><person><personId>2</personId><firstname>Antonio</firstname><lastname>Pallino</lastname><birthdate>1979-03-01</birthdate><currentHealth><measure><mid>4</mid><dateRegistered>2014-02-25</dateRegistered><measureType>weight</measureType><measureValue>85.5</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>5</mid><dateRegistered>2014-06-11</dateRegistered><measureType>height</measureType><measureValue>180</measureValue><measureValueType>Integer</measureValueType></measure><measure><mid>6</mid><dateRegistered>2014-09-01</dateRegistered><measureType>steps</measureType><measureValue>10000</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person><person><personId>3</personId><firstname>Marco</firstname><lastname>Pedrazzi</lastname><birthdate>1989-12-13</birthdate><currentHealth><measure><mid>7</mid><dateRegistered>2014-05-31</dateRegistered><measureType>weight</measureType><measureValue>75.7</measureValue><measureValueType>Double</measureValueType></measure><measure><mid>8</mid><dateRegistered>2014-07-05</dateRegistered><measureType>height</measureType><measureValue>178</measureValue><measureValueType>Integer</measureValueType></measure><measure><mid>9</mid><dateRegistered>2014-03-07</dateRegistered><measureType>steps</measureType><measureValue>5500</measureValue><measureValueType>Integer</measureValueType></measure></currentHealth></person></people></ns2:readPersonListByMeasurementResponse></S:Body></S:Envelope>
Person(2,Antonio,Pallino,Thu Mar 01 00:00:00 CET 1979)
Person(3,Marco,Pedrazzi,Wed Dec 13 00:00:00 CET 1989)
 
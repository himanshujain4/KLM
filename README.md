# KLM exercise Project

## About the Service

This is a sample Java / Maven / Spring Boot /Hibernate application that can be used to fetch the close price of a stock over various period of time.. It uses an in-memory database (H2) to store the data.


## Architecture
 
 1. **Controller:** is the presentation layer where the end points are located
 2. **Service:** is the service layer where the business logic resides
 3. **DAO:** is the persistence layer where the CRUD operations happen
 

## Unit Tests

 1. **For the Controller:** it uses the Spring Boot Test framework with mockMvc
 2. **For the Service/DAO:** it uses the Mockito framework with mock and injectMocks annotations 
 

## Exposed methods

**1. get the list of close rate of stock over time. Time can be year, month within a year or day within a month of year. 
	At least year should be present in the request to get the close price. HTTP Method: GET**
```
http://localhost:8080/getCloseRateOverTime/time?year=2014&month=04&day=04
```
```
{
  date: stock close price
}
```

**2. Get the average close rate of stock over a period. Period can be year, month within a year or day within a month of year.
	At least year should be present in the request to get the close price. HTTP Method: GET**
```
http://localhost:8080/getAverageCloseRateOverPeriod/period?year=2014&month=04&day=04
```
```
{
   date: stock close price
}
```

					

### To view your H2 in-memory database

The 'test' profile runs on H2 in-memory database. To view and query the database you can browse to http://localhost:8090/h2-console. Default username is 'sa' with a blank password. Make sure you disable this in your production profiles.



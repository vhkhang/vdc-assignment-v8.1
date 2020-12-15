# vdc-assignment-v8.1

## System overview

![Alt text](diagrams/System_overview.jpg)

## Services responsibilities:
* ALICE: A main contacting service from the Webpage/customer
* BOB: A connection point to get the voucher from 3rd party and directly or indirectly return the result to ALICE.
* DAVE: A notifier which needs to notify customer their new voucher by connect/use any system (SNS/...)
* The MessageQ: The indirectly way where can notify to system whenever the new voucher has been created.

(?) IMHO, we can easily scale and secure the system.

## TODO:
* Bob/Dave service are missing.
* The messageQ setup needs more time than what I've tented to invest into.
* Get all related vouchers of a phone number service is missing on ALICE.

## Project setup for ALICE
1/ `cd alice`

2/ Run `mvn spring-boot:run` to run the application

## Test data setup
1/ Open H2 console via browser. (http://localhost:8080/h2)

2/ url: jdbc:h2:file:./db/test_database 

3/ username: sa

4/ password:

## Supporting services (Swagger-ui):
It is serving under: `http://localhost:8080/swagger-ui.html`

## Using libs
* Spring boot starter web dependencies
* Spring JPA with H2
* Flyway for DB migration
* MockMvc for the better junit testing setup
* Lombok to reduce boiler plate code
* Springdoc for serving the swagger-ui / openApi-docs

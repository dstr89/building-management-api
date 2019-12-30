# building-management-api

## API usage

## Login

### Get token
```curl -X POST http://localhost:8080/auth/v1/login -H "Content-Type:application/json" -d "{\"username\":\"admin\", \"password\":\"!8hn}BUrpcxU/!4{\"}"```

### Test token
```curl -X GET http://localhost:8080/user/v1/me -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3Njk5OTA5LCJleHAiOjE1Nzc3MDM1MDl9.m5Q7R6NepB_fmFUu-4r2gXp8p24C12t1Bn_Hbb-4eYU"```

## Buildings

### List all buildings
```curl -X GET localhost:8080/api/v1/buildings -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3Njk5OTA5LCJleHAiOjE1Nzc3MDM1MDl9.m5Q7R6NepB_fmFUu-4r2gXp8p24C12t1Bn_Hbb-4eYU"```
### Get specific building by ID
```curl -X GET localhost:8080/api/v1/buildings/1 -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3Njk5OTA5LCJleHAiOjE1Nzc3MDM1MDl9.m5Q7R6NepB_fmFUu-4r2gXp8p24C12t1Bn_Hbb-4eYU"```
### Save new building
```curl -X POST localhost:8080/api/v1/buildings -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3Njk5OTA5LCJleHAiOjE1Nzc3MDM1MDl9.m5Q7R6NepB_fmFUu-4r2gXp8p24C12t1Bn_Hbb-4eYU" -H "Content-type:application/json" -d {\"name\":\"Zgrada1\",\"city\":\"Zagreb\"}```
### Update existing building
```curl -X PUT localhost:8080/api/v1/buildings/1 -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3Njk5OTA5LCJleHAiOjE1Nzc3MDM1MDl9.m5Q7R6NepB_fmFUu-4r2gXp8p24C12t1Bn_Hbb-4eYU" -H "Content-type:application/json" -d {\"name\":\"Zgrada1\",\"city\":\"Osijek\"}```
### Delete existing building
```curl -X DELETE localhost:8080/api/v1/buildings/2 -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3Njk5OTA5LCJleHAiOjE1Nzc3MDM1MDl9.m5Q7R6NepB_fmFUu-4r2gXp8p24C12t1Bn_Hbb-4eYU"```

## Spring

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/maven-plugin/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#boot-features-security)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#production-ready)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#using-boot-devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

# EVO Payments - Turnkey API - JAVA SDK
Java integration (library, samples etc.) to the Turnkey API.

## Maven modules

Maven projects, during development Maven 3.6.3 was used.

* __java-sdk-core__ the core code, can be used as a library (as a JAR dependency)

* __java-sdk-core-tester__ java-sdk-core unit tests, some of them are special cases (for simple, easy to understand examples please see java-sdk-sample-webshop)
* __java-sdk-sample-webshop__ a simple Spring Boot based example webshop, demonstrates basic java-sdk-core usage
* __java-sdk-command-line-client__ (old) a Java based command line client (can be used via command line from non-Java applications too), potential use cases are limited, consider it depricated (especially since SCA/3DS2)

java-sdk-core only has a few basic dependencies, it was made (intentionally) to be relatively bare-bones and simple, can be incorporated into any project easily.

## Java version

java-sdk-core requires minimum Java 7, however this was not extensively tested, because Java 7 is old and not well supported.
In practice Java 8 is the minimum and Java 11 or newer is recommended.

For java-sdk-sample-webshop prerequisites examine Spring Boot prerequisites/dependencies.

## Possible requests 

Every payment API operation has a "call" class. 

To successfully perform any request one needs to create the object (configure it) 
and then call its execute() method.

For details please see to the Javadoc documentation of the various AbstractApiCall subclasses in the com.evopayments.turnkey.apiclient package (in java-sdk-core).
Plus please study the most recent (up-to-date) API specification PDF (if you don't have it, please request it from our support).

## Typical Flow

### I. Access or create an ApplicationConfig object like this:

```java
ApplicationConfig config = ApplicationConfig.getInstanceBasedOnSysProp();
```

ApplicationConfig.getInstanceBasedOnSysProp() chooses from ProductionConfig.java (loaded from resources/application-production.properties) or TestConfig.java (resources/application-test.properties).

evopayments-turnkey-sdk-config system property influences the choice: -Devopayments-turnkey-sdk-config=test or -Devopayments-turnkey-sdk-config=production (the default is the test variant).

(NEW) With PropertiesObjectConfig.java an ApplicationConfig instance can be put together without relying on the fixed location property files.

### II. Create the AbstractApiCall subclass object:

```java
final Map<String, String> params = new HashMap<>();
inputParams.put("country", "FR");
inputParams.put("currency", "EUR");

AbstractApiCall callObject = new GetAvailablePaymentSolutionsCall(config, params);
```

The call parameters have to supplied via a Map (any map implementation is good, ie. HashMap) (the "params" parameter). 
For more information on the possible/needed parameters please check the the most recent (up-to-date) API specification PDF document.

The constructor will do a basic "pre" validation on the supplied parameters (without actual API requests, without HTTP traffic).
With ApplicationConfig.isPrevalidationEnabled() (if it is false) this can be turned off.

### III. Execute it:
```java
JSONObject result = callObject.execute();
```
For more information about the possible results (fields in the response body JSON object) please examine the most recent (up-to-date) API specification PDF document.

### IV. Watch for exceptions

Occasionally the SDK (java-sdk-core) will not be able to perform your request and it will throw an Exception. 
This could be due to misconfiguration or unexpected conditions like connectivity issues (to the API server). 

Exceptions are described in more detail in a later section of this document.

```java
try {
	AbstractApiCall call = new GetAvailablePaymentSolutionsCall(config, params);
} catch (TurnkeyValidationException e) {
	// issues with the supplied parameters (ie. missing amount, missing currency)... notify the user, exit the program, redirect to the error page etc.
} catch (TurnkeyGenericException e) {
	// any other error from java-sdk-core... notify the user, exit the program, redirect to the error page etc.
}
```

```java
try {
	JSONObject result = call.execute();
} catch (TurnkeyCommunicationException e) {
	// network error... basic API call issues... notify the user, exit the program, redirect to the error page etc.
} catch (TurnkeyGenericException e) {
	// any other error from java-sdk-core... notify the user, exit the program, redirect to the error page etc.
}
```

## Exceptions

java-sdk-core provides a few Exception classes.
All of these are subclasses of com.evopayments.turnkey.apiclient.exception.TurnkeyGenericException.

For details please see: com.evopayments.turnkey.apiclient.exception package (Javadoc comments etc.).

## Log

java-sdk-core uses SLF4J (http://www.slf4j.org/), thus it can utilize the logger library (for example: log4j, logback) used in the project (where java-sdk-core was included as a dependency).

Please adjust logging for com.evopayments.turnkey package to enable/disable logging (to set granularity etc.). 

## java-sdk-command-line-client usage

java -Devopayments-turnkey-sdk-config=test -jar turnkey-java-sdk-cmd.jar -action TOKENIZE -merchantId 5000 -password 5678 -number 5454545454545454 -nameOnCard "John Doe" -expiryMonth 12 -expiryYear 2022  -merchantId=167885 -password=56789 > reponse.json


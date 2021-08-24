# EVO Payments - Turnkey API - JAVA SDK
Java integration (library, samples etc.) to the Turnkey API.

## Maven modules

Maven projects, during development Maven 3.6.3 was used.

* __java-sdk-core__ the core code, can be used as a library (as a JAR dependency)

* __java-sdk-core-tester__ java-sdk-core unit tests, some of them are special cases (for simple, easy to understand examples please see java-sdk-sample-webshop)
* __java-sdk-sample-webshop__ a simple Spring Boot based example webshop, demonstrates basic java-sdk-core usage
* __java-sdk-command-line-client__ (old) a Java based command line client (can be used via command line from non-Java applications too), potential use cases are limited, consider it depricated (especially since SCA/3DS2)

java-sdk-core only has a few basic dependencies, it was made (intentionally) to be relatively bare-bones and simple, it can be incorporated into any project easily.

## Java version

java-sdk-core requires minimum Java 7, however this was not extensively tested, because Java 7 is old and not well supported.
In practice Java 8 is the minimum and Java 11 or newer is recommended.

For java-sdk-sample-webshop prerequisites examine Spring Boot prerequisites/dependencies (currently it is Java 8).
java-sdk-sample-webshop is configured to use embedded Tomcat (Spring Boot's default), but possible to configure it to a traditional .war file deployment 
(same as any Spring Boot webapp).
java-sdk-sample-webshop uses Apache Derby for storage (for the example orders. prodcuts etc.).

## Possible requests 

Every Turnkey API operation has a corresponding "call" class. 

To successfully perform any request one needs to create the object (configure it) 
and then call its execute() method.

For details please see to the Javadoc documentation of the various AbstractApiCall subclasses in the com.evopayments.turnkey.apiclient package (in java-sdk-core).
Plus please study the most recent (up-to-date) API specification PDF (if you don't have it, please request it from our support) (see docs folder, but please verify if the PDFs there are up-to-date).

## Typical Flow

### I. Access or create an ApplicationConfig object like this:

```java
ApplicationConfig config = ApplicationConfig.getInstanceBasedOnSysProp();
```

ApplicationConfig.getInstanceBasedOnSysProp() chooses from ProductionConfig.java (loaded from resources/turnkey-sdk-production.properties) or TestConfig.java (resources/turnkey-sdk-test.properties).

evopayments-turnkey-sdk-config system property influences the choice: -Devopayments-turnkey-sdk-config=test or -Devopayments-turnkey-sdk-config=production (the default is the test variant).

(Note: You can create TestConfig or ProductionConfig directly, see the getInstance methods.)
(New: Using PropertiesObjectConfig.java an ApplicationConfig instance can be put together without relying on the property files.)

URLs (API endpoint URLs and your webshop's URLs) are configured using this ApplicationConfig object.
"merchantId", "password", "country", "currency" are flexible, they can be supplied in the ApplicationConfig and also in the inputParams map (see next point below for inputParams usage).
The other parameters (which can change between transactions, such as amount, currency) always have to be supplied using the inputParams map.
Please read "Password encryption" chapter too.

### II. Create the AbstractApiCall subclass object:

```java
final Map<String, String> params = new HashMap<>();
inputParams.put("country", "FR");
inputParams.put("currency", "EUR");

AbstractApiCall callObject = new GetAvailablePaymentSolutionsCall(config, inputParams);
```

The call parameters have to be supplied via a Map (any map implementation is good, ie. HashMap) (the "inputParams" parameter). 
For more information on the possible/needed parameters please check the the most recent (up-to-date) API specification PDF document.

The constructor will do a basic "pre" validation on the supplied parameters (without actual API requests, without HTTP traffic).
With ApplicationConfig.isPrevalidationEnabled() (if it is false) this can be turned off.

### III. Execute it:
```java
JSONObject result = callObject.execute();
```
For more information about the possible results (fields in the response body JSON object) please examine the most recent (up-to-date) API specification PDF document.

To initiate a Cashier UI based (hosted by EVO Payment) UI you can use a special subset of the "call" classes.
Please see the subclasses of AbstractApvTokenCall (for example: PurchaseTokenCall).
These have special execute methods: executeAndBuildCashierHppUrl(), executeAndBuildCashierStandaloneUrl().
These do everything needed for a Cashier UI URL.
The returned URL string can be used as a redirection target (for the customer).
Iframe mode is slightly different, in iframe cases there is a Javascript libarary, the received token has to be used to initialize that.

For examples please see java-sdk-sample-webshop.

### IV. Watch for exceptions:

Occasionally the SDK (java-sdk-core) will not be able to perform your request and it will throw an Exception. 
This can happen due to misconfiguration or unexpected conditions like connectivity issues (to the API server). 

Exceptions are described in more detail in a later section of this document.

```java
try {
	AbstractApiCall call = new GetAvailablePaymentSolutionsCall(config, params);
} catch (TurnkeyValidationException e) {
	// issues with the supplied parameters (ie. missing amount)... notify the user, exit the program, redirect to an error page etc.
} catch (TurnkeyGenericException e) {
	// any other error from java-sdk-core... notify the user, exit the program, redirect to an error page etc.
}
```

```java
try {
	JSONObject result = call.execute();
} catch (TurnkeyCommunicationException e) {
	// network error... basic API call issues... notify the user, exit the program, redirect to an error page etc.
} catch (TurnkeyGenericException e) {
	// any other error from java-sdk-core... notify the user, exit the program, redirect to an error page etc.
}
```

## Exception classes

java-sdk-core provides a few Exception classes.
All of these are subclasses of com.evopayments.turnkey.apiclient.exception.TurnkeyGenericException.

For details please see: com.evopayments.turnkey.apiclient.exception package (Javadoc comments etc.).

## Log

java-sdk-core uses SLF4J (http://www.slf4j.org/), thus it can utilize the logger library (for example: log4j, logback) used in your project (where java-sdk-core was included as a dependency).

Please adjust logging for com.evopayments package to enable/disable logging (or to set granularity etc.). 

## java-sdk-command-line-client usage

java -Devopayments-turnkey-sdk-config=test -jar turnkey-java-sdk-cmd.jar -action TOKENIZE -number 5454545454545454 -nameOnCard "John Doe" -expiryMonth 12 -expiryYear 2022 -merchantId=1234 -password=1234 > reponse.json

(the merchantId and the password parameter can be supplied as a command line parameter, but also as ApplicationConfig (in the properties file, see java-sdk-command-line-client resources folder))
(please see the next chapter about the optional password encryption)

## Password encryption

Optionally it is possible to encrypt the password parameter. See com.evopayments.turnkey.util.crypto.TextEncryptionUtil class.
Ultimetly to use the API the SDK code needs the plain text password, so it has to be decrypted before API requests are made.
However the following optional technique makes it somewhat harder for an attacker to find out the plain text password.
Steps to use the encryption:

1. Create (generate etc.) an encryption password (this is not the API password!).
2. Set it as an environment variable on the dev/test and/or prod system where the SDK code will be used. The name has to be TURNKEY-JAVA-SDK-PROPPASS
3. Use com.evopayments.turnkey.util.crypto.TextEncryptionUtil.encryptBasedOnSystemPropPass(String) to encrypt your API password (just once, delete the method call after you have the encrypted  password)
4. Add the ENC- prefix to the encrypted password
5. You can use this encrypted form (looks like this: ENC-e0Kwzt2m4fxdA37ovsROpgdkaQ1BRsX/r0/RP9BSRCnByx8cwMq9NOFo5I7I6U+xzg==) in place of the API password (in the properties files and/or in the inputParam map). Note the SDK code will only function properly on systems, where the TURNKEY-JAVA-SDK-PROPPASS is set correctly!

For our examples/tests the TURNKEY-JAVA-SDK-PROPPASS has to be set to this value: test

## java-sdk-sample-webshop

The "Follow up actions (capture, refund etc.)" view requires username/password. 
This view demonstrates a small and simple custom backoffice view.
Use shopadmin/shopadmin.
For details see: com.evopayments.example.webshop.config.CustomWebSecurityConfigurerAdapter

Note that Turnkey has its own backoffice (Turnkey Backoffice UI), which is suitable for most merchants.
Custom UI for capture, void, refund etc. operations is rarely needed.

class: center, middle

# Testing in isolation

by Jakub Nabrdalik

---

## how long should your tests run

--

30 seconds = Maximum time, before a dev stops running them on every change to get feedback

--

3 minutes = Maximum time, when devs stop running them before each commit/push

--

We have 30 seconds all together for unit tests, and 3 minutes max for integration/acceptance

--

Is it possible to have 3k unit tests run < 30 sec?

--

If you don't touch the IO, it's not a problem

--

Is it possible to have 500 integration tests run < 3 min?

--

If you are smart with IO, it's possible

---

## integration vs isolation

In a perfect world, we would have only integration tests - the code would run just like in production

Many technologies (JEE) assumed exactly that. But this assumption is wrong, because of time constraints

Therefore we should run

- happy path tests as acceptance tests
- only things requiring integration, as integration tests (access to DB for example)
- everything else, as unit tests (all corner cases, business logic, etc)

---

## IoC

To be able to test in isolation (unit test), we most often use Inversion of Control (IoC) principle

Most popular of IoC techniques, is the Dependency Injection

A unit, that requires another unit to work, has that unit injected (for example passed in constructor)

Unit-testing that, requires us to just stub/mock the dependency

---

## stubbing

> Stubbing is the act of making collaborators respond to method calls in a certain way. When stubbing a method, you don’t care if and how many times the method is going to be called; you just want it to return some value, or perform some side effect, whenever it gets called.

Let's assume we have an object Trasnaction, that requires a collaborator TaxCaluclator, to do its calculations

We will call transaction.perform(), and transaction somewhere underneath will call calculator.calculateTax(..)

---

Two basic ways to create a stub

```Groovy
//in spock.lang.MockingApi
public Object Stub() {...}
public <T> T Stub(Class<T> type) {...}
```

And here is how you use it

```Groovy
given:
    TaxCalculator calculator = Stub() //here is the stub
    Transaction transaction = new Transaction(calculator) //we pass it in

when:
    transaction.perform(productName, price)

then:
    transaction.tax == BigDecimal.ONE
```

--

When Transaction calls calculator, what will calculator return?

--

Null or other "neutral" value. This way if it is not important for our test, what the calculator returns, we don't have to specify this.

---

How do we make a stub return something we want?


```Groovy
stub.method(params) >> whatItReturns
```

--

So in our case

```Groovy
given:
    TaxCalculator calculator = Stub() //here is the stub
    calculator.calculateTax(price) >> BigDecimal.ONE //it will return 1
    Transaction transaction = new Transaction(calculator) //we pass it in

when:
    transaction.perform(productName, price)

then:
    transaction.tax == BigDecimal.ONE
```

---

## stubbing subsequent calls

```Groovy
given:
    TaxCalculator calculator = Stub(TaxCalculator)
    Transaction transaction = new Transaction(calculator)
    calculator.calculateTax(price) >>> [BigDecimal.ONE,
                                        BigDecimal.TEN,
                                        {throw new RuntimeException()}]

when:
    transaction.perform(productName, price)

then:
    transaction.tax == BigDecimal.ONE

when:
    transaction.perform(productName, price)

then:
    transaction.tax == BigDecimal.TEN

when:
    transaction.perform(productName, price)

then:
    thrown(RuntimeException)
```

---

## stubs defined at creation

```Groovy
given:
    TaxCalculator calculator = Stub(TaxCalculator) {
        calculateTax(price) >> BigDecimal.ONE
    }
    Transaction transaction = new Transaction(calculator)

when:
    transaction.perform(productName, price)

then:
    transaction.tax == BigDecimal.ONE
```

---

## Mocking

> Mocking is the act of describing (mandatory) interactions between the object under specification and its collaborators. 

In other words, we use mocks instead of stubs, when we want to verify that an interaction happened

--

```Groovy
given:
  TaxCalculator calculator = Mock(TaxCalculator)
  Transaction transaction = new Transaction(calculator)

when:
  transaction.perform(productName, price)

then:
    1 * calculator.calculateTax(price) //this method was called once
```

---

## Verifying number of calls

```Groovy
1 * subscriber.receive("hello")      // exactly one call
0 * subscriber.receive("hello")      // zero calls
(1..3) * subscriber.receive("hello") // between one and three calls (inclusive)
(1.._) * subscriber.receive("hello") // at least one call
(_..3) * subscriber.receive("hello") // at most three calls
_ * subscriber.receive("hello")      // any number of calls, including zero
                                     // (rarely needed; see 'Strict Mocking')
```

---

## veryfying method parameters

```Groovy
given:
  TaxCalculator calculator = Mock(TaxCalculator)
  Transaction transaction = new Transaction(calculator)

when:
  transaction.perform(productName, price)

then:
  1 * calculator.calculateTax({it == 200}) //the parameter must == 200
```

---

## any parameter will do

```Groovy
given:
  TaxCalculator calculator = Mock(TaxCalculator)
  Transaction transaction = new Transaction(calculator)

when:
  transaction.perform(productName, price)

then:
  1 * calculator.calculateTax(_ as BigDecimal) // any BigDecimal
```

--

"_" in Spock means "anything"

---

## other parameter examples

```Groovy
1 * calculator.calculateTax(_)      //whatever
1 * calculator.calculateTax(!price) //not this price
1 * calculator.calculateTax(*_)     // any argument list
1 * calculator.calculateTax(!null)  // any non-null argument
1 * calculator.calculateTax({it.toString() == "200"}) 
```

---

## when order of calls matter

Let's say it is crucial that calculator was called only once, with very specific parameter

```Groovy
 given:
    Transaction transaction = new Transaction(calculator)

when:
    transaction.perform(productName, price)

then:
    1 * calculator.calculateTax(price) 

then:
    0 * calculator.calculateTax(_)
```

---

## mocking antipattern

Very special case

```Groovy
0 * _._
```

This means - no interaction with any mock

When would you use it?

--

Be extra careful when you use it. Most often it's a mistake (overspecification). If you do it, you will not be able to change any collaboration without breaking the test

Use it only when it's crucial from business perspective, that no interaction happened (very rare case)

---

## Mocking & Stubbing at once

```Groovy
given:
    TaxCalculator calculator = Mock()    
    Transaction transaction = new Transaction(calculator)

when:
    transaction.perform(productName, price)

then:
    1 * calculator.calculateTax(price) >> BigDecimal.ONE
    transaction.tax == BigDecimal.ONE
```

--

Warning - we cannot split the mocking (verification) and stubbing (defining return) into separate lines

---

## Spying

Spying is when you want to know/verify that some object called a collaborator, but you don't want to replace the collaborator with your mock. You want to send a spy to verify it happened on a real class.

--

So, assuming we have a XTaxCalculator class, you can spy on it this way

```Groovy
given:
    TaxCalculator calculator = Spy(XTaxCalculator, constructorArgs: [0.1])
    Transaction transaction = new Transaction(calculator)

when:
    transaction.perform(productName, price)

then:
    1 * calculator.calculateTax(price)
    transaction.tax == 20
```

--

> Think twice before using this feature. It might be better to change the design of the code under specification

Don't use it unless you have no ther choice

Pro tip - a need for a spy is very, very rare

---

## Mocking external services

What do we do, when our application calls another service (for example via HTTP).

--

We should not rely on external services in tests because

- network is very, very slow (slowest IO ever)
- network is unreliable (false positives)
- those services may be dead when we checkout our code again

--

To solve that problems we either
- stub our http clients to return preconfigured responses in integration/acceptance tests
- setup a local, small HTTP server on a different port that responds to our calls the way we want it

Stubbing http clients makes tests faster (no IO access), but mocking on HTTP level allows us to test the protocol client and proper serialization. Serialization can be tricky sometimes.

---

## wiremock

Wiremock is a small http server that can work as a standalone or in-memory.

So somewhere in our base acceptance test fixtures we have

```Java
WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration
    .wireMockConfig()
    .dynamicPort())

wireMockServer.start()

wireMockServer.stubFor(get(urlEqualTo("/some/thing"))
    .willReturn(aResponse()
        .withHeader("Content-Type", "text/plain")
        .withBody("Hello world!")));

//run your tests

wireMockServer.stop()
```

---

Wiremock has both stubbing and mocking (verification) capabilities

Also lots of configuration options

Tips

- set up the wire mock server once for all tests (performance)
- use wiremock in memory unless you can't (easier maintenance)
- run it on a dynamic port (random free) and point your config clients to the port. Do not relly on a specific port being free on a test machine (easier maintenance)

---

## attribute matching

```Java
stubFor(any(urlPathEqualTo("/everything"))
  .withHeader("Accept", containing("xml"))
  .withCookie("session", matching(".*12345.*"))
  .withQueryParam("search_term", equalTo("WireMock"))
  .withBasicAuth("jeff@example.com", "jeffteenjefftyjeff")
  .withRequestBody(equalToXml("<search-results />"))
  .withRequestBody(matchingXPath("//search-results"))
  .willReturn(aResponse()));
```

---

## standalone

You can also run wiremock as a standalone process. Usefull mainly for prototyping

```Java
$ java -jar wiremock-standalone-2.1.10.jar
```

You pass parameters in command line, and you can make it return content of files from a directory


---

### [part 6: organizing tests](part6.html)

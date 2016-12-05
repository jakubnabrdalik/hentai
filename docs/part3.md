class: center, middle

# Anatomy of a test

by Jakub Nabrdalik

---

## anatomy of a test

> What makes a clean test? Three things. Readability, readability, and readability.

> Robert C. Martin, Clean Code: A Handbook of Agile Software Craftsmanship

--

We will use Spock framework as a reference, becaus it implements TDD/BDD and it is very simple to use

Docs
- http://spockframework.github.io/spock/docs/1.1-rc-1/spock_primer.html

Playground
- http://meetspock.appspot.com/

---

## what is a test composed of

```Groovy
class PerformTransactionSpec extends Specification {
    BigDecimal price = new BigDecimal(200)
    String productName = "Sanity injectors"

    def "performing transaction should change the state to successful"() {
        given:
            Transaction transaction = new Transaction(productName, price)
        when:
            transaction.perform()
        then:
            transaction.status == TransactionStatus.SUCCESSFUL
    }
}
```

---

## Given

```Groovy
def "performing transaction should change the state to successful"() {
    given:
        Transaction transaction = new Transaction(productName, price)
```

--

Given is the part where we make assumptions

--

Given is also known as setup

--

Given can be followed by explaination

```Groovy
given: "we have a new transaction"
```

--

In Given every line is just a line of code, but we should **only prepare assumptions IMPORTANT from the perspective of this particular test**

---

## Good given

```Groovy
def "customer can rent films"() {
    given: "there is a film in catalogue"
        Film film = persistedFilms[0]
``` 
--

```Groovy
def "renting a new release should give 2 points per rental"() {
    given: "there is a new release in catalogue"
      Film film = persistedFilms.find {it.filmType == FilmType.NEW_RELEASE}
```

--

If there is a lot of logic behind preparing assumptions, move it out

```Groovy
def "should throw exception when trying to get unpublished article"() {
    given: 
        Article unpublishedArticle = createUnpublishedArticle()
```

---

## Is this a good given?

```Groovy
class LocalDateConverterSpec extends Specification {
    LocalDateConverter converter = new LocalDateConverter()
    int year = 2015
    int month = 1
    int day = 2

    def "should convert to database column"() {
        given:
            Date now = java.sql.Date.valueOf("$year-$month-$day")
```

---

## Is this a good given?

```Groovy
def "should create outlet insert command with valid params with new account"() {
    given:
        def defaultParams = OutletFactory.getValidOutletRequestParams()
        defaultParams.remove('mobileMoneyAccountNumber')
        defaultParams.remove('accountType')
        defaultParams.put('merchant.id', merchant.id.toString())
        controller.request.addParameters(defaultParams)
```

---

## Given suggestions

Use it to show what are the important assumptions of the test

Hide all the gory details, unless gory details are important assumptions

---

## When

When is where the action happens. This is what we test.

```Groovy
def "performing transaction should change the state to successful"() {
    given:
        Transaction transaction = new Transaction(productName, price)
    when:
        transaction.perform()
```

--

```Groovy
def "system shows details of a film"() {
    given:
        Film film = persistedFilms[0]
    when:
        ResultActions resultActions = mockMvc.perform(get("/films/" + film.id))
```

--

```Groovy
def "customer can rent films"() {
    given: "there is a film in catalogue"
        Film film = persistedFilms[0]
    when: "renting a film"
        ResultActions resultActions = rentAFilm(film)
```

---

## When suggestions

When is usually just a single line

If it is not a single line, which one do you really test?

When always come with then

---

## Then

Then is where we verify our assumptions.

```Groovy
def "performing transaction should change the state to successful"() {
    given:
        Transaction transaction = new Transaction(productName, price)
    when:
        transaction.perform()
    then:
        transaction.status == TransactionStatus.SUCCESSFUL
```

---

## Then

Every single line of then is verified, whether it's TRUE or FALSE. 

Then blocks are restricted to 
- conditions
- exception conditions
- interactions
- variable definitions.

---

```Groovy
//condition
then:
    transaction.status == TransactionStatus.SUCCESSFUL

//exception condition
then:
    thrown(IllegalArgumentException)

//interaction
then: "the film is returned"
    rentHasReturnedOn(resultActions, film, notNullValue())

//variable definition & condition
then: "split frames are saved"
    Article savedArticle = articleRepository.findOne(TEST_ARTICLE_ID)
    toJsonString(savedArticle.getFrames()) == toJsonString(FRAMES_JSON)
```

---

## Then examples

```Groovy
def "system shows details of a film"() {
    given:
        Film film = persistedFilms[0]
    when:
        ResultActions resultActions = mockMvc.perform(get("/films/" + film.id))
    then: 
        resultActions.andExpect(status().isOk())
        hasDetailsOf(resultActions, film)
```

--

```Groovy
def "customer can rent films"() {
    given: "there is a film in catalogue"
        Film film = persistedFilms[0]
    when: "renting a film"
        ResultActions resultActions = rentAFilm(film)
    then: "we get HTTP status 200 + rentId"
        resultActions.
            andExpect(status().isOk()).
            andExpect(jsonPath('rentId').value(any(Integer)));
```

--

A feature method may contain multiple pairs of when-then blocks

---

```Groovy
def "customer can rent films"() {
    given: "there is a film in catalogue"
        Film film = persistedFilms[0]
    when: "renting a film"
        ResultActions resultActions = rentAFilm(film)
    then: "we get HTTP status 200 + rentId"
        resultActions.
                andExpect(status().isOk()).
                andExpect(jsonPath('rentId').value(any(Integer)));

    when: "we search for the rent"
        long rentId = getRentId(resultActions)
        resultActions = mockMvc.perform(get("/rents/$rentId"))
    then: "it's the rent for the film we wanted"
        rentHasReturnedOn(resultActions, film, nullValue())

    when: "we return the film"
        mockMvc.perform(post('/returns').
            accept(MediaType.APPLICATION_JSON).
            param("rentId", rentId.toString())).
            andExpect(status().isOk()).
            andExpect(jsonPath('surcharge').value(any(Integer)));
    and: "we search for the rent again"
        resultActions = mockMvc.perform(get("/rents/$rentId"))
    then: "the film is returned"
        rentHasReturnedOn(resultActions, film, notNullValue())
}
```

---

## Bad then

```Groovy
when:
    controller.save()
then:
    1 * securityServiceMock.getCurrentlyLoggedUser() >> user
    1 * commandNotificationServiceMock.notifyAcceptors(_)
    Outlet.count() == 0
    MakerCheckerCommand.count() == 1
    MakerCheckerCommand savedCommand = MakerCheckerCommand.getAll().get(0)
    savedCommand.getCommandType() == CommandType.INSERT
    // mobileMoneyAccountNumber is missing in parameters
    savedCommand.getChange('mobileMoneyAccountNumber') == null
    controller.flash.message != null
    response.redirectedUrl == '/outlet/list'
```

Why is it bad?

---

## Then suggestions

Have only one business reason to fail

--

One business reason to fail sometimes means a few technical resons (the answer contains the required data AND the status code is 200)

--

If data verification takes a lot of lines, use extract method pattern

--

If you cannot use extract method pattern (give it a good name), then you probably should have another test for that

---

## Expect

Sometimes when (action) and then (verification) can be combined to one line in a natural way

This

```Groovy
when:
    def x = Math.max(1, 2)

then:
    x == 2
```

Could be presented as

```Groovy
expect:
    Math.max(1, 2) == 2
```

---

## Assertions

Sometimes single business assertion (verification) means several lines of code.

Other times it is reused.

```Groovy
def "offered PC matches preferred configuration"() {
  when:
      Pc pc = shop.buyPc()
  then:
      pc.vendor == "Sunny"
      pc.clockRate >= 2333
      pc.ram >= 4096
      pc.os == "Linux"
}
```

What can we do about it?

---

When we want to get rid of gory details

```Groovy
def "offered PC matches preferred configuration"() {
  when:
      Pc pc = shop.buyPc()
  then:
      pc.vendor == "Sunny"
      pc.clockRate >= 2333
      pc.ram >= 4096
      pc.os == "Linux"
}
```

--

We could return boolean

```Groovy
def "offered PC matches preferred configuration"() {
  when:
    PC pc = shop.buyPc()
  then:
    matchesPreferredConfiguration(pc)
}

private boolean matchesPreferredConfiguration(pc) {
  pc.vendor == "Sunny"
      && pc.clockRate >= 2333
      && pc.ram >= 4096
      && pc.os == "Linux"
}
```

---

So we return a boolean

```Groovy
private boolean matchesPreferredConfiguration(pc) {
  pc.vendor == "Sunny"
  && pc.clockRate >= 2333
  && pc.ram >= 4096
  && pc.os == "Linux"
}
```

The problem is, it wouldn't help us much in finding out what is wrong

```Groovy
Condition not satisfied:

matchesPreferredConfiguration(pc)
|                             |
false                         ...
```

---

But if we use key word "assert"

```Groovy
private void matchesPreferredConfiguration(pc) {
  assert pc.vendor == "Sunny"
  assert pc.clockRate >= 2333
  assert pc.ram >= 4096
  assert pc.os == "Linux"
}
```

--

The outcomes will be readable

```Groovy
Condition not satisfied:

assert pc.clockRate >= 2333
       |  |         |
       |  1666      false
       ...
```

"assert" verifies if the line is true or false, and prints nice results

---

## assertion suggestions 

When to write your own assertions

- when a single BUSINESS assertion is several lines of code long
- when an assertion is reused by different tests
- when it helps to express the intent (readability)
- when it makes the test easier to comprehend

---

## Exercise

How would you refactor this test

```Groovy
def "should create outlet insert command with valid params with new account"() {
    given:
        def defaultParams = OutletFactory.getValidOutletRequestParams()
        defaultParams.remove('mobileMoneyAccountNumber')
        defaultParams.remove('accountType')
        defaultParams.put('merchant.id', merchant.id.toString())
        controller.request.addParameters(defaultParams)
    when:
        controller.save()
    then:
        1 * securityServiceMock.getCurrentlyLoggedUser() >> user
        1 * commandNotificationServiceMock.notifyAcceptors(_)
        Outlet.count() == 0
        MakerCheckerCommand.count() == 1
        MakerCheckerCommand savedCommand = MakerCheckerCommand.getAll().get(0)
        savedCommand.getCommandType() == CommandType.INSERT
        // mobileMoneyAccountNumber is missing in parameters
        savedCommand.getChange('mobileMoneyAccountNumber') == null
        controller.flash.message != null
        response.redirectedUrl == '/outlet/list'
}
```

---

Start with finding out separate business tests in then

```Groovy
    then:
        //user is logged in
        1 * securityServiceMock.getCurrentlyLoggedUser() >> user
        
        //acceptor was notified
        1 * commandNotificationServiceMock.notifyAcceptors(_)
        
        //outlet was not created, but a command was
        Outlet.count() == 0
        MakerCheckerCommand.count() == 1
        
        //the type of the command is INSERT
        MakerCheckerCommand savedCommand = MakerCheckerCommand.getAll().get(0)
        savedCommand.getCommandType() == CommandType.INSERT
    
        //this is a new account (no number yet)
        // mobileMoneyAccountNumber is missing in parameters
        savedCommand.getChange('mobileMoneyAccountNumber') == null
    
        //we have something in a flash message
        controller.flash.message != null
    
        //we redirect to list
        response.redirectedUrl == '/outlet/list'
```

---

Now build a commmon ground

```Groovy
class SuccesfullOutletCreationSpec extends ControllerSpecification {
  def setup() {
    ...
  }
}
```

---

Have a single business reason to fail

```Groovy
def "should notify acceptor"() {
    when:
        controller.save()
    then:
        1 * commandNotificationServiceMock.notifyAcceptors(_)
}

def "should create command but not outlet"() {
    when:
        controller.save()
    then:
        Outlet.count() == old(Outlet.count())
        MakerCheckerCommand.count() == old(MakerCheckerCommand.count()) + 1
}

def "created command type is INSERT"() {
    when:
        controller.save()
    then:
        getSavedCommand().getCommandType() == CommandType.INSERT
}

def "created command doesn't have account number"() {
    when:
        controller.save()
    then:
        getSavedCommand().getChange('mobileMoneyAccountNumber') == null
}
```

---

Remove parts that test the algorithm.

We should only test the behaviour

```Groovy
 then:
        //user is logged in
        1 * securityServiceMock.getCurrentlyLoggedUser() >> user
```

This is just a prerequisite (not even an assumption)

```Groovy
class OutletCreationSpec extends ControllerSpecification {

  SecurityService securityServiceStub = Stub(SecurityService)

  def setup() {
      userIsLoggedIn()
      ...
  }

  private void userIsLoggedIn() {
      securityServiceStub.getCurrentlyLoggedUser() >> UserFactory.createAndSaveUser()
  }
```

---

Remove the parts that are not relevant to the business value

If those parts fail, the software still works well, and the business can perform its operations

```Groovy    
//we have something in a flash message
controller.flash.message != null

//we redirect to list
response.redirectedUrl == '/outlet/list'
```

Does it matter? Do I care? Is it the proper place to test it.

---

## assertion corner cases

In then clause (or custom assertion) should you verify against a string or a constant?

```Groovy
response.redirectedUrl == '/outlet/list'
```

or

```Groovy
response.redirectedUrl == OUTLET_REDIRECT_URL
```

--

Depends. I use constants, where the string is not part part of an external contract, because it's easier to refactor

Szczepan Faber (author of Mockito) prefers pure strings, because you cannot hide anything underneath

---

## parametrized tests

The data that enters our test is very important

The business likes to give examples as excell tables, or send them via email like that

```
    filmType    | numberOfDays | price
    NEW_RELEASE | 1            | 40
    REGULAR     | 5            | 90
    REGULAR     | 2            | 30
    OLD         | 7            | 90
```

Corner cases can be explored and defined using different sets of data

That's what we use parametrized tests for, and Spock is quite good at it

---

## where (parametrized test)

```Groovy
def "maximum of two numbers"() {
    expect:
        Math.max(a, b) == max

    where:
        a | b || max
        1 | 3 || 3
        7 | 4 || 7
        0 | 0 || 0
}
```

We often split variables that are assumptions, from expected with "||"

Notice that in this test variables are first used, then defined

This is thanks to Abstraxt Syntaxt Tree transformations (during compilation)

But the problem is we get only one error/success message, and we don't know which case failed. We only get

```
maximum of two numbers 
```

---

To get nice results, ad @Unroll to test class or method

```Groovy
@Unroll
def "maximum of two numbers with default unrolling"() {
    expect:
        Math.max(a, b) == max

    where:
        a | b || max
        1 | 3 || 3
        7 | 4 || 7
        0 | 0 || 0
}
```

We get

```
maximum of two numbers with default unrolling[0]
maximum of two numbers with default unrolling[1]
maximum of two numbers with default unrolling[2]
```

---

To get even better results, use variables in method name

```Groovy
@Unroll
def "max from #a and #b should be #max"() {
    expect:
        Math.max(a, b) == max

    where:
        a | b || max
        1 | 3 || 3
        7 | 4 || 7
        0 | 0 || 0
}
```

We get

```
max from 1 and 3 should be 3
max from 7 and 4 should be 7
max from 0 and 0 should be 0
```

---

You can also feed one variable from a list, file or a database

```Groovy
def "should throw exception on string #priceAsString"() {
    when:
        mapper.writeValueAsString(new Price(priceAsString))

    then:
        thrown(Price.BadPriceFormattingException)

    where:
        priceAsString << ["PLN10090", '$100', "Nomoney", "", "100", "1 000 000 PLN"]
}
```

---

A real case sccenario

```Groovy
def "string #priceAsString should create #priceAsJson"() {
  expect:
      mapper.writeValueAsString(new Price(priceAsString)) == priceAsJson

  where:
      priceAsString || priceAsJson
      "100 PLN"     || """{"amount":"100","currency":"PLN"}"""
      "100PLN"      || """{"amount":"100","currency":"PLN"}"""
      "100.00PLN"   || """{"amount":"100.00","currency":"PLN"}"""
      "100,00 PLN"  || """{"amount":"100,00","currency":"PLN"}"""
}
```

---

## Where gotchas

Two things to note:

- you cannot mix data with thrown exceptions in where ...unless you use closures

- in where clouse you cannot access instance fields... unless they are static or marked with @Shared

---

### [part 4: setup & cleanup](part4.html)

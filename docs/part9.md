class: center, middle

# Design by contract

## Defensive & Offensive Programming

---

## Design by contract

Contract must be set, we trust the contract

--

Preconditions — what must be true when a method is invoked

--

Postconditions — what must be true after a method completes successfully

--

Class invariants — what must be true about each instance of a class

---

## Defensive programming

We will check the contract, and compensate for errors

```Java
public Bio(
           String name,
           String photo,
           String description,
           List<String> tags) {
    this.name = isNull(name) ? "" : name;
    this.photo = isNull(name) ? "" : photo;
    this.description = isNullOrEmpty(description) ? "To be added..." : description;
    this.tags = isNull(tags) ? emptyList() : unmodifiableList(tags);
}
```

---

## Offensive programming

It's better to fail fast

```Java
public Bio(
        String name,
        String photo,
        String description,
        List<String> tags) {
    if(isNull(name) || isNull(photo) || isNull(description) || isNull(tags)) {
        throw new IllegalArgumentException();
    }
    ...
```

---

## Offensive programming with Guava

```Java
public Bio(
        String name,
        String photo,
        String description,
        List<String> tags) {
    this.name = checkNotNull(name);
    this.photo = checkNotNull(photo);
    this.description = checkNotNull(description);
    this.tags = checkNotNull(tags);
}
```

---

## Offensive programming with Java7

```Java
static import java.util.Objects.requireNonNull;

public Bio(
        String name,
        String photo,
        String description,
        List<String> tags) {
    this.name = requireNonNull(name);
    this.photo = requireNonNull(photo);
    this.description = requireNonNull(description);
    this.tags = requireNonNull(tags);
}
```

---

## Defensivie programming

- validate your input and compensate
- validate your output and compensate

---

## Offensive programming

- validate your input and throw if no good
- validate your output and throw if no good
- validate state and throw if no good

State - //this should never happen :)

---

## Contract vs inheritance

Weaken pre, strengthen post-conditions

> Subclasses in an inheritance hierarchy are allowed to weaken preconditions (but not strengthen them) and strengthen postconditions and invariants (but not weaken them).

---

## Validate state

--

- if we controll the input
- if our state is hidden
- then does validating internal state have any sense?

---

## assert keyword

--

Oracle documentation:

> An assertion is a statement in the JavaTM programming language that enables you to test your assumptions about your program. For example, if you write a method that calculates the speed of a particle, you might assert that the calculated speed is less than the speed of light.

--

> Each assertion contains a boolean expression that you believe will be true when the assertion executes. If it is not true, the system will throw an error. By verifying that the boolean expression is indeed true, the assertion confirms your assumptions about the behavior of your program, increasing your confidence that the program is free of errors.

--

Throws AssertionError

---

## Is this good in Java then?


```Java
EventService(EventRepository eventRepository, TokenHandler tokenHandler) {
    assert tokenHandler != null;
    assert tokenHandler != null;
    this.eventRepository = eventRepository;
    this.tokenHandler = tokenHandler;
}
```

---

## Assert in Java

Oracle documentation:

> Enabling and Disabling Assertions

> By default, assertions are disabled at runtime. Two command-line switches allow you to selectively enable or disable assertions.

---

## Assert keyword in Groovy

Assert in Groovy

```Groovy
EventService(EventRepository eventRepository, TokenHandler tokenHandler) {
    assert tokenHandler != null
    assert tokenHandler != null
    this.eventRepository = eventRepository
    this.tokenHandler = tokenHandler
}
```

---

## Assert keyword in Groovy

> Unlike Java with which Groovy shares the assert keyword, the latter in Groovy behaves very differently. First of all, an assertion in Groovy is always executed, independently of the -ea flag of the JVM

---

## Power assert in Groovy

```Groovy
def x = 2
def y = 7
def z = 5
def calc = { a,b -> a*b+1 }
assert calc(x,y) == [x,z].sum()
```

```Groovy
assert calc(x,y) == [x,z].sum()
       |    | |  |   | |  |
       15   2 7  |   2 5  7
                 false
```

---

# DSL

> A domain-specific language (DSL) is commonly described as a computer language targeted at a particular kind of problem and it is not planned to solve problems outside of its domain.

> An internal DSL is created with the main language of an application without requiring the creation (and maintenance) of custom compilers and interpreters.

List of patterns for DSLs: http://martinfowler.com/dslCatalog/

---

## Fluent API

> implementation of an object oriented API that aims to provide more readable code

> normally implemented by using method chaining to relay the instruction context of a subsequent call

---

## Fluent API example 1

```Java
Author author = AUTHOR.as("author");
create.selectFrom(author)
      .where(exists(selectOne()
                   .from(BOOK)
                   .where(BOOK.STATUS.eq(BOOK_STATUS.SOLD_OUT))
                   .and(BOOK.AUTHOR_ID.eq(author.ID))));
```

---

## Fluent API example 2

```Java
Vacation vacation = vacation().starting("10/09/2007")
                               .ending("10/17/2007")
                               .city("Paris")
                               .hotel("Hilton")
                               .airline("United")
                               .flight("UA-6886"); 
```

---

## Fluent API example 3

```Java
return osOfferItems.stream()
        .filter(item -> variantToOffers.getOfferIds().contains(item.getId()))
        .map(osOfferItem -> createOfferWithSeller(sellerIdToSeller, osOfferItem))
        .sorted(offerSorter::compareOffers)
        .collect(toList());
```

---

## Rules of API

--

1. be fluent, be natural

--

2. one parameter per method

--

3. return yourself except for finishing methods

--

4. you can give more options by using interfaces

---

## Task: DSL

You are writing a lib for working with excel files via Apache POI.

Create a DSL, that will allow the user to:

- create an xlsx
- create a worksheet
- fill a row with properties from an object
- fill rows with a collection of objects and add header on top of first row
- set cell value
- get cell value
- save xlsx to file
- save xlsx to output stream
- save xlsx to http response (including proper headers)

Make sure that you only allow the user to do things that make sense.

---

## Driving state changes

Empty interface is not a bad interface :)

---

# Exceptions

Runtime vs Checked

--

Hiding exceptions

--

Java pre 8: tryDoSomething(params)

--

Java 8: try with emphasis on positive logic

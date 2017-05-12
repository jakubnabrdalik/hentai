# Example of Hexagonal architecture with high cohesion modularization, CQRS and fast BDD tests in Java

This repo is an example of Hexagonal architecture with sensible modularization on a package level, that provides high cohesion, low coupling, and allows for Behaviour Driven Development that has

- allows for most tests to be run in milliseconds (unit tests without IO)
- while at the same time not falling into the trap of testing INTERNALS of a module (no test-per-class mistake)
- tests that focus on behaviour of each module (refactoring does not require changing test)
- just enough intergration/acceptance tests with focus on performance (minimum waiting for tests to pass)
- tests that describe requirements (living documentation)
- modules that have high cohesion (everything hidden except for APIs) and low coupling (modules connected via their APIs
- easy to explain, understand and follow

This example follows the type of code I write at work on a daily basis. So while this is an artificial example, all the rules and architecture approach are the effect of what works for my teams in real life projects.

I use this project to teach Behaviour Driven Development, Domain Driven Design, Command Query Responsibility Segregation and to show Spring live-coding.

Pull requests are welcome.

---

# The problem

Each project starst with a problem, from which we get a set of requirements. Here I'm using a task I once received as a homework from a company, that wanted to asses new candidates.

## Project – Video rental store

For a video rental store we want to create a system for managing the rental administration.
We want three primary functions.
- Have an inventory of films
- Calculate the price for rentals
- Keep track of the customers “bonus” points

## Price
The price of rentals is based type of film rented and how many days the film is rented for.
The customers say when renting for how many days they want to rent for and pay up front. If
the film is returned late, then rent for the extra days is charged when returning.

## Film types
The store has three types of films.
- New releases – Price is <premium price> times number of days rented.
- Regular films – Price is <basic price> for the fist 3 days and then <basic price> times the number of days over 3.
- Old film - Price is <basic price> for the fist 5 days and then <basic price> times the number of days over 5

<premium price> is 40 SEK
<basic price> is 30 SEK

The program should expose a rest-ish HTTP API.
The API should (at least) expose operations for

- Renting one or several films and calculating the price.
- Returning films and calculating possible surcharges.

## Examples of price calculations

Matrix 11 (New release) 1 days 40 SEK
Spider Man (Regular rental) 5 days 90 SEK
Spider Man 2 (Regular rental) 2 days 30 SEK
Out of Africa (Old film) 7 days 90 SEK
Total price: 250 SEK

When returning films late
Matrix 11 (New release) 2 extra days 80 SEK
Spider Man (Regular rental) 1 days 30 SEK
Total late charge: 110 SEK

## Bonus points
Customers get bonus points when renting films. A new release gives 2 points and other films
give one point per rental (regardless of the time rented).

---

# Acceptance specifications

After gathering a problem description in a natural language, the next step is to crete Specifications for our project. That is, to split our requirements into a set of scenarios that describe the behaviour of a system. 

Years ago this used to be done using Use Cases. Later on, the industry simplified this  to user stories, and now we follow the best practices of BDD. For this very simple project, we can create one main happy path specification. If this specification is implemented, our project brings money. 

## Happy path scenario:

As a hipster-deviant, to satisfy my weird desires, I want to:

given inventory has an old film "American Clingon Bondage" and a new release of "50 shades of Trumpet"

when I go to /films
then I see both films

when I go to /points
then I see I have no points

when I post to /calculate with both films for 3 days
then I can see it will cost me 120 SEK for Trumpet and 90 SEK for Clingon

when I post to /rent with both firms for 3 days
then I have rented both movies

when I go to /rent
then I see both movies are rented

when I go to /points
then I see I have 3 points

when I post to /return with Trumper
then trumper is returned

when I go to /rent
then I see only Clingon is rented

---

# Modules

Now, let's do just enough design up front. Let's split the application into modules.

This is the list of our modules with their responsibilities 

films
- list
- show
- add

rentals
- rent
- calculatePrice
- return
- list

points
- list
- addForRent

user
- getLoggedUser

We verify that our module design is solid by checking the number of communications between modules. High cohesion / low coupling means, that modules do not talk to often with each other, and that our API stays small.

---

# Implementation

We are ready to actually implement something using BDD. We shall start with implementing the acceptance spec (the only integration test so far), and then the film module. Watch git history for more details about each step.

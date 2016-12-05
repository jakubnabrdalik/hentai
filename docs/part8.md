class: center, middle

# TDD & BDD

---

### Kent Beck - Test Driven Development by Example 2003


Rules

- Write new code only if you first have a failing automated test

- Eliminate duplication


--

Effects

--

- You must design organically, with running code providing feedback between decisions

--

- You must write your own tests, since you can’t wait twenty times a day for someone else to write a test

--

- Your development environment must provide rapid response to small changes

--

- Your designs must consist of many highly cohesive, loosely coupled components, just to make testing easy

---

## Reg-Gree-Refactor mantra

### Red 

write a little test that doesn’t work, perhaps doesn’t even compile at first

### Green

make the test work quickly, committing whatever sins necessary in the process

### Refactor

eliminate all the duplication created in just getting the test to work

---

## Effects on QA


- If the defect density can be reduced enough, QA can shift from reactive to pro-active work

--

- If the number of nasty surprises can be reduced enough, project managers can estimate accurately enough to involve real customers in daily development

--

- If the topics of technical conversations can be made clear enough, programmers can work in minute-by-minute collaboration instead of daily or weekly collaboration

--

- Again, if the defect density can be reduced enough, we can have shippable software with new functionality every day, leading to new business relationships with customers

---

## How effective is TDD?

[Krzysztof Jelski presentation from Confitura 2016](https://www.youtube.com/watch?v=HB05IDI-xYk)

[slides](https://submissions-production.s3.amazonaws.com/attachments/attachments/000/002/310/original/TDD_effectiveness_Agile_Europe_2016.pdf?AWSAccessKeyId=AKIAIKK35CR3V3P7N25A&Expires=1472405855&Signature=%2BAGr1zKgUGVmcmXvyrC%2F6GMvBng%3D)

Baseline

- total cost is lower
- programming speed is equal or a bit slower
- defect rate is much lower
- quality improves

--

Question - Top down or bottom up?

TDD requires top down approach

--

Question - Design up front?

TDD pushes evolutionary design

---

## BDD

> BDD is a second-generation, outside-in, pull-based, multiple-stakeholder, multiple-scale, high-automation, agile methodology. It describes a cycle of interactions with well-defined outputs, resulting in the delivery of working, tested software that matters.

> Dan North, 2009

---

## BDD again

> Behavior-driven development combines the general techniques and principles of TDD with ideas from domain-driven design and object-oriented analysis and design

--

> Behavior-driven development is an extension of test-driven development

--

> BDD is largely facilitated through the use of a simple domain-specific language (DSL) using natural language constructs

---

## BDD basics

principles

- define a test set for the unit first;
- make the tests fail;
- then implement the unit;
- finally verify that the implementation of the unit makes the tests succeed.

--

given/when/then

- It starts by specifying the initial condition that is assumed to be true at the beginning of the scenario. This may consist of a single clause, or several.
- It then states which event triggers the start of the scenario.
- Finally, it states the expected outcome, in one or more clauses.

--

Nothing new, right?

---

## The truth

There is no such thing as TDD vs BDD

BDD is TDD done right

---

## The difference

Focus on behaviour, not the implementation!

--

Build and use ubiquitous language, shared between client, developers, QA, managers

--

> A ubiquitous language is a (semi-)formal language that is shared by all members of a software development team — both software developers and non-technical personnel

--

> the ubiquitous language allows business analysts to write down behavioral requirements in a way that will also be understood by developers

--

> In this way BDD becomes a vehicle for communication between all the different roles in a software project

--

Business support: design/read/explore

---

## QA and TDD/BDD


In agile software team, who is responsible for

- communication?

--

- requirements?

--

- tests?

--

- quality?

--

QA - specialized developer, that focuses more on requirements, communication, test code, than on production code

--

QA is part of the team

--

QA often has more knowledge of UX than developers

--

Question - How many QAs do I need per team (5-7 devs)?

--

Answer - doesn't matter. When QA is a developer as well, we look at capabilities, not roles/positions

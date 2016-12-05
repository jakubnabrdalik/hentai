class: center, middle

# Architecture and tests

by Jakub Nabrdalik

---

> A system that is comprehensively tested and passes all of its tests all of the time is a testable system. That’s an obvious statement, but an important one. Systems that aren’t testable aren’t verifiable. Arguably, a system that cannot be verified should never be deployed.

> Robert C. Martin, Clean Code: A Handbook of Agile Software Craftsmanship

---

> The belief that complex systems require armies of designers and programmers is wrong. 
A system that is not understood in its entirety, or at least to a significant degree of detail by a single individual, should probably not be built.

> Niklaus Wirth

---

## bad architecture 

--

Integration testing only (old JEE style)

Super slow unit testing (Grails 1/2)

Big ball of mud


---

## good architecture

When you are choosing a technology, verify that you can write and run unit/integration/acceptance tests very fast. Compary which technology gives faster feedback

Frontend technologies prefer unit testing without a browser (on node.js for example)

For backend technologies, the idea is that unit tests should run in miliseconds. Integration test should not rely on servers, and should run in seconds

If it is awkward to write tests, or tests are slow, drop the technology or achitecture

A popular design choice for business applications, is to make the whole domain (business logic) depend only on the basic technology (pure Java for example) and thus be able to have ultra-fast unit tests

We will look at a popular example of a testable architecture, based on ideas and language created by Eric Evans in his boook "Domain Driven Design"

---

## Domain Driven Design

Software design approach described by Eric Evans in his book "Domain-Driven Design: Tackling Complexity in the Heart of Software"

- placing the project's primary focus on the core domain and domain logic

- basing complex designs on a model of the domain

- initiating a creative collaboration between technical and domain experts to iteratively refine a conceptual model that addresses particular domain problems

We will talk about it later

---

## Monolith

With large applications, we hit a performance/feedback problem

- large codebase takes minutes to compile

--

- large codebase take minutes to pass all tests, even with good architecture

--

- the size of the codebase implies there is not a single developer that understands it anymore

--

- in fact you can understand just a few Bounded Contexts before your head explodes

--

- and large number of commits per day, doesn't help

--

- that too many people have to coordinate/communicate, thus slowing progress down

--

- finally, it's practically impossible to keep self-discipline, and thus quality in 100+ dev group--

---

## Microservices

> Inside every large program, there is a small program trying to get out.

> C.A.R. Hoare

--

> Microservices are a more concrete and modern interpretation of service-oriented architectures (SOA) used to build distributed software systems.

--

> allows the architecture of an individual service to emerge through continuous refactoring, hence reduces the need for a big up-front design and allows for releasing the software early and continuously.

---

## Microservices

- Distributed/versioned configuration

- Service registration and discovery

- Routing

- Service-to-service calls

- Load balancing

- Circuit Breakers

- Distributed messaging

- Distributed logging

- Deployment infrastucture (docker/mesos/cloud foundry)

- There are already frameworks to cover a lot of that (e.g.: Spring Cloud)

---

## Testing distributed systems

--

- end-2-end tests
- testing each component alone
- testing on production
- consumer driven contracts
- NASA style

---

## end-2-end tests

Test of a whole distributed system, on a dedicated environment

--

Advantages

- tells you if all components talk work together

--

Disadvantages

- very slow to setup and run
- you have to deploy all apps in specific version (same as on production) at once
- you have to coordinate production releases (once a week for example)
- you still do hardly ever test it on the same hardware/network setup, so it can still fail on production

---

## testing each component alone

--

Advantages

- fast feedback
- easy to evolve
- fast to go to production
- easy to setup

--

Disadvantages

- you don't know if it works with other services

--

This one you have to do either way

---

## testing on production

Focusing on logging, metrics, monitoring

--

Advantages

- you test your system on real hardware/network
- easy to setup

--

Disadvantages

- you will fail a lot, customers will not be happy

--

To mitigate customers not being happy you do

- continuous delivery
- contituous deployment
- A/B testing, blue-green deployment, 1%-5%-10%-50%-100% deployment
- automatic rollback (tricky to implement with data migration)
- feature toggles

---

## consumer driven contracts

Define a contract between microservices. Test that contract on server and clients. Changes require testing again

--

Advantages

- mitigates errors due to bad communication
- makes developers aware of backward compatible changes

--

Disadvantages

- tooling is still way from perfect
- maybe hard to convince all the other teams
- does not test hardware/networking/performance problems


---

## NASA style 

--

As described by Tom Gilb

For systems that are crucial, create those services two or three times, by different teams, preferably in different technologies

Run all 3 in parallel

Create a validation system, that compares outputs from 3 source systems, and decides which is right (2 of 3 are right, probability of correct answer, etc.)


---

### [part 8: TDD vs BDD vs QA](part8.html)

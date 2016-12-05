class: center, middle

# Domain Driven Design

# Concept

---

## Domain Driven Design

Software design approach described by Eric Evans in his book "Domain-Driven Design: Tackling Complexity in the Heart of Software"

- placing the project's primary focus on the core domain and domain logic

- focuses complex designs on a model of the domain

- initiating a creative collaboration between technical and domain experts to iteratively refine a conceptual model that addresses particular domain problems


---

## Domain model

> A domain model is not a particular diagram; it is the idea that the diagram is intended to convey. It is not just the knowledge in a domain expert’s head; it is a rigorously organized and selective abstraction of that knowledge. A diagram can represent and communicate a model, as can carefully written code, as can an English sentence.

--

Focus on domain, the business problem to solve, and deal with its complexity

Make architecture fit the domain by reflecting the domain in the code

One should be able to learn a lof about the domain from the code

> When software fits/reflects the domain, it reacts well to change over time

---

## Model is JUST a model

- We need to organize information

- Real life is too complex

- We decide what to leave out, what to take and from which perspective

- What to simplify, what to dig deeper

- Good model helps understand and collaborate

- Live is so complex we may need several models, from different perspectives

---

## Ubiquitous language

The language to communicate the model

Two levels of design:
- software design (architecture)
- code design (modelling)

Waterfall = one way communication

XP / Agile = two way communication

It used to be

- terminology of day-to-day discussions is disconnected from the terminology embedded in the code
- even in writing

Use the language based on the model, to communicate both in speach and in code

---

## Communication changes

> Any technical person contributing to the model must spend some time touching the code, whatever primary role he or she plays on the project. Anyone responsible for changing code must learn to express a model through the code. 

> Every developer must be involved in some level of discussion about the model and have contact with domain experts. 

> Those who contribute in different ways must consciously engage those who touch the code in a dynamic exchange of model ideas through the Ubiquitous Language.

> Design a portion of the software system to reflect the domain model in a very literal way, so that mapping is obvious. 

> Revisit the model and modify it to be implemented more naturally in software, even as you seek to make it reflect deeper insight into the domain.

---

## Bounded Context

Bounded Context vs single Canonical Model

> In those younger days we were advised to build a unified model of the entire business, but DDD recognizes that we've learned that "total unification of the domain model for a large system will not be feasible or cost-effective"

> So instead DDD divides up a large system into Bounded Contexts, each of which can have a unified model - essentially a way of structuring MultipleCanonicalModels.

> [http://martinfowler.com/bliki/BoundedContext.html]


---

## Stop modelling data

Problems with relational DB & Modelling

- single model way too big
- only one model vs different perspectives
- things discovered when we write code, will be lost
- not practical to implement in code
- OOP hates cycles, loves tree structures

Model behaviour, not data!

OOP is natural choice for most domains when using DDD, because it fits business concepts (and organizations) better than FP and PP. But be aware of when the domain fits better with FP (mathematics, phisics, etc.)

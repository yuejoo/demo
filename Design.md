- [Solutions](#solutions)
  * [Proposal 0. Process for all](#proposal-0-process-for-all)
    + [Diagram](#diagram)
    + [Estimates: (Small)](#estimates---small-)
      - [Pros:](#pros-)
      - [Cons:](#cons-)
  * [Proposal 1. Process in Streaming (Preferred)](#proposal-1-process-in-streaming--preferred-)
    + [Diagram](#diagram-1)
    + [Estimates: (Medium)](#estimates---medium-)
      - [Pros](#pros)
      - [Cons](#cons)
  * [Technologies](#technologies)
    + [Basic](#basic)
    + [CLI Libaray (TODO: Compare and decide)](#cli-libaray--todo--compare-and-decide-)
    + [Testing FrameWork](#testing-framework)
    + [DI Framework](#di-framework)
    + [Immutables Libaray](#immutables-libaray)
    + [Multithreading (TODO:)](#multithreading--todo--)
  * [Remaining questions](#remaining-questions)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>

## Solutions

### Proposal 0. Process for all
* 1. The CLI load the file in memory at one time with structured data model.
* 2. Porcess the in memory data model.
* 3. Translate the in meory data model to Output file.

#### Diagram

<p align="left">
  <img src="https://github.com/yuejoo/demo/blob/master/Solution-0.svg">
</p>
#### Estimates: (Small)
##### Pros:
* Straigt forward. Quick implementation.
##### Cons:
* Less efficiency, I/O is empty while processing the data.
* Memory Limited by the input file size. Even if the desired input file is feasible in memory.
* No scalibility.

### Proposal 1. Process in Streaming (Preferred)
Utilize the Producer-Consumer Model.
* 1. The CLI has to sort the input file if it's not sorted well.
* 2. The application load the file in streaming. Once one factory have the load completed, send this load complete factory to the process component.
* 3. Once the factory model processed complete, send it to the I/O queue waiting for writing in the outputfile.

#### Diagram
<p align="left">
  <img src="https://github.com/yuejoo/demo/blob/master/Solution-1.svg">
</p>

#### Estimates: (Medium)
##### Pros
* Efficiency while solving large scale of input files.
* Possible to scale on throttling the loading/processing rate which will free the memroy limitation.
##### Cons
* More complicate multi-threading process.

### Technologies
#### Basic
* Language: Java & Maven
#### CLI Libaray (TODO: Compare and decide)
https://github.com/remkop/picocli/wiki/CLI-Comparison
#### Testing FrameWork
* JUnit Testing
* in-built CLI Lib's Testing framework
#### DI Framework
Dagger or Guice.
#### Immutables Libaray
https://immutables.github.io/
#### Multithreading (TODO:)
Need some alternatives for using a multithreading work.


### Remaining questions
* How do we test the multithreading functions?
* How do we handle the automated build/complile framewrok.

## Solutions
The major discussion around is that it's to load the data one-time for all to memory.

### Proposal 0. (Process for all)
* 1. The CLI load the file in memory at one time with structured data model.
* 2. Porcess the in memory data model.
* 3. Translate the in meory data model to Output file.

#### Diagram

<p align="left">
  <img src="https://github.com/yuejoo/demo/blob/master/Solution-0.svg">
</p>

#### Pros:
* Straigt forward. Quick implementation.

#### Cons:
* Less efficiency, I/O is empty while processing the data.
* Memory Limited by the input file size. Even if the desired input file is feasible in memory.
* No scalibility.

### Proposal 1. (Process in Streaming)
* 1. The CLI has the option to sort the input file if it's not sorted well.
* 2. The application load the file in streaming. Once one factory have the load completed, send this load complete factory to the process component.
* 3. Once the factory model processed complete, send it to the I/O queue waiting for writing in the outputfile.

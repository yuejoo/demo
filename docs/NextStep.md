# Next Step
* Data validation.

For now the logic correctness is based on the correctness of the input file. If the input is wrong with beyond those assumption, the logic won't work.
A input data validator should be useful.

* Performance

Current the most time-consuming steps are loading and saving the csv files in the memory when dealing with the large scal files.
And applying the produce-consumer pattern (the Solution-1) could be an option to have a performance enhancement.

* Performance Tunning Options

JVM options tunning.

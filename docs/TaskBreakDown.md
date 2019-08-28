## Tasks BreakDown 
### Total Estimates (5 days (3-5 hours per day))
### 1. Setting up the basic evironment. (3~4 hours)
* Necessary libraies installed.
* Testing Framework setting up.
* Basic CLI main function created. 

### 2. Pre-process Components. (3-4 hours)
The pre-process is single thread process. The function should be staight forward.
* Design/Implement necessray models. (Easy by using the immutables lib)
* Design/Implement functional classes. (CSV Parsing, I/O ~~Streaming loading~~, Internal model factory)
* Unit tests.

### 3. Post-process Components. (3-4 hours)
Same as the pre-process components. The post-process is single thread process. This function should be staight forward.
* Design/Implement necessray models. (Easy by using the immutables lib)
* Design functional classes.  (CSV creator, I/O Streaming writing)
* Unit tests.

### 4. Main-process Components. (3-4 hours)
The Main Process components need thread pool to schedule multiple threading processes.
* Design functional classes.  (Multi-Threading functions, Business Logic Function(Factory-Scheduler))
* Unit tests.

### 5. Assembling. (3-4 hours)
Assembling the components into a functionl task.
* Components Assembling
* Unit Testing/Functional testing
* Bug fix.

### 6. Exception Handling (3~4 hours)
* Exception Handling check/implemented with correct error information populated.

### 7. Integration Testing (2 hours)
* Integration Testing(Function/Performance) with generating some sample input.
* Bug Fix (TBD)

### 8. Good To Have (2 hours)
* CLI Interface/UI Features (Process status showing, -help information..etc.)

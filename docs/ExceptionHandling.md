## Type of Exceptions we should handle
### 1. Parsing/Writing CSV Files
* I/O Exception
* Out of memeory 
* Object Mapping Failure (Miss typing, wrong format, miss header etc.) (IO/Exception too.)

### 2. Planning (Not required, but good to have.)
* Data Validation 
* Current Logic is based on the assumptions:
1. Days are the raw number
2. There is no missing days .
3. Input demands are accumulated.

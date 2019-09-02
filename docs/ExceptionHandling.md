## Type of Exceptions we should handle
### 1. Parsing/Writing CSV Files
* I/O Exception
* Out of memeory 
* Object Mapping Failure (Miss typing, wrong format, miss header etc.) (IO/Exception too.)

### 2. Planning (Not required, but good to have.) (Not implemented yet.)
* Data Validation  
* Current Logic is based on the assumptions:
1. Days are the raw number (**Fixed by** https://github.com/yuejoo/demo/commit/67588f0265ceda8993ebdff187a6a066b439f513)
2. Input demands are accumulated.

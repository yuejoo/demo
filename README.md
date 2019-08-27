# Production Linearity Problem
# Context
## Description
You are a manufacturing plant manager. Your customer provides forecasts at a few key dates in a cumulative way (meaning that the demand at a later date includes the quantities before that date), and you need to plan your production accordingly.
However, in order to utilise your production line that is available everyday, you can’t wait until the day of new demands to manufacture the incremental quantity of products. Hence, you will need to “linearise” the quantity across a few pre-defined number of days (“pre-build days”), or the days between two demand key dates, whichever is smaller, for your factories to better plan your production schedule.
## Input Data
You will read two parameters from the command line,
1) the maximum pre-build days that factories are allowed to manufacture the product;
2) an input CSV file that contains the factory name, product name, production days, and demand
quantities at key dates.
## Output Data
You will export your result to a new CSV file,
1) with “pre-build days” number in the output file name;
2) add an extra ‘produce’ column to the input table to show your results.
3) Fill up the blank “demand” in between the days.
Please refer to the file ‘production_linearity.3.csv’ and ‘production_linearity.6.csv’ for examples.


## Requirements
* 1. A functional **CLI tool** which having two arguments "Pre-Build Days" and "Input CSV file."
* 2. Testing (Unit Testing& Integration Testing)
* 3. Handle the exception in a meaningful information.

## Good To Have
* Other CLI information  (Showing current Processing state, CLI help information etc)
* Performance tuning options. (Processing Speed, memory usage, etc...)

## Assumption
* 1. The single factory has multiple prodcution lines for each product. (multiple demands for different products)
* 2. The factory can only produce product during the prebuild days or the days between two demand key dates (for each products). There is no chance to make the product line producing for multiple demand orders for a single product. 
* 3. Assume input file could have 100,000 lines. 100 x 100 x 100 for site x product x days.



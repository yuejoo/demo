- [Production Linearity Problem](#production-linearity-problem)
- [Context](#context)
  * [Description](#description)
  * [Input Data](#input-data)
  * [Output Data](#output-data)
  * [Requirements](#requirements)
  * [Good To Have](#good-to-have)
  * [Assumption](#assumption)
  * [Docs QuickLink](#docs-quicklink)
    + [1. Design](#1-design)
    + [2. Models](#2-models)
    + [3. PlanningLogic](#3-planninglogic)
    + [4. Task Break Down](#4-task-break-down)
    + [5.CommandLineTool Usage](#5commandlinetool-usage)
    + [6.Exception handling.](#6exception-handling)
    + [7. Next step](#7-next-step)


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
* A **CLI tool** which having two arguments "Pre-Build Days" and "Input CSV file." as input (**Done**)
* Testing (Unit Testing& Integration Testing) (**Done**)
* Handling the exceptions. (**Done**)

## Good To Have
* Other CLI information  (Showing current Processing state, CLI help information etc) (**Done**)
* Performance tuning options. (Processing Speed, memory usage, etc...) (**Not implemented**)

## Assumption
* 1. The single factory can produce multiple product. (multiple demands for different products)
* 2. The factory can only produce product during the prebuild days or the days between two demand key dates (for each product).
* 3. Assume input file could have 100,000 lines. 100 x 100 x 100 for site x product x days.

## Docs QuickLink
### 1. Design 
https://github.com/yuejoo/demo/blob/master/docs/Design.md

### 2. Models
https://github.com/yuejoo/demo/blob/master/docs/Models.md

### 3. PlanningLogic
https://github.com/yuejoo/demo/blob/master/docs/PlanningLogic.md

### 4. Task Break Down
https://github.com/yuejoo/demo/blob/master/docs/TaskBreakDown.md

### 5.CommandLineTool Usage
https://github.com/yuejoo/demo/blob/master/docs/CommandLineTool.md

### 6.Exception handling.
https://github.com/yuejoo/demo/blob/master/docs/CommandLineTool.md

### 7. Next step
https://github.com/yuejoo/demo/blob/master/docs/NextStep.md

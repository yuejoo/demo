## Planning Logic 
### Initial idea
We have two-steps method to finally plan the prodces for each FC-Product pair.
* 1. Fufill the demand gap
Reversely looping through the sorted days list, get the latest keyday and fill the current day with keydays demand. 
see the **left** process in the following diagram.
* 2. Planning the produces
Looping throught the sorted demands/days list, if the current day falls in the preBuild days gap, get the mostRecent keyday and comupte the average produces it needs for that day with adding the last days' produce status. see the **right** process in the following diagram.

<p align="left">
  <img src="https://github.com/yuejoo/demo/blob/master/docs/PlanningLogic.svg">
</p>


## Updated
The initial idea will only work in the assumption that the input days are in the consecutive raw days. (Factory working on consective days.)
However if the factory is not working on the consecutive days. (1 3 6 8 9). We will only plan the produce with wrong number which assumes that all days are consecutive.
https://github.com/yuejoo/demo/commit/67588f0265ceda8993ebdff187a6a066b439f513
### Fix
Updating the original planning logic with implementing a queue to temporarily hold the days before reaching the most recent keyDays. When the current day reaches to the keyday, re-planning those demands in the queue with poping from the front. (calculating the two days' distance by current queue's size)


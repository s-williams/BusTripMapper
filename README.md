# Bus Trips

Coding challenge mapping credit card payment data to bus trips.

Run by running the JUnit tests located in /test/. Input files containing credit card payment taps are located in resources, and output files containing the calculated trips are created in /out/.

Future ideas for enhancements and improvements:
* Currently, the tap and trip classes use Strings for a lot of their data. It would be much better to either come up with unique classes for each of the data types (e.g. a PAN class which could also validate that the credit card numbers) or use enums where possible.
* The project runs in batches: first the data is loaded via CSV, then the trips are calculated. I don't think real world systems would work like this. I think it should be rewritten so that the trips are recalculated after each tap data is loaded, as in a stream.
* More test data and more complex examples (maybe some way to automagically generate test cases?).

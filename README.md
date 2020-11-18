# Bus Trips

Coding challenge mapping credit card payment data to bus trips.

Run by running the JUnit tests located in `/test/`. Input files containing credit card payment taps are located in resources, and output files containing the calculated trips are created in `/out/`.

The code is hopefully relatively simple to understand, with the main class being the `BusTripMapper`. The function that reads the input file is `readFile()`, the function that maps the input file data to bus trips is `mapTrips()`, and the function that writes the output file is `writeFile()`. 

Future ideas for enhancements and improvements:
* Currently, the Tap and Trip classes use Strings for a lot of their data. It would be much better to either come up with unique classes for each of the data types (e.g. a PAN class which could also validate that credit card numbers) or use enums, such as for the bus stops or company IDs.
* The project runs in batches: first the data is loaded via CSV, then the trips are calculated. I don't think real world systems would work like this. I think it should be rewritten so that the trips are recalculated after each tap data is loaded, as in a continuous data stream.
* More test data and more complex examples (maybe some way to automagically generate test cases?).

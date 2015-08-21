CS 4328 Project 1 Spring 2014
Lavanya Tammineni A04377240

Project Files:
Makefile             -  Used to make the Project
README.TXT           -  This file
Enums.java           -  Enums in the Project
Event.java           -  Event Node structure
Process.java         -  Process Node Structure
Scheduler.java       -  Scheduling algorithms
Simulator.java       -  data manipulation methods
ISimulator.java      -  Interface
OSPRoject1.java      -  Main method

To Build Project:
make clean
make

Make Rules:
make     			   -  build the project
clean     			   -  removes object, executable, and output
make test 			   -  builds project and runs test output
make run ARGS="<Scheduler> <arrivalRate> <avgService> <quantum>"  -  builds project and runs for specific input arguments


Syntax:
./sim <Scheduler> <arrivalRate> <avgService> <quantum>
<simtype> = 1 for FCFS, 2 for SRTF, 3 for HRRN, 4 for Round Robin
<arrivalrate> = Average Arrival Rate
<avgservice> = Average Service Time
<quantum> = Quantum Time for Round-Robin

Output Files:
Output will be displayed in console. To copy the console start script before starting the execution and exit the script.

script filename

// execution

exit or type ctrl + D

Note : test.sh file should have the execution permission.

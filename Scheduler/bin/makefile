JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        Enums.java \
        Event.java \
        ISimulator.java \
	Process.java \	
	Simulator.java \
	Scheduler.java \
	OSProject1.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	 $(RM) *.class
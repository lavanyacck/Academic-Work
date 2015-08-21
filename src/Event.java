public class Event {
	double arrivalTime;
	Enums.eventType pType;
	int processID;

	public Event() {
		arrivalTime = 0;
		pType = Enums.eventType.UNKNOWN;
		processID = 0;
	}

	public Event(Event event) {
		arrivalTime = event.arrivalTime;
		pType = event.pType;
		processID = event.processID;
	}

	public Event(double arrivalTime_f, 
			Enums.eventType pType_f, int processID_f) {
		arrivalTime = arrivalTime_f;
		pType = pType_f;
		processID = processID_f;
	}
}
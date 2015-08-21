public class Process {
	double arrivalTime;
	double serviceTime;
	double remainingTime;
	double turnAroundTime;
	double responseTime;
	double startedTime;
	double completedTime;
	double lastPreEmptionTime;
	int queueSizeonCompletion;
	int processID;

	public Process() {
		arrivalTime = 0;
		serviceTime = 0;
		remainingTime = 0;
		turnAroundTime = 0;
		startedTime = 0;
		completedTime = 0;
		responseTime = 0;
		queueSizeonCompletion = 0;
		lastPreEmptionTime = 0;
		processID = 0;
	}

	public Process(double aTime, double sTime, int PID)
	{
		arrivalTime = aTime;
		serviceTime = sTime;
		processID = PID;
		remainingTime = sTime;
		turnAroundTime = 0;
		responseTime = 0;
		startedTime = 0;
		completedTime = 0;
		queueSizeonCompletion = 0;
		lastPreEmptionTime = 0;
	}
	
	// TODO
	public Process(double aTime, double sTime, double rTime, double wTime,
			double stTime, double cTime, int pID) {
		arrivalTime = aTime;
		serviceTime = sTime;
		remainingTime = rTime;
		turnAroundTime = wTime;
		startedTime = stTime;
		completedTime = cTime;
		processID = pID;
	}
}

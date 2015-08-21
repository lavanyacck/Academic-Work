// different scheduler algorithms are defined here
public class Scheduler extends Simulator {
	// local variables
	double lastCompletedTask = 0;
	Event currentEvent = new Event();
	Process newProcess = new Process();

	// FCFS
	void FCFS(double avgArrival, double avgServiceRate) {
		// Initialization
		init();
		lastCompletedTask = 0;
	    // generateProcess(avgArrival, avgServiceRate);
		 generateProcesses(avgArrival, avgServiceRate);
		// generateProcesses();
		while (count < num) {
			currentEvent = getEvent();
			if (currentEvent == null)
				return;
			clock = currentEvent.arrivalTime;

			switch (currentEvent.pType) {
			case ARRIVAL:
				// generate another arrival after one arrival is processed
				// generateProcess(avgArrival, avgServiceRate);

				// add process to ready queue
				addProcesstoRQ(currentEvent.processID);
				if (cpu == Enums.CPUStatus.IDLE) {
					// update CPU idle time and status
					CPUIdleTime += (clock - lastCompletedTask);
					cpu = Enums.CPUStatus.BUSY;

					// update the process start time and add the departure event
					currentProcess = getProcess();
					updateProcessStartTime(currentProcess.processID, clock);
					schedule_event(currentProcess.processID, clock
							+ currentProcess.serviceTime,
							Enums.eventType.DEPARTURE);
				} else {
					// addProcesstoRQ(currentEvent.processID);
				}
				break;

			case DEPARTURE:
				count++;
				updateProcessCompletedTime(currentEvent.processID, clock);
				updateProcessRemainingTime(currentEvent.processID, 0);
				updateWaitingQueueSize(currentEvent.processID,
						readyQueue.size());
				if (readyQueue.size() == 0) {
					cpu = Enums.CPUStatus.IDLE;
					lastCompletedTask = clock;
				} else {
					currentProcess = getProcess();
					updateProcessStartTime(currentProcess.processID, clock);
					schedule_event(currentProcess.processID, clock
							+ currentProcess.serviceTime,
							Enums.eventType.DEPARTURE);
				}
				break;
			default:
				break;
			}
		}
		generate_report();
	}

	// SRTF
	void SRTF(double avgArrival, double avgServiceRate) {
		// local variable 
		double prevClock = 0;
		lastCompletedTask = 0;
		// Initialization
		init();
		// generateProcess(avgArrival, avgServiceRate);
		 generateProcesses(avgArrival, avgServiceRate);
		// generateProcesses();

		while (count < num) {
			currentEvent = getEvent();
			if (currentEvent == null && readyQueue.isEmpty())
				return;
			else if (currentEvent == null && !readyQueue.isEmpty())
				currentEvent = new Event();
			prevClock = clock;
			clock = currentEvent.arrivalTime;

			switch (currentEvent.pType) {
			case ARRIVAL:
				// generate another arrival after one arrival is processed
				// generateProcess(avgArrival, avgServiceRate);

				// add process to ready queue
				addProcesstoRQ(currentEvent.processID);
				if (cpu == Enums.CPUStatus.IDLE) {
					// update CPU idle time and status
					CPUIdleTime += clock - lastCompletedTask;
					cpu = Enums.CPUStatus.BUSY;

					// update the process start time and add the departure event
					currentProcess = getProcess();
					updateProcessStartTime(currentProcess.processID, clock);
					schedule_event(currentProcess.processID, clock
							+ currentProcess.remainingTime,
							Enums.eventType.DEPARTURE);
				} else {
					updateProcessRemainingTime(currentProcess.processID,
							currentProcess.remainingTime
									- (clock - prevClock));
					newProcess = getSRTJob(currentProcess);
					if (currentProcess.processID != newProcess.processID) {
						currentProcess = newProcess;
						updateProcessStartTime(currentProcess.processID, clock);
						schedule_event(currentProcess.processID, clock
								+ currentProcess.remainingTime,
								Enums.eventType.DEPARTURE);
					}
				}
				break;

			case DEPARTURE:
				count++;
				updateProcessCompletedTime(currentEvent.processID, clock);
				updateProcessRemainingTime(currentEvent.processID, 0);
				updateWaitingQueueSize(currentEvent.processID,
						readyQueue.size());
				if (readyQueue.size() == 0) {
					cpu = Enums.CPUStatus.IDLE;
					lastCompletedTask = clock;
				} else {
					currentProcess = getSRTJob(null);
					if (currentProcess.serviceTime == currentProcess.remainingTime)
						updateProcessStartTime(currentProcess.processID, clock);
					schedule_event(currentProcess.processID, clock
							+ currentProcess.remainingTime,
							Enums.eventType.DEPARTURE);
				}
				break;
			default:
				currentProcess = getSRTJob(null);
				updateProcessStartTime(currentProcess.processID, clock);
				schedule_event(currentProcess.processID, clock
						+ currentProcess.serviceTime, Enums.eventType.DEPARTURE);
				break;
			}
		}
		generate_report();
	}

	// HRRN
	void HRRN(double avgArrival, double avgServiceRate) {
		// Initialization
		init();
		lastCompletedTask = 0;
		// generateProcess(avgArrival, avgServiceRate);
		generateProcesses(avgArrival, avgServiceRate);
		// generateProcesses();
		while (count < num) {
			currentEvent = getEvent();
			if (currentEvent == null)
				return;
			clock = currentEvent.arrivalTime;

			switch (currentEvent.pType) {
			case ARRIVAL:
				// generate another arrival after one arrival is processed
				// generateProcess(avgArrival, avgServiceRate);

				// add process to ready queue
				addProcesstoRQ(currentEvent.processID);
				if (cpu == Enums.CPUStatus.IDLE) {
					// update CPU idle time and status
					CPUIdleTime += clock - lastCompletedTask;
					cpu = Enums.CPUStatus.BUSY;

					// update the process start time and add the departure event
					currentProcess = getProcess();
					updateProcessStartTime(currentProcess.processID, clock);
					schedule_event(currentProcess.processID, clock
							+ currentProcess.serviceTime,
							Enums.eventType.DEPARTURE);
				} else {
					// addProcesstoRQ(currentEvent.processID);
				}
				break;

			case DEPARTURE:
				count++;
				updateProcessCompletedTime(currentEvent.processID, clock);
				updateProcessRemainingTime(currentEvent.processID, 0);
				updateWaitingQueueSize(currentEvent.processID,
						readyQueue.size());
				
				if (readyQueue.size() == 0) {
					cpu = Enums.CPUStatus.IDLE;
					lastCompletedTask = clock;
				} else {
					currentProcess = getLeastHRRNJob(clock);
					updateProcessStartTime(currentProcess.processID, clock);
					schedule_event(currentProcess.processID, clock
							+ currentProcess.serviceTime,
							Enums.eventType.DEPARTURE);
				}
				break;
			default:
				break;
			}
		}
		generate_report();
	}

	// RR
	void RR(double avgArrival, double avgServiceRate, double quantum) {
		// Initialization
		init();
		lastCompletedTask = 0;
		// generateProcess(avgArrival, avgServiceRate);
		generateProcesses(avgArrival, avgServiceRate);
		// generateProcesses();
		while (count < num) {
			currentEvent = getEvent();
			if (currentEvent == null)
				return;
			clock = currentEvent.arrivalTime;

			switch (currentEvent.pType) {
			case ARRIVAL:
				// generate another arrival after one arrival is processed
				 //generateProcess(avgArrival, avgServiceRate);

				// add process to ready queue
				addProcesstoRQ(currentEvent.processID);
				if (cpu == Enums.CPUStatus.IDLE) {
					// update CPU idle time and status
					CPUIdleTime += clock - lastCompletedTask;
					cpu = Enums.CPUStatus.BUSY;

					// update the process start time and add the departure event
					currentProcess = getProcess();
					updateProcessStartTime(currentProcess.processID, clock);
					if (quantum >= currentProcess.remainingTime)
						schedule_event(currentProcess.processID, clock
								+ currentProcess.remainingTime,
								Enums.eventType.DEPARTURE);
					else {
						schedule_event(currentProcess.processID, clock
								+ quantum, Enums.eventType.TIMESLICE);
						updateProcessRemainingTime(currentProcess.processID,
								currentProcess.remainingTime - quantum);
					}
				} else {
					// addProcesstoRQ(currentEvent.processID);
				}
				break;

			case DEPARTURE:
				count++;
				updateProcessCompletedTime(currentEvent.processID, clock);
				updateProcessRemainingTime(currentEvent.processID, 0);
				updateWaitingQueueSize(currentEvent.processID,
						readyQueue.size());
				// updateProcessWaitingTime(currentEvent.processID,
				// getProcess(currentEvent.processID).startedTime -
				// currentEvent.arrivalTime);
				if (readyQueue.size() == 0) {
					cpu = Enums.CPUStatus.IDLE;
					lastCompletedTask = clock;
				} else {
					currentProcess = getProcess();
					if (currentProcess.remainingTime == currentProcess.serviceTime)
						updateProcessStartTime(currentProcess.processID, clock);
					if (quantum >= currentProcess.remainingTime)
						schedule_event(currentProcess.processID, clock
								+ currentProcess.remainingTime,
								Enums.eventType.DEPARTURE);
					else {
						schedule_event(currentProcess.processID, clock
								+ quantum, Enums.eventType.TIMESLICE);
						updateProcessRemainingTime(currentProcess.processID,
								currentProcess.remainingTime - quantum);
					}
				}
				break;
			case TIMESLICE:
				addProcesstoRQ(currentEvent.processID);
				currentProcess = getProcess();
				if (currentProcess.remainingTime == currentProcess.serviceTime)
					updateProcessStartTime(currentProcess.processID, clock);
				if (quantum >= currentProcess.remainingTime)
					schedule_event(currentProcess.processID, clock
							+ currentProcess.remainingTime,
							Enums.eventType.DEPARTURE);
				else {
					schedule_event(currentProcess.processID, clock + quantum,
							Enums.eventType.TIMESLICE);
					updateProcessRemainingTime(currentProcess.processID,
							currentProcess.remainingTime - quantum);
				}
				break;
			default:
				break;
			}
		}
		generate_report();
	}
}

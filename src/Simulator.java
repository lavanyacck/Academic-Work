import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Simulator implements ISimulator {

	// variables
	int count;
	int UID;
	float rate;
	double clock;
	double arrivalTime;
	Random rand;
	Enums eType;
	Enums.CPUStatus cpu;
	LinkedList<Event> eventList;
	LinkedList<Process> trackList;

	public Process currentProcess;
	public LinkedList<Process> readyQueue;
	
	// variables used for reporting
	double CPUIdleTime;
	double avgTurnAroundTime;
	double avgResponseTime;
	double avgWaitingQueue;
	double throughput;
	double avgCPUUtil;
	public  Map<Integer, ArraySet> result = new TreeMap<Integer, ArraySet>();
    public  int res1 = 0;
	// all the variables are initialized here
	@Override
	public void init() {
		clock = 0;
		count = 0;
		rate = 0;
		UID = 0;
		arrivalTime = 0;
		rand = new Random();
		eType = new Enums();
		cpu = Enums.CPUStatus.IDLE;
		currentProcess = new Process();
		eventList = new LinkedList<Event>();
		trackList = new LinkedList<Process>();
		readyQueue = new LinkedList<Process>();

		CPUIdleTime = 0;
		avgTurnAroundTime = 0;
		avgResponseTime = 0;
		avgWaitingQueue = 0;
		throughput = 0;
		avgCPUUtil = 0;
	}

	// returns a random number between 0 and 1
	float urand() {
		return (rand.nextFloat());
	}

	// returns a random number that follows poisson distribution
	double genpoisson(double avgServiceRate) {
		double u, x;
		x = 0;
		while (x == 0) {
			u = urand();
			x = (-avgServiceRate) * Math.log(u);
		}
		return (x);
	}

	// returns a random number that follows an exp distribution
	double genexp(double lambda) {
		double u, x;
		x = 0;
		while (x == 0) {
			u = urand();
			x = (double) ((-1 / lambda) * Math.log(u));
		}
		return (x);
	}

	//
	void generateProcesses(double avgArrival, double avgServiceRate) {
		double arrivalTime = 0;

		for (int i = 0; i < 2 * num; i++) {
			arrivalTime += genexp(avgArrival);
			// store the processes
			trackList.add(new Process(arrivalTime, genpoisson(avgServiceRate),
					i+1));

			// add to event list
			eventList.add(new Event(arrivalTime, Enums.eventType.ARRIVAL, i+1));
		}
	}

	void generateProcess(double avgArrival, double avgServiceRate) {

		arrivalTime += genexp(avgArrival);
		// store the processes
		trackList.add(new Process(arrivalTime, genpoisson(avgServiceRate),
				++UID));

		// add to event list
		schedule_event(UID, arrivalTime, Enums.eventType.ARRIVAL);
	}

	void generateProcesses() {
		trackList.add(new Process(0, 3, 1));
		trackList.add(new Process(2, 6, 2));
		trackList.add(new Process(4, 4, 3));
		trackList.add(new Process(6, 5, 4));
		trackList.add(new Process(8, 2, 5));

		eventList.add(new Event(0, Enums.eventType.ARRIVAL, 1));
		eventList.add(new Event(2, Enums.eventType.ARRIVAL, 2));
		eventList.add(new Event(4, Enums.eventType.ARRIVAL, 3));
		eventList.add(new Event(6, Enums.eventType.ARRIVAL, 4));
		eventList.add(new Event(8, Enums.eventType.ARRIVAL, 5));
	}

	@Override
	public Event getEvent() {
		if (eventList.size() != 0)
			return eventList.remove();
		else
			return null;
	}

	@Override
	public void generate_report() {
		int counter = 0;
		for (Process p : trackList) {
			if (counter <= num )
			{
				if (p.remainingTime == 0) {
				p.turnAroundTime = p.completedTime - p.arrivalTime;
				p.responseTime = p.startedTime - p.arrivalTime;
				avgTurnAroundTime += p.turnAroundTime;
				avgResponseTime += p.responseTime;
				avgWaitingQueue += p.queueSizeonCompletion;
				counter++;
				}
			} else
				break;
		}
		avgResponseTime = avgResponseTime / num;
		avgTurnAroundTime = avgTurnAroundTime / num;
		avgWaitingQueue = avgWaitingQueue / num;
		throughput = num / clock;
		avgCPUUtil = 1.0 - (CPUIdleTime / clock);

		Output();
	}

	void Output() {
		System.out.println("Total Simulation Time: " + clock);
		System.out.println("Average Response Time: " + avgResponseTime);
		System.out.println("Average Turn-Around Time: " + avgTurnAroundTime);
		System.out.println("Average # of Processes Waiting in Queue:  "
				+ avgWaitingQueue);
		System.out.println("Throughput: " + throughput);
		System.out.println("Average CPU Utilization: " + avgCPUUtil );	
		System.out.println("--------------------------------------------");		
		
			result.put(res1, new ArraySet(avgTurnAroundTime, throughput, avgCPUUtil, avgWaitingQueue));
			res1++;
			if(res1>=149)
			{
				Iterator<Integer> keySetIterator = result.keySet().iterator();
				String s1= "";
				String s2= "";
				String s3= "";
				String s4= "";
				while (keySetIterator.hasNext()) {
					Integer key = keySetIterator.next();
					ArraySet resultArray = result.get(key);
					 s1 = s1 + ","+resultArray.turn;
					 s2 =s2 + ","+resultArray.thro;
					 s3 =s3 + ","+resultArray.cpu;
					 s4 =s4 + ","+resultArray.no;
				}
				System.out.println(s1);
				System.out.println("--------------------------------------------");	
				System.out.println(s2);
				System.out.println("--------------------------------------------");	
				System.out.println(s3);
				System.out.println("--------------------------------------------");	
				System.out.println(s4);
				System.out.println("--------------------------------------------");	
			}
	}

	@Override
	public void schedule_event(int PID, double time, Enums.eventType type) {
		int i = 0;
		while (i < eventList.size()) {
			if (eventList.get(i).arrivalTime <= time)
				i++;
			else
				break;
		}
		eventList.add(i, new Event(time, type, PID));
	}

	void updateProcessStartTime(int PID, double time) {
		for (Process p : trackList) {
			if (p.processID == PID) {
				p.startedTime = time;
				break;
			}
		}
	}

	void updateProcessCompletedTime(int PID, double time) {
		for (Process p : trackList) {
			if (p.processID == PID) {
				p.completedTime = time;
				break;
			}
		}
	}

	void updateProcessRemainingTime(int PID, double time) {
		for (Process p : trackList) {
			if (p.processID == PID) {
				p.remainingTime = time;
				break;
			}
		}
	}

	void updateWaitingQueueSize(int PID, int size) {
		for (Process p : trackList) {
			if (p.processID == PID) {
				p.queueSizeonCompletion = size;
				break;
			}
		}
	}

	// adds the process to ready queue
	void addProcesstoRQ(int PID) {
		for (Process p : trackList) {
			if (PID == p.processID) {
				readyQueue.add(p);
				return;
			}
		}
	}

	Process getSRTJob(Process current) {
		int pos = 0;
		for (int i = 1; i < readyQueue.size(); i++) {
			if (readyQueue.get(pos).remainingTime > readyQueue.get(i).remainingTime)
				pos = i;
		}
		if (current != null) {
			if (readyQueue.get(pos).remainingTime > current.remainingTime)
				return current;
			else {
				addProcesstoRQ(current.processID);
				deleteDepartureEvent(current.processID);
				return readyQueue.remove(pos);
			}
		}
		return readyQueue.remove(pos);
	}

	void deleteDepartureEvent(int PID) {
		int i = 0;
		for (; i < eventList.size(); i++) {
			if (eventList.get(i).processID == PID
					&& eventList.get(i).pType == Enums.eventType.DEPARTURE)
				break;
		}
		eventList.remove(i);
	}

	Process getLeastHRRNJob(double time) {
		int pos = 0;
		for (int i = 1; i < readyQueue.size(); i++) {
			if (HRRN(readyQueue.get(pos), time) < HRRN(readyQueue.get(i), time))
				pos = i;
		}
		return readyQueue.remove(pos);
	}

	double HRRN(Process p, double time) {
		return ((clock - p.arrivalTime) + p.serviceTime) / p.serviceTime;
	}

	// returns the head process in ready queue
	Process getProcess() {
		return readyQueue.remove();
	}
}
class ArraySet {
	double id, turn, thro, cpu, no;

	ArraySet() {
	};

	ArraySet(double tur, double h, double c, double nf) {
		turn = tur;
		thro = h;
		cpu = c;
		no = nf;
	}

	public double getLow() {
		return turn;
	}

	public void setLow(double low) {
		this.turn = low;
	}

	public double getHigh() {
		return thro;
	}

	public void setHigh(double high) {
		this.thro = high;
	}

	public double getSum() {
		return cpu;
	}

	public void setSum(double sum) {
		this.cpu = sum;
	}
}
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class OSProject1 {
	
	public static void main(String[] args) {
		try {
			
			// local variables with default values
			double avgArrival = 1;
			double avgService = 0.06;
			double quantum = 0.01;
			int scheduler = 2;
			Scheduler sh = new Scheduler();

			System.out.println("Discrete Time Event Stimulation");
			System.out
					.println(" Test Input Order : <Scheduler> <Avg Arrival Rate> <Avg Service Time> <Quantum>");
			System.out.println("1 FCFS | 2 SRTF | 3 HRRN | 4 RR");
			printDefaults();
			System.out.println("---------------------------------");
			System.out.println("Results for the run are :");

			if (args.length >= 4) {
				scheduler = Integer.parseInt(args[0].trim());
				avgArrival = Double.parseDouble(args[1].trim());
				avgService = Double.parseDouble(args[2].trim());
				quantum = Double.parseDouble(args[3].trim());
			} else if (args.length == 3) {
				scheduler = Integer.parseInt(args[0].trim());
				avgArrival = Double.parseDouble(args[1].trim());
				avgService = Double.parseDouble(args[2].trim());
			} else if (args.length == 2) {
				scheduler = Integer.parseInt(args[0].trim());
				avgArrival = Double.parseDouble(args[1].trim());
			} else if (args.length == 1) {
				scheduler = Integer.parseInt(args[0].trim());
			}

			for (int i = 1; i <= 5; i++) {
				if(i<=4)
				scheduler = i;				
				if (i == 4)
					quantum = 0.01;
				if (i == 5)
					quantum = 0.2;
				for (int j = 1; j <= 30; j++) {
					avgArrival = j;
					// pick the scheduler
					if (scheduler == 1) {
						System.out.println("Scheduling Processes using FCFS");
						sh.FCFS(avgArrival, avgService);
					} else if (scheduler == 2) {
						System.out.println("Scheduling Processes using SRTF");
						sh.SRTF(avgArrival, avgService);
					} else if (scheduler == 3) {
						System.out.println("Scheduling Processes using HRRN");
						sh.HRRN(avgArrival, avgService);
					} else if (scheduler == 4) {
						System.out.println("Scheduling Processes using RR");
						sh.RR(avgArrival, avgService, quantum);
					}
				}
			}
		
			
			// pick the scheduler
			/*if (scheduler == 1) {
				System.out.println("Scheduling Processes using FCFS");
				sh.FCFS(avgArrival, avgService);
			} else if (scheduler == 2) {
				System.out.println("Scheduling Processes using SRTF");
				sh.SRTF(avgArrival, avgService);
			} else if (scheduler == 3) {
				System.out.println("Scheduling Processes using HRRN");
				sh.HRRN(avgArrival, avgService);
			} else if (scheduler == 4) {
				System.out.println("Scheduling Processes using RR");
				sh.RR(avgArrival, avgService, quantum);
			}*/
			

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	static void printDefaults() {
		System.out
				.println("Below default values are used if less arguments are provided");
		System.out.println("Scheduler FCFS");
		System.out.println("Average arrival rate 1");
		System.out.println("Average service rate 0.06");
		System.out.println("Average quantum 0.2");
	}

}

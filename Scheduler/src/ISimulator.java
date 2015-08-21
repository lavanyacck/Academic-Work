public interface ISimulator {
	void init();
	Event getEvent();
	void generate_report();
	void schedule_event(int PID, double time, Enums.eventType type);
	
	public static final int num = 10000;
}

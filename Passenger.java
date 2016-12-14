import java.util.Random;
public class Passenger implements Comparable<Passenger>{
	private Random generator=new Random();
	private boolean international=false;
	private double flightIsLeaving=0;
	private double currentTime=0;
	private double departureTime;
	private double counterQTime=0;
	private double securityQTime=0;
	private double gateQTime=0;
	private double counterTime=0;
	private double securityTime=0;
	private double gateTime=0;
	private double timeInAirport=0;
	private double arrivalTime;
	private int numBags;
	private boolean arriveOnTime=false;
	private double flightLeavingTime=0;
	private boolean firstClass;
	public Passenger(boolean first,boolean outCountry, double arrival){
		firstClass=first;
		arrivalTime=arrival;
		departureTime=arrival;
		international=outCountry;
		double x=generator.nextDouble();
		while(x<=.6&&international){
			numBags++;
			x=generator.nextDouble();
		}
		while(x<=.8&&!international){
			numBags++;
			x=generator.nextDouble();
		}
	}
	public boolean international(){
		return international;
	}
	public void setCurrentTime(double a){
		currentTime=a;
	}
	public void flightIsLeaving(double a){
		flightIsLeaving=a;
	}
	public double getCurrentTime(){
		return currentTime;
	}
	public double arrival(){
		return arrivalTime;
	}
	public boolean onTime(){
		return arriveOnTime;
	}
	public boolean isFirstClass(){
		return firstClass;
	}
	public void arrivedOnTime(){
		arriveOnTime=true;
	}
	public double getFlightLeavingTime(){
		return flightLeavingTime;
	}
	public void setFlightLeavingTime(double a){
		flightLeavingTime=a;
	}
	//check if departureTime
	public boolean missedFlight(){
		timeInAirport=counterTime+counterQTime+securityTime+securityQTime+departureTime;
		return flightIsLeaving<timeInAirport;
	}
	
	//check if they have been in the airport longer than the time they had until departure
	public boolean moneyBack(){
	return departureTime>(5400)&&(missedFlight());
	}
	
	public int numberOfBags(){
		return numBags;
	}
	//individual waiting places and there return values
	public void atCounter(double a){
		counterTime=a-currentTime;
		currentTime=a;
	}
	public void atSecurity(double a){
		securityTime=a-currentTime;
		currentTime=a;
	}
	public void atGate(double a){
		gateTime=a-currentTime;
		a=currentTime;
	}
	public double counterTime(){
		return counterTime;
	}
	public double securityTime(){
		return securityTime;
	}
	public double gateTime(){
		return gateTime;
	}
	public void inCounterQ(double a){
		counterQTime=a-currentTime;
		currentTime=a;
	}
	public void inSecurityQ(double a){
		securityQTime=a-currentTime;
		currentTime=a;
	}
	public void inGateQ(double a){
		gateQTime=a-currentTime;
		currentTime=a;
	}
	public double counterQTime(){
		return counterQTime;
	}
	public double securityQTime(){
		return securityQTime;
	}
	public double gateQTime(){
		return gateQTime;
	}

	@Override
	public int compareTo(Passenger another) {
		if (this.arrivalTime<another.arrivalTime){
			return 1;
		}else{
			return -1;
		}
	}
	
	

	
	
	

}

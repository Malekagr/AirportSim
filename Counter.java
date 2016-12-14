import java.util.Random;
public class Counter {
	Random randGen= new Random();
	double seconds=0;
	double step1=0;
	double step2=0;
	double step3=0;
	int numberOfBags=0;
	double processTime=0;
	Passenger beingServiced=null;
	double notServicingTime=0;
	boolean inUse=false;
	boolean firstClass=false;
	public Counter(boolean first){
		firstClass=first;
	}
	public void getData(Passenger a){
		beingServiced =a;
		inUse=true;
		
	}
	public void makeProcess(){
		double x=1-randGen.nextDouble();
		processTime=-1*Math.log(x)*180;
	}
	public Passenger getPassenger(){
		return beingServiced;
	}
	//Initializes how long each step will take, then stores it in processTime
	public void makeStep(){
		numberOfBags=beingServiced.numberOfBags();
		double x=1-randGen.nextDouble();
		double y=1-randGen.nextDouble();
		step1=-1*Math.log(x)*120;
		step3=-1*Math.log(y)*180;
		double bagRand;
		for(int i=0;i<numberOfBags;i++){
			bagRand=1-randGen.nextDouble();
			step2=step2+(-1*Math.log(bagRand)*60);
		}
		processTime=step1+step2+step3;
		
	}
	
	//increases how many seconds have passed at the counter by 1 second
	public void incrementCount(){
		seconds++;
	}
	
	/* checks if the seconds is greater or equal to
	 * the process time, if so, process is done and 
	 * customer has been serviced
	 */
	 
	public boolean isDone(){
		if(beingServiced==null){
			return true;
		}
		if (seconds>=processTime){
			inUse=false;
			
		}
		return seconds>=processTime;
	}
	
	//returns how long the counter was not in Serivce
	public double notServicingTime(){
		return notServicingTime;
	}
	
	public void notServicing(){
		notServicingTime++;
	}
	//check if the counter is currently servicing a customer
	public boolean isInUse(){
		return inUse;
	}
	
	//makes inUse true to indicate servicing customer
	public void nextCustomer(){
		inUse=true;
	}
	
	//changes variables back to 0
	public void clear(){
		seconds=0;
		step1=0;
		step2=0;
		step3=0;
		processTime=0;
		inUse=false;
		firstClass=false;
	}
}

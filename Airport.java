
/*
 * Malek Ameli-Grillon 
 * Airport simulation
 * 
 * Create a simulation of an airport to model a passengers journey from entering the airport to boarding the plane.
 * Goal: Determining how the airport's revenue and is changed by manipulating the number of check in and security counters, 
 *  in addition to how the passengers path through the airport changes.
 *  
 *  How to run: compile and run code. You will be prompted to enter a number of days. Keep it within the native int size, or else you will have to re-enter a different number. 
 *  Afterwards you will be prompted to enter the number of first class desks and the number of commuter class desks (these can only total to 6). 
 *  The simulation will run and print a list of stats to the terminal
 * 
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Airport {
	static Random generator = new Random();
	static int whichStep = 0;
	static int timeElapsed = 0;
	static int here3 = 0;
	static int numberInternationalsMade = 0;
	static int numberCommutersMade = 0;
	static int g = 0;
	static int ge = 0;
	static int numberCoachCounterB = 0;
	static int numberFirstCounterB = 0;
	static int numberCoachCounterE = 0;
	static int numberFirstCounterE = 0;
	static int numberCoachSecurityB = 0;
	static int numberFirstSecurityB = 0;
	static int numberCoachSecurityE = 0;
	static int numberFirstSecurityE = 0;

	public static void main(String[] args) {
		// list Create security and queues

		Counter fcSecurity1 = new Counter(true);
		Counter ccSecurity1 = new Counter(false);
		Counter ccSecurity2 = new Counter(false);
		Scanner inscan = new Scanner(System.in);

		LineQueue<Passenger> internationalLeaving = new LineQueue<Passenger>();
		LineQueue<Passenger> checkFirstQ = new LineQueue<Passenger>();
		LineQueue<Passenger> checkComQ = new LineQueue<Passenger>();
		LineQueue<Passenger> securityFirstQ = new LineQueue<Passenger>();
		LineQueue<Passenger> securityComQ = new LineQueue<Passenger>();
		LineQueue<Passenger> gateComQ = new LineQueue<Passenger>();
		double[] interArray = new double[2];
		LineQueue<Passenger> interArrival = new LineQueue<Passenger>();
		LineQueue<Passenger> commuterArrival = new LineQueue<Passenger>();
		int days = 0;
		/*
		 * Get inputs from user to run simulation (days, number of desks)
		 */
		while (true) {
			try {
				System.out.println("How many days to run the simulation?");
				days = inscan.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Incorrect input type. Please enter an integer");
				inscan.next();
			}

		}

		double TIME = 60 * 60 * 24 * days;
		int numOfCoachAgents = 3;
		int numOfFirstAgents = 1;
		int totalAgents = 0;
		int checker;
		System.out.println("There can be a maximum of 6 desks:");
		do {
			while (true) {
				try {
					System.out.println("How many coach class check-in desks will there be? (enter 0 for default)");
					checker = inscan.nextInt();
					if (checker > 0) {
						numOfCoachAgents = checker;
					}
					break;
				} catch (InputMismatchException e) {
					System.out.println("Incorrect input type. Please enter an integer");
					inscan.next();
				}
			}
			while (true) {
				try {
					System.out.println("How many first class check-In desks will there be? (enter 0 for default)");
					checker = inscan.nextInt();
					if (checker > 0) {
						numOfFirstAgents = checker;
					}
					break;
				} catch (InputMismatchException e) {
					System.out.println("Incorrect input type. Please enter an integer");
					inscan.next();
				}
			}

			totalAgents = numOfFirstAgents + numOfCoachAgents;
			if (totalAgents > 6) {
				System.out.println("You can have a maximum of 6 counters, enter new numbers");
			}

		} while (totalAgents > 6);

		double curInt = 0;
		double curCom = 0;
		// check in counters
		Counter[] coachCounter = new Counter[numOfCoachAgents];
		Counter[] firstCounter = new Counter[numOfFirstAgents];
		for (int i = 0; i < firstCounter.length; i++) {
			Counter fcCounter1 = new Counter(true);
			firstCounter[i] = fcCounter1;
		}
		for (int i = 0; i < coachCounter.length; i++) {
			Counter ccCounter1 = new Counter(false);
			coachCounter[i] = ccCounter1;
		}
		int flightRate = 3600;

		int numberOfCoachC = 0;
		double totalCoachCheckQ = 0;
		double totalCoachCheck = 0;
		double totalCoachSecurityQ = 0;
		double totalCoachS = 0;
		double totalGate = 0;

		int numberOfFirst = 0;
		int numberOfCoachI = 0;
		double totalFirstCheckQ = 0;
		double totalFirstCheck = 0;
		double totalFirstSecurityQ = 0;
		double totalFirstSecurity = 0;

		int firstMissedFlight = 0;
		int coachMissedFlight = 0;
		int coachRefund = 0;
		int firstRefund = 0;

		Passenger nextCommuter = null;
		Passenger nextInternational = null;

		// keep going until timeElapsed has reached TIME running
		while (timeElapsed != TIME) {
			// System.out.println("while");
			/*
			 * First part of the program, generating commuters that are arriving
			 * to the airport, then queueing them in the appropriate check in
			 * location
			 * 
			 * 
			 */

			// check if commuter should be generated
			if (timeElapsed % flightRate == 1800) {
				commuterArrival = makeCommuter();
				nextCommuter = commuterArrival.dequeue();
				curCom = timeElapsed;
			} // end if

			// check if international should be generated and get the first
			// person that will arrive
			if (timeElapsed % 21600 == 0) {
				interArrival = makeInter();
				nextInternational = interArrival.dequeue();
				curInt = timeElapsed;
			} // end if

			if (nextCommuter != null && curCom - nextCommuter.arrival() <= timeElapsed) {
				nextCommuter.setCurrentTime(timeElapsed);
				g++;
				checkComQ.enqueue(nextCommuter);
				if (!commuterArrival.isEmpty()) {
					nextCommuter = commuterArrival.dequeue();
					curCom = timeElapsed;
				}
			} // end if

			if (curInt - 21600 - nextInternational.arrival() <= timeElapsed) {
				ge++;
				if (nextInternational.isFirstClass()) {
					nextInternational.setCurrentTime(timeElapsed);
					checkFirstQ.enqueue(nextInternational);
				} else {
					nextInternational.setCurrentTime(timeElapsed);
					checkComQ.enqueue(nextInternational);
				}
				// nextInternational.flightIsLeaving(curInt+(21600-nextInternational.arrival()));
				if (!interArrival.isEmpty()) {
					nextInternational = interArrival.dequeue();
					curInt = timeElapsed;
				}
			} // end if

			/*
			 * second part of the process, dequeuing from the bag line, and
			 * servicing them at the bag counter
			 */

			// first class counter

			for (int i = 0; i < coachCounter.length; i++) {
				checkCounter(checkComQ, securityComQ, coachCounter[i], timeElapsed);
			}
			for (int i = 0; i < firstCounter.length; i++) {
				checkCounter(checkFirstQ, securityFirstQ, firstCounter[i], timeElapsed);
			}

			checkSecurity(securityFirstQ, gateComQ, fcSecurity1, internationalLeaving);
			checkSecurity(securityComQ, gateComQ, ccSecurity1, internationalLeaving);
			checkSecurity(securityComQ, gateComQ, ccSecurity2, internationalLeaving);

			if (timeElapsed > 0 && timeElapsed % 3600 == 1800) {
				for (int i = 0; i < 50 && !gateComQ.isEmpty(); i++) {
					Passenger gettingOn = gateComQ.dequeue();
					gettingOn.atGate(timeElapsed);
					// System.out.println("i: "+i);
					numberOfCoachC++;
					totalCoachCheckQ = totalCoachCheckQ + gettingOn.counterQTime();
					totalCoachCheck = totalCoachCheck + gettingOn.counterTime();
					totalCoachSecurityQ = totalCoachSecurityQ + gettingOn.securityQTime();
					totalCoachS = totalCoachS + gettingOn.securityTime();
					totalGate = totalGate + gettingOn.gateTime();
				}
			}

			while (!internationalLeaving.isEmpty()) {
				Passenger gettingOn = internationalLeaving.dequeue();

				if (gettingOn.isFirstClass()) {
					numberOfFirst++;
					totalFirstCheckQ = totalFirstCheckQ + gettingOn.counterQTime();
					totalFirstCheck = totalFirstCheck + gettingOn.counterTime();
					totalFirstSecurityQ = totalFirstSecurityQ + gettingOn.securityQTime();
					totalFirstSecurity = totalFirstSecurity + gettingOn.securityTime();
				} else {

					numberOfCoachI++;
					totalCoachCheckQ = totalCoachCheckQ + gettingOn.counterQTime();
					totalCoachCheck = totalCoachCheck + gettingOn.counterTime();
					totalCoachSecurityQ = totalCoachSecurityQ + gettingOn.securityQTime();
					totalCoachS = totalCoachS + gettingOn.securityTime();
					if (timeElapsed > gettingOn.getFlightLeavingTime()) {
						coachMissedFlight++;
						if (gettingOn.onTime()) {
							coachRefund++;
						}
					}
				}
			}
			timeElapsed++;
		} // end of while

		/*
		 * print out statistics
		 */
		int allCoach = numberOfCoachC + numberOfCoachI;

		System.out.println("There were " + allCoach + " coach class passengers that made it through the airport");
		System.out.println(
				"\t" + numberOfCoachI + " were international fliers\n\t" + numberOfCoachC + " were commuter fliers");
		System.out.printf("\tTheir average time in the check in queue was %.2f minutes\n",
				((double) ((int) totalCoachCheckQ / allCoach) / 60));
		System.out.println("\tTheir average time at the check in counter " + (double) ((int) totalCoachCheck / allCoach)
				+ " seconds");
		System.out.println("\tTheir average time in the security queue was " + (int) (totalCoachSecurityQ / allCoach)
				+ " seconds");
		System.out.println("\tTheir average time in security was " + (int) (totalCoachS / allCoach) + " seconds");

		System.out
				.println("\nTheir were " + numberOfFirst + " first class passengers that made it through the airport ");
		System.out.printf("\tTheir average time in the check in queue was %.2f minutes\n",
				((double) ((int) totalFirstCheckQ / numberOfFirst) / 60));
		System.out.println("\tTheir average time at the check in counter "
				+ (double) ((int) totalFirstCheck / numberOfFirst) + " seconds");
		System.out.println("\tTheir average time in the security queue was "
				+ (int) (totalFirstSecurityQ / numberOfFirst) + " seconds");
		System.out.println(
				"\tThere average time in security was " + (int) (totalFirstSecurity / numberOfFirst) + " seconds\n");

		System.out
				.println(firstMissedFlight + " first class passengers missed flights " + firstRefund + " got a refund");
		System.out
				.println(coachMissedFlight + " coach class passengers missed flights " + coachRefund + " got a refund");
		System.out.println("There were " + totalAgents + " agents, making a total of " + totalAgents * days * 24 * 25);
		System.out.println("The airport made $" + 500 * numberOfCoachI + " from coach class international fliers");
		System.out.println("The airport made $" + 1000 * numberOfFirst + " from first class international fliers");
		System.out.println("The airport made $" + 200 * numberOfCoachC + " from coach class commute fliers");
		System.out.println("It cost $" + 10000 * days * 4 + " to run the international flights");
		System.out.println("It cost $" + 1000 * days * 24 + " to run the commute flights");

	}

	public static void checkCounter(LineQueue<Passenger> line, LineQueue<Passenger> next, Counter servicing,
			double timeElapsed) {
		if (servicing.isDone()) {
			if (servicing.getPassenger() != null) {
				servicing.getPassenger().atCounter(timeElapsed);
				next.enqueue(servicing.getPassenger());
				// System.out.println(servicing.getPassenger().international());
				if (servicing.getPassenger().isFirstClass()) {
					numberFirstCounterE++;
				} else {
					numberCoachCounterE++;
				}
				servicing.getData(null);
			}
			if (!line.isEmpty()) {
				servicing.clear();
				servicing.getData(line.dequeue());
				if (servicing.getPassenger().isFirstClass()) {
					numberFirstCounterB++;
				} else {
					numberCoachCounterB++;
				}
				servicing.getPassenger().inCounterQ(timeElapsed);
				servicing.makeStep();

			} else {
				servicing.notServicing();
			}
		}
		servicing.incrementCount();
	}

	public static void checkSecurity(LineQueue<Passenger> line, LineQueue<Passenger> next, Counter servicing,
			LineQueue<Passenger> leave) {
		if (servicing.isDone()) {
			if (servicing.getPassenger() != null) {
				servicing.getPassenger().atSecurity(timeElapsed);
				if (!servicing.getPassenger().international()) {
					here3++;
					next.enqueue(servicing.getPassenger());
					if (servicing.getPassenger().isFirstClass()) {
						numberFirstSecurityE++;
					} else {
						numberCoachSecurityE++;
					}

					servicing.getData(null);
				} else {
					leave.enqueue(servicing.getPassenger());
					if (servicing.getPassenger().isFirstClass()) {
						numberFirstSecurityE++;
					} else {
						numberCoachSecurityE++;
					}
					servicing.getData(null);
				}
			}
			if (!line.isEmpty()) {
				servicing.clear();
				servicing.getData(line.dequeue());
				servicing.getPassenger().inSecurityQ(timeElapsed);
				servicing.makeProcess();
			} else {
				servicing.notServicing();
			}
		}
		servicing.incrementCount();
	}

	public static void step() {
		whichStep++;
		System.out.println(whichStep);
	}

	public static LineQueue<Passenger> makeInter() {
		LineQueue<Passenger> internationals = new LineQueue<Passenger>();
		double fillSeat;
		int numCoach = 0;
		int numFirst = 0;
		double[] data = new double[2];

		for (int i = 0; i < 150; i++) {
			fillSeat = generator.nextDouble();
			if (fillSeat <= .85) {
				numCoach++;
			}
		}
		for (int i = 0; i < 50; i++) {
			fillSeat = generator.nextDouble();
			if (fillSeat <= .8) {
				numFirst++;
			}
		}
		ArrayList<Passenger> interFlight = new ArrayList<Passenger>();

		// Passenger[] interFlight = new Passenger[numCoach+numFirst];
		for (int i = 0; i < numCoach; i += 2) {
			data = polarCoordinate(75 * 60, 50 * 60 * 60);
			Passenger p;
			if (numCoach - i == 1) {
				numberInternationalsMade++;

				p = new Passenger(false, true, data[0]);
				if (data[0] >= 5400) {
					p.arrivedOnTime();
				}
				p.setFlightLeavingTime(timeElapsed + (21600 - p.arrival()));
				interFlight.add(p);
			} else {
				p = new Passenger(false, true, data[0]);
				numberInternationalsMade += 2;
				if (data[0] >= 5400) {
					p.arrivedOnTime();
				}
				p.setFlightLeavingTime(timeElapsed + (21600 - p.arrival()));
				interFlight.add(p);
				p = new Passenger(false, true, data[1]);
				if (data[1] >= 5400) {
					p.arrivedOnTime();
				}
				p.setFlightLeavingTime(timeElapsed + (21600 - p.arrival()));
				interFlight.add(p);
			}
		} // end of for

		for (int i = 0; i < numFirst; i += 2) {
			Passenger p;
			data = polarCoordinate(75 * 60, 50 * 60 * 60);
			if (numFirst - i == 1) {
				numberInternationalsMade++;
				p = new Passenger(true, true, data[0]);
				if (data[0] >= 5400) {
					p.arrivedOnTime();
				}
				p.setFlightLeavingTime(timeElapsed + (21600 - p.arrival()));
				interFlight.add(p);
			} else {
				p = new Passenger(true, true, data[0]);
				numberInternationalsMade += 2;
				if (data[0] >= 5400) {
					p.arrivedOnTime();
				}
				p.setFlightLeavingTime(timeElapsed + (21600 - p.arrival()));
				interFlight.add(p);
				p = new Passenger(true, true, data[1]);
				if (data[1] >= 5400) {
					p.arrivedOnTime();
				}
				p.setFlightLeavingTime(timeElapsed + (21600 - p.arrival()));
				interFlight.add(p);
			}

		} // end of for
		Collections.sort(interFlight);
		for (int i = 0; i < interFlight.size(); i++) {
			internationals.enqueue(interFlight.get(i));
		}
		return internationals;
	}

	/*
	 * Generates a queue of passengers for the next hour
	 *
	 */
	public static LineQueue<Passenger> makeCommuter() {
		LineQueue<Passenger> commuters = new LineQueue<Passenger>();
		double t = 0;
		double a;
		while (t < 3600) {
			a = genExp(90);
			if (a < 1) {
				a = 1;
			}
			t += a;
			if (t < 3600) {
				numberCommutersMade++;
				Passenger passenger = new Passenger(false, false, t);
				commuters.enqueue(passenger);
			}
		}
		return commuters;
	}

	/*
	 * Generate numbers required for statistics
	 */
	public static double genExp(double a) {
		double x = 1 - generator.nextDouble();
		double exp = -1 * Math.log(x) * a;
		return exp;

	}
	/*
	 * Generate polar coordinates
	 */

	public static double[] polarCoordinate(double mean, double variance) {
		double data[] = new double[2];
		double U1 = 0;
		double U2 = 0;
		double v1;
		double v2;
		double U;
		while (U1 == 0 || U2 == 0) {
			v1 = (generator.nextDouble() * 2) - 1;
			v2 = (generator.nextDouble() * 2) - 1;
			U = Math.pow(v1, 2) + Math.pow(v2, 2);
			if (U < 1) {
				U1 = v1 * Math.pow((-2 * Math.log(U) / U), .5);
				U2 = v2 * Math.pow((-2 * Math.log(U) / U), .5);
				U1 = (U1 * Math.sqrt(variance)) + mean;
				U2 = (U2 * Math.sqrt(variance)) + mean;
				data[0] = U1;
				data[1] = U2;

			}
		}
		return data;
	}

}


import java.util.Random;
public class Simulation2 {
    private static Random gen;//random generator
    private static int numDeques;//the number of Deques
    private static int numVacc;//the number of vaccines available
    private static int numPeople;//the number of people who show up
    private static Deque<Integer>[] deques;//the deques
    private static int time;//counter to simulate time passing
    private static int[] vaccByRank;//keeps track of the number of people vaccinated by their ranking
    private static int[] totByRank;//keeps track of the number of people who show up by their ranking
    private static int vaccCount;//counter for people who are vaccinated
    private static int[] inDeque;//keeps track of the number of people added to each deque
    private static int[] totVacc;//keeps track of the total number of vaccines available in each line
    private static int count;//total number of people processed
    
    public static void main(String[] args) {
		init_vars(args);
		dist_vacc();

		/***
		 *the simulation: 
		 *1. the simulation runs until all the vaccines are used up
		 *2. each time through the loop, time is increased by 1
		 *3. each time through, we assume a new person shows up with 75% probability
		 *4. each time through, we assume a person is vaccinated in each line with 25% probability
		 ***/
		while(count < numPeople || vaccCount < numVacc) {
		    time++;
		    if(count < numPeople) {
				int x = gen.nextInt(4);
				//new person shows up with 75% probability
				if(x != 0) {
				    newPerson();
				}
		    }
		    if(vaccCount < numVacc) {
				//each Deque gets through one person with 25% probability
				for(int i = 0; i < deques.length; i++) {
				    int x = gen.nextInt(4);
				    if(x == 0)
					next(i);
				}
		    }
		    //set a time limit in case numPeople < numVacc
		    if(time == numVacc*10)
				break;
		}

		//print results
		System.out.println("Number of people processed: " + count);
		System.out.println("Number of people vaccinated: " + vaccCount);
		System.out.println("Total number of vaccines: " + numVacc);
		System.out.println("Total time: " + time);
		System.out.println("Statistics by Risk:");
		for(int i = 0; i < 3; i++)
		    printRiskStats(i);
    }

    //initialize the variables
    private static void init_vars(String[] args) {
		numDeques = 2;
		numVacc = Integer.parseInt(args[1]);
		gen = new Random(System.currentTimeMillis());
		deques = new Deque[numDeques];
		numPeople = gen.nextInt(1000);
		for(int i = 0; i < deques.length; i++)
		    deques[i] = new Deque<Integer>();
		time = 0;
		vaccByRank = new int[3];
		totByRank = new int[3];
		vaccCount = 0;
		inDeque = new int[numDeques];
		totVacc = new int[numDeques];
		count = 0;
    }

    //distribute the vaccines among the deques;
    //this method is to distribute them by their
    //priority among all the deques, giving
    //any extras to the first deque
    private static void dist_vacc() {
    	int highRisk = (int)(numVacc*(80/100.0f));
    	int lowRisk = (int) (numVacc*(20/100.0f));
    	
		totVacc[0] = highRisk;
		totVacc[1] = lowRisk;
    }

    //a new person is represented by a numerical ranking
    //1: high-risk
    //2: medium-risk
    //3: low-risk
    private static void newPerson() {
		int risk = gen.nextInt(3) + 1;//risk assigned with uniform probability
		totByRank[risk-1]++; // add int to the index of their ranking
		count++; // increase total number of people processed. 
		
		//if there are no more vaccines, just count the person
		if(vaccCount >= numVacc) 
		    return;
		
		// Input people depending on their risk. 
		if (risk == 0) {
			deques[0].addToFront(risk);
			inDeque[0]++;
		} else if (risk == 1) {
			deques[0].addToBack(risk);
			inDeque[0]++;
		} else if (risk == 2) {
			deques[1].addToBack(risk);
			inDeque[1]++;
		}
    }

    //process the next person in deque i
    private static void next(int i) {
		if(vaccCount >= numVacc)
		    return;
		int risk = 0;
		try {
		    risk = deques[i].getFirst();
		    vaccByRank[risk - 1]++;
		    vaccCount++;
		} catch (EmptyDequeException e) {
		}
    }

    //print risk stats
    private static void printRiskStats(int i) {
		System.out.println("\tRisk Level: " + (i+1));
		System.out.println("\tTotal: " + totByRank[i]);
		System.out.println("\tTotal Vaccinated: " + vaccByRank[i]);
		System.out.println("\tPercentange Vaccinated: " + (double)vaccByRank[i]/totByRank[i]*100.0);
    }
}

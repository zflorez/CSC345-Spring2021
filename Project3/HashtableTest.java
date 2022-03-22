import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class HashtableTest {
    private static Hashtable table;
    public static void main(String[] args) {
	table = new Hashtable();
	String fn = args[0] + "_input" + Integer.parseInt(args[1]) + ".txt";
	BufferedReader br;
	try {
	    br = new BufferedReader(new FileReader(fn));
	    String cmd = br.readLine();
	    while(cmd != null) {
		processCmd(cmd);
		cmd = br.readLine();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private static void processCmd(String cmd) {
	String[] split = cmd.split(" ");
	if(split[0].equals("put")) { 
	    table.put(split[1], Integer.parseInt(split[2]));
	}
	else if(split[0].equals("get")) {
	    System.out.println(table.get(split[1]));
	} else if(split[0].equals("delete")) {
	    System.out.println(table.delete(split[1]));
	} else if(split[0].equals("contains")) {
	    System.out.println(table.contains(split[1]));
	} else if(split[0].equals("isEmpty")) {
	    System.out.println(table.isEmpty());
	} else if(split[0].equals("size")) {
	    System.out.println(table.size());
	}
    }
}

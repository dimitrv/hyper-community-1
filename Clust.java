// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !! Author: Nikos Koulouris            !!
// !! Email: nk.nikoskoulouris@gmail.com !!
// !! Date 15/10/2014                    !!
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;


public class Clust {
	static int n1=0;
	static int n2=0;
	static int n3=0;
	
	//  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//  !! Change to file destination of input file !!
	//  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	static String fileDestination = "C:\\Users\\workspace\\test1.txt";
	
	
	
	public static void main(String[] args) throws IOException {
		int result = 0;
		float Q;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileDestination));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//precalaculate n1, n2, n3
		precalculate(reader);
		reader.close();
		
		
		//reread file
		reader = new BufferedReader(new FileReader(fileDestination));
		Graph T = new Graph(reader, n1, n2, n3);	
		
		do {
			T.updateLabels();
		} while(T.buildReducedGraphAdnContinue());
		
		System.out.println("Final Labels:");
		T.printlabels();

	}

	private static  void precalculate(BufferedReader r) throws IOException {
		//does all the precalculations, counting nodes in each partite
		String line = null;
		line = r.readLine();
		HashSet part1 = new HashSet();
		HashSet part2 = new HashSet();
		HashSet part3 = new HashSet();
		
		while ((line = r.readLine()) != null) {
			String[] parts = line.split("\\t");
			part1.add(parts[0]);
			part2.add(parts[1]);
			part3.add(parts[2]);
			
		}
		n1 = part1.size();
		n2 = part2.size();
		n3 = part3.size();
	}
}

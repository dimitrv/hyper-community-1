// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !! Author: Nikos Koulouris            !!
// !! Email: nk.nikoskoulouris@gmail.com !!
// !! Date 15/10/2014                    !!
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.lang.Math;

import org.apache.commons.math3.util.*;
import org.apache.commons.math3.util.MultidimensionalCounter.Iterator;





public class Graph {
	public int i, j, k, n_total, count1=0, count2=0;
	public int m = 0; 						//number of hyperedges
	public int[] n = new int[3];		//number of nodes in k-th node set
	public int[] c = new int[3];		//number of communities in each k-th node set
	public ArrayList<ArrayList<Integer>> S = new ArrayList<ArrayList<Integer>>(3);		//S[k] indicates community membership of i-kth node
	public ArrayList<ArrayList<Integer>> V = new ArrayList<ArrayList<Integer>>(3);		//V[k] the k-th node set
	public ArrayList<ArrayList<Integer>> n_com = new ArrayList<ArrayList<Integer>>(3);		//the number of nodes in the a_i community of the k-th node set
	public ArrayList<ArrayList<ArrayList>> connectedWithK = new ArrayList<ArrayList<ArrayList>>(3);		//struct used to faster calculate with chich nodes each node is connected
	public ArrayList<ArrayList> belongToCommunity = new ArrayList<ArrayList>(3);

	
	public Graph (BufferedReader r, int n1, int n2, int n3) throws IOException {
		this.n[0] = n1;
		this.c[0] = n1;
		this.n[1] = n2;
		this.c[1] = n2;
		this.n[2] = n3;
		this.c[2] = n3;
		V.add(new ArrayList<Integer>());
		V.add(new ArrayList<Integer>());
		V.add(new ArrayList<Integer>());
		
		S.add(new ArrayList<Integer>());
		S.add(new ArrayList<Integer>());
		S.add(new ArrayList<Integer>());

		n_com.add(new ArrayList<Integer>());
		n_com.add(new ArrayList<Integer>());
		n_com.add(new ArrayList<Integer>());
		
		
		connectedWithK.add(new ArrayList<ArrayList>());
		connectedWithK.add(new ArrayList<ArrayList>());
		connectedWithK.add(new ArrayList<ArrayList>());
		
		belongToCommunity.add(new ArrayList());
		belongToCommunity.add(new ArrayList());
		belongToCommunity.add(new ArrayList());
		
		
		for (int nn1=0; nn1 < n1; nn1++) {
			connectedWithK.get(0).add(new ArrayList());
		}
		
		for (int nn2=0; nn2 < n2; nn2++) {
			connectedWithK.get(1).add(new ArrayList());
		}
		
		for (int nn3=0; nn3 < n3; nn3++) {
			connectedWithK.get(2).add(new ArrayList());
		}
		
		

		this.n_total = n1 + n2 + n3;
		
		
		String line = null;
		line = r.readLine();
		while ((line = r.readLine()) != null) {
		    m++;
			String[] parts = line.split("\\t");
		    this.i = Integer.parseInt(parts[0]);
		    if (!V.get(0).contains(i)) {
		    	V.get(0).add(i);
		    	S.get(0).add(i);
		    	n_com.get(0).add(1);
		    	belongToCommunity.get(0).add( new ArrayList(Arrays.asList(V.get(0).indexOf(i))));
		    }
		    
		    this.j = Integer.parseInt(parts[1]);
		    if (!V.get(1).contains(j)) {
		    	V.get(1).add(j);
		    	S.get(1).add(j);
		    	n_com.get(1).add(1);
		    	belongToCommunity.get(1).add( new ArrayList(Arrays.asList(V.get(1).indexOf(j))));
		    }
		    
		    this.k = Integer.parseInt(parts[2]);
		    if (!V.get(2).contains(k)) {
		    	V.get(2).add(k);
		    	S.get(2).add(k);
		    	n_com.get(2).add(1);
		    	belongToCommunity.get(2).add( new ArrayList(Arrays.asList(V.get(2).indexOf(k))));
		    }
		    
		    int ii, jj, kk;
		    ii = V.get(0).indexOf(i);
		    jj= V.get(1).indexOf(j);
		    kk = V.get(2).indexOf(k);
		    

		    connectedWithK.get(0).get(ii).add(Integer.toString(jj)+ "_" + Integer.toString(kk));
		    connectedWithK.get(1).get(jj).add(Integer.toString(ii)+ "_" + Integer.toString(kk));
			connectedWithK.get(2).get(kk).add(Integer.toString(ii)+ "_" + Integer.toString(jj));		    
		    
		}
		
		System.out.println("Initial labels");
		System.out.println(S.get(0));
    	System.out.println(S.get(1));
	    System.out.println(S.get(2));
		System.out.println();

	}
	
	
	
	//Used to build the reduced graph from struct r
	public Graph (HashSet<ArrayList<Integer>> r, int n1, int n2, int n3) {
		this.n[0] = n1;
		this.c[0] = n1;
		this.n[1] = n2;
		this.c[1] = n2;
		this.n[2] = n3;
		this.c[2] = n3;
		V.add(new ArrayList<Integer>());
		V.add(new ArrayList<Integer>());
		V.add(new ArrayList<Integer>());
		
		S.add(new ArrayList<Integer>());
		S.add(new ArrayList<Integer>());
		S.add(new ArrayList<Integer>());

		n_com.add(new ArrayList<Integer>());
		n_com.add(new ArrayList<Integer>());
		n_com.add(new ArrayList<Integer>());
		
		
		connectedWithK.add(new ArrayList<ArrayList>());
		connectedWithK.add(new ArrayList<ArrayList>());
		connectedWithK.add(new ArrayList<ArrayList>());
		
		belongToCommunity.add(new ArrayList());
		belongToCommunity.add(new ArrayList());
		belongToCommunity.add(new ArrayList());
		
		
		for (int nn1=0; nn1 < n1; nn1++) {
			connectedWithK.get(0).add(new ArrayList());
		}
		
		for (int nn2=0; nn2 < n2; nn2++) {
			connectedWithK.get(1).add(new ArrayList());
		}
		
		for (int nn3=0; nn3 < n3; nn3++) {
			connectedWithK.get(2).add(new ArrayList());
		}
		
		

		this.n_total = n1 + n2 + n3;
		
            java.util.Iterator<ArrayList<Integer>> itr = r.iterator();
			while (itr.hasNext()) {
		    m++;
		    ArrayList tmp = itr.next();
		    this.i = (Integer) tmp.get(0);
		    if (!V.get(0).contains(i)) {
		    	V.get(0).add(i);
		    	S.get(0).add(i);
		    	n_com.get(0).add(1);
		    	belongToCommunity.get(0).add( new ArrayList(Arrays.asList(V.get(0).indexOf(i))));
		    }
		    
		    this.j = (Integer) tmp.get(1);
		    if (!V.get(1).contains(j)) {
		    	V.get(1).add(j);
		    	S.get(1).add(j);
		    	n_com.get(1).add(1);
		    	belongToCommunity.get(1).add( new ArrayList(Arrays.asList(V.get(1).indexOf(j))));
		    }
		    
		    this.k = (Integer) tmp.get(2);
		    if (!V.get(2).contains(k)) {
		    	V.get(2).add(k);
		    	S.get(2).add(k);
		    	n_com.get(2).add(1);
		    	belongToCommunity.get(2).add( new ArrayList(Arrays.asList(V.get(2).indexOf(k))));
		    }
		    
		    int ii, jj, kk;
		    ii = V.get(0).indexOf(i);
		    jj= V.get(1).indexOf(j);
		    kk = V.get(2).indexOf(k);
		    

		    connectedWithK.get(0).get(ii).add(Integer.toString(jj)+ "_" + Integer.toString(kk));
		    connectedWithK.get(1).get(jj).add(Integer.toString(ii)+ "_" + Integer.toString(kk));
			connectedWithK.get(2).get(kk).add(Integer.toString(ii)+ "_" + Integer.toString(jj));		    
		    
		}
		
	}
	
	
	
	public float caclulate_psi(int i, int label, int curr) {		
	//calculate psi for node curr, for possible label label in the partite i	
		float res = 0;
		if ((n_com.get(i).get(label) == 1 && curr == label) || (n_com.get(i).get(label) == 0 && curr != label)) {
			res = (float) (n[i]*Math.log(c[i]+1) - n[i]*Math.log( c[i]));
			float t = 1;
			for (int k=0; k<3; k++) {
				if (k==i)
					continue;
				else
					t *= c[k]*Math.log(m+1);
			}
			return (res+t);
		}
		else
			return res;
	}
	


	public float caclulate_phi(int i, int label, int curr, int currNode, int[][] b1, HashMap tempHash0, HashMap tempHash1 ) {		//label = index of label
	//calculate phi for node curr, for possible label label in the partite i
		float res = 0;
		int a1, a2 , b2;
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);		
		list.add(1);
		list.add(2);	
		list.remove(i);
		int k0 = list.get(0), k1 = list.get(1);
		
		ArrayList<String> loop0 = new ArrayList<String>();
	
		
		
		List<Integer> tempList = (List<Integer>) belongToCommunity.get(i).get(label);
		for (Integer node: tempList) {
			ArrayList<String> tempLoop = connectedWithK.get(i).get(node);
			for (String s: tempLoop ) {
				String parts[] = s.split("_");
				int node1, node2;
				node1 = Integer.parseInt(parts[0]);
				node2 = Integer.parseInt(parts[1]);
				String toAdd;
				toAdd = tempHash0.get(S.get(k0).get(node1))+ "_" + tempHash1.get(S.get(k1).get(node2));
				if (!loop0.contains(toAdd))
					loop0.add(toAdd);				
			}
		}
	
		
		if (curr != label) {
			
			ArrayList<String> tempLoop = connectedWithK.get(i).get(currNode);
			for (String s: tempLoop ) {
				String parts[] = s.split("_");
				int node1, node2;
				node1 = Integer.parseInt(parts[0]);
				node2 = Integer.parseInt(parts[1]);
				String toAdd;
				toAdd = Integer.toString(V.get(k0).indexOf(S.get(k0).get(node1)))+ "_" + Integer.toString(V.get(k1).indexOf(S.get(k1).get(node2)));
				if (!loop0.contains(toAdd))
					loop0.add(toAdd);
				
			}
		}

		
		
		
		
		
		for (String ss: loop0) {
			String parts0[] = ss.split("_");
			int indexOfCommunity0, indexOfCommunity1, localb1;
			indexOfCommunity0 = Integer.parseInt(parts0[0]);
			indexOfCommunity1 = Integer.parseInt(parts0[1]);	
				b2 = 0;
				
				int comm1, comm2, commOfLabel;
				comm1 = V.get(k0).get(indexOfCommunity0);
				comm2 = V.get(k1).get(indexOfCommunity1);
				commOfLabel = V.get(i).get(label);
				int a = 1;
				a1 = n_com.get(i).get(label) + 1;
				a2 = n_com.get(i).get(label);
				if (curr == label) {
					a1--;
					a2--;
				}
				a *= n_com.get(k0).get(indexOfCommunity0) * n_com.get(k1).get(indexOfCommunity1);
				a1 *= a;
				a2 *= a;
			
			
			
				if (b1[indexOfCommunity0][indexOfCommunity1] == -1) {
					localb1 = 0;
					ArrayList<String> loop = connectedWithK.get(i).get(curr);
					for (String s: loop ) {
						String parts[] = s.split("_");
						int node1, node2;
						node1 = Integer.parseInt(parts[0]);
						node2 = Integer.parseInt(parts[1]);
						if (S.get(k0).get(node1) == comm1)
							if (S.get(k1).get(node2) == comm2) {
								localb1++;
							}
						
					}
					b1[indexOfCommunity0][indexOfCommunity1] = localb1;
				}
				else {
					localb1 = b1[indexOfCommunity0][indexOfCommunity1];
				}
				
				
				
				//calculate b2
				for (int node = 0; node < n[i]; node++) {
					if (node == currNode || S.get(i).get(node) != commOfLabel)
						continue;
					ArrayList<String> loop2 = connectedWithK.get(i).get(node);
					for (String s: loop2 ) {
						count1++;
						String parts[] = s.split("_");
						int node1, node2;
						node1 = Integer.parseInt(parts[0]);
						node2 = Integer.parseInt(parts[1]);
						if (S.get(k0).get(node1) == comm1)
							if (S.get(k1).get(node2) == comm2) {
								b2++;
								count2++;
							}
					}	
				}
					
				res += CombinatoricsUtils.binomialCoefficientLog(a1, localb1+b2);	
				if (a2 != 0)
					res -= CombinatoricsUtils.binomialCoefficientLog(a2, b2);	

			}			

		return res;
	}
	

	

	public void updateLabels() {
		float Q;
		//repeat until QtotalOld = QtotalNew
		int Qold=-1, Qnew=-1;
		do {
			Qold = Qnew;
			Qnew = 0;
			for (int k = 0; k < 3; k++) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(0);		
				list.add(1);
				list.add(2);	
				list.remove(k);
				int k0 = list.get(0), k1 = list.get(1);
				
				HashMap tempHash0 = new HashMap();
				HashMap tempHash1 = new HashMap();
				
				ArrayList<Integer> arrayList = V.get(k0);
				for (int l = 0; l < arrayList.size(); l++) {
					Integer community = arrayList.get(l);
					tempHash0.put(community, l);			
				}
				
				ArrayList<Integer> arrayList1 = V.get(k1);
				for (int l = 0; l < arrayList1.size(); l++) {
					Integer community = arrayList1.get(l);
					tempHash1.put(community, l);			
				}
				
				
				
				for (int node = 0; node < n[k]; node++) {
					
					int[][] b1 = new int[c[k0]][c[k1]];
					for (int a0=0; a0<c[k0]; a0++)
						for (int a1 = 0; a1<c[k1]; a1++)
							b1[a0][a1]= -1;
					
					
					float Qmin = Float.MAX_VALUE;
						int indexOfLabelOfMin = -1;
						int curr = V.get(k).indexOf(S.get(k).get(node));		//index of current community
						for (int indexOfCommunity = 0; indexOfCommunity < c[k]; indexOfCommunity++) {

							Q = caclulate_phi(k, indexOfCommunity, curr, node, b1, tempHash0, tempHash1) + caclulate_psi(k, indexOfCommunity, curr);
							
							if (Q < Qmin) {
								Qmin = Q;
								indexOfLabelOfMin = indexOfCommunity;
							}
						}
						
						Qnew+=Qmin;
						S.get(k).set(node, V.get(k).get(indexOfLabelOfMin));
						
						
						//update n_com
						int temp = n_com.get(k).get(curr);
						n_com.get(k).set(curr, temp - 1 );
						int temp2 = n_com.get(k).get(indexOfLabelOfMin);
						n_com.get(k).set(indexOfLabelOfMin, temp2+1);
						
						
						if (n_com.get(k).get(curr) == 0) {		//if a community is deleted
							//update c[k]
							c[k]--;
							
							//update V
							V.get(k).remove(curr);
							n_com.get(k).remove(curr);
							
							//remove curr
							List tempList = (List) belongToCommunity.get(k).get(indexOfLabelOfMin);
							tempList.add(((List)belongToCommunity.get(k).get(curr)).get(0));
							belongToCommunity.get(k).set(indexOfLabelOfMin, tempList);
							belongToCommunity.get(k).remove(curr);
						
						}
							
					}
				}
		} while (Qold == Qnew);
	}


	public void printlabels() {

		System.out.println(S.get(0));
		System.out.println(S.get(1));
		System.out.println(S.get(2));
		System.out.println();
	}




	public boolean buildReducedGraphAdnContinue() throws IOException {
		
		HashSet<ArrayList<Integer>> T = new HashSet<ArrayList<Integer>>();
		
		for (int node = 0; node < n[0]; node++) {
			ArrayList<String> tempLoop = connectedWithK.get(0).get(node);
			for (String s: tempLoop ) {
				String parts[] = s.split("_");
				int node1, node2;
				node1 = Integer.parseInt(parts[0]);
				node2 = Integer.parseInt(parts[1]);
				String toAdd;
				T.add(new ArrayList<Integer>(Arrays.asList(S.get(0).get(node), S.get(1).get(node1),S.get(2).get(node2))));			
			}
		
		}
		
		//Build Reduced Graph
		Graph S = new Graph(T, c[0], c[1], c[2]);
		ArrayList<ArrayList<Integer>> Scopy =  (ArrayList<ArrayList<Integer>>) S.S.clone();
		
		//Update Reduced Graph's labels
		S.updateLabels();
		boolean cont;
		
		//if nothing changed return false/ else true
		if (S.n[0]==S.c[0] && S.n[1]==S.c[1] && S.n[2]==S.c[2] ) {
			cont= false;
		}
		else {
			cont = true;
			
			//decompose reduced graph and update graph T

			for (int k =0; k < 3; k++) {
				if (S.S.get(k)!= Scopy.get(k)) {
					for (int ii=0; ii<Scopy.get(k).size(); ii++) {
						int oldCom = Scopy.get(k).get(ii);
						int newCom = S.S.get(k).get(ii);
						if (oldCom!=newCom) { //if the nodes changed
							int curr = V.get(k).indexOf(oldCom);
							int indexOfLabelOfMin = V.get(k).indexOf(newCom);
							
							for (int kk=0; kk < this.S.get(k).size(); kk++) { //iterate over normal graph
								if (this.S.get(k).get(kk) == oldCom) {
									this.S.get(k).set(kk, newCom);
								}	
							}
							
							//update c[k]
							c[k]--;
							
							//update V
							V.get(k).remove(curr);
							n_com.get(k).remove(curr);
							
							//remove curr
							List tempList = (List) belongToCommunity.get(k).get(indexOfLabelOfMin);
							tempList.add(((List)belongToCommunity.get(k).get(curr)).get(0));
							belongToCommunity.get(k).set(indexOfLabelOfMin, tempList);
							belongToCommunity.get(k).remove(curr);
						}
					}
				}
			}		
		}
		return cont; 
	}

	
}

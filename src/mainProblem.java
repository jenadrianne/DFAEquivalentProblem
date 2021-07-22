import java.util.*;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class mainProblem {

	public static Map <Integer, String> nfaMap = new HashMap<Integer, String>();
	public static List<Integer> arraySet = new ArrayList<Integer>();

	
	public static void main(String[] args) {
		// read the contents of the NFA table; 
				try {
					File file = new File("../DFAProcess/src/nfa.txt");
					File file1 = new File("../DFAProcess/src/nfa2.txt");
					File file2 = new File("../DFAProcess/src/nfa3.txt");
					File file3 = new File("../DFAProcess/src/nfa4.txt");
					
					
					mainProcess(file);
					mainProcess(file1);
					mainProcess(file2);
					mainProcess(file3);
				} catch (Exception ez) {
					ez.printStackTrace();
				}
	}
	
	public static void mainProcess(File file) {
		try {
			nfaMap = new HashMap<Integer, String>();
			arraySet = new ArrayList<Integer>();
			BufferedReader reader = new BufferedReader(new FileReader(file)); 
			String str = ""; 
			
			while((str = reader.readLine()) != null) {
				//split items into two columns; 
				String[] split = str.split(",");
				
				//prepare values for the map
				Integer col1 = Integer.parseInt(split[0]);
				String col2 = ""; 
				
				for(int x=1; x<split.length;x++) {
					col2 += split[x];
				}
				
				//add items to map 
				nfaMap.put(col1, col2);
				arraySet.add(col1);
			}
			
			// create a set  
			printSubsets();
			equivalent();
			
			System.out.println();
			System.out.println("================================================================");
			System.out.println();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	static void printSubsets()
    {
    	Object[] set = arraySet.toArray();
    	StringBuilder stringBuilder = new StringBuilder();
        int n = set.length;
        
        for (int i = 0; i < (1<<n); i++)
        {
            
            String strsubset = "{ "; 
            // Print current subset
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                	strsubset = strsubset + set[j] + ",";
                }
            }
            strsubset = strsubset.substring(0,strsubset.length()-1);
            strsubset += " },";
            stringBuilder.append(strsubset); 
        }
        
        System.out.println("DFA = {" + stringBuilder.substring(0, stringBuilder.length()-1)+ "}");
    }
	
	public static void equivalent() {
		for(Entry<Integer, String> entry: nfaMap.entrySet()) {
			System.out.println();
			System.out.print("E(" + entry.getKey()+ ") = { ");
			String value = entry.getValue(); 
			
			System.out.print(entry.getKey());
			
			if(value.equals("empty")) {
				System.out.print(" }");
				continue; 
			}
			
			for(int x=0; x<value.length(); x++) {
				String s = String.valueOf(value.charAt(x));
			
				if(s.matches("-?\\d+")){
					Integer keyvalue = Integer.parseInt(s); 
					System.out.print(", "+s);
					String item = nfaMap.get(keyvalue);
					equivalentitem(item);
				}
			}
			
			System.out.print(" }");
			System.lineSeparator();
		}
	}
	
	public static void equivalentitem(String item ) {	
		if(item.equals("empty")) {
			return; 
		}
		
		for(int x=0; x< item.length(); x++) {
			String s = String.valueOf(item.charAt(x));
			if(s.matches("-?\\d+")){
				System.out.print(", "+s);
				Integer keyvalue = Integer.parseInt(s); 
				String temp = nfaMap.get(keyvalue);
				equivalentitem(temp);
			}
		}
		
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(String.valueOf(s)); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(Exception e) {
	        return false;
	    }
	    return true;
	}
}

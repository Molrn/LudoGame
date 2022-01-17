import java.util.*;
import java.util.Map.*;

//	Class containing a map
//	key : String representing 2 dimensions coordinates
//	Value : integer representing the index of the corresponding case
public class BoardMapper {
	private Map<String,Integer> map;
	
//	Constructor : initialized every index of the 15*15 grid
	public BoardMapper() {
		map = new HashMap<String,Integer> ();
		for(int i = 0; i<5; i++) {
			map.put("0 "+i, -2);
			map.put(i+" 5", -2);
			map.put("5 "+(i+1), -2);
			map.put((i+1)+" 0", -2);
			
			map.put("9 "+i, -3);
			map.put((i+9)+" 5", -3);
			map.put("14 "+(i+1), -3);
			map.put((i+10)+" 0", -3);
			
			map.put("9 "+(i+9), -4);
			map.put((i+9)+" 14", -4);
			map.put("14 "+(i+10), -4);
			map.put((i+10)+" 9", -4);
			
			map.put("0 "+(i+9), -5);
			map.put(i+" 14", -5);
			map.put("5 "+(i+10), -5);
			map.put((i+1)+" 9", -5);
			
			map.put((i+1)+" 6", i);
			
		}
		for(int i = 0; i<6; i++) {
			map.put("6 "+i, 10-i);
			map.put("8 "+i, 12+i);
			map.put((i+9)+" 6", 18+i);
			map.put((i+9)+" 8", 30-i);
			map.put("6 "+(i+9), 43-i);
			map.put("8 "+(i+9), 31+i);
			map.put(i+" 8", 49-i);
			
			map.put((i+1)+" 7", 151+i);
			map.put("7 "+(i+1), 251+i);
			map.put((i+8)+" 7", 356-i);
			map.put("7 "+(i+8), 456-i);
		}
		
		map.put("7 0", 11);
		map.put("14 7", 24);
		map.put("7 14", 37);
		map.put("0 7", 50);
		map.put("0 6", 51);
		
		map.put(6+" "+6, 56);
		map.put(7+" "+7, 56);
		map.put(8+" "+8, 56);
		map.put(6+" "+8, 56);
		map.put(8+" "+6, 56);
		
		for(int i = 0; i<4; i++) {
			for(int j = 0; j<4; j++) { 
				map.put((i+1)+" "+(j+1), -10); 
				map.put((i+1)+" "+(j+10), -11);
				map.put((i+10)+" "+(j+1), -12); 
				map.put((i+10)+" "+(j+10), -13); 
			}
		}
	}
	
//	Getter
	public Map<String,Integer> getMap(){ return map; }
	
//	Returns a key from a value
	public  String getKey( Integer value) {
	    for (Entry<String, Integer> entry : map.entrySet()) {
	        if (entry.getValue().equals(value)) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
}

import java.util.HashSet;
import java.util.Set;

public class MapReader {

	public static String[] read(String s) {
		char[] chars = s.toCharArray();
		
		Set<String> names = new HashSet<>();
		StringBuffer buffer = new StringBuffer();
		
		for(char c : chars) {
			boolean flag = buffer.length() == 0 ? Character.isJavaIdentifierStart(c) : Character.isJavaIdentifierPart(c);
			if(flag && c != '\n' && c != '\r') {
				buffer.append(c);
			} else if(buffer.length() > 0) {
				names.add(buffer.toString());
				buffer.setLength(0);
			}
		}
		return names.toArray(new String[0]);
	}
	
}

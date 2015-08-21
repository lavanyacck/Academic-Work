import java.util.Enumeration;
import java.util.Vector;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ASTappl extends SimpleNode {
	public ASTappl(int id) {
		super(id);
	}

	public ASTappl(Prog3 p, int id) {
		super(p, id);
	}

	/* Added method */
 
	public String astToString() {
		return "(" + ((SimpleNode) jjtGetChild(0)).astToString() + " "
				+ ((SimpleNode) jjtGetChild(1)).astToString() + ")";
	}

	/* Added methods and field for Prog3 */	
	
	public Vector freeVars() {
		Vector v = new Vector(2,2);
		Vector temp = ((SimpleNode) jjtGetChild(0)).freeVars();	
		temp.remove("");
		v.addAll(temp);
		temp = ((SimpleNode) jjtGetChild(1)).freeVars();
		temp.remove("");
		 for (Object obj : temp) {
		        if (!v.contains(obj)) {
		        	v.add(obj);
		        }
		    }
		return v;
	}

}

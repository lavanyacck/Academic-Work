import java.util.Vector;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ASTadd extends SimpleNode {
	public ASTadd(int id) {
		super(id);
	}

	public ASTadd(Prog3 p, int id) {
		super(p, id);
	}

	/* Added method */

	public String astToString() {
		return "+";
	}

	/* Added methods and field for Prog3 */
	
	public Vector freeVars() {
		Vector v = new Vector(1, 1);
		v.add("");
		return v;
	}
	
	public SimpleNode substitute(String varName, SimpleNode expr) {
		return null;
	}
	
	/* public ASTadd(SimpleNode left, SimpleNode right) {
		super(Prog3TreeConstants.JJTADD);
		this.children = null;
		this.jjtAddChild(left, 0);
		this.jjtAddChild(right, 1);
	} */
}

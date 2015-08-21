import java.util.Vector;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ASTlamb extends SimpleNode {
	public ASTlamb(int id) {
		super(id);
	}

	public ASTlamb(Prog3 p, int id) {
		super(p, id);
	}

	/* Added method */

	public String astToString() {
		return "(L " + ((SimpleNode) jjtGetChild(0)).astToString() + " . "
				+ ((SimpleNode) jjtGetChild(1)).astToString() + ")";
	}

	/* Added methods and field for Prog3 */

	public Vector freeVars() {
		Vector v = new Vector(2, 2);
		Vector temp = ((SimpleNode) jjtGetChild(0)).freeVars();
		temp.remove("");
		Vector temp2 = ((SimpleNode) jjtGetChild(1)).freeVars();
		temp2.remove("");
		for (Object obj : temp2) {
			if (!temp.contains(obj)) {
				v.add(obj);
			}
		}
		return v;
	}

	public Vector boundVars() {
		Vector temp = ((SimpleNode) jjtGetChild(0)).freeVars();
		temp.remove("");
		return temp;
	}
}

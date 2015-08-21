import java.util.Vector;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ASTnum extends SimpleNode {
	public ASTnum(int id) {
		super(id);
	}

	public ASTnum(Prog3 p, int id) {
		super(p, id);
	}

	/* Added methods and field */

	public void setValue(String n) {
		value = Integer.parseInt(n);
	}

	public String toString() {
		return Integer.toString(value);
	}

	public String astToString() {
		return toString();
	}

	private int value;

	/* Added methods and field for Prog3 */
	
	public Vector freeVars() {
		Vector v = new Vector(1, 1);
		v.add("");
		return v;
	}
}

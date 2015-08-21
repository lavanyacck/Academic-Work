import java.util.Vector;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ASTvar extends SimpleNode {
	public ASTvar(int id) {
		super(id);
	}

	public ASTvar(Prog3 p, int id) {
		super(p, id);
	}

	/* Added methods and field */

	public void setName(String n) {
		name = n;
	}

	public String toString() {
		return name;
	}

	public String astToString() {
		return name;
	}

	private String name;

	/* Added methods and field for Prog3 */
	
	public Vector freeVars() {
		Vector v = new Vector(1, 1);
		v.add(name);
		return v;
	}
}

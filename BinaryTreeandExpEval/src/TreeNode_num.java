public class TreeNode_num extends TreeNode implements Node {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TreeNode_num(int i) {
		super(i);
	}

	double val;

	public TreeNode_num(double d) {
		super(NUM);
		this.children = null;
		val = d;
	}

	public void addChild(Node n, int i) {
		if (children == null) {
			children = new Node[i + 1];
		} else if (i >= children.length) {
			Node c[] = new Node[i + 1];
			System.arraycopy(children, 0, c, 0, children.length);
			children = c;
		}
		children[i] = n;
	}

	public double getGetdata() {
		return val;
	}

	public Node getChild(int i) {
		return children[i];
	}

	public int getNumChildren() {
		return (children == null) ? 0 : children.length;
	}

	public String toString() {
		return "" + val;
	}

	public String toString(String prefix) {
		return prefix + toString();
	}

	protected TreeNode clone() throws CloneNotSupportedException {
		return (TreeNode) super.clone();
	}
}

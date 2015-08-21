public class TreeNode_mul extends TreeNode implements Node {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TreeNode_mul(int i) {
		super(i);
	}

	public TreeNode_mul(TreeNode left, TreeNode right) {
		super(MUL);
		this.children = null;
		this.addChild(left, 0);
		this.addChild(right, 1);
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

	public Node getChild(int i) {
		return children[i];
	}

	public int getNumChildren() {
		return (children == null) ? 0 : children.length;
	}

	public String toString() {
		return "*";
	}

	public String toString(String prefix) {
		return prefix + toString();
	}

	protected TreeNode clone() throws CloneNotSupportedException {
		return (TreeNode) super.clone();
	}
}

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Stack;
import java.util.Vector;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SimpleNode implements Node, Serializable {
	protected Node parent;
	protected Node[] children;
	protected int id;
	protected Prog3 parser;

	private Stack<Character> stack;
	private String inputStr;
	private String outputStr;
	SimpleNode root;

	public SimpleNode(int i) {
		id = i;
	}

	public SimpleNode(Prog3 p, int i) {
		this(i);
		parser = p;
	}

	public void jjtOpen() {
	}

	public void jjtClose() {
	}

	public void jjtSetParent(Node n) {
		parent = n;
	}

	public Node jjtGetParent() {
		return parent;
	}

	public void jjtAddChild(Node n, int i) {
		if (children == null) {
			children = new Node[i + 1];
		} else if (i >= children.length) {
			Node c[] = new Node[i + 1];
			System.arraycopy(children, 0, c, 0, children.length);
			children = c;
		}
		children[i] = n;
	}

	public Node jjtGetChild(int i) {
		return children[i];
	}

	public int jjtGetNumChildren() {
		return (children == null) ? 0 : children.length;
	}

	/*
	 * You can override these two methods in subclasses of SimpleNode to
	 * customize the way the node appears when the tree is dumped. If your
	 * output uses more than one line you should override toString(String),
	 * otherwise overriding toString() is probably all you need to do.
	 */

	public String toString() {
		return Prog3TreeConstants.jjtNodeName[id];
	}

	public String toString(String prefix) {
		return prefix + toString();
	}

	/*
	 * Override this method if you want to customize how the node dumps out its
	 * children.
	 */

	public void dump(String prefix) {
		System.out.println(toString(prefix));
		if (children != null) {
			for (int i = 0; i < children.length; ++i) {
				SimpleNode n = (SimpleNode) children[i];
				if (n != null) {
					n.dump(prefix + " ");
				}
			}
		}
	}

	/* Added method */

	public String astToString() {
		return "";
	}

	/* Added methods and field for Prog3 */

	protected int counter = 0;

	public Vector freeVars() {
		return null;
	}

	public void dumpWithFreevars(String prefix) {
		System.out.println(toString(prefix) + "  " + freeVars());
		if (children != null) {
			for (int i = 0; i < children.length; ++i) {
				SimpleNode n = (SimpleNode) children[i];
				if (n != null) {
					n.dumpWithFreevars(prefix + " ");
				}
			}
		}
	}

	// clone

	public Object deepClone(Object object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream objOS = new ObjectOutputStream(baos);
			objOS.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public SimpleNode substitute(String varName, SimpleNode expr) {

		// if the node is lambda node, then check if the variable we
		// are substituting is bound
		if (this != null && this.id == 7 && this.freeVars().contains(varName)) {
			boolean found = false;
			String varn = "";
			Vector temp2 = expr.freeVars();
			Vector temp = ((ASTlamb) this).boundVars();
			for (Object obj : temp2) {
				if (temp.contains(obj)) {
					found = true;
					varn = obj.toString();
				}
			}
			if (found) {
				boolean flag = false;
				String replace = "";
				while (!flag) {
					replace = uniqueName();
					if (!((ASTlamb) this).boundVars().contains(replace)
							&& !replace.equals(varName)
							&& !this.freeVars().contains(replace)
							&& !expr.freeVars().contains(replace))
						flag = true;
				}
				ASTvar v = new ASTvar(4);
				v.setName(replace);
				this.alphaReduction(varn, (SimpleNode) (v));
			}
		}

		if (this != null && this.freeVars().contains(varName)) {
			if (this.id == 4 && this.toString().equals(varName)) {
				this.jjtGetParent()
						.jjtAddChild((SimpleNode) deepClone(expr), 1);
			}

			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					SimpleNode n = (SimpleNode) children[i];
					if (n != null && n.freeVars().contains(varName)) {
						if (n.id == 4 && n.toString().equals(varName)) {
							n.jjtGetParent().jjtAddChild(
									(SimpleNode) deepClone(expr), i);
						} else
							return n.substitute(varName, expr);
					}
				}
			}
		}

		return this;
	}

	public String uniqueName() {
		return "x" + (++counter);
	}

	public boolean eval() {
		if (this.id == 6) {
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					SimpleNode n = (SimpleNode) children[i];
					if (n != null) {
						// if application node have its left child as lambda
						// node means we found the beta redex
						if (i == 0 && n.id == 7) {
							((SimpleNode) n.jjtGetChild(1))
									.reduce(n.jjtGetChild(0).toString(),
											(SimpleNode) deepClone(this
													.jjtGetChild(1)));

							if (this == this.jjtGetParent().jjtGetChild(0)) {
								this.jjtGetParent()
										.jjtAddChild(
												(SimpleNode) deepClone(n
														.jjtGetChild(1)),
												0);
								this.jjtGetParent().jjtGetChild(0)
										.jjtSetParent(this.jjtGetParent());
							} else {
								this.jjtGetParent()
										.jjtAddChild(
												(SimpleNode) deepClone(n
														.jjtGetChild(1)),
												1);
								this.jjtGetParent().jjtGetChild(1)
										.jjtSetParent(this.jjtGetParent());
							}
							return true;
						} else
							if (n.eval())
								return true;
					}
				}
			}
		}

		else {
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					SimpleNode n = (SimpleNode) children[i];
					return n.eval();
				}
			}
		}
		return false;
	}

	public SimpleNode normalOrderEvaluate() {
		boolean found = true;
		while (found) {
			found = eval();
		}
		return deltaeval(this);
	}

	public void alphaReduction(String varName, SimpleNode expr) {
		if (this != null) {
			if (this.id == 4 && this.toString().equals(varName)) {
				if (this == this.jjtGetParent().jjtGetChild(0))
					this.jjtGetParent().jjtAddChild(
							(SimpleNode) deepClone(expr), 0);
				else
					this.jjtGetParent().jjtAddChild(
							(SimpleNode) deepClone(expr), 1);
			}
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					SimpleNode n = (SimpleNode) children[i];
					n.alphaReduction(varName, expr);
				}
			}
		}
	}

	public void reduce(String varName, SimpleNode expr) {
		if (this != null) {
			if (this.id == 4 && this.toString().equals(varName)) {
				if (this == this.jjtGetParent().jjtGetChild(0)) {
					this.jjtGetParent().jjtAddChild(
							(SimpleNode) deepClone(expr), 0);
					this.jjtGetParent().jjtGetChild(0)
							.jjtSetParent(this.jjtGetParent());
				} else {
					this.jjtGetParent().jjtAddChild(
							(SimpleNode) deepClone(expr), 1);
					this.jjtGetParent().jjtGetChild(1)
							.jjtSetParent(this.jjtGetParent());
				}
			}
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					SimpleNode n = (SimpleNode) children[i];
					n.reduce(varName, expr);
				}
			}
		}
	}

	// evaluation
	protected SimpleNode deltaeval(SimpleNode root1) {
		boolean found = true;
		
		while (found) {
			SimpleNode root = (SimpleNode) root1.jjtGetChild(0);
			found = false;
			if (root != null && root.id == 6) {
				while (root != null && root.children != null
						&& root.jjtGetNumChildren() > 1) {
					if (((SimpleNode) (root.jjtGetChild(1))).id != 5)
						root = (SimpleNode) root.jjtGetChild(1);
					else
						root = (SimpleNode) root.jjtGetChild(0);
				}

				root = (SimpleNode) root.jjtGetParent();
				int left = Integer.parseInt(root.jjtGetChild(1).toString());
				int oprId = ((SimpleNode) root.jjtGetChild(0)).id;
				root = (SimpleNode) root.jjtGetParent();
				int right = Integer.parseInt(root.jjtGetChild(1).toString());
				int value = 0;

				switch (oprId) {
				case 2:
					value = left + right;
					break;
				case 3:
					value = left * right;
					break;
				}

				ASTnum num = new ASTnum(5);
				num.setValue(value + "");

				// update the node
				if (root == root.jjtGetParent().jjtGetChild(0)) {
					root.jjtGetParent().jjtAddChild(
							(SimpleNode) deepClone(num), 0);
					root.jjtGetParent().jjtGetChild(0)
							.jjtSetParent(root.jjtGetParent());
				} else {
					root.jjtGetParent().jjtAddChild(
							(SimpleNode) deepClone(num), 1);
					root.jjtGetParent().jjtGetChild(1)
							.jjtSetParent(root.jjtGetParent());
				}
				found = true;
			}
		}
		return this;
	}

}

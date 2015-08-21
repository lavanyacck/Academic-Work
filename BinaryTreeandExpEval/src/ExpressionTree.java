import java.util.Stack;

public class ExpressionTree {

	private Stack<Character> stack;
	private String inputStr;
	private String outputStr;
	TreeNode root;

	ExpressionTree() {
		root = new TreeNode();
	}

	protected void buildTree(String exp) {
		Stack<TreeNode> s_Operand = new Stack<TreeNode>();
		String elements[] = exp.split(" ");

		for (int i = 0; i < elements.length; i++) {
			if (!(elements[i].isEmpty())) {
				if ((!(elements[i].contains("+")))
						&& (!(elements[i].contains("*")))) {
					// number
					TreeNode_num num = new TreeNode_num(
							Double.parseDouble((elements[i].trim())));
					num.id = TreeNode.NUM;
					num.children = null;
					s_Operand.push(num);
				} else {
					// character
					TreeNode operand2 = s_Operand.pop();
					TreeNode operand1 = s_Operand.pop();
					TreeNode newNode = null;

					if ((elements[i].trim()).equals("+")) {
						newNode = new TreeNode_add(operand1, operand2);
					} else {
						newNode = new TreeNode_mul(operand1, operand2);
					}

					s_Operand.push(newNode);
				}
			}
		}
		root = s_Operand.pop();
		root.dump(" ");
	}

	// to get the prefix notation
	protected String getPostfixExression(String input) {
		stack = new Stack<Character>();
		inputStr = input;
		outputStr = "";

		for (int j = 0; j < inputStr.length(); j++) {
			char ch = inputStr.charAt(j);
			switch (ch) {
			case '+':
				// case '+':
				gotOperand(ch, 1);
				break;
			case '*':
				// case '*':
				gotOperand(ch, 2);
				break;
			default:
				outputStr = outputStr + ch;
				break;
			}
		}
		while (!stack.isEmpty()) {
			outputStr = outputStr + " " + stack.pop();
		}
		return outputStr;
	}

	protected void gotOperand(char opThis, int prec1) {
		while (!stack.isEmpty()) {
			char opTop = (Character) stack.pop();

			int prec2 = 0;
			if (opTop == '+') {
				prec2 = 1;
			} else {
				prec2 = 2;
			}
			if (prec2 < prec1) {
				stack.push(opTop);
				break;
			} else
				outputStr = outputStr + " " + opTop;

		}
		stack.push(opThis);
	}

	// evaluation
	protected double eval(TreeNode root) {
		if (root != null) {
			if (!(root.id == 0)) {
				double val1 = eval((TreeNode) root.getChild(0));
				double val2 = eval((TreeNode) root.getChild(1));

				switch (root.id) {
				case 1:
					return val1 + val2;
				case 2:
					return val1 * val2;
				default:
					return 0;
				}
			} else
				return root.getGetdata();
		}
		return 0;
	}

	// clone
	protected TreeNode cloneTreeNode(TreeNode tn)
			throws CloneNotSupportedException {
		return (TreeNode) tn.deepClone(tn);
	}

	// double
	protected void doubleValue(TreeNode root) {
		if (root != null) {
			if (root.id == 0) {
				((TreeNode_num) root).val = root.getGetdata() * 2;
			} else {
				doubleValue((TreeNode) root.getChild(0));
				doubleValue((TreeNode) root.getChild(1));
			}
		}
	}
}

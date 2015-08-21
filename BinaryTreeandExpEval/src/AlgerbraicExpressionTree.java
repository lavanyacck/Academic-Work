import java.io.*;
import java.util.Scanner;

public class AlgerbraicExpressionTree {

	public static void main(String[] args) throws IOException,
			CloneNotSupportedException {

		String outputStr;
		Scanner scanner = null;
		ExpressionTree tree = new ExpressionTree();
		try {
			scanner = new Scanner(System.in);
			System.out
					.println(">>> Simplified Arithmetic Expression Evaluator <<<");
			System.out.print("Enter an Arithmetic Expression: \n");
			outputStr = tree.getPostfixExression(scanner.nextLine());

			// tree
			System.out.println("The Tree Representation :");
			tree.buildTree(outputStr);
			System.out.println("The Value :" + tree.eval(tree.root));

			// clone
			System.out.println("The Cloned Tree Representation :");
			TreeNode clonedTree = (TreeNode) tree.cloneTreeNode(tree.root);
			clonedTree.dump(" ");
			System.out.println("The Value :" + tree.eval(clonedTree));

			// doubled tree
			System.out.println("The Doubled Tree Representation :");
			tree.doubleValue(tree.root);
			tree.root.dump(" ");
			System.out.println("The Value :" + tree.eval(tree.root));

			// cloned tree reprint
			System.out.println("The Cloned Tree Representation Again :");
			clonedTree.dump(" ");
			System.out.println("The Value :" + tree.eval(clonedTree));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (scanner != null)
				scanner.close();
		}

	}

}

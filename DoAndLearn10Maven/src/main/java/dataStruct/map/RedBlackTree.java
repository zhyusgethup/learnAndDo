package dataStruct.map;

public class RedBlackTree {
	class Node {
		public static final int BLACK = 1;
		public static final int RED = 2;
		private int color;
		private Node parent;
		private Node leftChild;
		private Node rightChild;
		private int value;
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public boolean isRootNode() {
			return parent == null;
		}
 		public int getColor() {
			return color;
		}
		public void setColor(int color) {
			this.color = color;
		}
		public Node getParent() {
			return parent;
		}
		public void setParent(Node parent) {
			this.parent = parent;
		}
		public Node getLeftChild() {
			return leftChild;
		}
		
		private boolean canSetChild(Node child) {
			if(this.color == RED && child.getColor() == RED) {
				return false;
			}
			return true;
		}
		public boolean setLeftChild(Node leftChild) {
			if(canSetChild(leftChild)) {
				this.leftChild = leftChild;
				return true;
			}
			return false;
		}
		public Node getRightChild() {
			return rightChild;
		}
		public boolean setRightChild(Node rightChild) {
			if(canSetChild(rightChild)) {
				this.rightChild = rightChild;
				return true;
			}
			return false;
		}
		
	}
	
	private Node root;
	private int height;
	private int size;
	
}

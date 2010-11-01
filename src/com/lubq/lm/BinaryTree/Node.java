package com.lubq.lm.BinaryTree;

public class Node {
	private char key;
	private Node left, right;

	public Node(char key) {
		this(key, null, null);
	}

	public Node(char key, Node left, Node right) {
		this.key = key;
		this.left = left;
		this.right = right;
	}

	public char getKey() {
		return key;
	}

	public void setKey(char key) {
		this.key = key;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}
}
/**
 * An appallingly named binary search tree that outputs traversals in String form. The element with the lowest
 * value is stored on the left side, and if a node is added whose value is equal to another node, the new node
 * will be placed to the left of the previous node. When traversing the tree, the left side of a node is
 * considered before the right side.
 *
 * @author nyuen
 *
 * @param <E> the type of object to be stored by this tree.
 */
public class Treeee<E extends Comparable<E>> {

	/**
	 * The node at the "top" of the tree which all operations on the tree are based upon. No node points to this 
	 * node, and every node in the tree can be reached by traversing the nodes that this node points to.
	 */
	private knowed<E> root;

	/**
	 * Adds a node containing the specified element to the tree as a "leaf" (a node pointing to no other nodes).
	 * If the tree is empty, the new element will become the root.
	 * 
	 * @param data the element to be contained within the node
	 */
	public void add(E data){
		if (data != null){
			if (root == null)
			{
				root = new knowed<E>(data);
			}else{
				add(data, root);
			}
		}
	}

	private void add(E data, knowed<E> here){
		/* 
		 * In the event that the value of a new node is equal to a previous one, the new node will be placed
		 * to the left
		 */
		if (data.compareTo(here.data) <= 0) {
			if (here.left == null){
				here.left = new knowed<E>(data);
			}else{
				add(data, here.left);
			}
		}else{
			if (here.right == null){
				here.right = new knowed<E>(data);
			}else{
				add(data, here.right);
			}
		}
	}
	
	/**
	 * Removes a specified element from the tree. The left-most right node "below" the removed node
	 * (in the sub-tree stemming from node as root) becomes the new value of the removed node.
	 * 
	 * @param data the value of the element to be removed
	 * 
	 * @return <tt>true</tt> if the specified element was found and removed, and <tt>false</tt> otherwise.
	 */
	public boolean remove(E data){
		if (root != null && data != null){
			int diff = data.compareTo(root.data);
			// Removes element if found in root
			if (diff == 0){
				rootRemove();
				return true;
			}else{
				return remove(data, root);
			}
		}
		return false;
	}

	/**
	 * Finds a node that contains the specified element inside a sub-tree
	 * 
	 * @param data the element to be removed
	 * 
	 * @param here the root of the sub-tree
	 * 
	 * @return <tt>true</tt> if the element was found and removed, and <tt>false</tt> otherwise
	 */
	private boolean remove(E data, knowed<E> here){
		int diff = data.compareTo(here.data);
		// If the desired element's value is greater than the value within the node, look to the right
		if (diff > 0) {
			knowed<E> righty = here.right;
			if (righty != null){
				if (data.compareTo(righty.data) == 0){
					remove(here, false);
					return true;
				}
				remove(data, righty);
			}
		// Otherwise, look to the left
		} else {
			knowed<E> lefty = here.left;
			if (lefty != null){
				if (data.compareTo(lefty.data) == 0){
					remove(here, true);
					return true;
				}
				remove(data, lefty);
			}
		}
		// Will return false after the entire tree is searched without the element being found
		return false;
	}

	/**
	 * Removes the specified node from the tree and replaces the node's value with the left-most right node inside its sub-tree.
	 * The left-most right value's sub-tree will be appended onto the node's parent.
	 * 
	 * @param parent the "parent" node of the node to be removed that points to the node
	 * 
	 * @param isLeft <tt>true</tt> if the node to be removed is the node to the left of the parent, and <tt>false</tt> if 
	 * the node is to the right of the parent
	 */
	private void remove(knowed<E> parent, boolean isLeft){	
		// Child is the node to be removed
		knowed<E> child;
		if (isLeft){
			child = parent.left;
		}else{
			child = parent.right;
		}
		
		// Going left to find the left-most right
		if (child.left != null){
			knowed<E> current = child.left;
			knowed<E> righty = current.right;
			if (righty == null){
				knowed<E> lefter = current.left;
				child.data = current.data;
				child.left = lefter;		
			}else if (righty.right == null){
				child.data = righty.data;
				current.right = null;
			}else{
				// Goes down the right-side branch of the node to the left of child until the end of that branch is reached
				while (righty.right != null){
					current = righty;
					righty = current.right;
				}
				child.data = righty.data;
				current.right = righty.left;
			}
			
		// The node has a sub-tree on its right side but not on its left. The parent now points to the node's sub-tree.
		}else if (child.right != null){
			if (isLeft){
				parent.left = child.right;
			}else{
				parent.right = child.right;
			}
			
		// The node is a "leaf" without children
		}else{
			if (isLeft){
				parent.left = null;
			}else{
				parent.right = null;
			}
		}
	}

	/**
	 * Removes the root and replaces it with the left-most right node inside the tree.
	 */
	private void rootRemove(){
		// Goes left to find the left-most right
		if (root.left != null){
			knowed<E> current = root.left;
			knowed<E> righty = current.right;
			if (righty == null){
				root.data = current.data;
				root.left = null;
			}else if (righty.right == null){
				root.data = righty.data;
				current.right = null;
			}else{
				// Goes down the right-side branch of the node to the left of the root until the end of that branch is reached
				while (righty.right != null){
					current = righty;
					righty = current.right;
				}
				root.data = righty.data;
				current.right = righty.left;
			}
		/*
		 *  The root has a sub-tree on its right side but not on its left. The tree is replaced by the root's sub-tree to the
		 *  right.
		 */
		}else if (root.right != null){
			root = root.right;
			
		// The root is a "leaf" without children
		}else{
			root = null;
		}
	}
	
	/**
	 * Returns in String form an in-order traversal of the entire tree. Each element is separated by a single space.
	 * 
	 * @return the in-order traversal of the tree represented as a String
	 */
	public String inOrderTraversal(){
		return inOrderTraversal(root);
	}

	/**
	 * Returns in String form an in-order traversal of a sub-tree of the tree. Each element is separated by a single space.
	 * 
	 * @param here the root of the sub-tree
	 * 
	 * @return the in-order traversal of the sub-tree represented as a String
	 */
	public String inOrderTraversal(knowed<E> here){
		String rtn = "";
		if (here == null){
			return "";
		}
		rtn += inOrderTraversal(here.left);
		rtn += here.data + " ";
		rtn += inOrderTraversal(here.right);
		return rtn;
	}
	
	/**
	 * Returns in String form an pre-order traversal of the entire tree. Each element is separated by a single space.
	 * 
	 * @return the pre-order traversal of the tree represented as a String
	 */
	public String preOrderTraversal(){
		return preOrderTraversal(root);
	}

	/**
	 * Returns in String form an pre-order traversal of a sub-tree of the tree. Each element is separated by a single space.
	 * 
	 * @param here the root of the sub-tree
	 * 
	 * @return the pre-order traversal of the sub-tree represented as a String
	 */
	public String preOrderTraversal(knowed<E> here){
		String rtn = "";
		if (here == null){
			return "";
		}
		rtn += here.data + " ";
		rtn += preOrderTraversal(here.left);
		rtn += preOrderTraversal(here.right);
		return rtn;
	}
	
	/**
	 * Returns in String form an post-order traversal of the entire tree. Each element is separated by a single space.
	 * 
	 * @return the post-order traversal of the tree represented as a String
	 */
	public String postOrderTraversal(){
		return postOrderTraversal(root);
	}

	/**
	 * Returns in String form an post-order traversal of a sub-tree of the tree. Each element is separated by a single space.
	 * 
	 * @param here the root of the sub-tree
	 * 
	 * @return the post-order traversal of the sub-tree represented as a String
	 */
	public String postOrderTraversal(knowed<E> here){
		String rtn = "";
		if (here == null){
			return "";
		}
		rtn += postOrderTraversal(here.left);
		rtn += postOrderTraversal(here.right);
		rtn += here.data + " ";
		return rtn;
	}

	/**
	 * A node that has been deliberately misspelled because I feel like it.
	 * 
	 * @author nyuen
	 *
	 * @param <T> the type of object to be stored in the node
	 */
	private class knowed<T extends Comparable<T>> implements Comparable<knowed<T>>{
		public knowed<T> left;
		public knowed<T> right;
		public T data;

		public knowed(T data){
			this.data = data;
		}

		public int compareTo( knowed<T> other ) {
			return data.compareTo(other.data);
		}
	}
}

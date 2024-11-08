public class BSTree<T> {
    private class BSTNode<T> {
        public int key;
        public T data;
        public BSTNode<T> left, right;


        /**
         * Creates a new instance of BSTNode
         */

        public BSTNode(int k, T val) {

            key = k;
            data = val;
            left = right = null;

        }


        public BSTNode(int k, T val, BSTNode<T> l, BSTNode<T> r) {

            key = k;
            data = val;
            left = l;
            right = r;

        }
    }

    BSTNode<T> root, current;


    /**
     * Creates a new instance of BST
     */

    public void BST() {
        root = current = null;

    }


    public boolean empty() {
        return root == null;

    }


    public boolean full() {
        return false;

    }

    public T retrieve() {
        return current.data;
    }

    public boolean findkey(int tkey) {
        BSTNode<T> p = root, q = root;
        if (empty())
            return false;
        while (p != null) {
            q = p;
            if (p.key == tkey) {
                current = p;
                return true;
            } else if (tkey < p.key) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        current = q;
        return false;
    }

    public boolean insert(int k, T val) {
        BSTNode<T> p, q = current;

        if (findkey(k)) {

            current = q;  // findkey() modified current

            return false; // key already in the BST

        }


        p = new BSTNode<T>(k, val);

        if (empty()) {
            root = current = p;
            return true;

        } else {
            // current is pointing to parent of the new key
            if (k < current.key)
                current.left = p;
            else {
                current.right = p;
                current = p;
                return true;
            }
        }
        return false;

    }
}


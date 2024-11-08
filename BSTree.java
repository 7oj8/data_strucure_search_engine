public class BSTree<T> {
    private class BSTNode<T> {
        public String key;
        public T data;
        public BSTNode<T> left, right;



        /**
         * Creates a new instance of BSTNode
         */

        public BSTNode(String k, T val) {

            key = k;
            data = val;
            left = right = null;

        }


        public BSTNode(String k, T val, BSTNode<T> l, BSTNode<T> r) {

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

    public boolean findkey(String tkey) {
        BSTNode<T> p = root, q = root;
        if (empty())
            return false;
        while (p != null) {
            q = p;
            if (p.key.equals(tkey)) {
                current = p;
                return true;
            } else if (tkey.compareTo(p.key) < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        current = q;
        return false;
    }

    public boolean insert(String k, T val) {
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
            if (k.compareTo(current.key)< 0)
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


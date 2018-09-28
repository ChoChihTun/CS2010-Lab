import java.util.*;
import java.io.*;

// write your matric number here: A0154907Y
// write your name here: Cho Chih Tun
// write list of collaborators here: Referred to the BSTDemo file and lecture note
// year 2018 hash code: wrMQ8UMcPU5q7F4UPNhT (do NOT delete this line)

class PatientNames {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  // --------------------------------------------
<<<<<<< HEAD

=======
>>>>>>> Test
  private AVLTree maleTree;
  private AVLTree femaleTree;
  private HashMap<String, Integer> genderMap;

  // --------------------------------------------

  public PatientNames() {
    // Write necessary code during construction;
    //
    // write your answer here

    // --------------------------------------------
    maleTree = new AVLTree();
    femaleTree = new AVLTree();
    genderMap = new HashMap<>();
<<<<<<< HEAD


=======
>>>>>>> Test
    // --------------------------------------------
  }

  void AddPatient(String patientName, int gender) {
    // You have to insert the information (patientName, gender)
    // into your chosen data structure
    //
    // write your answer here

    // --------------------------------------------
<<<<<<< HEAD
    Patient newPatient = new Patient(patientName, gender);
    if (gender == 1) {
      maleTree.insert(newPatient);
      genderMap.put(patientName, 1);
    } else {
      femaleTree.insert(newPatient);
      genderMap.put(patientName, 2);
    }
=======
     Patient newPatient = new Patient(patientName, gender);
    if (gender == 1)
      maleTree.insert(newPatient);
    else 
      femaleTree.insert(newPatient);

    genderMap.put(patientName, gender);
    
>>>>>>> Test
    // --------------------------------------------
  }

  void RemovePatient(String patientName) {
    // You have to remove the patientName from your chosen data structure
    //
    // write your answer here

<<<<<<< HEAD
    // --------------------------------------------
    int gender = genderMap.get(patientName);
    if (gender == 1) {
      maleTree.delete(patientName);
    } else {
      femaleTree.delete(patientName);
    }
=======
    // --------------------------------------------'
    if (genderMap.get(patientName) == 1) 
      maleTree.delete(patientName);
    else
      femaleTree.delete(patientName);

    genderMap.remove(patientName);
>>>>>>> Test
    // --------------------------------------------
  }

  int Query(String START, String END, int gender) {
    int ans = 0;

    // You have to answer how many patient name starts
    // with prefix that is inside query interval [START..END)
    //
    // write your answer here

    // --------------------------------------------
<<<<<<< HEAD
    /*
    if (gender == 1) {
      ans = maleTree.countNames(START, END);
    } else if (gender == 2) {
      ans = femaleTree.countNames(START, END);
    } else {
      ans = maleTree.countNames(START, END) + femaleTree.countNames(START, END);
    }
    */
    
=======

>>>>>>> Test
    if (gender == 1) {
      ans = maleTree.countNames(START, END);
    } else if (gender == 2) {
      ans = femaleTree.countNames(START, END);
    } else {
      ans = maleTree.countNames(START, END) + femaleTree.countNames(START, END);
    }
    // --------------------------------------------

    return ans;
  }

  void run() throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    while (true) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      if (command == 0) // end of input
        break;
      else if (command == 1) // AddPatient
        AddPatient(st.nextToken(), Integer.parseInt(st.nextToken()));
      else if (command == 2) // RemovePatient
        RemovePatient(st.nextToken());
      else // if (command == 3) // Query
        pr.println(Query(st.nextToken(), // START
                         st.nextToken(), // END
                         Integer.parseInt(st.nextToken()))); // GENDER
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    PatientNames ps2 = new PatientNames();
    ps2.run();
  }
}

// Represents patient in the system
class Patient {
  public String name;
  public int gender;

  Patient(String name, int gender) {
    this.name = name;
    this.gender = gender;
  }
}

// Represents each vertex in the AVL tree.
class AVLTreeVertex {
  AVLTreeVertex(Patient patient) {
    key = patient;
    parent = left = right = null;
    height = 0;
<<<<<<< HEAD
    size = 0; 
=======
    size = 1;
>>>>>>> Test
  }
  public AVLTreeVertex parent, left, right;
  public Patient key;
  public int height;
  public int size; 
}

class AVLTree {
  private AVLTreeVertex root;

  public AVLTree() { root = null; }

  // overloaded recursive method to perform findMin
  private Patient findMin(AVLTreeVertex T) {
    if (T == null)
      throw new NoSuchElementException("AVL Tree is empty, no minimum");
    else if (T.left == null)
      return T.key; // this is the min
    else
      return findMin(T.left); // go to the left
  }

<<<<<<< HEAD
  // Searches for minimum key value in AVL tree
  public Patient findMin() {
    return findMin(root);
  }

  // overloaded recursive method to perform findMax
  private Patient findMax(AVLTreeVertex T) {
    if (T == null)
      throw new NoSuchElementException("AVL Tree is empty, no maximum");
    else if (T.right == null)
      return T.key; // this is the max
    else
      return findMax(T.right); // go to the right
  }

  // Searches for maximum key value in AVL tree
  public Patient findMax() {
    return findMax(root);
  }

=======
>>>>>>> Test
  // overloaded recursive method to find successor
  private Patient successor(AVLTreeVertex T) {
    if (T.right != null) // this subtree has right subtree
      return findMin(T.right); // the successor is the minimum of right subtree
    else {
      AVLTreeVertex par = T.parent;
      AVLTreeVertex curr = T;
      // if par is not root and curr is its right children
      while ((par != null) && (curr == par.right)) {
        curr = par; // continue moving up
        par = curr.parent;
      }
      return par == null ? null : par.key; // this is the successor of T
    }
  }

  // method called to remove an existing patient in AVL Tree
  public void delete(String name) {
    root = delete(root, name);
  }

// overloaded recursive method to perform deletion of an existing vertex in AVL Tree
  private AVLTreeVertex delete(AVLTreeVertex T, String v) {
    if (T == null) return T; // Cannot find the item to delete

    int comparison = T.key.name.compareTo(v);

    if (comparison < 0) {
      // Searches right
      T.right = delete(T.right, v);
    } else if (comparison > 0) {
      // Searches left
      T.left = delete(T.left, v);
    } else {
      // Vertex to be deleted
      if (T.left == null && T.right == null) {
        T = null;
      } else if (T.left == null && T.right != null) {
        T.right.parent = T.parent;
        T = T.right;
      } else if (T.left != null && T.right == null) {
        T.left.parent = T.parent;
        T = T.left;
      } else {
        // Has 2 children
        Patient successor = successor(T);
        T.key = successor;
        T.right = delete(T.right, successor.name); // delete old successor
      }
    }

    if (T != null) {
      T = update(T);
      return balanceAVL(T);
    }

    return T; //Updated Tree
  }

  // method called to insert a new key with value v into AVL Tree
  public void insert(Patient v) {
    root = insert(root, v);
    updateSize(root);
  }  
  
  // overloaded recursive method to perform insertion of new vertex into AVL Tree
  private AVLTreeVertex insert(AVLTreeVertex T, Patient v) {
    if (T == null)
      return new AVLTreeVertex(v); // insertion point is found

    if (T.key.name.compareTo(v.name) < 0) { // search to the right
      T.right = insert(T.right, v);
      T.right.parent = T;
    } else { // search to the left
      T.left = insert(T.left, v);
      T.left.parent = T;
    }

    T = update(T);
    T = balanceAVL(T);

    return T;
  }

  // Check for any violation to AVL with respect to vertex T
  private AVLTreeVertex balanceAVL(AVLTreeVertex T) {
<<<<<<< HEAD
    int balanceFactor = getBalanceFactor(T);
    if (balanceFactor < -1 || balanceFactor > 1) {
      return rebalance(T, balanceFactor);
    }

    return T;
  }

  // Use postOrder transversal to update size
  private void updateSize(AVLTreeVertex T) {
    if (T == null)
      return;

    updateSize(T.left);

    updateSize(T.right);

    if (T.right == null && T.left == null) {
      T.size = 1;
    } else if (T.right == null && T.left != null) {
      T.size = 2;
    } else if (T.right != null && T.left == null) {
      T.size = 2;
    } else {
      T.size = T.left.size + T.right.size + 1;
    }
  }

  private int getRank(AVLTreeVertex T) {
    if (T == null) 
      return 0;

    // right of root
    if (root.key.compareTo(T.key) < 0) {
      if (T.left == null) 
        return 1 + getRank(T.parent);
      else
        return T.left.size + 1 + getRank(T.parent);
    } else {
      // Left of root or root
      if (T.left == null)
        return 1;
      else
        return T.left.size + 1;
    }
  }

  // Fix one of the 4 possible case
  private AVLTreeVertex rebalance(AVLTreeVertex T, int balanceFactor) {

    if (balanceFactor >= 2) {
=======
    if (T == null) return T;

    int balanceFactor = getBalanceFactor(T);
    if (balanceFactor == 2) {
>>>>>>> Test
      // Left Right case
      if (getBalanceFactor(T.left) == -1) {
        T.left = rotateLeft(T.left); // Next rotation combined with left left case
      }

      // Left Left case
      return rotateRight(T);
    } else if (balanceFactor == -2) { // BalanceFactor = -2

      // Right left case
      if (getBalanceFactor(T.right) == 1) {
        T.right = rotateRight(T.right); // Next rotation combined with right right case
      }

      // Right right case
      return rotateLeft(T);
    } else 
      return T;
  }
  
  public AVLTreeVertex rotateLeft(AVLTreeVertex T){
    AVLTreeVertex w = T.right;
    w.parent = T.parent;
    T.parent = w;
    T.right = w.left;
    if (w.left != null) {
      w.left.parent = T;
    }
    w.left = T;

    // Update height and size
    T = update(T);
    w = update(w);

    return w;
}

  public AVLTreeVertex rotateRight(AVLTreeVertex T) {
    AVLTreeVertex w = T.left;
    w.parent = T.parent;
    T.parent = w;
    T.left = w.right;
    if (w.right != null) {
      w.right.parent = T;
    }
    w.right = T;

    // Update height and size
    T = update(T);
    w = update(w);

    return w;
  }

  public AVLTreeVertex update(AVLTreeVertex T) { // updates vertex height and size and returns it
    T.size = getSize(T.left) + getSize(T.right) + 1;
    T.height = Math.max(getHeight(T.left), getHeight(T.right)) + 1;
    return T;
  }

  // Get balance factor for vertex T
  private int getBalanceFactor(AVLTreeVertex T) {
    if (T == null) return 0;
    else
      return getHeight(T.left) - getHeight(T.right);
  }
  
  // get the height of vertex T
  private int getHeight(AVLTreeVertex T) {
<<<<<<< HEAD
    if (T == null) return -1;
    else return Math.max(getHeight(T.left), getHeight(T.right)) + 1;
  }

  private AVLTreeVertex getLastVertex(AVLTreeVertex T, String END) {
    // leaf vertex is reached but greater than END
    if (T.left == null && T.right == null && T.key.getName().compareTo(END) > 0) {
      return null;
    }

    // vertex's patient name is the largest name that is smaller than or equal to END
    if ((!(T.key.getName().compareTo(END) > 0) && T.right == null)
        || !(T.key.getName().compareTo(END) > 0) && T.right.key.getName().compareTo(END) > 0) {
          return T;
        }
    
    if (T.key.getName().compareTo(END) < 0) {
      return getLastVertex(T.right, END);
    } else {
      return getLastVertex(T.left, END);
    }
  }

  private AVLTreeVertex getFirstVertex(AVLTreeVertex T, String START) {
    // leaf vertex is reached but smaller than START
    if (T.left == null && T.right == null && T.key.getName().compareTo(START) < 0) {
      return null;
    }

    // vertex's patient name is the smallest name that is greater than or equal to
    // START
    if ((!(T.key.getName().compareTo(START) < 0) && T.left == null)
        || !(T.key.getName().compareTo(START) < 0) && T.left.key.getName().compareTo(START) < 0) {
      return T;
    }

    if (T.key.getName().compareTo(START) > 0) {
      return getFirstVertex(T.left, START);
    } else {
      return getFirstVertex(T.right, START);
    }
  }

  // public method called to perform inorder traversal to count names
  public int countNames(String START, String END) {
    if (root == null) {
      return 0;
    }

    AVLTreeVertex lastValidVertix = getLastVertex(root, END); // get last vertex within the interval
    AVLTreeVertex firstValidVertix = getFirstVertex(root, START); // get first vertex within the interval
    System.out.println("FIrst: " + firstValidVertix.key.getName());
    System.out.println("Last: " + lastValidVertix.key.getName());

    inorder();
    return getRank(lastValidVertix) - getRank(firstValidVertix);
    }


/*
  // overloaded method to perform inorder traversal to count names
  private int countNames(AVLTreeVertex T, String START, String END) {
    int count = 0;
    if (T == null)
      return 0;

    count += countNames(T.left, START, END); // recursively go to the left

    // If the current name is in the interval
    if ((T.key.getName().compareTo(START) > 0 || T.key.getName().compareTo(START) == 0)
        && T.key.getName().compareTo(END) < 0) {
      
        count++;
    }

    count += countNames(T.right, START, END); // recursively go to the right

    return count;
  }

  // public method called to perform inorder traversal to count names
  public int countNames(String START, String END) {
    return countNames(root, START, END);
=======
    if (T == null)
      return -1;
    else
      return T.height;
  } 
  
  // Gets size of vertex T
  public int getSize(AVLTreeVertex T) {
    if (T == null)
      return 0;
    return T.size;
  }

  // get the rank of name
  private int getRank(AVLTreeVertex T, String name) {
    if (T == null)
      return 1; 
    else if (T.key.name.equals(name))
      return getSize(T.left) + 1;
    else if (T.key.name.compareTo(name) < 0)
      return getSize(T.left) + 1 + getRank(T.right, name);
    else
      return getRank(T.left, name);
>>>>>>> Test
  }
  */

<<<<<<< HEAD
    // public method called to perform inorder traversal
  public void inorder() {
    inorder(root);
    System.out.println();
  }

  // overloaded method to perform inorder traversal
  protected void inorder(AVLTreeVertex T) {
    if (T == null)
      return;

    inorder(T.left); // recursively go to the left
    System.out.println("Name: " + T.key.getName() + "-> Size: " + T.size); // visit this BST node
    inorder(T.right); // recursively go to the right

    } 

  }
=======
  // Count number of names in the interval
  public int countNames(String START, String END) {
    if (root == null) {
      return 0;
    }
    
    return getRank(root, END) - getRank(root, START);
    }
}
>>>>>>> Test

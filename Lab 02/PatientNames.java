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

  private AVLTree T;

  //Patient class with name and gender
  // store patient name in AVL tree by the name lexico

  //Insert --> like AVL Tree then check for AVl property
  //Delete --> Like AVL Tree then check for AVL property


  // --------------------------------------------

  public PatientNames() {
    // Write necessary code during construction;
    //
    // write your answer here

    // --------------------------------------------
    T = new AVLTree();


    // --------------------------------------------
  }

  void AddPatient(String patientName, int gender) {
    // You have to insert the information (patientName, gender)
    // into your chosen data structure
    //
    // write your answer here

    // --------------------------------------------
    Patient newPatient = new Patient(patientName, gender);
    T.insert(newPatient);
    // --------------------------------------------
  }

  void RemovePatient(String patientName) {
    // You have to remove the patientName from your chosen data structure
    //
    // write your answer here

    // --------------------------------------------
    T.delete(patientName);

    // --------------------------------------------
  }

  int Query(String START, String END, int gender) {
    int ans = 0;

    // You have to answer how many patient name starts
    // with prefix that is inside query interval [START..END)
    //
    // write your answer here

    // --------------------------------------------
    ans = T.countNames(START, END, gender);

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
class Patient implements Comparable<Patient> {
  private String name;
  private int gender;

  Patient(String name, int gender) {
    this.name = name;
    this.gender = gender;
  }

  public String getName() {
    return name;
  }

  public int getGender() {
    return gender;
  }

  @Override
  public int compareTo(Patient key) {
    return this.name.compareTo(key.getName());
  }

  @Override
  public boolean equals(Object v) {
    if (this == v) {
      return true;
    }

    if (!(v instanceof Patient)) {
      return false;
    }

    Patient other = (Patient) v;

    if (this.name.equals(other.getName()) && this.gender == other.getGender()) {
      return true;
    }

    return false;
  }
}

// Represents each vertex in the AVL tree.
class AVLTreeVertex {
  AVLTreeVertex(Patient patient) {
    key = patient;
    parent = left = right = null;
    height = 0;
    size = 0; 
    maleCount = 0;
  }
  public AVLTreeVertex parent, left, right;
  public Patient key;
  public int height;
  public int size; // How many children I have, including me
  public int maleCount;
}

class AVLTree {
  private AVLTreeVertex root;

  public AVLTree() { root = null; }

  // method called to search for a value v
  public Patient search(Patient v) {
    AVLTreeVertex res = search(root, v);
    return res == null ? null : res.key;
  }

  // overloaded recursive method to perform search
  private AVLTreeVertex search(AVLTreeVertex T, Patient v) {
    if (T == null)
      return null; // not found
    else if (T.key.equals(v)) // USE EQUAL --> oVERRIDE
      return T; // found
    else if (T.key.compareTo(v) < 0)
      return search(T.right, v); // search to the right
    else
      return search(T.left, v); // search to the left
  }

  // overloaded recursive method to perform findMin
  private Patient findMin(AVLTreeVertex T) {
    if (T == null)
      throw new NoSuchElementException("AVL Tree is empty, no minimum");
    else if (T.left == null)
      return T.key; // this is the min
    else
      return findMin(T.left); // go to the left
  }

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

  // public method to find successor to given value v in AVL Tree
  public Patient successor(Patient v) {
    AVLTreeVertex vPos = search(root, v);
    return vPos == null ? null : successor(vPos);
  }

  // method called to remove an existing patient in AVL Tree
  public void delete(String name) {
    root = delete(root, name);
  }

  // overloaded recursive method to perform deletion of an existing vertex in AVL Tree
  private AVLTreeVertex delete(AVLTreeVertex T, String name) {
    if (T == null) return T; // Cannot find the item to delete

    if (T.key.getName().compareTo(name) < 0) {
      // Searches right
      T.right = delete(T.right, name);
    } else if (T.key.getName().compareTo(name) > 0) {
      // Searches left
      T.left = delete(T.left, name);
    } else {
      // Vertex to be deleted
      if (T.left == null && T.right == null) {
        AVLTreeVertex parent = T.parent;
        T = null;
        balanceAVL(parent);
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
        T.right = delete(T.right, successor.getName()); // delete old successor
      }
    }

    if (T != null)
      return balanceAVL(T);

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

    if (T.key.compareTo(v) < 0) { // search to the right
      T.right = insert(T.right, v);
      T.right.parent = T;
    } else { // search to the left
      T.left = insert(T.left, v);
      T.left.parent = T;
    }

    T.height = getHeight(T); // Update height of each node

    return balanceAVL(T);
  }

  // Check for any violation to AVL with respect to vertex T
  private AVLTreeVertex balanceAVL(AVLTreeVertex T) {
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
      if (T.key.getGender() == 1)
        T.maleCount = 1;
      else
        T.maleCount = 0;
    } else if (T.right == null && T.left != null) {
      T.size = 2;
      if (T.key.getGender() == 1 && T.left.key.getGender() == 1) {
        T.maleCount = 2;
      } else if ((T.key.getGender() == 1 && T.left.key.getGender() == 2) || (T.key.getGender() == 2 && T.left.key.getGender() == 1)) {
        T.maleCount = 1;
      } else {
        T.maleCount = 0;
      }
    } else if (T.right != null && T.left == null) {
      T.size = 2;
      if (T.key.getGender() == 1 && T.right.key.getGender() == 1) {
        T.maleCount = 2;
      } else if ((T.key.getGender() == 1 && T.right.key.getGender() == 2) || (T.key.getGender() == 2 && T.right.key.getGender() == 1)) {
        T.maleCount = 1;
      } else {
        T.maleCount = 0;
      }
    } else {
      T.size = T.left.size + T.right.size + 1;
      if (T.key.getGender() == 1)
        T.maleCount = T.left.maleCount + T.right.maleCount + 1;
      else
        T.maleCount = T.left.maleCount + T.right.maleCount;
    }
  }

  private int getRank(AVLTreeVertex T) {
    if (T == null) 
      return 0;

    // right of root
    if (root.key.compareTo(T.key) > 0) {
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
      // Left Right case
      if (getBalanceFactor(T.left) == -1) {
        T.left = rotateLeft(T.left); // Next rotation combined with left left case
      }

      // Left Left case
      return rotateRight(T);
    } else { // BalanceFactor = -2

      // Right left case
      if (getBalanceFactor(T.right) == 1) {
        T.right = rotateRight(T.right); // Next rotation combined with right right case
      }
    
      // Right right case
      return rotateLeft(T);
      }
    }

  private AVLTreeVertex rotateLeft(AVLTreeVertex T) {
    if (T.left == null)
      return T;

    AVLTreeVertex w = T.right;
    w.parent = T.parent;
    T.parent = w;
    T.right = w.left;
    if (w.left != null) {
      w.left.parent = T;
    }
    w.left = T;

    // Update height
    T.height = getHeight(T);
    w.height = getHeight(w);
    return w;
  }

  private AVLTreeVertex rotateRight(AVLTreeVertex T) {
    if (T.right == null)
      return T;

    AVLTreeVertex w = T.left;
    w.parent = T.parent;
    T.parent = w;
    T.left = w.right;
    if (w.right != null) {
      w.right.parent = T;
    }
    w.right = T;

    // Update height
    T.height = getHeight(T);
    w.height = getHeight(w);
    return w;
  }
  

  // Get balance factor for vertex T
  private int getBalanceFactor(AVLTreeVertex T) {
    if (T.left == null && T.right != null) {
      return 0 - T.right.height;
    } else if (T.left != null && T.right == null) {
      return T.left.height;
    } else if (T.left == null && T.right == null) {
      return 0;
    } else {
      return T.left.height - T.right.height;
    }
  }

  // Get height of vertex T
  private int getHeight(AVLTreeVertex T) {
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

    if (T.key.getName().compareTo(START) < 0) {
      return getFirstVertex(T.left, START);
    } else {
      return getFirstVertex(T.right, START);
    }
  }

  // public method called to perform inorder traversal to count names
  public int countNames(String START, String END, int gender) {
    AVLTreeVertex lastValidVertix = getLastVertex(root, END); // get last vertex within the interval
    AVLTreeVertex firstValidVertix = getFirstVertex(root, START); // get first vertex within the interval

    int totalCount = getRank(lastValidVertix) - getRank(firstValidVertix);

    // num of male
    if (gender == 1) {
      
    }
  }
}
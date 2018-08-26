import java.util.*;
import java.io.*;
import javafx.util.Pair;

// write your matric number here: A0154907Y
// write your name here: Cho Chih Tun
// write list of collaborators here: Referred to the BinaryHeapDemo.java
// year 2018 hash code: tPW3cEr39msnZUTL2L5J (do NOT delete this line)

class EmergencyRoom {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  private ArrayList<Patient> patientList;
  private int BinaryHeapSize;

  public EmergencyRoom() {
    // Write necessary code during construction
    //
    // write your answer here
    patientList = new ArrayList<>();
    Patient dummy = new Patient("DUMMY", 0, BinaryHeapSize);
    patientList.add(dummy);
    BinaryHeapSize = 0;
  }

  int parent(int i) { return i>>1; }  // shortcut for i/2, round down

  int left(int i) { return i<<1; } // shortcut for 2*i

  int right(int i) { return (i<<1) + 1; } // shortcut for 2*i + 1

  void swap(int i) {
    // Swap current node with its parent node
    Patient temp = patientList.get(i);
    patientList.set(i, patientList.get(parent(i)));
    patientList.set(parent(i), temp);
  }

  void shiftUp(int i) {
    while (i > 1 && patientList.get(parent(i)).getEmergencyLvl() <= patientList.get(i).getEmergencyLvl()) {
      boolean isSwap = true;
      // Compares the arrival time of patients with same emergency level
      if (patientList.get(parent(i)).getEmergencyLvl() == patientList.get(i).getEmergencyLvl() && patientList.get(i).getArrivalOrderNumber() > patientList.get(parent(i)).getArrivalOrderNumber()) {
        isSwap = false;
      }

      if (isSwap) {
        swap(i);
      }

      i = parent(i);
    }
  }


  void shiftDown(int i) {
    while (i <= BinaryHeapSize) {
      int maxV = patientList.get(i).getEmergencyLvl(), max_id = i;
      if (left(i) <= BinaryHeapSize) { // compare value of this node with its left subtree, if possible
        boolean isSwap = false;
        // If left child is of higher emergency level
        if (maxV < patientList.get(left(i)).getEmergencyLvl()) {
          isSwap = true;

        // If left child has same emergency level but arrives earlier
        } else if (maxV == patientList.get(left(i)).getEmergencyLvl() && patientList.get(left(i)).getArrivalOrderNumber() < patientList.get(i).getArrivalOrderNumber()) {
          isSwap = true;
        }

        if (isSwap) {
          maxV = patientList.get(left(i)).getEmergencyLvl();
          max_id = left(i);
        }
      }
      
      if (right(i) <= BinaryHeapSize) { // now compare with its right subtree, if possible
        boolean isSwap = false;
        // If right child is of higher emergency level
        if (maxV < patientList.get(right(i)).getEmergencyLvl()) {
          isSwap = true;

          // If right child has same emergency level but arrives earlier
        } else if (maxV == patientList.get(right(i)).getEmergencyLvl()
            && patientList.get(right(i)).getArrivalOrderNumber() < patientList.get(max_id).getArrivalOrderNumber()) {
          isSwap = true;
        }

        if (isSwap) {
          maxV = patientList.get(right(i)).getEmergencyLvl();
          max_id = right(i);
        }
      }

      if (max_id != i) {
        Patient temp = patientList.get(i);
        patientList.set(i, patientList.get(max_id));
        patientList.set(max_id, temp);
        i = max_id;
      } else
        break;
    }
  }

  void ExtractMax() {
    Patient patient = patientList.get(1);
    patientList.set(1, patientList.get(BinaryHeapSize));
    BinaryHeapSize--; // virtual decrease
    shiftDown(1);
    patientList.remove(patient);
  }

  void ArriveAtHospital(String patientName, int emergencyLvl) {
    // You have to insert the information (patientName, emergencyLvl)
    // into your chosen data structure
    //
    // write your answer here

    BinaryHeapSize++; // Update total number of patients

    // Creates a new patient
    Patient newPatient = new Patient(patientName, emergencyLvl, BinaryHeapSize);
    
    if (BinaryHeapSize >= patientList.size()) {
      patientList.add(newPatient);
    } else {
      patientList.set(BinaryHeapSize, newPatient);
    }

    // Fix any violation to max heap property
    shiftUp(BinaryHeapSize);
  }

  void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
    // You have to update the emergencyLvl of patientName to
    // emergencyLvl += incEmergencyLvl
    // and modify your chosen data structure (if needed)
    //
    // write your answer here
    // Searches for the patient
    for (int i = 1; i <= BinaryHeapSize; i++) {
      if (patientList.get(i).getName().equals(patientName)) {
        Patient patient = patientList.get(i); // patient to be updated
        patient.updateEmergencyLvl(incEmergencyLvl);

        // Fixes any violation to max heap property
        shiftUp(i);
        break; // Exits loop
      }
    }
  }

  void Treat(String patientName) {
    // This patientName is treated by the doctor
    // remove him/her from your chosen data structure
    //
    // write your answer here
    for (int i = 1; i <= BinaryHeapSize; i++) {
      if (patientList.get(i).getName().equals(patientName)) {
        Patient patient = patientList.get(i); // patient to be updated

        // Sets patient emergency level to 101 to override any existing level, including 100
        patient.setEmergencyLvl(101);

        // Fixes any violation to max heap property
        shiftUp(i);
        // Removes treated patient
        ExtractMax();
        break;
      }
    }
  }

  String Query() {
    String ans = "The emergency suite is empty";

    // You have to report the name of the patient that the doctor
    // has to give the most attention to currently. If there is no more patient to
    // be taken care of, return a String "The emergency suite is empty"
    //
    // write your answer here
    if (BinaryHeapSize > 0) {
      ans = patientList.get(1).getName();
    }

    System.out.println("================================================================");
    for (int i = 1; i <= BinaryHeapSize; i++) {
      System.out.println("Name: " + patientList.get(i).getName() + ". Arrival: "
          + patientList.get(i).getArrivalOrderNumber() + ". Level: " + patientList.get(i).getEmergencyLvl());
    }
    return ans;
  }

  void run() throws Exception {
    // do not alter this method

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
    while (numCMD-- > 0) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      switch (command) {
        case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 1: UpdateEmergencyLvl(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 2: Treat(st.nextToken()); break;
        case 3: pr.println(Query()); break;
      }
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    EmergencyRoom ps1 = new EmergencyRoom();
    ps1.run();
  }
}

// Contains details of the patient
class Patient {
  private int orderNumber;
  private String name;
  private int emergencyLvl;

  public Patient(String name, int emergencyLvl, int orderNumber) {
    this.orderNumber = orderNumber;
    this.name = name;
    this.emergencyLvl = emergencyLvl;
  }

  public void updateEmergencyLvl(int incEmergencyLvl) {
    this.emergencyLvl += incEmergencyLvl;
  }

  public void setEmergencyLvl(int emergencyLvl) {
    this.emergencyLvl = emergencyLvl;
  }

  public String getName() { return name; }

  public int getEmergencyLvl() { return emergencyLvl; }

  public int getArrivalOrderNumber() { return orderNumber; }
}
import java.util.*;
import java.io.*;

// write your matric number here: A0154907Y
// write your name here: Cho Chih Tun
// write list of collaborators here:
// year 2018 hash code: tPW3cEr39msnZUTL2L5J (do NOT delete this line)

class EmergencyRoom {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  private ArrayList<Pair<String, Integer>> patientList;
  private int BinaryHeapSize;

  public EmergencyRoom() {
    // Write necessary code during construction
    //
    // write your answer here
    patientList = new ArrayList<>();
    BinaryHeapSize = 0;
  }

  int parent(int i) { return i>>1; }  // shortcut for i/2, round down

  int left(int i) { return i<<1; } // shortcut for 2*i

  int right(int i) { return (i<<1) + 1; } // shortcut for 2*i + 1

  void shiftUp(int i) {
    while (i > 1 && patientList.get(parent(i)).getValue() < patientList.get(i).getValue()) {
      // Swap parent and child node
      Pair<String, Integer> temp = patientList.get(i);
      patientList.set(i, patientList.get(parent(i)));
      patientList.set(parent(i), temp);
      i = parent(i);
    }
  }

  void ArriveAtHospital(String patientName, int emergencyLvl) {
    // You have to insert the information (patientName, emergencyLvl)
    // into your chosen data structure
    //
    // write your answer here
    // Creates a new patient
    Pair<String, Integer> newPatient = new Pair<>(patientName, emergencyLvl);
    
    BinaryHeapSize++; // Update total number of patients
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



  }

  void Treat(String patientName) {
    // This patientName is treated by the doctor
    // remove him/her from your chosen data structure
    //
    // write your answer here



  }

  String Query() {
    String ans = "The emergency suite is empty";

    // You have to report the name of the patient that the doctor
    // has to give the most attention to currently. If there is no more patient to
    // be taken care of, return a String "The emergency suite is empty"
    //
    // write your answer here



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
import java.util.*;

public class Main {
    public static <StudentController> void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentControllerImpl controller = new StudentControllerImpl(); // Replace with a concrete implementation

        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();  // consume newline
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter Marks: ");
                    float marks = sc.nextFloat();
                    Student s = new Student();
                    controller.addStudent(s);
                    break;

                case 2:
                    List<Student> students = controller.getAllStudents();
                    System.out.println("\n--- Student List ---");
                    for (Student st : students) {
                        System.out.println("ID: " + st.getStudentID() +
                                ", Name: " + st.getName() +
                                ", Dept: " + st.getDepartment() +
                                ", Marks: " + st.getMarks());
                    }
                    break;

                case 3:
                    System.out.print("Enter Student ID to Update: ");
                    int updateID = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter New Department: ");
                    String newDept = sc.nextLine();
                    System.out.print("Enter New Marks: ");
                    float newMarks = sc.nextFloat();
                    Student updatedStudent = new Student();
                    controller.addStudent(updatedStudent);
                    break;

                case 4:
                    System.out.print("Enter Student ID to Delete: ");
                    int deleteID = sc.nextInt();
                    controller.addStudent(deleteID);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}

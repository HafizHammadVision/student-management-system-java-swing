///Student Management System V4///
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Color;

public class StudentManagementSystem {
    public static void main(String[] args){
        ArrayList<Student> students=new ArrayList<>();
        JFrame frame=new JFrame("Student Management System");
        frame.setLayout(new BorderLayout(10,10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,2,5,5));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2,3,5,5));

        JLabel l1=new JLabel("Student Name : ");
        JTextField t1=new JTextField();

        JLabel l2=new JLabel("Roll No : ");
        JTextField t2=new JTextField();

        JButton ad=new JButton("Add Student");
        JButton view=new JButton("View Students");
        JButton search=new JButton("Search Student");
        JButton update=new JButton("Update Student");
        JButton delete=new JButton("Delete Student");

        JTextArea a1=new JTextArea();
        JScrollPane scroll =new JScrollPane(a1);
        a1.setEditable(false);
        a1.setLineWrap(true);
        a1.setWrapStyleWord(true);

        scroll.setBorder(
                BorderFactory.createTitledBorder("Student Records")
        );

        JButton clear=new JButton("Clear");

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font textFont = new Font("Arial", Font.PLAIN, 14);

        l1.setFont(labelFont);
        l2.setFont(labelFont);

        t1.setFont(textFont);
        t2.setFont(textFont);

        a1.setFont(textFont);

        ad.addActionListener( e -> {
            String studentName=t1.getText();
            if (studentName.trim().equals("") || t2.getText().trim().equals("")){
                JOptionPane.showMessageDialog(frame,"All Fields are Required");
            }
            else {
                try {
                    int rollNo = Integer.parseInt(t2.getText());
                    boolean duplicate = false;
                    for (Student s : students) {
                        if (s.getRollNo() == rollNo) {
                            duplicate = true;
                            break;
                        }
                    }
                    if (duplicate) {
                        JOptionPane.showMessageDialog(frame,"Roll No Already Exists");
                    }
                      else {
                        Student s1 = new Student(studentName, rollNo);
                        students.add(s1);
                        JOptionPane.showMessageDialog(frame,"Student Added Succesfully");
                        t1.setText("");
                        t2.setText("");
                    }
                }
                catch(NumberFormatException ex){
                       JOptionPane.showMessageDialog(frame,"Roll No Must be Numeric");
                    }
            }
        });

        view.addActionListener( e -> {
            String data="";
            if(students.isEmpty()){
                JOptionPane.showMessageDialog(frame,"No Students Available");
            }
            else {
                for (Student s : students) {
                    data += "Student Name : " + s.getStudentName() + "\n";
                    data += "Roll No : " + s.getRollNo() + "\n\n";
                }
                a1.setText(data);
                a1.setForeground(Color.BLUE);
            }
        });

        search.addActionListener( e -> {
            if (t2.getText().trim().equals("")){
                JOptionPane.showMessageDialog(frame,"Roll No Must be Required");
            }
            else {
                try {
                    int searchRollNo = Integer.parseInt(t2.getText());
                    boolean found = false;
                    for (Student s : students) {
                        if (s.getRollNo() == searchRollNo) {
                            a1.setText("Student Found \n" + "Student Name : " + s.getStudentName() + "\n" + "Roll No : " + s.getRollNo());
                            a1.setForeground(Color.BLACK);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(frame,"Student Not Found");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,"Roll No Must be Numeric");
                }
            }
        });

        update.addActionListener( e -> {
            String newName=t1.getText();
            if (newName.trim().equals("") || t2.getText().trim().equals("")){
                JOptionPane.showMessageDialog(frame,"All Fields are Required");
            }
            else {
                try {
                    int updateRollNo = Integer.parseInt(t2.getText());
                    boolean found = false;
                    for (Student s : students) {
                        if (s.getRollNo() == updateRollNo) {
                            s.setStudentName(newName);
                            JOptionPane.showMessageDialog(frame,"Student Updated successfully");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(frame,"Student NOt Found");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,"Roll No Must be Numeric");
                }
            }
        });

        delete.addActionListener( e -> {
            if (t2.getText().trim().equals("")){
                JOptionPane.showMessageDialog(frame,"Roll No must be Required");
            }
            else {
                try {
                    int deleteRollNo = Integer.parseInt(t2.getText());
                    int indexToDelete = -1;
                    for (int i = 0; i < students.size(); i++) {
                        if (students.get(i).getRollNo() == deleteRollNo) {
                            indexToDelete = i;
                            break;
                        }
                    }
                    int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this student?");
                    if (choice == JOptionPane.YES_OPTION) {
                        if (indexToDelete != -1) {
                            students.remove(indexToDelete);
                            JOptionPane.showMessageDialog(frame, "Student Deleted SuccessFully");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Student Not Found");
                        }
                    }
                    }
                catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(frame, "Roll No Must be Numeric");
                    }
            }
        });

         clear.addActionListener( e -> {
                 t1.setText("");
                 t2.setText("");
                 a1.setText("");
                 t1.requestFocus();
         });
        topPanel.add(l1);
        topPanel.add(t1);

        topPanel.add(l2);
        topPanel.add(t2);

        frame.add(topPanel,BorderLayout.NORTH);

        bottomPanel.add(ad);
        bottomPanel.add(update);
        bottomPanel.add(delete);
        bottomPanel.add(view);
        bottomPanel.add(search);
        bottomPanel.add(clear);

        frame.add(bottomPanel,BorderLayout.SOUTH);

        centerPanel.add(scroll,BorderLayout.CENTER);
        frame.add(centerPanel);
        
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
class Student{
    private String studentName;
    private int rollNo;

    Student(String n,int r){
        studentName=n;
        rollNo=r;
    }
    public void setStudentName(String n){
        studentName=n;
    }
    public void setRollNo(int r){
        rollNo=r;
    }
    public String getStudentName(){
        return studentName;
    }
    public int getRollNo(){
        return rollNo;
    }
}
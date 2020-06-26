package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.lang.Human;

import java.util.ArrayList;
                                                                                    
/**                                                                                 
 * This class is represents a <code>Student</code>.                                 
 */
public class Student extends Human {

  private String pName;
  private ArrayList<String> pClasses;
  private double pGPA;
  private String pGender;

  /**
   * Creates a new <code>Student</code>
   *
   * @param name
   *        The student's name
   * @param classes
   *        The names of the classes the student is taking.  A student
   *        may take zero or more classes.
   * @param gpa
   *        The student's grade point average
   * @param gender
   *        The student's gender ("male" or "female", or "other", case insensitive)
   */
  public Student(String name, ArrayList<String> classes, double gpa, String gender) {
    super(name);
    pName = name;
    pClasses = classes;
    pGPA = gpa;
    pGender = gender;
  }

  /**
   * All students say "\"This class is too much work\""
   */
  @Override
  public String says() {
    return "\"This class is too much work\"";
  }

  /**
   * Returns a <code>String</code> that describes this
   * <code>Student</code>.
   */
  public String toString() {
    String studentDescription = pName + " has a GPA of " + pGPA + " and is taking " + pClasses.size() + " classes: ";

    for (int i = 0; i < pClasses.size(); i++) {
      if (i == pClasses.size() - 1) {
        studentDescription += pClasses.get(i) + ". ";
      }
      else {
        studentDescription += pClasses.get(i) + ", ";
      }
    }

    String whatTheySay = new String();

    if (pGender.equals("male")) {
      whatTheySay+="He";
    }
    else if (pGender.equals("female")) {
      whatTheySay+="She";
    }
    else {
      whatTheySay+="They";
    }

    whatTheySay = whatTheySay + " says " + this.says() + ".";

    studentDescription += whatTheySay;

    return studentDescription;
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
//    for (String string : args)
//    {
//      System.err.println(string);
//    }

    try {
      ArrayList<String> classes = new ArrayList<String>();
      for (int i = 3; i < args.length; i++) {
        classes.add(args[i]);
      }
      Student cStudent = new Student(args[0], classes, Double.parseDouble(args[2]), args[1]);

      System.out.println(cStudent.toString());
    } catch (Exception e) {
      System.err.println("Missing command line arguments");
    }
    finally {
      System.exit(1);
    }
  }
}
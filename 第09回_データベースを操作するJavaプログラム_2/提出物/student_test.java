public class student_test {
    public static void main(String[] args) {
        StudentTestControl studentControl = new StudentTestControl();
        StudentTestMainFrame mainFrame = new StudentTestMainFrame(studentControl);
        mainFrame.setBounds(5, 5, 700, 455);
        mainFrame.setVisible(true);
    }
}

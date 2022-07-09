import java.awt.*;
import java.awt.event.*;

public class StudentTestMainFrame extends Frame implements ActionListener, WindowListener, KeyListener {
    StudentTestControl studentControl;

    //
    TextField tfStudentID;
    TextField tfSecondName, tfFirstName;
    TextField tfYear, tfMonth, tfDay;

    Button buttonInsert;
    Button buttonSelect;

    TextArea textMessage;

    public StudentTestMainFrame(StudentTestControl sc){
        studentControl = sc;

        // 各フィールドの生成
        tfStudentID = new TextField("", 6);
        tfSecondName = new TextField("", 10);
        tfFirstName = new TextField("", 10);
        tfYear = new TextField("", 4);
        tfMonth = new TextField("", 2);
        tfDay = new TextField("", 2);

        // ボタンの生成
        buttonInsert = new Button("学生追加");
        buttonSelect = new Button("学生表示");

        // テキストエリアの生成と編集不可の設定
        textMessage = new TextArea(20, 80);
        textMessage.setEditable(false);

        // ウィンドウへ登録
        setLayout(new FlowLayout());
        add(new Label("学生ID"));
        add(tfStudentID);
        add(new Label("姓"));
        add(tfSecondName);
        add(new Label("名"));
        add(tfFirstName);
        add(new Label("誕生日"));
        add(tfYear);
        add(new Label("年"));
        add(tfMonth);
        add(new Label("月"));
        add(tfDay);
        add(new Label("日"));

        add(buttonInsert);
        add(buttonSelect);

        add(textMessage);

        buttonInsert.addActionListener(this);
        buttonSelect.addActionListener(this);

        addWindowListener(this);
        addKeyListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 結果を受け取る変数とメッセージエリアの初期化
        String result = "";
        textMessage.setText("");

        // ボタンごとの処理
        if (e.getSource() == buttonInsert){
            result = studentControl.insertStudent(tfStudentID.getText(), tfSecondName.getText(), tfFirstName.getText(), tfYear.getText(), tfMonth.getText(), tfDay.getText());
        } else if (e.getSource() == buttonSelect){
            result = studentControl.selectStudent();
        }

        // 結果をメッセージエリアに設定
        textMessage.setText(result);
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO 自動生成されたメソッド・スタブ

    }

}

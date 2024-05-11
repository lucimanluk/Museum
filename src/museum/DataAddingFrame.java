/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museum;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class DataAddingFrame extends JFrame implements ActionListener {

    private final JLabel nameLabel = new JLabel("Name");
    private JTextField nameField = new JTextField(20);
    private final JLabel descriptionLabel = new JLabel("Description");
    private JTextField descriptionField = new JTextField(20);
    private final JLabel regionLabel = new JLabel("Region of origin");
    private JTextField regionField = new JTextField(20);
    private final JLabel yearLabel = new JLabel("Year of production");
    private JTextField yearField = new JTextField(20);
    private final JLabel roomLabel = new JLabel("Room placement");
    private JTextField roomField = new JTextField(20);
    private final JButton addDataButton2 = new JButton("Add data");
    private final JButton closeFrame = new JButton("Close");
    private Database db;

    public DataAddingFrame(Database db) {
        this.db = db;

        setLayout(new GridLayout(6,2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(nameLabel);
        add(nameField);
        add(descriptionLabel);
        add(descriptionField);
        add(regionLabel);
        add(regionField);
        add(yearLabel);
        add(yearField);
        add(roomLabel);
        add(roomField);

        addDataButton2.addActionListener(this);
        closeFrame.addActionListener(this);
        add(addDataButton2);
        add(closeFrame);

        setSize(800, 300);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDataButton2) {
            if (!nameField.getText().isEmpty() && !descriptionField.getText().isEmpty() && !regionField.getText().isEmpty() && !yearField.getText().isEmpty() && !roomField.getText().isEmpty()) {
                db.insertData();
                db.view2();
            }
        } else if (e.getSource() == closeFrame) {
            dispose();
        }

    }
}

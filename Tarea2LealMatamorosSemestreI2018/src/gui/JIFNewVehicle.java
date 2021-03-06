package gui;

import business.VehicleBusiness;
import domain.Vehicle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class JIFNewVehicle extends JInternalFrame implements ActionListener, InternalFrameListener {

    private VehicleBusiness vehicleBusiness;

    private JLabel jlName, jlYear, jlMileage, jlAmerican, jlSerie;
    private JTextField jtfName, jtfYear, jtfMileage, jtfSerie;
    private JRadioButton jrbYes, jrbNo;
    private ButtonGroup buttonGroup;
    private JButton jbOk;

    public JIFNewVehicle() throws IOException {
        super("New Vehicle", false, true, false, false);
        this.setLayout(null);
        this.init();
        this.setLocation(20, 40);
        this.setSize(300, 325);
        this.addInternalFrameListener(this);
    } // constructor

    private void init() throws IOException {
        this.vehicleBusiness = new VehicleBusiness();

        this.jlName = new JLabel("Name");
        this.jlYear = new JLabel("Year");
        this.jlMileage = new JLabel("Mileage");
        this.jlAmerican = new JLabel("American");
        this.jlSerie = new JLabel("Serie");

        this.jtfName = new JTextField();
        this.jtfYear = new JTextField();
        this.jtfMileage = new JTextField();
        this.jtfSerie = new JTextField();

        this.jrbYes = new JRadioButton("Yes");
        this.jrbNo = new JRadioButton("No");
        this.buttonGroup = new ButtonGroup();
        this.buttonGroup.add(this.jrbYes);
        this.buttonGroup.add(this.jrbNo);

        this.jbOk = new JButton("OK");
        this.jbOk.addActionListener(this);
        this.jbOk.setFocusable(false);

        this.jlName.setBounds(30, 40, 40, 15);
        this.jlYear.setBounds(30, 80, 40, 15);
        this.jlMileage.setBounds(30, 120, 70, 15);
        this.jlAmerican.setBounds(30, 160, 70, 15);
        this.jlSerie.setBounds(30, 200, 70, 15);

        this.jtfName.setBounds(112, 40, 135, 22);
        this.jtfYear.setBounds(112, 80, 135, 22);
        this.jtfMileage.setBounds(112, 120, 135, 22);
        this.jtfSerie.setBounds(112, 200, 135, 22);

        this.jrbYes.setBounds(115, 160, 50, 15);
        this.jrbNo.setBounds(180, 160, 50, 15);

        this.jbOk.setBounds(190, 240, 55, 30);

        this.add(this.jlName);
        this.add(this.jlYear);
        this.add(this.jlMileage);
        this.add(this.jlAmerican);
        this.add(this.jlSerie);
        this.add(this.jtfName);
        this.add(this.jtfYear);
        this.add(this.jtfMileage);
        this.add(this.jtfSerie);
        this.add(this.jrbYes);
        this.add(this.jrbNo);
        this.add(this.jbOk);
    } // init

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.jbOk) {
            try {
                if (newData()) {
                    this.jtfName.setText("");
                    this.jtfYear.setText("");
                    this.jtfMileage.setText("");
                    this.jtfSerie.setText("");
                    JOptionPane.showMessageDialog(rootPane, "Success");
                }
            } catch (IOException ex) {
                Logger.getLogger(JIFNewVehicle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } // actionPerformed

    private boolean newData() throws IOException {
        String name;
        int year, serie;
        float mileage;
        name = this.jtfName.getText();
        if (name.equals("")) {
            JOptionPane.showMessageDialog(this, "All spaces are required", "Error", 0);
            return false;
        } else {
            try {
                year = Integer.parseInt(this.jtfYear.getText());
                mileage = Float.parseFloat(this.jtfMileage.getText());
                serie = Integer.parseInt(this.jtfSerie.getText());
                if (year < 1885 || mileage < 0 || serie <= 0) {
                    String message = "Invalid data";
                    if (year < 1885) {
                        message = message + ", the first automobiles were created in the 18th century.";
                    }
                    JOptionPane.showMessageDialog(this, message, "Error", 2);
                    return false;
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "The year, mileage and serie must be a number.", "Error", 0);
                return false;
            }
        }
        if (this.jrbYes.isSelected() == false && this.jrbNo.isSelected() == false) {
            JOptionPane.showMessageDialog(this, "You must select if the car is American.", "Error", 0);
            return false;
        }
        if (!vehicleBusiness.isValid(serie)) {
            JOptionPane.showMessageDialog(this, "The series is already registered.", "Error", 0);
            return false;
        }
        Vehicle newVehicle = new Vehicle(name, year, mileage, false, serie);
        if (this.jrbYes.isSelected()) {
            newVehicle.setAmerican(true);
        }

        this.vehicleBusiness.addEndRecord(newVehicle);
        return true;
    } // newData

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        Window.jmiNewVehicle.setEnabled(true);
    } // Evento internalFrame para que cuando se cierre habilite el JMitem con el que se abre la ventana

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
    }

} // fin de la clase

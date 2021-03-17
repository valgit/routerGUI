import javax.swing.*;

public class leftPanel {
    private JTextField textFieldStartLatMin;
    private JTextField textFieldStartLatSec;
    private JTextField textFieldStartLonDeg;

    public JTextField getTextFieldStartLatMin() {
        return textFieldStartLatMin;
    }

    public JTextField getTextFieldStartLatSec() {
        return textFieldStartLatSec;
    }

    public JTextField getTextFieldStartLonDeg() {
        return textFieldStartLonDeg;
    }

    public JTextField getTextFieldStartLonMin() {
        return textFieldStartLonMin;
    }

    public JTextField getTextFieldStartLonSec() {
        return textFieldStartLonSec;
    }

    public JTextField getTextFieldStopLatDeg() {
        return textFieldStopLatDeg;
    }

    public JTextField getTextFieldStopLatMin() {
        return textFieldStopLatMin;
    }

    public JTextField getTextFieldStopLatSec() {
        return textFieldStopLatSec;
    }

    public JTextField getTextFieldStopLonDeg() {
        return textFieldStopLonDeg;
    }

    public JTextField getTextFieldStopLonSec() {
        return textFieldStopLonSec;
    }

    public JTextField getTextFieldStartLatDeg() {
        return textFieldStartLatDeg;
    }

    public JTextField getTextFieldStopLonMin() {
        return textFieldStopLonMin;
    }

    private JTextField textFieldStartLonMin;
    private JTextField textFieldStartLonSec;
    private JTextField textFieldStopLatDeg;
    private JTextField textFieldStopLatMin;
    private JTextField textFieldStopLatSec;
    private JTextField textFieldStopLonDeg;
    private JTextField textFieldStopLonSec;
    private JButton loadRouteButton;
    private JTextField textFieldStartLatDeg;
    private JTextField textFieldStopLonMin;

    public JPanel getLeftPanel() {
        return leftPanel;
    }

    private JPanel leftPanel;
}

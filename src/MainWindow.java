/*
 * launch the program
 * create the main window in it's own thread
 */

import javax.swing.*;

public class MainWindow {

        public static void main(String[] args) {
            SwingUtilities.invokeLater(runJFrameLater);
        }

        static Runnable runJFrameLater = new Runnable() {

            @Override
            public void run() {
                RouteView jFrameWindow = new RouteView();
                jFrameWindow.setVisible(true);
            }

        };

}


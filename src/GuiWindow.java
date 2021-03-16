import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;

public class GuiWindow extends JFrame  {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private RouteView routeview;
        private JButton filesel;
        private File selectedFile;// = new File("src/images/image01.jpg");
        //TODO:
        private File projectdir= new File("d:\\work\\stopproj");

        public GuiWindow(){

            setTitle("Route View");
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            setLayout( new FlowLayout() );

            // TODO: show map
            /*
             *
             */
            routeview =new RouteView();
            routeview.setVisible(true);
            //getContentPane().add(routeview);

            /*
             * load a GPX ?
             */
            filesel =new JButton("Select File");
            filesel.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent ae) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("project directory");
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        projectdir = fileChooser.getCurrentDirectory();
                    }
                }
            });


            add(filesel);

            JButton buttonExit = new JButton(" Exit ");
            buttonExit.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent ae) {
                    System.exit(0);
                }
            });

            add(buttonExit);

            //Display the window.
            pack();
            setVisible(true);


        }


}

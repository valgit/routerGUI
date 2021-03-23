import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RouteView extends JFrame implements JMapViewerEventListener {

    private static final long serialVersionUID = 1L;

    private JMapViewer theMap;
    private JLabel zoomLabel;
    private JLabel zoomValue;
    private JLabel mperpLabelName;
    private JLabel mperpLabelValue;
    private JLabel helpLabel;
    private JLabel mRightLbl;

    //TODO: create object
    private Coordinate mStartPoint;
    private Coordinate mEndPoint;
    // view race info :
    private leftPanel lfPane;

    /**
     * Setups the JFrame layout, sets some default options for the JMapViewerTree and displays a map in the window.
     */
    public RouteView() {
        super("Route Viewer");
        setupJFrame();

        theMap = new JMapViewer(/*"Zones"*/);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();
        JPanel helpPanel = new JPanel(new BorderLayout());

        JButton button = new JButton("setDisplayToFitMapMarkers");
        button.addActionListener(e -> map().setDisplayToFitMapMarkers());
        JComboBox<TileSource> tileSourceSelector = new JComboBox<>(new TileSource[] {
                new OsmTileSource.Mapnik(),
                new BingAerialTileSource(),
                //new SeaMapSource(), // TODO: not working
        });
        tileSourceSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                map().setTileSource((TileSource) e.getItem());
            }
        });
        panelTop.add(tileSourceSelector);
        tileSourceSelector.setSelectedIndex(0);
        mperpLabelName = new JLabel("Meters/Pixels: ");
        mperpLabelValue = new JLabel(String.format("%s", map().getMeterPerPixel()));
        zoomLabel = new JLabel("Zoom: ");
        zoomValue = new JLabel(String.format("%s", map().getZoom()));

        mRightLbl = new JLabel("Use toto right mouse button to move,\n "
                + "left double click or mouse wheel to zoom.");
        helpPanel.add(this.helpLabel = new JLabel(""), "Center");
        helpPanel.add(this.mRightLbl, "East");

        panelTop.add(zoomLabel);
        panelTop.add(zoomValue);
        panelTop.add(mperpLabelName);
        panelTop.add(mperpLabelValue);

        final JCheckBox showTileGrid = new JCheckBox("Tile grid visible");
        showTileGrid.setSelected(map().isTileGridVisible());
        showTileGrid.addActionListener(e -> map().setTileGridVisible(showTileGrid.isSelected()));
        panelBottom.add(showTileGrid);

        final JCheckBox showToolTip = new JCheckBox("ToolTip visible");
        showToolTip.addActionListener(e -> map().setToolTipText(null));
        panelBottom.add(showToolTip);

        final JCheckBox showMapMarker = new JCheckBox("Map markers visible");
        showMapMarker.setSelected(map().getMapMarkersVisible());
        showMapMarker.addActionListener(e -> map().setMapMarkerVisible(showMapMarker.isSelected()));
        panelBottom.add(showMapMarker);

        final JCheckBox showZoomControls = new JCheckBox("Show zoom controls");
        showZoomControls.setSelected(map().getZoomControlsVisible());
        showZoomControls.addActionListener(e -> map().setZoomControlsVisible(showZoomControls.isSelected()));
        panelBottom.add(showZoomControls);

        add(panel, BorderLayout.NORTH);
        add(helpPanel, BorderLayout.SOUTH);

        lfPane = new leftPanel();
        add(lfPane.getLeftPanel(),BorderLayout.WEST);

        lfPane.getLoadRouteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                /*
                Show show = new Show(JOptionPane.getFrameForComponent(this), true);
                show.setVisible(true);
                */
                 System.out.println("load CSV call");
            }
        });

        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(panelBottom, BorderLayout.SOUTH);

        // Listen to the map viewer for user operations so components will
        // receive events and updates
        map().addJMVListener(this);

        // Set some options, e.g. tile source and that markers are visible
        //TODO: peut-être la source VR dans une combobox ?
        //map().setTileSource(new OsmTileSource.Mapnik());
        map().setTileLoader(new OsmTileLoader(map()));
        map().setMapMarkerVisible(true);
        map().setZoomControlsVisible(true);

        // activate map in window
        //theMap.setTreeVisible(true);
        add(theMap, BorderLayout.CENTER);

        map().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    map().getAttribution().handleAttribution(e.getPoint(), true);
                    //TODO: better
                    final JPopupMenu mn = createPopupMenu(e);
                    if (mn != null) {
                        mn.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        map().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                boolean cursorHand = map().getAttribution().handleAttributionCursor(p);
                if (cursorHand) {
                    map().setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    map().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                Coordinate coord = (Coordinate) map().getPosition(p);
                final String message = Utils.FormatLat(coord.getLat()) + " " + Utils.FormatLon(coord.getLon());
                if (showToolTip.isSelected()) map().setToolTipText(message);

                //final String message = coord.getLat() + " " + coord.getLon();
                helpLabel.setText(message);
                //System.out.println(map.getPosition(e.getPoint()));

            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                super.windowClosing(e);
            }
        });
    }


    private void setupJFrame() {
        setSize(400, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    private JMapViewer map() {
        //return theMap.getViewer();
        return theMap;
    }


    /**
     * @param args Main program arguments
     */
    //public static void main(String[] args) { new RouteView().setVisible(true);  }

    private static Coordinate c(double lat, double lon) {
        return new Coordinate(lat, lon);
    }

    private void updateZoomParameters() {
        if (mperpLabelValue != null)
            mperpLabelValue.setText(String.format("%.2f", map().getMeterPerPixel()));
        if (zoomValue != null)
            zoomValue.setText(String.format("%s", map().getZoom()));
    }


    @Override
    public void processCommand(JMVCommandEvent command) {
        if (command.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
                command.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
            updateZoomParameters();
        }
        /*
        MouseEvent e = this.map().getLastMove();
        Coordinate coord = (Coordinate)this.map().getPosition(e.getPoint());
        final String message = Utils.FormatLat(coord.getLat()) + " " + Utils.FormatLon(coord.getLon());
        this.helpLabel.setText(message);

        */
    }

    private JPopupMenu createPopupMenu(MouseEvent evt) {
        final Coordinate coord = (Coordinate)this.map().getPosition(evt.getPoint());

        final JMenuItem createStartWP = new JMenuItem("Create start waypoint");
        final JMenuItem createEndWP = new JMenuItem("Create end waypoint");
        final JMenuItem createWP = new JMenuItem("Create etape waypoint");
        final JPopupMenu popup = new JPopupMenu();
            popup.add(createStartWP);
            popup.add(createEndWP);
            popup.addSeparator();
            popup.add(createWP);

        final int no3;
        createStartWP.addActionListener(e -> {
            //no3 = this.mWayPoints.size() + 1;
            this.CreateCircleWayPoint(coord, 0);
            //this.SaveWaypoints();
            return;
        });

        createEndWP.addActionListener(e -> {
            //no3 = this.mWayPoints.size() + 1;
            this.CreateCircleWayPoint(coord, 1);
            //this.SaveWaypoints();
            return;
        });

        return popup;
    }

    private void CreateCircleWayPoint(final Coordinate coord, final int no) {
        /*
        final WayPoint p = new WayPoint(no, coord.getLat(), coord.getLon(), 0, (float)size, this.mRaceDetails.ite);
        p.Show(this.map(), this.WaypointLayer);
        this.mWayPoints.add(p);
        */
        if (no == 0) {
            mStartPoint = coord;
            theMap.addMapMarker(new MapMarkerDot("start",mStartPoint));
            Integer d = (int)coord.getLat();
            double remain = (coord.getLat()-d);
            Integer m = (int)( remain * 60.0);
            Integer s = (int)((remain - m/60.0)*3600.0);
            lfPane.getTextFieldStartLatDeg().setText(d.toString());
            lfPane.getTextFieldStartLatMin().setText(m.toString());
            lfPane.getTextFieldStartLatSec().setText(s.toString());

            d = (int)coord.getLon();
            remain = (coord.getLon()-d);
            m = (int)( remain * 60.0);
            s = (int)((remain - m/60.0)*3600.0);
            lfPane.getTextFieldStartLonDeg().setText(d.toString());
            lfPane.getTextFieldStartLonMin().setText(m.toString());
            lfPane.getTextFieldStartLonSec().setText(s.toString());
        }
        if (no == 1) {
            mEndPoint = coord;
            theMap.addMapMarker(new MapMarkerDot("end", mEndPoint));

            Integer d = (int)coord.getLat();
            double remain = (coord.getLat()-d);
            Integer m = (int)( remain * 60.0);
            Integer s = (int)((remain - m/60.0)*3600.0);
            lfPane.getTextFieldStopLatDeg().setText(d.toString());
            lfPane.getTextFieldStopLatMin().setText(m.toString());
            lfPane.getTextFieldStopLatSec().setText(s.toString());

            d = (int)coord.getLon();
            remain = (coord.getLon()-d);
            m = (int)( remain * 60.0);
            s = (int)((remain - m/60.0)*3600.0);
            lfPane.getTextFieldStopLonDeg().setText(d.toString());
            lfPane.getTextFieldStopLonMin().setText(m.toString());
            lfPane.getTextFieldStopLonSec().setText(s.toString());
        }

    }

}


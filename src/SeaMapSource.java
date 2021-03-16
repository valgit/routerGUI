import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.tilesources.AbstractOsmTileSource;

/**
 * openseamap tile
 */
public class SeaMapSource  extends AbstractOsmTileSource {
        /**
         * Constructs a new {@code SeaMapSource} tile source.
         * http://tiles.openseamap.org/seamark/${z}/${x}/${y}.png
         */
        public SeaMapSource() {
            super("openSeaMap", "http://tiles.openseamap.org/seamark", "openseamap");
        }

        @Override
        public int getMaxZoom() {
            return 18;
        }

        @Override
        public String getAttributionText(int zoom, ICoordinate topLeft, ICoordinate botRight) {
            return "© OpenSeaMap contributors";
        }

        @Override
        public String getAttributionLinkURL() {
            return "https://openseamap.org/<";
        }
}

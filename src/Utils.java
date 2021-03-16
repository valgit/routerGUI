public class Utils {
    public static String FormatLat(double lat) {
        String hemis = " N";
        if (lat < 0.0) {
            lat *= -1.0;
            hemis = " S";
        }
        String result = String.valueOf((int)Math.floor(lat));
        result += "°";
        double remain = lat - Math.floor(lat);
        final int min = (int)Math.floor(remain * 60.0);
        result = result + String.valueOf(min) + "'";
        remain -= min / 60.0;
        final int sec = (int)Math.floor(remain * 3600.0);
        result = result + String.valueOf(sec) + hemis;
        return result;
    }

    public static String FormatLon(double lon) {
        String hemis = " E";
        if (lon > 180.0) {
            lon -= 360.0;
        }
        if (lon < -180.0) {
            lon += 360.0;
        }
        if (lon < 0.0) {
            lon *= -1.0;
            hemis = " W";
        }
        String result = String.valueOf((int)Math.floor(lon));
        result += "°";
        double remain = lon - Math.floor(lon);
        final int min = (int)Math.floor(remain * 60.0);
        result = result + String.valueOf(min) + "'";
        remain -= min / 60.0;
        final int sec = (int)Math.floor(remain * 3600.0);
        result = result + String.valueOf(sec) + hemis;
        return result;
    }
}

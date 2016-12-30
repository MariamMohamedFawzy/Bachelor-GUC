
package JXMapClasses;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.plaf.basic.BasicButtonUI;

import GUI.GUIFunctions;

import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

/**
 * This example demonstrates how to geocode coordinates by an address and vice versa.
 *
 * @author Vitaly Eremenko
 * @author Sergei Piletsky
 */
public class GeocoderSpecficLocation extends MapView {
	
	private static LatLng finalLoc = new LatLng();
	
	public static LatLng getFinalLoc() {
		return finalLoc;
	}

	public static void setFinalLoc(LatLng finalLoc) {
		GeocoderSpecficLocation.finalLoc = finalLoc;
	}

	public static JFrame  frame;

    private OptionsWindow optionsWindow;
    
    private double lat;
    private double lng;
        

    public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public GeocoderSpecficLocation(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
        // Setting of a ready handler to MapView object. onMapReady will be called when map initialization is done and
        // the map object is ready to use. Current implementation of onMapReady customizes the map object.
        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                // Getting the associated map object
                final Map map = getMap();
                // Setting initial zoom value
                map.setZoom(7.0);
                // Creating a map options object
                MapOptions options = new MapOptions();
                // Creating a map type control options object
                MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                // Changing position of the map type control
                controlOptions.setPosition(ControlPosition.TOP_RIGHT);
                // Setting map type control options
                options.setMapTypeControlOptions(controlOptions);
                // Setting map options
                map.setOptions(options);

                performGeocode(lat, lng);
            }
        });
    }

    @Override
    public void addNotify() {
        super.addNotify();

        optionsWindow = new OptionsWindow(this, new Dimension(50, 40)) {
            @Override
            public void initContent(JWindow contentWindow) {
                JPanel content = new JPanel(new GridBagLayout());
                content.setBackground(Color.white);

                Font robotoPlain13 = new Font("Roboto", 0, 13);
              

                final JButton okButton = new JButton();
                okButton.setIcon(new ImageIcon(MapOptionsExample.class.getResource("res/change.png")));
                okButton.setRolloverIcon(new ImageIcon(MapOptionsExample.class.getResource("res/change.png")));
                okButton.setBorder(BorderFactory.createEmptyBorder());
                okButton.setUI(new BasicButtonUI());
                okButton.setOpaque(false);
                ActionListener okActionListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	GUIFunctions.hideMapAfterBusinessShow();
                    }
                };
                okButton.addActionListener(okActionListener);
                
                
              
                content.add(okButton, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(11, 0, 11, 11), 0, 0));
               
                contentWindow.getContentPane().add(content);
            }

            @Override
            protected void updatePosition() {
                if (parentFrame.isVisible()) {
                    Point newLocation = parentFrame.getContentPane().getLocationOnScreen();
                    newLocation.translate(56, 11);
                    contentWindow.setLocation(newLocation);
                    contentWindow.setSize(40, 40);
                }
            }
        };
//        optionsWindow.contentWindow.setSize(50, 40);
    }

    
    @Override
    public void removeNotify() {
        super.removeNotify();
        optionsWindow.dispose();
    }

    private void performGeocode(double lat2, double lng2) {
        // Getting the associated map object
        final Map map = getMap();
        // Creating a geocode request
        GeocoderRequest request = new GeocoderRequest();
        finalLoc = request.getLocation();
        
        // Setting address to the geocode request
        LatLng ll = new LatLng(lat2, lng2);
        request.setLocation(ll);

        // Geocoding position by the entered address
        getServices().getGeocoder().geocode(request, new GeocoderCallback(map) {
            @Override
            public void onComplete(GeocoderResult[] results, GeocoderStatus status) {
                // Checking operation status
                if ((status == GeocoderStatus.OK) && (results.length > 0)) {
                    // Getting the first result
                    GeocoderResult result = results[0];
                    // Getting a location of the result
                    LatLng location = result.getGeometry().getLocation();
                    finalLoc = location;
                    // Setting the map center to result location
                    map.setCenter(location);
                    // Creating a marker object
                    Marker marker = new Marker(map);
                    // Setting position of the marker to the result location
                    marker.setPosition(location);
                    // Creating an information window
                    InfoWindow infoWindow = new InfoWindow(map);
                    // Putting the address and location to the content of the information window
                    infoWindow.setContent("<b>" + result.getFormattedAddress() + "</b><br>" + location.toString());
                    // Moving the information window to the result location
                    infoWindow.setPosition(location);
                    // Showing of the information window
                    infoWindow.open(map, marker);
                }
            }
        });
    }


//    public static void main(String[] args) {
//        final GeocoderExample mapView = new GeocoderExample();
//
//        frame = new JFrame("Geocoder");
//
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.add(mapView, BorderLayout.CENTER);
//        frame.setSize(700, 500);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
}
package JXMapClasses;
import javax.swing.JComponent;

public interface ControlPanel {
    JComponent getControlPanel();

    void configureControlPanel();

    int getPreferredHeight();
}
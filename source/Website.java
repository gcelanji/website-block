package websiteblocker;

import javafx.beans.property.SimpleStringProperty;

public class Website {

    private SimpleStringProperty URL;

    public Website(String URL) {
        this.URL = new SimpleStringProperty(URL);
    }

    public String getURL() {
        return URL.get();
    }

    public void setURL(String URL) {
        this.URL.set(URL);
    }

    @Override
    public String toString() {
        return (URL.get());
    }

}

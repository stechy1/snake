package cz.zcu.fav.ups.snake.model;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * jednoduchý model představující přihlašovací údaje k serveru
 */
public class LoginModel {

    private static final Pattern m = Pattern.compile(
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private static final int FLAG_USERNAME = 1;
    private static final int FLAG_HOST = 2;
    private static final int FLAG_PORT = 4;

    private final StringProperty username = new SimpleStringProperty("unknown");
    private final StringProperty host = new SimpleStringProperty("192.168.56.1");
    private final IntegerProperty port = new SimpleIntegerProperty(10000);
    private final BooleanProperty valid = new SimpleBooleanProperty(true);

    private int flags;

    public LoginModel() {
        username.addListener(usernameListener);
        host.addListener(hostListener);
        port.addListener(portListener);
        valid.addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
    }

    private void setValidityFlag(int flag, boolean value) {
        int oldFlagValue = this.flags;
        if (value) {
            flags |= flag;
        } else {
            flags &= ~flag;
        }

        if (flags == oldFlagValue) {
            return;
        }

        if (flags == 0) {
            setValid(true);
        }
    }

    private final ChangeListener<String> usernameListener = (observable, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            System.out.println("Jméno je nevalidní: " + newValue);
            setValidityFlag(FLAG_USERNAME, true);
            setValid(false);
        } else {
            System.out.println("Jméno je validní: " + newValue);
            setValidityFlag(FLAG_USERNAME, false);
        }
    };

    private final ChangeListener<String> hostListener = (observable, oldValue, newValue) -> {
        if (newValue.isEmpty() || !m.matcher(newValue).matches() && !Objects.equals(newValue, "localhost")) {
            System.out.println("Host je nevalidní: " + newValue);
            setValidityFlag(FLAG_HOST, true);
            setValid(false);
        } else {
            System.out.println("Host je validní: " + newValue);
            setValidityFlag(FLAG_HOST, false);
        }
    };

    private final ChangeListener<Number> portListener = (observable, oldValue, newValue) -> {
        //if (newValue.intValue() <= 1023 || newValue.intValue() > 51000) {
        if (newValue.intValue() >> 16 > 0) {
            System.out.println("Port je nevalidní: " + newValue);
            setValidityFlag(FLAG_PORT, true);
            setValid(false);
        } else {
            System.out.println("Port je validní: " + newValue);
            setValidityFlag(FLAG_PORT, false);
        }
    };

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getHost() {
        return host.get();
    }

    public StringProperty hostProperty() {
        return host;
    }

    public void setHost(String host) {
        this.host.set(host);
    }

    public int getPort() {
        return port.get();
    }

    public IntegerProperty portProperty() {
        return port;
    }

    public void setPort(int port) {
        this.port.set(port);
    }

    public boolean isValid() {
        return valid.get();
    }

    public BooleanProperty validProperty() {
        return valid;
    }

    private void setValid(boolean valid) {
        this.valid.set(valid);
    }
}

package example;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("xBean")
@SessionScoped
public class XBean implements Serializable {
    private String x = "0";
    private String hiddenX;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getHiddenX() {
        return hiddenX;
    }

    public void setHiddenX(String x) {
        this.hiddenX = x;
    }
}
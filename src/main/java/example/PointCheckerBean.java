package example;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.ArrayList;

@Named("pointCheckerBean")
@RequestScoped
public class PointCheckerBean {

    /*public boolean checker(double x, double y, double r) {
        ArrayList<Double> forX = new ArrayList<>();
        forX.add(-2d);
        forX.add(-1d);
        forX.add(0d);
        forX.add(1d);
        forX.add(2d);

        ArrayList<Double> forR = new ArrayList<>();
        forR.add(1d);
        forR.add(1.5d);
        forR.add(2d);
        forR.add(2.5d);
        forR.add(3d);

        return forX.contains(x) && forR.contains(r) && y >= -3 && y <= 5;
    }*/

    public boolean checker(double x, double y, double r) {

        ArrayList<Double> forR = new ArrayList<>();
        forR.add(1d);
        forR.add(1.5d);
        forR.add(2d);
        forR.add(2.5d);
        forR.add(3d);

        return x >= -2 && x <= 2 && forR.contains(r) && y >= -3 && y <= 5;
    }

    public boolean validatePoint(double x, double y, double r) {
        return (x <= 0 && y <= 0) && (x * x + y * y <= r * r / 4) ||
                (x >= 0 && y >= 0 && x <= r && y <= r) ||
                (x >= 0 && y <= 0) && (x <= r / 2) && (y >= -r) && (x + y >= -r);
    }
}
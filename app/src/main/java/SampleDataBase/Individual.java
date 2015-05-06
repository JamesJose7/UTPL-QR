package SampleDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agua on 21/04/15.
 */
public class Individual {

    private String mName;
    private String mBackground;
    private int mID;
    private String mPlate;
    private String mNuevosAntecedentes = "";
    private String mDates = "";

    public Individual(String name, String background, int id, String plate) {
        mName = name;
        mBackground = background;
        mID = id;
        mPlate = plate;
    }

    public int getID() {
        return mID;
    }

    public String getPlate() {
        return mPlate;
    }

    public void setBackground(String background) {
        mBackground = background;
    }

    public String getBackground() {
        return mBackground;
    }

    public String getName() {
        return mName;
    }

    public void setNuevosAntecedentes(String nuevosAntecedentes) {
        mNuevosAntecedentes = nuevosAntecedentes;
    }

    public String getNuevosAntecedentes() {
        return mNuevosAntecedentes;
    }

    public void setDates(String dates) {
        mDates = dates;
    }

    public String getDates() {
        return mDates;
    }
}

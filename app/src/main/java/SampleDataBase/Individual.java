package SampleDataBase;

/**
 * Created by agua on 21/04/15.
 */
public class Individual {

    private String mName;
    private String mBackground;
    private int mID;
    private String mPlate;

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

    public String getBackground() {
        return mBackground;
    }

    public String getName() {
        return mName;
    }
}

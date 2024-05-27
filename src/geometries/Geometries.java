package geometries;

import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {

    private final List<Intersectable> L = new LinkedList<>();

    /**
     * empty constructs Geometries
     */
    public Geometries() {
    }

    /**
     * parameters constructs Geometries
     * @param geometries group of shapes
     */
    public Geometries(Intersectable...geometries){
    }

}

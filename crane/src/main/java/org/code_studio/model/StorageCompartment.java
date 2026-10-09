package org.code_studio.model;

import org.apache.commons.lang3.StringUtils;

public class StorageCompartment implements SystemComponent {
    

    private Coordinate coords;

    public StorageCompartment(Coordinate coords) {
        this.coords = coords;
    }


    /**
     * Returns String in crane format: xxxxyyzz
     * Every coordinate has leading zeroes
     */
    public String getId() {
        return 
            StringUtils.leftPad(coords.getX().toString(), 4, "0")
          + StringUtils.leftPad(coords.getY().toString(), 2, "0")
          + StringUtils.leftPad(coords.getZ().toString(), 2, "0")
          ;
    }

    public Integer getX() {
        return coords.getX();
    }
    public void setX(Integer x) {
        this.coords.setX(x);
    }
    public Integer getY() {
        return coords.getY();
    }
    public void setY(Integer y) {
        this.coords.setY(y);
    }
    public Integer getZ() {
        return coords.getZ();
    }
    public void setZ(Integer z) {
        this.coords.setZ(z);
    }

    @Override
    public String toString() {
        return getId();
    }

    
}
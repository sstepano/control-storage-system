package org.code_studio.model;

import org.apache.commons.lang3.StringUtils;

public class Coordinate {

    private Integer x; // RM = RED    | CRANE = DUBINA
    private Integer y; // RM = DUBINA | CRANE = VISINA
    private Integer z; // RM = VISINA | CRANE = RED
    private CoordinateType coordinateType;

    
    public Coordinate (Integer x, Integer y, Integer z, CoordinateType coordinateType) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.coordinateType = coordinateType;
    }

    /**
     * Creates coordinates based on the string and Coordinates type.
     * RM: xxyyyzz | CRANE: xxxxyyzz
     * @param coordinates
     * @param coordinateType
     */
    public Coordinate (String coordinates, CoordinateType coordinateType) {
        switch(coordinateType) {
            case CoordinateType.CRANE -> { // xxxxyyzz
                this.x = Integer.parseInt(coordinates.substring(0, 4));
                this.y = Integer.parseInt(coordinates.substring(4, 6));
                this.z = Integer.parseInt(coordinates.substring(6, 8));
            }
            case CoordinateType.RM -> { // xxxyyyzz
                this.x = Integer.parseInt(coordinates.substring(0, 2));
                this.y = Integer.parseInt(coordinates.substring(2, 5));
                this.z = Integer.parseInt(coordinates.substring(5, 7));

            }
            default -> { throw new UnsupportedOperationException("Unsupported CoordinateType: " + coordinateType.getId()); }
        }

        this.coordinateType = coordinateType;
    }

    public Integer getX() {
        return x;
    }
    public void setX(Integer x) {
        this.x = x;
    }
    public Integer getY() {
        return y;
    }
    public void setY(Integer y) {
        this.y = y;
    }
    public Integer getZ() {
        return z;
    }
    public void setZ(Integer z) {
        this.z = z;
    }

    /**
     * Returns String representation of coordinats, with zero padding, depending on Coords.Type
     */
    public String getId () {
        switch(coordinateType) {
            case CoordinateType.CRANE -> { // xxxxyyzz
                return 
                    StringUtils.leftPad(x.toString(), 4, "0")
                  + StringUtils.leftPad(y.toString().toString(), 2, "0")
                  + StringUtils.leftPad(z.toString().toString(), 2, "0");
            }
            case CoordinateType.RM    -> { // xxyyyzz
                return 
                    StringUtils.leftPad(x.toString(), 2, "0")
                  + StringUtils.leftPad(y.toString().toString(), 3, "0")
                  + StringUtils.leftPad(z.toString().toString(), 2, "0");
            }
            default -> { throw new UnsupportedOperationException("Unsupported CoordinateType: " + coordinateType.getId()); }
        }
    }

    public Coordinate getCraneCoordinates() {
        Coordinate convertedCoords = new Coordinate(this.x, this.y, this.z, CoordinateType.CRANE);

        switch (coordinateType) {
            case CoordinateType.CRANE -> { return this; }
            case CoordinateType.RM    -> { 
                convertedCoords.x = (int)Math.floor(convertedCoords.y / 2) + convertedCoords.y % 2;
                convertedCoords.y = this.z;
                convertedCoords.z = (this.x * 2) + ((this.y % 2) -1);
                return convertedCoords;
             }
            default -> { throw new UnsupportedOperationException("Unsupported CoordinateType: " + coordinateType.getId()); }
        }
    }

    //TODO
    public Coordinate getRMCoordinates() {
        Coordinate convertedCoords = new Coordinate(this.x, this.y, this.z, CoordinateType.RM);

        switch (coordinateType) {
            case CoordinateType.CRANE -> { 
                convertedCoords.x = (int)Math.floor(convertedCoords.y / 2) + convertedCoords.y % 2;
                convertedCoords.y = convertedCoords.z;
                convertedCoords.z = (convertedCoords.x * 2) + ((convertedCoords.y % 2) -1);
                return convertedCoords;
             }
            case CoordinateType.RM    -> { return this; }
            default -> { throw new UnsupportedOperationException("Unsupported CoordinateType: " + coordinateType.getId()); }
        }
    }

    @Override
    public String toString () {
        return this.getId();
    }
    
}

package org.code_studio.model;

import org.code_studio.Common;

public class UserData {

    /**
     * We are using 7 out of 10 bytes to store
     * RM coordinates xxyyyzz that will be assigned to first
     * TRANSFER request message, so when we actually need to
     * get the coordinates and convert it to crane coords,
     * we use only message we received and its UserData.
     */
    private Coordinate rmCoordinates;

    /**
     * Default constructor
     */
    public UserData() {}


    public UserData(Coordinate rmCoordinates) {
        this.rmCoordinates = rmCoordinates;
    }


    /**
     * Creates userdata from RAW string. RAW string contains ClientID integer
     * Adds padding char ? until the end of the 10 byte message
     * @param userData
     */
    public UserData (String userData) {
        String parsed = null;
        if (userData != null && !userData.isEmpty()) {
            //parsed = userData.replaceAll("\\?", "");
        	if (userData.length() >= 7) {
        		parsed = userData.substring(0, 7);
        	}
            try {
            	rmCoordinates = new Coordinate(parsed, CoordinateType.RM);
            } catch (Exception e) {
				Common.log(getClass(), "Invalid number received in UserData. Cannot create coordinate.", "ERROR");
			}
        }
    }


    @Override
    public String toString() {
        return rmCoordinates == null ? null : rmCoordinates.toString();
    }

    
}
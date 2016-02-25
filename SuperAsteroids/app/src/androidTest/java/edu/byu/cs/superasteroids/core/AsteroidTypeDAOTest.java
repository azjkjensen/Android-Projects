package edu.byu.cs.superasteroids.core;

import android.test.AndroidTestCase;

import edu.byu.cs.superasteroids.database.AsteroidTypeDAO;
import edu.byu.cs.superasteroids.model.AsteroidType;

/**
 * Created by Jk on 2/24/2016.
 */
public class AsteroidTypeDAOTest extends AndroidTestCase {
    public void testAddAsteroid(){
        AsteroidType a = new AsteroidType();
        long id = AsteroidTypeDAO.getInstance().addAsteroidType(a);
        assertEquals(a == AsteroidTypeDAO.getInstance().getByID(id),true);
    }
}

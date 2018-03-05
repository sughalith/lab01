package pl.arek.undeadTests;

import org.junit.Test;
import pl.arek.Undead;

import static org.junit.Assert.assertNotNull;

public class undeadTests {
    @Test
    public void isZombie() {
        Undead zombie = new Undead();
        assertNotNull(zombie);
    }
}

package pl.arek.undeadTests.domain;

import org.junit.Before;
import org.junit.Test;
import pl.arek.domain.Undead;
import pl.arek.repository.UndeadRepository;
import pl.arek.repository.UndeadRepositoryFactory;

import static org.junit.Assert.*;

public class UndeadTest {

    UndeadRepository undeadRepository;

    @Test
    public void isZombie() {
        Undead zombie = new Undead();
        assertNotNull(zombie);
    }

    @Test
    public void getById() {
        Long idToFind = (long) 1;
        assertNotNull(undeadRepository.getById(idToFind));
    }

    @Test
    public void addUndead() {
        Undead zombie = new Undead();
        zombie.setId((long) 1);
        zombie.setType("Testowy 1");
        undeadRepository.addUndead(zombie);
        assertNotNull(undeadRepository.getById(zombie.getId()));

    }

    @Test
    public void deleteUndead() {
        Undead zombie = undeadRepository.getById((long) 1);
        undeadRepository.deleteUndead(zombie);
        if (undeadRepository.getAll().size() > 0) {
            assertNotNull(undeadRepository.getAll());
            assertNull(undeadRepository.getById(zombie.getId()));

        }
    }

    @Test
    public void updateUndead() {
        Undead clicker = new Undead();
        clicker.setId((long) 1);
        clicker.setType("Clicker");
        Long zombieToUpdate = (long) 1;
        undeadRepository.updateUndead(zombieToUpdate, clicker);
        assertEquals(undeadRepository.getById(zombieToUpdate).getType(), clicker.getType());

        for (Undead undead : undeadRepository.getAll()) {
            if (clicker.getId().equals(zombieToUpdate)) {
                assertNotEquals(undead.getType(), clicker.getType());
            }
        }
    }

    @Test
    public void getAll() {
        assertNotNull(undeadRepository.getAll());
    }

    @Before
    public void initRepository() {
        undeadRepository = UndeadRepositoryFactory.getInstance();
        Undead walker = new Undead();
        Undead ghoul = new Undead();
        Undead voodoo = new Undead();
        Undead screamer = new Undead();

        walker.setId((long) 1);
        walker.setType("Walker");

        ghoul.setId((long) 2);
        ghoul.setType("Ghoul");

        voodoo.setId((long) 3);
        voodoo.setType("Voodoo");

        screamer.setId((long) 4);
        screamer.setType("Screamer");

        undeadRepository.addUndead(walker);
        undeadRepository.addUndead(ghoul);
        undeadRepository.addUndead(voodoo);
        undeadRepository.addUndead(screamer);
    }
}

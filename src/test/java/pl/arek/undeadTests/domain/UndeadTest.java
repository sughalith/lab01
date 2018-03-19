package pl.arek.undeadTests.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pl.arek.domain.Undead;
import pl.arek.repository.UndeadRepository;
import pl.arek.repository.UndeadRepositoryFactory;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class UndeadTest {

    UndeadRepository undeadRepository;

    @Test
    public void getById() throws SQLException{
        int idToFind = undeadRepository.getAll().size()-1;
        assertNotNull(undeadRepository.getById(idToFind));
    }

    @Test
    public void addUndead() throws SQLException{
        Undead zombie = new Undead();
        zombie.setType("Testowy 1");
        undeadRepository.addUndead(zombie);
        assertNotNull(undeadRepository.getById(undeadRepository.getAll().size()-1));

    }

    @Test
    public void deleteUndead() throws SQLException{
        Undead zombie = undeadRepository.getById(1);
        undeadRepository.deleteUndead(zombie);

        if (undeadRepository.getAll().size() > 0) {
            assertNotNull(undeadRepository.getAll());
            assertNull(undeadRepository.getById(zombie.getId()));

        }
    }

    @Test
    public void updateUndead() throws SQLException{
        Undead clicker = new Undead();
        clicker.setType("Clicker");
        int zombieToUpdate = undeadRepository.getAll().size()-2;
        undeadRepository.updateUndead(zombieToUpdate, clicker);
        assertEquals(undeadRepository.getById(zombieToUpdate).getType(), clicker.getType());

        for (Undead undead : undeadRepository.getAll()) {
            if (clicker.getType().equals(undeadRepository.getById(zombieToUpdate).getType())) {
                if(zombieToUpdate == undead.getId()){
                    assertEquals(undeadRepository.getById(zombieToUpdate).getType(), undead.getType());
                }
                else {
                    assertNotEquals(zombieToUpdate, undead.getId());
                }
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

        walker.setType("Walker");

        ghoul.setType("Ghoul");

        voodoo.setType("Voodoo");

        screamer.setType("Screamer");

        undeadRepository.addUndead(screamer);
        undeadRepository.addUndead(walker);
        undeadRepository.addUndead(ghoul);
        undeadRepository.addUndead(voodoo);
    }

    @After
    public void dropTable() throws SQLException {
        undeadRepository.dropDatatable();
    }



}

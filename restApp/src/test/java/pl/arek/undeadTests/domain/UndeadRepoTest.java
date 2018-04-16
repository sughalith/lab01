package pl.arek.undeadTests.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.arek.domain.Undead;
import pl.arek.service.UndeadRepository;
import pl.arek.service.UndeadRepositoryImpl;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UndeadRepoTest {
    UndeadRepository undeadRepository;

    @Before
    public void setup() throws SQLException {
        undeadRepository = new UndeadRepositoryImpl();
    }

    @After
    public void cleanup() throws SQLException {
    //undeadRepository.dropDatatable();
    }

    @Test
    public void checkConnection() {
        assertNotNull(undeadRepository.getConnection());
    }

    @Test
    public void checkAdding() throws SQLException{
        Undead undead = new Undead();
        undead.setId(1);
        undead.setType("clicker");
        assertEquals(1, undeadRepository.addUndead(undead));
    }

}

package pl.arek.undeadTests.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.arek.domain.Undead;
import pl.arek.repository.UndeadRepository;
import pl.arek.repository.UndeadRepositoryImpl;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UndeadMockedTest {

    UndeadRepository undeadRepository;

    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement addUndeadStmt;
    @Mock
    private PreparedStatement updateUndeadStmt;
    @Mock
    private PreparedStatement deleteUndeadStmt;
    @Mock
    private PreparedStatement getAllUndeadsStmt;
    @Mock
    private PreparedStatement getUndeadByIdStmt;
    @Mock
    private PreparedStatement dropTableStmt;
    @Mock
    ResultSet resultSet;


    @Ignore
    @Test
    public void getById() throws SQLException {
        int idToFind = undeadRepository.getAll().size() - 1;
        assertNotNull(undeadRepository.getById(idToFind));
    }

    @Test
    public void addUndead() throws SQLException {

        when(addUndeadStmt.executeUpdate()).thenReturn(1);

        Undead undead = new Undead();
        undead.setType("Clicker");
        assertEquals(1, undeadRepository.addUndead(undead));
        verify(addUndeadStmt, times(1)).setString(1, "Clicker");
        verify(addUndeadStmt).executeUpdate();

    }

    @Test
    public void deleteUndead() throws SQLException {

        when(deleteUndeadStmt.executeUpdate()).thenReturn(1);
        Undead undead = new Undead();
        undead.setId(1);
        undead.setType("Clicker");
        assertEquals(1, undeadRepository.deleteUndead(undead));
        verify(deleteUndeadStmt, times(1)).setInt(1, undead.getId());
        verify(deleteUndeadStmt).executeUpdate();

    }

    @Ignore
    @Test
    public void updateUndead() throws SQLException {
        Undead clicker = new Undead();
        clicker.setType("Clicker");
        int zombieToUpdate = undeadRepository.getAll().size() - 2;
        undeadRepository.updateUndead(zombieToUpdate, clicker);
        assertEquals(undeadRepository.getById(zombieToUpdate).getType(), clicker.getType());

        for (Undead undead : undeadRepository.getAll()) {
            if (clicker.getType().equals(undeadRepository.getById(zombieToUpdate).getType())) {
                if (zombieToUpdate == undead.getId()) {
                    assertEquals(undeadRepository.getById(zombieToUpdate).getType(), undead.getType());
                } else {
                    assertNotEquals(zombieToUpdate, undead.getId());
                }
            }
        }
    }

    @Ignore
    @Test
    public void getAll() {
        assertNotNull(undeadRepository.getAll());
    }

    @Before
    public void initRepository() throws SQLException {
        when(connectionMock.prepareStatement("INSERT INTO Undead (name) VALUES (?)")).thenReturn(addUndeadStmt);
//        when(connectionMock.prepareStatement("SELECT id, name FROM Undead")).thenReturn(getAllUndeadsStmt);
//        when(connectionMock.prepareStatement("SELECT id, name FROM Undead WHERE id = ?")).thenReturn(getUndeadByIdStmt);
        when(connectionMock.prepareStatement("UPDATE Undead SET name = ? WHERE id = ?")).thenReturn(updateUndeadStmt);
        when(connectionMock.prepareStatement("DELETE FROM Undead WHERE id = ?")).thenReturn(deleteUndeadStmt);
        when(connectionMock.prepareStatement("DROP TABLE Undead")).thenReturn(dropTableStmt);
        undeadRepository = new UndeadRepositoryImpl();
        undeadRepository.setConnection(connectionMock);
        verify(connectionMock).prepareStatement("INSERT INTO Undead (name) VALUES (?)");
        verify(connectionMock).prepareStatement("SELECT * FROM Undead");
        verify(connectionMock).prepareStatement("SELECT * FROM Undead WHERE id = ?");
        verify(connectionMock).prepareStatement("UPDATE Undead SET name = ? WHERE id = ?");
        verify(connectionMock).prepareStatement("DELETE FROM Undead WHERE id = ?");
        verify(connectionMock).prepareStatement("DROP TABLE Undead");

    }
}

package pl.arek.undeadTests.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.arek.domain.Undead;
import pl.arek.repository.UndeadRepository;
import pl.arek.repository.UndeadRepositoryImpl;

import static org.hamcrest.CoreMatchers.*;
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


    @Test
    public void getById() throws SQLException {
//        int idToFind = undeadRepository.getAll().size() - 1;
//        assertNotNull(undeadRepository.getById(idToFind));
        AbstractResultSetById mockedResultSet = mock(AbstractResultSetById.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getInt("id")).thenCallRealMethod();
        when(mockedResultSet.getString("name")).thenCallRealMethod();
        when(getUndeadByIdStmt.executeQuery()).thenReturn(mockedResultSet);

        assertNotNull(undeadRepository.getById(1));

        verify(getUndeadByIdStmt, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getInt("id");
        verify(mockedResultSet, times(1)).getString("name");
        verify(mockedResultSet, times(1)).next();

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

    @Test
    public void getAll() throws SQLException{
        //assertNotNull(undeadRepository.getAll());

        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getInt("id")).thenCallRealMethod();
        when(mockedResultSet.getString("name")).thenCallRealMethod();
        when(getAllUndeadsStmt.executeQuery()).thenReturn(mockedResultSet);

        assertEquals(1, undeadRepository.getAll().size());

        verify(getAllUndeadsStmt, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getInt("id");
        verify(mockedResultSet, times(1)).getString("name");
        verify(mockedResultSet, times(2)).next();

    }

    abstract class AbstractResultSet implements ResultSet {
        int i = 0;

        @Override
        public boolean next() throws SQLException {
            if (i == 1)
                return false;
            i++;
            return true;
        }

        @Override
        public int getInt(String s) throws SQLException {
            return 1;
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            return "clicker";
        }
    }

    abstract class AbstractResultSetById implements ResultSet {
        int i = 0;

        @Override
        public int getInt(String s) throws SQLException {
            return 1;
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            return "clicker";
        }

        @Override
        public boolean next() throws SQLException {
            if (i == 1)
                return false;
            i++;
            return true;
        }
    }

    @Test
    public void updateUndead() throws SQLException {


        when(updateUndeadStmt.executeUpdate()).thenReturn(1);
        Undead undead = new Undead();

        undead.setId(1);
        undead.setType("clicker");

        Undead updatedUndead = new Undead();
        updatedUndead.setId(undead.getId());
        updatedUndead.setType("zombie");

        assertEquals(1, undeadRepository.updateUndead(undead.getId(), updatedUndead));
        verify(updateUndeadStmt).executeUpdate();


    }

    @Before
    public void initRepository() throws SQLException {
        when(connectionMock.prepareStatement("INSERT INTO Undead (name) VALUES (?)")).thenReturn(addUndeadStmt);
        when(connectionMock.prepareStatement("SELECT * FROM Undead")).thenReturn(getAllUndeadsStmt);
        when(connectionMock.prepareStatement("SELECT * FROM Undead WHERE id = ?")).thenReturn(getUndeadByIdStmt);
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

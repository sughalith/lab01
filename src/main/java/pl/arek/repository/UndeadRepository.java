package pl.arek.repository;

import pl.arek.domain.Undead;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UndeadRepository {
    public List<Undead> getAll();
    public Undead getById(int id) throws SQLException;
    public int addUndead(Undead undead);
    public int deleteUndead(Undead undead) throws SQLException;
    public int updateUndead(int oldId, Undead newUndead) throws SQLException;
    public void dropDatatable() throws SQLException;
    public void setConnection(Connection connection) throws SQLException;


    }

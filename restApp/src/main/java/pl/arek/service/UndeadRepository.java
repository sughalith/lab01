package pl.arek.service;

import pl.arek.domain.Undead;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

 public interface UndeadRepository {
     List<Undead> getAll();
     Undead getById(int id) throws SQLException;
     int addUndead(Undead undead);
     int deleteUndead(Undead undead) throws SQLException;
     int updateUndead(int oldId, Undead newUndead) throws SQLException;
     void dropDatatable() throws SQLException;
     void setConnection(Connection connection) throws SQLException;
    Connection getConnection();


    }

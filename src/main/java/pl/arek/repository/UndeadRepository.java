package pl.arek.repository;

import pl.arek.domain.Undead;

import java.util.List;

public interface UndeadRepository {
    public List<Undead> getAll();
    public void initDatabase();
    public Undead getById(Long id);
    public void addUndead(Undead undead);
    public void deleteUndead(Undead undead);
    public void updateUndead(long oldId, Undead newUndead);

}

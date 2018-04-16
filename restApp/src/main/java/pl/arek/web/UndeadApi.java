package pl.arek.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.arek.domain.Undead;
import pl.arek.service.UndeadRepository;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Path;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class UndeadApi {

    @Autowired
    UndeadRepository undeadRepository;

    @RequestMapping("/")
    public String index(){
        return "This is non rest, just checking if everything works";
    }

    @RequestMapping(value = "/GET/undead/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Undead getUndead(@PathVariable("id") int id) throws SQLException{
        return undeadRepository.getById(id);
    }

    @RequestMapping(value = "/GET/undeads", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Undead> getUndeads(@RequestParam(value = "filter",required = false) String f) throws SQLException{
        if(f != null){
        List<Undead> undeads = new LinkedList<Undead>();
        for(Undead u : undeadRepository.getAll()){
            if (u.getType().contains(f)){
                undeads.add(u);
            }
        }
        return undeads;
        }
        else {
            return undeadRepository.getAll();

        }
    }

    @RequestMapping(value = "/POST/undead", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long addUndead(@RequestBody Undead u){
        return new Long(undeadRepository.addUndead(u));
    }

    @RequestMapping(value = "/DELETE/undead/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteUndead(@PathVariable("id") int id) throws SQLException{
        Undead undeadToDel = new Undead();
        undeadToDel.setId(id);
        return new Long(undeadRepository.deleteUndead(undeadToDel));
    }
}

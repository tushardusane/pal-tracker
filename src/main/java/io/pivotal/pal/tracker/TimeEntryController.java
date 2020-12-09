package io.pivotal.pal.tracker;

import org.apache.coyote.Response;
import org.springframework.http.*;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public  TimeEntryController(TimeEntryRepository timeEntryRepository){
        this.timeEntryRepository =timeEntryRepository;
    }
    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry){
       TimeEntry res= timeEntryRepository.create(timeEntry);
        ResponseEntity<TimeEntry> response=new ResponseEntity<>(res,HttpStatus.CREATED);
    return response;
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id){
        ResponseEntity<TimeEntry> response;
        Optional<TimeEntry> res= Optional.ofNullable(timeEntryRepository.find(id));
        if(res.isEmpty())
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            response=new ResponseEntity<TimeEntry>(res.get(),HttpStatus.OK);

        return response;
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id,@RequestBody TimeEntry timeEntry){
        ResponseEntity<TimeEntry> response;
        TimeEntry result = timeEntryRepository.update(id, timeEntry);
      //  Optional<TimeEntry> res= Optional.ofNullable(timeEntryRepository.find(id));
        if(ObjectUtils.isEmpty(result))
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        timeEntryRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list(){
        return new ResponseEntity<>(timeEntryRepository.list(),HttpStatus.OK);
    }
}

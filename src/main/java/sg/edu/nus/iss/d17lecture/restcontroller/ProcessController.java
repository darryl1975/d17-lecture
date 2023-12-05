package sg.edu.nus.iss.d17lecture.restcontroller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import sg.edu.nus.iss.d17lecture.service.ProcessService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping(path="/process")
public class ProcessController {

    @Autowired
    ProcessService processSvc;

    @PostMapping(path = "/searchBook", produces = "application/json")
    public String processBookSearch(@RequestBody MultiValueMap<String, String> form) {

        String author = form.getFirst("searchName");
        System.out.println("Author: " + author);

        String result = processSvc.searchBook(author);

        return result;
    }

    @GetMapping(path = "/countries")
    public ResponseEntity<String> getCountries() {

        ResponseEntity<String> results = processSvc.getCountries();
        return results;
    }
    
    
    
}

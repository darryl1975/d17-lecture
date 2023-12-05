package sg.edu.nus.iss.d17lecture.controller;

import java.io.StringReader;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.d17lecture.restcontroller.ProcessController;
import sg.edu.nus.iss.d17lecture.service.ProcessService;

@Controller
@RequestMapping(path = "/home")
public class HomeController {

    @Autowired
    ProcessService processSvc;

    @Autowired
    ProcessController processCtrl;

    @GetMapping(path = "/booksearch")
    public String bookSearchForm() {
        return "booksearch";
    }

    @GetMapping("/countries")
    public ResponseEntity<String> listCountries() {
        ResponseEntity<String> result = processSvc.getCountries();

        // redirecting to ProcessController to do the retrieval
        // return "redirect:/process/countries";

        return result;
    }

    @GetMapping("/countries/jsontest")
    public ResponseEntity<String> listCountries2() {
        ResponseEntity<String> result = processSvc.getCountries();

        String jsonString = result.getBody().toString();

        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();
        System.out.println("jsonObject:" + jsonObject);


        return result;
    }

}

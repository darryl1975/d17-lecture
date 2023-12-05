package sg.edu.nus.iss.d17lecture.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.d17lecture.model.Country;
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
    public String listCountries2(Model model) {
        ResponseEntity<String> result = processSvc.getCountries();

        String jsonString = result.getBody().toString();

        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();
        // System.out.println("jsonObject:" + jsonObject);

        JsonObject jsonObjectData = jsonObject.getJsonObject("data");
        System.out.println("jsonObjectData:" + jsonObjectData);
        System.out.println("jsonObjectData size: " + jsonObjectData.size());

        List<Country> countries = new ArrayList<Country>();
        Set<Entry<String, JsonValue>> entries = jsonObjectData.entrySet();
        for (Entry<String, JsonValue> entry : entries) {
            // System.out.println(entry.getKey() + " > " + entry.getValue().toString());
            // System.out.println(entry.getKey() + " > " +
            // entry.getValue().asJsonObject().getString("country"));

            countries.add(new Country(entry.getKey(), entry.getValue().asJsonObject().getString("country")));
        }

        model.addAttribute("countries", countries);
        return "countrylist";
    }

    @GetMapping(path = "/countrysearch")
    public String countrySearchForm() {
        return "countrysearch";
    }

    @GetMapping(path = "/countrysearchregion")
    public String countrySearchRegionForm(HttpSession session, Model model) {
        ResponseEntity<String> result = processSvc.getCountries();

        String jsonString = result.getBody().toString();

        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();
        JsonObject jsonObjectData = jsonObject.getJsonObject("data");

        List<String> regions = new ArrayList<String>();
        Set<Entry<String, JsonValue>> entries = jsonObjectData.entrySet();
        for (Entry<String, JsonValue> entry : entries) {
            String regionValue = entry.getValue().asJsonObject().getString("region");
            System.out.println(regionValue);

            if (regions.size() == 0) {
                regions.add(entry.getValue().asJsonObject().getString("region"));
            } else {
                List<String> foundValue = regions.stream().filter(s -> s.equals(regionValue)).collect(Collectors.toList());

                if (foundValue.size() > 0) {
                    System.out.println("foundValue " + foundValue);
                } else {
                    regions.add(regionValue);
                } 
            }
        }

        System.out.println(regions.toString());
        model.addAttribute("regions", regions);

        return "countrysearchregion";
    }

}

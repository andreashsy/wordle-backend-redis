package wordle.backend.redis.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import wordle.backend.redis.services.WordlistService;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class WordleController {

    @Autowired
    WordlistService wordlistService;

    @PostMapping(path="/word", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> editWord(@RequestBody String requestBody) {
        String message;
        String word = requestBody.split(" ")[1];
        System.out.println("Request Body >>>>>>> " + requestBody);

        if (requestBody.startsWith("ADD")) {
            message = " has been added";
            wordlistService.pushAddWord(word);

        } else if (requestBody.startsWith("REM")) {
            message = " has been removed";
            wordlistService.pushRemoveWord(word);
        } else {
            message = " does not have the correct format";
        }

        String addWordsConcat = String.join(",", wordlistService.getAddWordList());

        String removeWordsConcat = String.join(",", wordlistService.getRemoveWordList());

        JsonObject jsonResponse = Json.createObjectBuilder()
            .add("message", "%s%s".formatted(word, message))
            .add("addList", addWordsConcat)
            .add("removeList", removeWordsConcat)
            .build();
        
        return ResponseEntity.ok(jsonResponse.toString());
    }
    
}

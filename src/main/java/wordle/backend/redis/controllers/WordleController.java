package wordle.backend.redis.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class WordleController {

    @PostMapping(path="/word", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> editWord(@RequestBody String word) {
        String action;
        System.out.println("Word >>>>>>> " + word);

        if (word.startsWith("ADD")) {
            action = "added";
        } else if (word.startsWith("REM")) {
            action = "removed";
        } else {
            action = "error";
        }

        JsonObject jsonResponse = Json.createObjectBuilder()
            .add("message", "%s has been %s".formatted(word.split(" ")[1], action))
            .build();
        
        return ResponseEntity.ok(jsonResponse.toString());
    }
    
}

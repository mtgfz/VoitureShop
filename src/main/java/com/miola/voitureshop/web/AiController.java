package com.miola.voitureshop.web;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ai")
public class AiController {

    private final ChatClient chatClient;

    public AiController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/recommend")
    public String recommend(@RequestParam double budget) {
        return chatClient.prompt()
                .user("Je cherche une voiture avec un budget de " + budget + " MAD. Donne-moi 3 recommandations courtes.")
                .call()
                .content();
    }

    @GetMapping("/analyze")
    public String analyze(@RequestParam String marque, @RequestParam String modele) {
        return chatClient.prompt()
                .user("Analyse brièvement la voiture " + marque + " " + modele + " : fiabilité, prix marché, points forts.")
                .call()
                .content();
    }
}
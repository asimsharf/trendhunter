package com.sudagoarth.trendhunter.controller;

import com.sudagoarth.trendhunter.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmbedController {
  private final OpenAIService openAI;

  @GetMapping("/embed")
  public int embed(@RequestParam String q) {
    return openAI.embedText(q).length; // return vector length just to verify
  }
}

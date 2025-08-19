package com.sudagoarth.trendhunter.service;

import com.sudagoarth.trendhunter.entity.Product;
import com.sudagoarth.trendhunter.repository.EmbeddingRepository;
import com.sudagoarth.trendhunter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimilarityService {
  private final OpenAIService openAI;
  private final EmbeddingRepository embeddings;
  private final ProductRepository products;

  public void indexProduct(Product p) {
    if (!openAI.isConfigured()) return;
    String text = (p.getBrand() + " " + p.getModel() + " " + 
                  Optional.ofNullable(p.getTitleNorm()).orElse(p.getTitleRaw()))
                  .trim();
    float[] v = openAI.embedText(text);
    embeddings.upsert(p.getId(), v);
  }

  public List<Product> similarToText(String text, int limit) {
    if (!openAI.isConfigured()) return List.of();
    float[] q = openAI.embedText(text);
    List<UUID> ids = embeddings.similar(q, limit);
    Map<UUID, Product> byId = products.findAllById(ids).stream()
        .collect(Collectors.toMap(Product::getId, p -> p));
    return ids.stream().map(byId::get).filter(Objects::nonNull).toList();
  }
}

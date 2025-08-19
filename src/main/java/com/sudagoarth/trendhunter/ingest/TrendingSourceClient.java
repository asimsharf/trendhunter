package com.sudagoarth.trendhunter.ingest;

import java.util.List;

public interface TrendingSourceClient {
    /** Returns the source key, e.g. "MOCK", "NOON" */
    String source();

    /** Fetch trending products. Replace with real API calls in concrete impls. */
    List<ExternalProduct> fetchTrending(int max);
}

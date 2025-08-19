//package com.sudagoarth.trendhunter;
//
//// src/test/java/.../PgVectorIntegrationTest.java
//@Testcontainers
//@SpringBootTest
//class PgVectorIntegrationTest {
//    @Container
//    static PostgreSQLContainer<?> pg =
//            new PostgreSQLContainer<>("pgvector/pgvector:pg16")
//                    .withDatabaseName("trendhunter")
//                    .withUsername("postgres")
//                    .withPassword("postgres");
//
//    @DynamicPropertySource
//    static void props(DynamicPropertyRegistry r) {
//        r.add("spring.datasource.url", pg::getJdbcUrl);
//        r.add("spring.datasource.username", pg::getUsername);
//        r.add("spring.datasource.password", pg::getPassword);
//    }
//
//    @Autowired JdbcTemplate jdbc;
//
//    @Test
//    void ivfCosineIndexWorks() {
//        jdbc.execute("CREATE EXTENSION IF NOT EXISTS vector");
//        jdbc.execute("""
//      CREATE TABLE IF NOT EXISTS product_embedding(
//        product_id UUID PRIMARY KEY,
//        embedding vector(3072)
//      )
//    """);
//        jdbc.execute("""
//      CREATE INDEX IF NOT EXISTS idx_product_embedding_cosine
//      ON product_embedding USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100)
//    """);
//        // Insert + query…
//    }
//}

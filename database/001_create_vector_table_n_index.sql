-- Install the extensions as per documentation provided in readme
CREATE TABLE IF NOT EXISTS core.vector_store (
                                            id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
                                            content text,
                                            metadata json,
                                            embedding vector(1536) -- 1536 is the default embedding dimension
);

CREATE INDEX ON core.vector_store USING HNSW (embedding vector_cosine_ops);

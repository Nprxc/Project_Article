CREATE TABLE IF NOT EXISTS articles (
                                        id SERIAL PRIMARY KEY,
                                        title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    published_date TEXT NOT NULL
    );

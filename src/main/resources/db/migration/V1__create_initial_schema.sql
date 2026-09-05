CREATE TABLE "user"(
    id TEXT UNIQUE PRIMARY KEY NOT NULL,
    name TEXT NOT NULL,
    lastname TEXT NOT NULL,
    username TEXT UNIQUE NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    profile_photo_url TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE "credential"(
    id TEXT UNIQUE PRIMARY KEY NOT NULL,
    user_id TEXT NOT NULL,
    platform_name TEXT NOT NULL,
    login TEXT NOT NULL,
    password TEXT NOT NULL,
    email VARCHAR(150),
    link TEXT,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_user FOREIGN KEY(user_id)
        REFERENCES "user"(id)
);
-- =====================
-- users
-- =====================
INSERT INTO users (id, provider_id, github_id, name, email) VALUES (1, 'google-oauth2|123456789', 101, 'Taro Yamada', 'taro@example.com');

-- =====================
-- skills
-- =====================
INSERT INTO skills (id, name, level, description, is_framework, category) VALUES (1, 'Java', 4, 'Spring Bootを用いたバックエンド開発', false, 'Language');
INSERT INTO skills (id, name, level, description, is_framework, category) VALUES (2, 'TypeScript', 3, 'Next.jsでのフロントエンド開発', false, 'Language');
INSERT INTO skills (id, name, level, description, is_framework, category) VALUES (3, 'Spring Boot', 4, 'REST APIセキュリティ設計', true, 'Backend');
INSERT INTO skills (id, name, level, description, is_framework, category) VALUES (4, 'Next.js', 3, 'App RouterによるSSR/SSG対応', true, 'Frontend');
INSERT INTO skills (id, name, level, description, is_framework, category) VALUES (5, 'PostgreSQL', 3, 'RDB設計クエリチューニング', false, 'Database');
INSERT INTO skills (id, name, level, description, is_framework, category) VALUES (6, 'Docker', 2, 'コンテナ化docker-compose環境構築', false, 'DevOps');

-- =====================
-- projects
-- =====================
INSERT INTO projects (id, title, explanation, url, github_url, is_github) VALUES (1, 'ポートフォリオサイト', 'Spring Boot + Next.jsで構築した個人ポートフォリオ', 'https://portfolio.example.com', 'https://github.com/taro/portfolio', true);
INSERT INTO projects (id, title, explanation, url, github_url, is_github) VALUES (2, 'タスク管理アプリ', 'チーム向けのカンバンボード型タスク管理ツール', 'https://taskapp.example.com', 'https://github.com/taro/taskapp', true);

-- project_skill中間テーブル
INSERT INTO project_skill (project_id, skill_id) VALUES (1, 1);
INSERT INTO project_skill (project_id, skill_id) VALUES (1, 2);
INSERT INTO project_skill (project_id, skill_id) VALUES (1, 3);
INSERT INTO project_skill (project_id, skill_id) VALUES (1, 4);
INSERT INTO project_skill (project_id, skill_id) VALUES (1, 5);
INSERT INTO project_skill (project_id, skill_id) VALUES (2, 1);
INSERT INTO project_skill (project_id, skill_id) VALUES (2, 3);
INSERT INTO project_skill (project_id, skill_id) VALUES (2, 5);
INSERT INTO project_skill (project_id, skill_id) VALUES (2, 6);

-- =====================
-- careers
-- =====================
INSERT INTO careers (id,user_id,career_name, time) VALUES (1, 1,'株式会社サンプル バックエンドエンジニア', '2023-04-01 00:00:00');
INSERT INTO careers (id, user_id,career_name, time) VALUES (2, 1,'フルスタックエンジニア', '2021-04-01 00:00:00');
INSERT INTO careers (id,user_id, career_name, time) VALUES (3, 1,'大学 情報工学部 卒業', '2021-03-31 00:00:00');

-- =====================
-- licenses
-- =====================
INSERT INTO licenses (id, license_name, created_at) VALUES (1, '基本情報技術者試験', '2020-06-01');
INSERT INTO licenses (id, license_name, created_at) VALUES (2, 'AWS Solutions Architect', '2022-11-01');
INSERT INTO licenses (id, license_name, created_at) VALUES (3, 'TOEIC 800点', '2021-09-01');

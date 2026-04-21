-- =====================
-- users
-- =====================
INSERT INTO users (id, provider_id, github_id, name, email) VALUES (1, 'google-oauth2|111111111', 101, '山田 太郎', 'taro@example.com');
INSERT INTO users (id, provider_id, github_id, name, email) VALUES (2, 'google-oauth2|222222222', 102, '鈴木 花子', 'hanako@example.com');
INSERT INTO users (id, provider_id, github_id, name, email) VALUES (3, 'google-oauth2|333333333', 103, '佐藤 健', 'ken@example.com');
INSERT INTO users (id, provider_id, github_id, name, email) VALUES (4, 'google-oauth2|444444444', 104, '田中 美咲', 'misaki@example.com');
INSERT INTO users (id, provider_id, github_id, name, email) VALUES (5, 'google-oauth2|555555555', 105, '伊藤 龍之介', 'ryunosuke@example.com');


-- =====================
-- skills
-- =====================

-- --- User 1: 山田 太郎（Java / Spring Boot 系バックエンド） ---
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (1,  1, 'Java',        4, 'Spring Bootを用いたREST API設計・実装',           false, 'Language');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (2,  1, 'TypeScript',  3, 'Next.jsでのフロントエンド開発',                   false, 'Language');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (3,  1, 'Spring Boot', 4, 'REST APIセキュリティ・認証認可設計',               true,  'Backend');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (4,  1, 'Next.js',     3, 'App RouterによるSSR/SSG対応',                     true,  'Frontend');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (5,  1, 'PostgreSQL',  3, 'RDB設計・クエリチューニング',                     false, 'Database');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (6,  1, 'Docker',      2, 'コンテナ化・docker-compose環境構築',              false, 'DevOps');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (7,  1, 'Gradle',      3, 'マルチプロジェクトビルド管理',                    true,  'Build');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (8,  1, 'JUnit',       3, 'ユニットテスト・モックを用いた品質担保',          true,  'Testing');

-- --- User 2: 鈴木 花子（Python / ML 系） ---
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (9,  2, 'Python',      5, '機械学習・データ分析・スクリプト自動化',          false, 'Language');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (10, 2, 'R',           3, '統計解析・データビジュアライゼーション',          false, 'Language');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (11, 2, 'PyTorch',     4, 'ディープラーニングモデルの構築・学習',            true,  'ML');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (12, 2, 'scikit-learn',3, '分類・回帰・クラスタリングパイプライン構築',      true,  'ML');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (13, 2, 'FastAPI',     4, '高速REST APIサーバー構築',                        true,  'Backend');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (14, 2, 'Pandas',      4, 'データクレンジング・集計処理',                    true,  'Data');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (15, 2, 'MySQL',       3, 'テーブル設計・ストアドプロシージャ作成',          false, 'Database');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (16, 2, 'Docker',      3, 'ML環境のコンテナ化・GPU対応設定',                false, 'DevOps');

-- --- User 3: 佐藤 健（Go / クラウドインフラ系） ---
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (17, 3, 'Go',          5, 'マイクロサービス・CLIツール開発',                 false, 'Language');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (18, 3, 'Rust',        2, 'システムプログラミング・WebAssembly入門',          false, 'Language');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (19, 3, 'Gin',         4, 'Goによる高速HTTPルーティング',                    true,  'Backend');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (20, 3, 'gRPC',        4, 'Protobufを使ったサービス間通信',                  true,  'Backend');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (21, 3, 'Kubernetes',  4, 'クラスター設計・HPA/リソース管理',                false, 'DevOps');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (22, 3, 'Terraform',   3, 'IaCによるAWSインフラのコード管理',                false, 'DevOps');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (23, 3, 'Redis',       3, 'キャッシュ設計・Pub/Sub活用',                    false, 'Database');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (24, 3, 'GitHub Actions', 4, 'CI/CDパイプライン構築・自動テスト・デプロイ',  false, 'DevOps');

-- --- User 4: 田中 美咲（フロントエンド / モバイル系） ---
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (25, 4, 'TypeScript',  5, '型設計・ジェネリクスを活用した堅牢なUI実装',     false, 'Language');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (26, 4, 'Dart',        3, 'FlutterによるクロスプラットフォームUI開発',       false, 'Language');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (27, 4, 'React',       5, 'Hooks・Context・パフォーマンス最適化',            true,  'Frontend');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (28, 4, 'Vue.js',      4, 'Composition APIによるSPA構築',                   true,  'Frontend');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (29, 4, 'Flutter',     3, 'iOS/AndroidアプリのUI実装',                      true,  'Mobile');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (30, 4, 'Tailwind CSS',4, 'レスポンシブデザイン・カスタムテーマ設定',       true,  'Frontend');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (31, 4, 'Storybook',   3, 'コンポーネントカタログ整備・Visual Regression Test', true, 'Testing');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (32, 4, 'GraphQL',     3, 'Apollo Clientを用いたデータフェッチ設計',         false, 'API');

-- --- User 5: 伊藤 龍之介（フルスタック / セキュリティ系） ---
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (33, 5, 'PHP',         4, 'Laravelによる業務システム開発',                   false, 'Language');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (34, 5, 'Ruby',        3, 'Rails APIモード・RSpecによるテスト',               false, 'Language');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (35, 5, 'JavaScript',  4, 'Vanilla JS・ES2022+モダン構文',                   false, 'Language');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (36, 5, 'Laravel',     4, 'Eloquent ORM・Passport認証実装',                  true,  'Backend');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (37, 5, 'Ruby on Rails',3,'RESTful API設計・ActiveRecord活用',               true,  'Backend');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (38, 5, 'React',       3, 'SPA構築・Redux Toolkit状態管理',                  true,  'Frontend');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (39, 5, 'MongoDB',     3, 'ドキュメント設計・Aggregation Pipeline',          false, 'Database');
INSERT INTO skills (id, user_id, name, level, description, is_framework, category) VALUES (40, 5, 'Nginx',       3, 'リバースプロキシ・SSL/TLS設定',                   false, 'Infrastructure');


-- =====================
-- projects
-- =====================

-- --- User 1 ---
INSERT INTO projects (id, user_id, title, explanation, url, github_url, is_github) VALUES (1, 1, 'ポートフォリオサイト',   'Spring Boot + Next.jsで構築した個人ポートフォリオ',              'https://portfolio.taro.example.com',  'https://github.com/taro/portfolio', true);
INSERT INTO projects (id, user_id, title, explanation, url, github_url, is_github) VALUES (2, 1, 'タスク管理アプリ',       'チーム向けカンバンボード型タスク管理ツール',                    'https://taskapp.taro.example.com',    'https://github.com/taro/taskapp',   true);

-- --- User 2 ---
INSERT INTO projects (id, user_id, title, explanation, url, github_url, is_github) VALUES (3, 2, '売上予測ダッシュボード', 'PyTorch + FastAPIで構築した小売向け売上予測Webアプリ',          'https://sales.hanako.example.com',    'https://github.com/hanako/sales-forecast', true);
INSERT INTO projects (id, user_id, title, explanation, url, github_url, is_github) VALUES (4, 2, '画像分類API',            'ResNetベースの商品画像分類REST API',                            'https://imgapi.hanako.example.com',   'https://github.com/hanako/image-classifier', true);

-- --- User 3 ---
INSERT INTO projects (id, user_id, title, explanation, url, github_url, is_github) VALUES (5, 3, 'マイクロサービス基盤',   'Go + gRPC + Kubernetesで構築したマイクロサービスプラットフォーム', 'https://msvc.ken.example.com',        'https://github.com/ken/microservices', true);
INSERT INTO projects (id, user_id, title, explanation, url, github_url, is_github) VALUES (6, 3, 'IaCテンプレート集',      'TerraformによるAWSマルチアカウント環境のIaCテンプレート',        NULL,                                  'https://github.com/ken/terraform-templates', true);

-- --- User 4 ---
INSERT INTO projects (id, user_id, title, explanation, url, github_url, is_github) VALUES (7, 4, 'UIコンポーネントライブラリ', 'React + Tailwind CSSで作成したデザインシステム',              'https://ui.misaki.example.com',       'https://github.com/misaki/ui-kit',  true);
INSERT INTO projects (id, user_id, title, explanation, url, github_url, is_github) VALUES (8, 4, '家計簿モバイルアプリ',   'FlutterでiOS/Android両対応の家計簿アプリ',                      'https://kakeibo.misaki.example.com',  'https://github.com/misaki/kakeibo', true);

-- --- User 5 ---
INSERT INTO projects (id, user_id, title, explanation, url, github_url, is_github) VALUES (9,  5, '予約管理システム',       'Laravel + ReactによるサロンむけWeb予約管理システム',            'https://reserve.ryu.example.com',     'https://github.com/ryu/reservation', true);
INSERT INTO projects (id, user_id, title, explanation, url, github_url, is_github) VALUES (10, 5, 'ブログCMSプラットフォーム', 'Ruby on Rails製のマルチユーザー対応ブログCMS',               'https://cms.ryu.example.com',         'https://github.com/ryu/blog-cms',   true);


-- project_skill 中間テーブル
-- project 1 (user1)
INSERT INTO project_skill (project_id, skill_id) VALUES (1, 1);
INSERT INTO project_skill (project_id, skill_id) VALUES (1, 2);
INSERT INTO project_skill (project_id, skill_id) VALUES (1, 3);
INSERT INTO project_skill (project_id, skill_id) VALUES (1, 4);
INSERT INTO project_skill (project_id, skill_id) VALUES (1, 5);
-- project 2 (user1)
INSERT INTO project_skill (project_id, skill_id) VALUES (2, 1);
INSERT INTO project_skill (project_id, skill_id) VALUES (2, 3);
INSERT INTO project_skill (project_id, skill_id) VALUES (2, 5);
INSERT INTO project_skill (project_id, skill_id) VALUES (2, 6);
INSERT INTO project_skill (project_id, skill_id) VALUES (2, 8);
-- project 3 (user2)
INSERT INTO project_skill (project_id, skill_id) VALUES (3, 9);
INSERT INTO project_skill (project_id, skill_id) VALUES (3, 11);
INSERT INTO project_skill (project_id, skill_id) VALUES (3, 13);
INSERT INTO project_skill (project_id, skill_id) VALUES (3, 14);
INSERT INTO project_skill (project_id, skill_id) VALUES (3, 15);
-- project 4 (user2)
INSERT INTO project_skill (project_id, skill_id) VALUES (4, 9);
INSERT INTO project_skill (project_id, skill_id) VALUES (4, 11);
INSERT INTO project_skill (project_id, skill_id) VALUES (4, 13);
INSERT INTO project_skill (project_id, skill_id) VALUES (4, 16);
-- project 5 (user3)
INSERT INTO project_skill (project_id, skill_id) VALUES (5, 17);
INSERT INTO project_skill (project_id, skill_id) VALUES (5, 19);
INSERT INTO project_skill (project_id, skill_id) VALUES (5, 20);
INSERT INTO project_skill (project_id, skill_id) VALUES (5, 21);
INSERT INTO project_skill (project_id, skill_id) VALUES (5, 23);
-- project 6 (user3)
INSERT INTO project_skill (project_id, skill_id) VALUES (6, 22);
INSERT INTO project_skill (project_id, skill_id) VALUES (6, 24);
-- project 7 (user4)
INSERT INTO project_skill (project_id, skill_id) VALUES (7, 25);
INSERT INTO project_skill (project_id, skill_id) VALUES (7, 27);
INSERT INTO project_skill (project_id, skill_id) VALUES (7, 30);
INSERT INTO project_skill (project_id, skill_id) VALUES (7, 31);
-- project 8 (user4)
INSERT INTO project_skill (project_id, skill_id) VALUES (8, 26);
INSERT INTO project_skill (project_id, skill_id) VALUES (8, 29);
INSERT INTO project_skill (project_id, skill_id) VALUES (8, 32);
-- project 9 (user5)
INSERT INTO project_skill (project_id, skill_id) VALUES (9, 33);
INSERT INTO project_skill (project_id, skill_id) VALUES (9, 35);
INSERT INTO project_skill (project_id, skill_id) VALUES (9, 36);
INSERT INTO project_skill (project_id, skill_id) VALUES (9, 38);
INSERT INTO project_skill (project_id, skill_id) VALUES (9, 39);
-- project 10 (user5)
INSERT INTO project_skill (project_id, skill_id) VALUES (10, 34);
INSERT INTO project_skill (project_id, skill_id) VALUES (10, 35);
INSERT INTO project_skill (project_id, skill_id) VALUES (10, 37);
INSERT INTO project_skill (project_id, skill_id) VALUES (10, 40);


-- =====================
-- careers
-- =====================
-- User 1
INSERT INTO careers (id, user_id, career_name, time) VALUES (1,  1, '株式会社サンプル バックエンドエンジニア',        '2023-04-01 00:00:00');
INSERT INTO careers (id, user_id, career_name, time) VALUES (2,  1, '株式会社テック フルスタックエンジニア',          '2021-04-01 00:00:00');
INSERT INTO careers (id, user_id, career_name, time) VALUES (3,  1, '○○大学 情報工学部 卒業',                        '2021-03-31 00:00:00');
-- User 2
INSERT INTO careers (id, user_id, career_name, time) VALUES (4,  2, '株式会社AIラボ MLエンジニア',                    '2022-07-01 00:00:00');
INSERT INTO careers (id, user_id, career_name, time) VALUES (5,  2, '株式会社データソリューションズ データアナリスト','2020-04-01 00:00:00');
INSERT INTO careers (id, user_id, career_name, time) VALUES (6,  2, '○○大学 数学科 卒業',                            '2020-03-31 00:00:00');
-- User 3
INSERT INTO careers (id, user_id, career_name, time) VALUES (7,  3, '株式会社クラウドネイティブ インフラエンジニア',  '2021-10-01 00:00:00');
INSERT INTO careers (id, user_id, career_name, time) VALUES (8,  3, '合同会社スタートアップ バックエンドエンジニア',  '2019-04-01 00:00:00');
INSERT INTO careers (id, user_id, career_name, time) VALUES (9,  3, '○○工業大学 情報システム学科 卒業',               '2019-03-31 00:00:00');
-- User 4
INSERT INTO careers (id, user_id, career_name, time) VALUES (10, 4, '株式会社UIデザインラボ フロントエンドエンジニア', '2022-01-01 00:00:00');
INSERT INTO careers (id, user_id, career_name, time) VALUES (11, 4, '株式会社モバイルファクトリー iOSエンジニア',     '2020-04-01 00:00:00');
INSERT INTO careers (id, user_id, career_name, time) VALUES (12, 4, '○○大学 メディアデザイン学科 卒業',               '2020-03-31 00:00:00');
-- User 5
INSERT INTO careers (id, user_id, career_name, time) VALUES (13, 5, '株式会社ウェブソリューション フルスタックエンジニア', '2023-01-01 00:00:00');
INSERT INTO careers (id, user_id, career_name, time) VALUES (14, 5, '個人事業主 フリーランスエンジニア',               '2021-06-01 00:00:00');
INSERT INTO careers (id, user_id, career_name, time) VALUES (15, 5, '○○大学 経営情報学科 卒業',                       '2021-03-31 00:00:00');


-- =====================
-- licenses
-- =====================
-- User 1
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (1,  1, '基本情報技術者試験',                    '2020-06-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (2,  1, '応用情報技術者試験',                    '2021-11-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (3,  1, 'AWS Solutions Architect Associate',     '2022-11-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (4,  1, 'TOEIC 800点',                           '2021-09-01');
-- User 2
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (5,  2, '統計検定2級',                           '2019-11-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (6,  2, 'G検定（ジェネラリスト検定）',           '2021-03-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (7,  2, 'AWS Certified Machine Learning Specialty','2023-05-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (8,  2, 'TOEIC 920点',                           '2022-04-01');
-- User 3
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (9,  3, '基本情報技術者試験',                    '2018-11-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (10, 3, 'AWS Solutions Architect Professional',  '2022-03-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (11, 3, 'Certified Kubernetes Administrator (CKA)', '2023-02-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (12, 3, 'Linux Foundation LFCS',                 '2021-08-01');
-- User 4
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (13, 4, '色彩検定2級',                           '2019-06-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (14, 4, 'ウェブデザイン技能検定2級',              '2020-10-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (15, 4, 'Google UX Design Certificate',          '2022-07-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (16, 4, 'TOEIC 750点',                           '2021-02-01');
-- User 5
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (17, 5, '基本情報技術者試験',                    '2020-06-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (18, 5, '情報セキュリティマネジメント試験',       '2021-11-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (19, 5, 'AWS Solutions Architect Associate',     '2023-01-01');
INSERT INTO licenses (id, user_id, license_name, created_at) VALUES (20, 5, 'LPIC Level 1',                          '2022-06-01');

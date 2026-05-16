-- ===== CORRIDAS =====
MERGE INTO corrida (id, titulo, descricao, tempo_segundos, ativa)
KEY(id) VALUES (1, 'Corrida de Java', 'Teste seus conhecimentos em Java!', 60, true);

MERGE INTO corrida (id, titulo, descricao, tempo_segundos, ativa)
KEY(id) VALUES (2, 'Corrida de Web', 'HTML, CSS e JavaScript!', 90, true);

MERGE INTO corrida (id, titulo, descricao, tempo_segundos, ativa)
KEY(id) VALUES (3, 'Corrida de Banco de Dados', 'SQL e conceitos de BD!', 60, true);

-- ===== PERGUNTAS CORRIDA 1 - Java =====
MERGE INTO pergunta (id, enunciado, resposta_correta, corrida_id)
KEY(id) VALUES (1, 'Qual palavra-chave define uma classe em Java?', 0, 1);

MERGE INTO pergunta (id, enunciado, resposta_correta, corrida_id)
KEY(id) VALUES (2, 'Qual método é o ponto de entrada de um programa Java?', 1, 1);

MERGE INTO pergunta (id, enunciado, resposta_correta, corrida_id)
KEY(id) VALUES (3, 'O que significa JVM?', 2, 1);

-- ===== PERGUNTAS CORRIDA 2 - Web =====
MERGE INTO pergunta (id, enunciado, resposta_correta, corrida_id)
KEY(id) VALUES (4, 'O que significa HTML?', 1, 2);

MERGE INTO pergunta (id, enunciado, resposta_correta, corrida_id)
KEY(id) VALUES (5, 'Qual tag define um parágrafo em HTML?', 0, 2);

MERGE INTO pergunta (id, enunciado, resposta_correta, corrida_id)
KEY(id) VALUES (6, 'O que é CSS?', 2, 2);

-- ===== PERGUNTAS CORRIDA 3 - Banco =====
MERGE INTO pergunta (id, enunciado, resposta_correta, corrida_id)
KEY(id) VALUES (7, 'Qual comando busca dados no SQL?', 0, 3);

MERGE INTO pergunta (id, enunciado, resposta_correta, corrida_id)
KEY(id) VALUES (8, 'O que é uma chave primária?', 1, 3);

MERGE INTO pergunta (id, enunciado, resposta_correta, corrida_id)
KEY(id) VALUES (9, 'Qual comando remove registros no SQL?', 3, 3);

-- ===== ALTERNATIVAS CORRIDA 1 =====
DELETE FROM pergunta_alternativas WHERE pergunta_id = 1;
INSERT INTO pergunta_alternativas VALUES (1, 'class'), (1, 'define'), (1, 'struct'), (1, 'object');

DELETE FROM pergunta_alternativas WHERE pergunta_id = 2;
INSERT INTO pergunta_alternativas VALUES (2, 'start()'), (2, 'main()'), (2, 'run()'), (2, 'init()');

DELETE FROM pergunta_alternativas WHERE pergunta_id = 3;
INSERT INTO pergunta_alternativas VALUES (3, 'Java Visual Machine'), (3, 'Java Version Manager'), (3, 'Java Virtual Machine'), (3, 'Java Variable Model');

-- ===== ALTERNATIVAS CORRIDA 2 =====
DELETE FROM pergunta_alternativas WHERE pergunta_id = 4;
INSERT INTO pergunta_alternativas VALUES (4, 'Hyper Tool Markup Language'), (4, 'HyperText Markup Language'), (4, 'High Text Modern Language'), (4, 'Hyperlink Markup Language');

DELETE FROM pergunta_alternativas WHERE pergunta_id = 5;
INSERT INTO pergunta_alternativas VALUES (5, '<p>'), (5, '<par>'), (5, '<text>'), (5, '<pg>');

DELETE FROM pergunta_alternativas WHERE pergunta_id = 6;
INSERT INTO pergunta_alternativas VALUES (6, 'Linguagem de programação'), (6, 'Banco de dados'), (6, 'Linguagem de estilização'), (6, 'Framework JavaScript');

-- ===== ALTERNATIVAS CORRIDA 3 =====
DELETE FROM pergunta_alternativas WHERE pergunta_id = 7;
INSERT INTO pergunta_alternativas VALUES (7, 'SELECT'), (7, 'FIND'), (7, 'GET'), (7, 'FETCH');

DELETE FROM pergunta_alternativas WHERE pergunta_id = 8;
INSERT INTO pergunta_alternativas VALUES (8, 'Um campo qualquer'), (8, 'Identificador único de um registro'), (8, 'Uma chave estrangeira'), (8, 'Um índice secundário');

DELETE FROM pergunta_alternativas WHERE pergunta_id = 9;
INSERT INTO pergunta_alternativas VALUES (9, 'REMOVE'), (9, 'ERASE'), (9, 'DROP'), (9, 'DELETE');

-- ===== PARTICIPANTES =====
MERGE INTO participante (id, nome, email, admin)
KEY(id) VALUES (1, 'admin', 'admin@quiz.com', true);

MERGE INTO participante (id, nome, email, admin)
KEY(id) VALUES (2, 'joao', 'joao@quiz.com', false);

MERGE INTO participante (id, nome, email, admin)
KEY(id) VALUES (3, 'maria', 'maria@quiz.com', false);

-- ===== RESULTADOS (para ranking não ficar vazio) =====
MERGE INTO resultado (id, pontuacao, data_hora, corrida_id, participante_id)
KEY(id) VALUES (1, 3, '2026-05-15 10:00:00', 1, 2);

MERGE INTO resultado (id, pontuacao, data_hora, corrida_id, participante_id)
KEY(id) VALUES (2, 2, '2026-05-15 11:00:00', 2, 3);

MERGE INTO resultado (id, pontuacao, data_hora, corrida_id, participante_id)
KEY(id) VALUES (3, 1, '2026-05-15 12:00:00', 3, 2);

-- ===== RESET DOS IDs =====
ALTER TABLE participante ALTER COLUMN id RESTART WITH 100;
ALTER TABLE corrida ALTER COLUMN id RESTART WITH 100;
ALTER TABLE pergunta ALTER COLUMN id RESTART WITH 100;
ALTER TABLE resultado ALTER COLUMN id RESTART WITH 100;
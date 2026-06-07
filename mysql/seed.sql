-- ============================================================
-- SEED — NewWay Dashboard
-- Execute no banco: newway2  (ou o banco configurado no .env)
-- ============================================================

-- ── Anos de chegada ──────────────────────────────────────────
INSERT INTO chegada (ano) VALUES (2023), (2024), (2025);

-- ── Chegadas mensais ─────────────────────────────────────────
-- 2023 (fkChegada = 1)
INSERT INTO chegada_mes (qtdChegadaMes, mes, fkChegada) VALUES
(12500, 'Janeiro',   1),
(14200, 'Fevereiro', 1),
(13800, 'Março',     1),
(11900, 'Abril',     1),
(13100, 'Maio',      1),
(15600, 'Junho',     1),
(18200, 'Julho',     1),
(17400, 'Agosto',    1),
(14800, 'Setembro',  1),
(15200, 'Outubro',   1),
(16400, 'Novembro',  1),
(19800, 'Dezembro',  1);

-- 2024 (fkChegada = 2)
INSERT INTO chegada_mes (qtdChegadaMes, mes, fkChegada) VALUES
(13100, 'Janeiro',   2),
(15400, 'Fevereiro', 2),
(14200, 'Março',     2),
(12800, 'Abril',     2),
(14300, 'Maio',      2),
(16900, 'Junho',     2),
(19500, 'Julho',     2),
(18200, 'Agosto',    2),
(15600, 'Setembro',  2),
(16800, 'Outubro',   2),
(17200, 'Novembro',  2),
(21000, 'Dezembro',  2);

-- 2025 (fkChegada = 3)
INSERT INTO chegada_mes (qtdChegadaMes, mes, fkChegada) VALUES
(14200, 'Janeiro',   3),
(16100, 'Fevereiro', 3),
(15400, 'Março',     3),
(13500, 'Abril',     3),
(15800, 'Maio',      3),
(17800, 'Junho',     3),
(20500, 'Julho',     3),
(19200, 'Agosto',    3),
(16200, 'Setembro',  3),
(17500, 'Outubro',   3),
(18100, 'Novembro',  3),
(22500, 'Dezembro',  3);

-- ── Chegadas por localidade (2025) ───────────────────────────
INSERT INTO chegada_localidade (qtdChegadaLocalidade, localidade, fkChegada) VALUES
(45000, 'São Paulo',       3),
(38000, 'Rio de Janeiro',  3),
(22000, 'Florianópolis',   3),
(18000, 'Salvador',        3),
(15000, 'Foz do Iguaçu',  3),
(14000, 'Gramado',         3),
(13500, 'Fortaleza',       3),
(11000, 'Manaus',          3),
(10500, 'Recife',          3),
( 9800, 'Brasília',        3);

-- ── Gastos turísticos ─────────────────────────────────────────
INSERT INTO gasto (tipo, valor) VALUES
('Alimentação',           35.00),
('Hospedagem',            28.00),
('Transporte',            18.00),
('Lazer e entretenimento',12.00),
('Compras',                7.00);

-- ── Tempo de permanência ──────────────────────────────────────
INSERT INTO permanencia (tipo, qtdDias) VALUES
('Menos de 3 dias',   2),
('3 a 7 dias',        5),
('8 a 14 dias',      11),
('Mais de 14 dias',  22);

-- ── Motivações de viagem ──────────────────────────────────────
INSERT INTO motivo (porcentagem, tipo) VALUES
(52, 'Lazer'),
(22, 'Negócios'),
(16, 'Visita a familiares'),
(10, 'Saúde e bem-estar');

-- ── Sub-motivos de lazer (fkMotivo = 1 → Lazer) ──────────────
INSERT INTO motivoLazer (tipoMotivoLazer, porcentagem, fkMotivo) VALUES
('Praia e sol',         38, 1),
('Turismo cultural',    24, 1),
('Ecoturismo',          20, 1),
('Aventura e esportes', 18, 1);

-- ── Eventos da agência (próximos eventos) ─────────────────────
INSERT INTO evento (nomeEvento, localidade, dataEvento, fkAgencia) VALUES
('Festa Junina Nordestina', 'Campina Grande - PB', '2026-06-12', 1),
('Festival de Inverno',     'Campos do Jordão - SP', '2026-07-08', 1),
('Semana do Turismo Baiano','Salvador - BA',        '2026-07-20', 2),
('Rock in Rio',             'Rio de Janeiro - RJ',  '2026-09-25', 2),
('Oktoberfest',             'Blumenau - SC',        '2026-10-02', 1);

-- ── Analytics por estado (mapa) — populado pelo LeitorExcel ──
-- Seed de fallback: usado apenas se o LeitorExcel não popular a tabela eventos
INSERT INTO eventos (nomeEvento, municipio, estado, dtInicial, dtTermino, tipoEvento, publicoEsperado) VALUES
('Carnaval SP',          'São Paulo',        'São Paulo',            '2025-02-28', '2025-03-05', 'Cultural',    580000),
('Réveillon RJ',         'Rio de Janeiro',   'Rio de Janeiro',       '2024-12-31', '2025-01-02', 'Cultural',    310000),
('Festival de Inverno',  'Campos do Jordão', 'São Paulo',            '2025-07-01', '2025-07-31', 'Cultural',    220000),
('Oktoberfest',          'Blumenau',         'Santa Catarina',       '2025-10-03', '2025-10-19', 'Cultural',    176000),
('Festival de Gramado',  'Gramado',          'Rio Grande do Sul',    '2025-08-15', '2025-08-25', 'Cultural',    140000),
('Fortal',               'Fortaleza',        'Ceará',                '2025-07-25', '2025-07-28', 'Musical',     165000),
('Carnival Salvador',    'Salvador',         'Bahia',                '2025-02-27', '2025-03-04', 'Cultural',    210000),
('Festejo Junino',       'Campina Grande',   'Paraíba',              '2025-06-01', '2025-06-30', 'Cultural',     74000),
('Semana Tech BH',       'Belo Horizonte',   'Minas Gerais',         '2025-09-10', '2025-09-12', 'Tecnologia',  340000),
('Fórum Negócios DF',    'Brasília',         'Distrito Federal',     '2025-11-05', '2025-11-07', 'Negócios',    198000),
('ExpoPR',               'Curitiba',         'Paraná',               '2025-04-14', '2025-04-20', 'Exposição',   220000),
('Amazon Summit',        'Manaus',           'Amazonas',             '2025-06-20', '2025-06-22', 'Negócios',     54000),
('Festa do Peão',        'Barretos',         'São Paulo',            '2025-08-21', '2025-08-31', 'Rodeio',      143000),
('Carnival Recife',      'Recife',           'Pernambuco',           '2025-02-28', '2025-03-04', 'Cultural',    143000),
('Festival Literário',   'Paraty',           'Rio de Janeiro',       '2025-07-09', '2025-07-13', 'Literatura',   89000);

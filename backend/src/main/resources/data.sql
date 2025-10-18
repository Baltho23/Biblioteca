INSERT INTO BOOKS (title, author, genre, copies_available) VALUES
('Cien anios de soledad', 'Gabriel Garcia Marquez', 'Realismo magico', 5),
('El principito', 'Antoine de Saint-Exupery', 'Fabula', 3),
('Rayuela', 'Julio Cortazar', 'Ficcion', 2);


INSERT INTO MEMBERS (name, email) VALUES
('Juan Perez', 'juan@example.com'),
('Laura Gomez', 'laura@example.com');

INSERT INTO LOAN (book_id, member_id, loan_date, due_date, return_date) VALUES
(1, 1, DATE '2025-10-01', DATE '2025-10-10', DATE '2025-10-08'),  -- Devuelto a tiempo
(2, 2, DATE '2025-10-05', DATE '2025-10-15', NULL),               -- Aún no devuelto
(3, 1, DATE '2025-09-20', DATE '2025-09-30', DATE '2025-10-02');  -- Devuelto con retraso
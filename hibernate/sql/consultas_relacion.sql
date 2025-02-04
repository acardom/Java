--Alumnos y sus asignaturas
SELECT 
    p.nombre AS alumno, 
    a.nombre AS asignatura, 
    ce.nombre AS curso_escolar
FROM 
    alumno_se_matricula_asignatura am
JOIN 
    persona p ON am.id_alumno = p.id
JOIN 
    asignatura a ON am.id_asignatura = a.id
JOIN 
    curso_escolar ce ON am.id_curso_escolar = ce.id
WHERE 
    p.tipo = 'alumno';


-- Profesores y sus asignaturas
SELECT 
    p.nombre AS profesor, 
    a.nombre AS asignatura, 
    d.nombre AS departamento
FROM 
    profesor pr
JOIN 
    persona p ON pr.id_profesor = p.id
JOIN 
    asignatura a ON pr.id_departamento = a.id_grado
JOIN 
    departamento d ON pr.id_departamento = d.id
WHERE 
    p.tipo = 'profesor';

-- Alumnos y sus datos personales
SELECT 
    id, 
    nif, 
    nombre, 
    apellido1, 
    apellido2, 
    ciudad, 
    direccion, 
    telefono, 
    fecha_nacimiento, 
    sexo 
FROM 
    persona 
WHERE 
    tipo = 'alumno';

-- Profesores y sus datos personales
SELECT 
    id, 
    nif, 
    nombre, 
    apellido1, 
    apellido2, 
    ciudad, 
    direccion, 
    telefono, 
    fecha_nacimiento, 
    sexo 
FROM 
    persona 
WHERE 
    tipo = 'profesor';

--Asignaturas y sus grados
SELECT 
    a.nombre AS asignatura, 
    g.nombre AS grado
FROM 
    asignatura a
JOIN 
    grado g ON a.id_grado = g.id;


--Profesores y sus departamentos
SELECT 
    d.nombre AS departamento, 
    p.nombre AS profesor
FROM 
    profesor pr
JOIN 
    persona p ON pr.id_profesor = p.id
JOIN 
    departamento d ON pr.id_departamento = d.id;



--Alumnos y sus asignaturas
SELECT 
    a.nombre AS asignatura, 
    p.nombre AS alumno, 
    ce.nombre AS curso_escolar
FROM 
    alumno_se_matricula_asignatura am
JOIN 
    asignatura a ON am.id_asignatura = a.id
JOIN 
    persona p ON am.id_alumno = p.id
JOIN 
    curso_escolar ce ON am.id_curso_escolar = ce.id
WHERE 
    p.tipo = 'alumno';
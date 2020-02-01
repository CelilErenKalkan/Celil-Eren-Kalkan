USE HR;
/*
-- 1
SELECT last_name, 
	   (SELECT COUNT(employee_id) FROM HR..employees WHERE departments.manager_id = employees.employee_id)
FROM HR..employees
WHERE

--2

*/
-- 3
SELECT COUNT(employee_id)
FROM employees
WHERE  last_name LIKE '%n';

-- 4
SELECT department_id, department_name, location_id,
		(SELECT COUNT(employees.employee_id) FROM employees WHERE employees.department_id 
		= departments.department_id)
FROM departments;

-- 5
SELECT last_name, hire_date
FROM employees 
WHERE hire_date IN(SELECT hire_date FROM employees WHERE DATENAME(DAY, hire_date) <= 5);

-- 6


-- 7
/*
SELECT department_id, department_name, manager_id, location_id
FROM departments
WHERE department_id IN(SELECT department_id FROM departments WHERE )*/

-- 8
-- a
/*
SELECT MAX((SELECT COUNT(*) FROM employees e WHERE e.department_id = d.department_id))
AS department_id, department_name
FROM departments d;*/
-- b

-- c
SELECT d.department_id, d.department_name, 
(SELECT COUNT(*) FROM employees e WHERE e.department_id = d.department_id) 
FROM departments d
WHERE (SELECT COUNT(*) FROM employees e WHERE e.department_id = d.department_id) < 3
AND (SELECT COUNT(*) FROM employees e WHERE e.department_id = d.department_id) > 0;

-- 9
SELECT DATENAME(DAY, hire_date), 
(SELECT COUNT(*) FROM employees WHERE DATENAME(YEAR, hire_date) = DATENAME(YEAR, hire_date))
FROM employees
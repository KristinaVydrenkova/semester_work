create table usr(
    id serial primary key,
    first_name varchar not null,
    last_name varchar not null,
    patronymic varchar default '-',
    email varchar unique not null,
    password varchar not null,
    role_id int,
    date_of_birth varchar default '-',
    photo varchar default 'default_photo.jpg',
    foreign key (role_id) references roles (id)
);
create table roles(
    id int primary key,
    role varchar
);
insert into roles(id,role) values (1,'учитель');
insert into roles(id,role) values (2,'ученик');

create table usr_uuid(
                         id serial,
                         foreign key (id) references usr(id),
                         uuid varchar,
                         time timestamp
);

create table test_answers(
    id serial,
    test_number int,
    q_number int,
    answer varchar
);
insert into test_answers (test_number, q_number, answer) values (1,1,4);
insert into test_answers (test_number, q_number, answer) values (1,2,4);
insert into test_answers (test_number, q_number, answer) values (1,3,1);
insert into test_answers (test_number, q_number, answer) values (1,4,3);
insert into test_answers (test_number, q_number, answer) values (1,5,2);
insert into test_answers (test_number, q_number, answer) values (2,1,1);
insert into test_answers (test_number, q_number, answer) values (2,2,715);
insert into test_answers (test_number, q_number, answer) values (2,3,2);
insert into test_answers (test_number, q_number, answer) values (2,4,667);
insert into test_answers (test_number, q_number, answer) values (2,5,4);

create table scores(
    id serial,
    student_id int,
    test_number int,
    scores int,
    possible_scores int,
    foreign key (student_id) references usr(id)
);
create table teacher_student(
    id serial,
    t_id int,
    s_id int,
    foreign key (t_id) references usr(id),
    foreign key (s_id) references usr(id)
);

create table teacher_tasks(
    id serial,
    t_id int,
    t_name varchar,
    task varchar,
    deadline varchar,
    foreign key (t_id) references usr(id)
);
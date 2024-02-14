
    alter table if exists t_city 
       drop constraint if exists FKnmyuhlka2j25tagqkxofmp4ui;

    alter table if exists t_review 
       drop constraint if exists FK949c779wyl8qdjh7ked94ljyj;

    drop table if exists t_city cascade;

    drop table if exists t_review cascade;

    drop table if exists t_user cascade;

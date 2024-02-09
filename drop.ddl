
    alter table if exists t_review 
       drop constraint if exists FK949c779wyl8qdjh7ked94ljyj;

    alter table if exists t_review 
       drop constraint if exists FKi3aokqdxtfs38iim9fmgde5o6;

    drop table if exists t_city cascade;

    drop table if exists t_review cascade;

    drop table if exists t_user cascade;

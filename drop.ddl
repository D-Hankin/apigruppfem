
    alter table if exists t_review 
       drop constraint if exists FK949c779wyl8qdjh7ked94ljyj;

    alter table if exists t_review 
       drop constraint if exists FK1mxophnet0pihvbtbru4ated1;

    drop table if exists t_city cascade;

    drop table if exists t_review cascade;

    drop table if exists t_user cascade;

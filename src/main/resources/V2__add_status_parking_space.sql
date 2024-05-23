alter table parking_spaces
    add column status varchar(20);
alter table users
    add column is_active boolean default true;
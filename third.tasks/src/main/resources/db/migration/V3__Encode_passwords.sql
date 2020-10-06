-- TODO why not working after every launch?
create extension if not exists pgcrypto;

update users
set password = crypt(password, gen_salt('bf', 8))

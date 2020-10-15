delete
from user_role;
delete
from users;

insert into users(id, active, password, username)
values (1, true, '$2a$08$u3TqLI5Y1svqoue090OzS.QL57.Dqpy0/Kv0NgKeuTzJ88KjrXCru', 'b'),
       (2, true, '$2a$08$u3TqLI5Y1svqoue090OzS.QL57.Dqpy0/Kv0NgKeuTzJ88KjrXCru', 'mike');

insert into user_role(user_id, role_set)
values (1, 'USER'), (1, 'ADMIN'),
       (2, 'USER');


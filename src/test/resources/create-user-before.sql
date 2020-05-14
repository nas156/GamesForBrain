insert into usr(id, active, age, email, username, password, role)
values (1, true, '2001-05-25', 'email@pavlo.com', 'admin',
        '$2a$10$P/J45xkOuXUa5BvTAYizjuGqRGGlLBbUrqPwM/VVTdctzot6.C5aq', 'ADMIN'),
        (2, true, '2001-05-25', 'email2@pavlo.com', 'user',
        '$2a$10$P/J45xkOuXUa5BvTAYizjuGqRGGlLBbUrqPwM/VVTdctzot6.C5aq', 'USER'),
        (3, true, '2001-05-25', 'email@pavlo.com', 'userToDelete',
        '$2a$10$P/J45xkOuXUa5BvTAYizjuGqRGGlLBbUrqPwM/VVTdctzot6.C5aq', 'USER'),
        (4, true, '2001-05-25', 'email@pavlo.com', 'userToUpdate',
        '$2a$10$P/J45xkOuXUa5BvTAYizjuGqRGGlLBbUrqPwM/VVTdctzot6.C5aq', 'USER');
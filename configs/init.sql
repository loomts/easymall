create user 'loomt' identified with mysql_native_password by '123456';
grant all privileges on *.* to 'loomt';
flush privileges;
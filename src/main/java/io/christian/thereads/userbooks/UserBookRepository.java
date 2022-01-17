package io.christian.thereads.userbooks;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserBookRepository extends CassandraRepository<UserBooks, UserBooksPrimaryKey> {
    
}

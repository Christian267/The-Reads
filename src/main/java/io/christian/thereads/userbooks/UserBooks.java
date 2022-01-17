package io.christian.thereads.userbooks;

import java.time.LocalDate;

import javax.persistence.Id;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

@Table(value = "book_by_user_and_bookid")
public class UserBooks {

    @PrimaryKey
    private UserBooksPrimaryKey key; 
    
    @Column("started_date")
    @CassandraType(type = Name.TEXT)
    private LocalDate startedDate;
    
    @Column("completed_date")
    @CassandraType(type = Name.TEXT)
    private LocalDate completedDate;
    
    @Column("reading_status")
    @CassandraType(type = Name.TEXT)
    private String readStatus;

    @Column("rating")
    @CassandraType(type = Name.INT)
    private int rating;

    public LocalDate getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(LocalDate startedDate) {
        this.startedDate = startedDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    
}

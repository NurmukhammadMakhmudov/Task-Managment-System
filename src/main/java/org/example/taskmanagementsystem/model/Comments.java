    package org.example.taskmanagementsystem.model;


    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import jdk.jfr.Timestamp;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDateTime;

    @Entity
    @Table(name = "comments")
    @Data
    public class Comments {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String comment;

        @Timestamp
        @Column(name = "creation_date")
        private LocalDateTime creationDate;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "task_id")
        @JsonManagedReference   ("comments")
        private Task task;

        @ManyToOne
        private Users authors;


        public Comments() {
            this.creationDate = LocalDateTime.now();
        }
    }

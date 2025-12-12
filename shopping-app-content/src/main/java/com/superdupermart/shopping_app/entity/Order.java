package com.superdupermart.shopping_app.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;


import java.io.Serializable;
import java.util.Date;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "`order`") // Use backticks since "order" is a reserved word
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_placed")
    private Date datePlaced;
    @Column(name = "order_status")
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;


}

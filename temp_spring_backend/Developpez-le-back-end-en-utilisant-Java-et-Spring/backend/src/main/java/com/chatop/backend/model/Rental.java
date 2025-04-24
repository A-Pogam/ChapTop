package com.chatop.backend.model;

import jakarta.persistence.*;
import com.chatop.backend.model.User;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "RENTALS")
public class Rental {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private Double surface;
  private Double price;
  private String picture;
  private String description;
  private Timestamp createdAt;
  private Timestamp updatedAt;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  @OneToMany(mappedBy = "rental")
  private List<Message> messages;
}

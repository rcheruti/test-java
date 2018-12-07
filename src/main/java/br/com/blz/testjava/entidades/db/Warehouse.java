package br.com.blz.testjava.entidades.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "warehouse")
public class Warehouse {
  
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;
  
  
  @Column(name = "locality")
  private String locality;
  
  @Column(name = "quantity")
  private int quantity;
  
  @Column(name = "type")
  private String type;
  
  
  @ToString.Exclude 
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inventory_id")
  @JsonIgnore
  private Inventory inventory;
  
  // ---------------------
  
  
}

package service.payment.outbox;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "outbox")
public class Outbox {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String data;  // id товара, статус платежа

  public Outbox(String data) {
    this.data = data;
  }

  protected Outbox() {
  }

  public String getData() {
    return data;
  }
}


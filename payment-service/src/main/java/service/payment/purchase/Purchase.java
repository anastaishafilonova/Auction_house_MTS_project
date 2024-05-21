package service.payment.purchase;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase")
public class Purchase {
  @Column(name="balance")
  private int money;
  @Id
  @Column(name="user_id")
  private Long userId;


  public Purchase(Long userId, int money) {
    this.money = money;
    this.userId = userId;
  }

  protected Purchase() {
  }

  public int getMoney() {
    return money;
  }

  public void setMoney(int money) {
    this.money = money;
  }
}

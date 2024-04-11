# MTS_project_auction_house
## Проект "Аукционный дом". Java, 2 семестр.  
### Команда: Лебедева Мария, Филонова Анастасия, Шаров Святослав.  
## Микросервисы:  
### 1. ItemService
Сущности:  
`Customer` - класс покупателя, имеет следующие поля: _customer_id_, _first_name_, _last_name_, _balance_ (сколько денег на счету), _goods_ (список купленных товаров), _bet_ (текущая ставка)  
`Good` - класс товара, имеет следующие поля: _good_id_, _name_, _price_ (по какой ставке товар доступен сейчас), _owner_ (тот, кто купил), _seller_ (тот, кто продаёт), _time_ (оставшееся время аукциона), _start_time_ (время начала аукциона для данного товара), _status_ (куплен товар или ещё нет), _min_bet_ (минимальная ставка, на которую можно повышать стоимость покупки товара, задаётся продавцом)
`Seller` - класс продавца, имеет следующие поля: _seller_id_, _first_name_, _last_name_, _balance_ (сколько денег на счету), _goods_ (cписок продаваемых товаров)  
Для каждой сущности должны быть созданы отдельные репозитории: `CustomerRepository`, `GoodRepository`, `SellerRepository`.    
Все методы взаимодействия с сущностями прописываются в сервисах:  
`CustomerService`: createCustomer, deleteCustomer, updateCustomer, getCustomer, getBalance (по _customer_id_), increaseBalance (по _customer_id_ на какую-то конкретную сумму), decreaseBalance (по _customer_id_ на какую-то конкретную сумму), buyGood, deleteGood, increaseBet (по _customer_id_ на какую-то конкретную сумму);  
`GoodService`: createGood, deleteGood, updateGood, getCurrentPrice, changeCurrentPrice;
`SellerService`: createSeller, deleteSeller, getSellerByGood (узнать продавца по товару), getSeller;
Связи сущностей:  
`Custimer` и `Good` - oneToMany;
`Seller` и `Good` - oneToMany;
`Good` и `Seller` - manyToOne;  
### 2. AuctionService
В данном сервисе происходят основные действия аукциона:  
1. 

### 3. AuthorizationService  
В данном сервисе происходит авторизация, регистрация пользователей, назначение ролей.  
Пользователь может зарегистрироваться и авторизоваться под одной из ролей: customer / seller. 
Должен быть контроллер `AuthController` c endpoints: logIn, logOut, sigh up, checkRole.
И соответствующий сервис `AuthService`. Важно, что пользователь не должен иметь доступа к редактированию товаров других владельцев, поэтому при редактировании товара должна происходить проверка, что данный товар действительно принадлежит этому пользователю. Для этих целей можно сделать табличку товар-владелец.

### 4. PaymentService  
В данном сервисе заложена следующая логика:  
1. Перед тем, как покупатель назначает свою ставку, должна пройти проверка, что на его балансе достаточно средств.   
2. Во время покупки деньги со счёта покупателя должны быть отправлены на счёт владельца товара.  
3. Пользователь должен уметь снимать деньги со своего счёта (не более того, что там лежит).  
4. Пользователь должен уметь пополнять свой счёт.  
Должен быть контроллер `PayController` со следующими endpoints: checkBalance, payGood, withdrawMoney, depositMoney;
И соответствующий сервис `PayService` с теми же методами. 

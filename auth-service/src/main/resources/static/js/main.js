
var myModal = new bootstrap.Modal(document.getElementById('exampleModal'), {
  keyboard: false
})

var enterModal = new bootstrap.Modal(document.getElementById('enterModal'), {
  keyboard: false
})



// Добавить счетчик числа товаров в локальное хранилище
if (!localStorage.getItem('product')) {
  localStorage.setItem('product', JSON.stringify([]))
}

// Добавить счетчик числа ставок в локальное хранилище
if (!localStorage.getItem('bet')) {
  localStorage.setItem('bet', JSON.stringify([]))
}

// Добавить счетчик людей в локальное хранилище
if (!localStorage.getItem('people')) {
  localStorage.setItem('people', JSON.stringify([]))
}

document.querySelector('button.add-new-people').addEventListener('click', function () {
  let firstName = document.getElementById('people-name').value
  let lastName = document.getElementById('people-last-name').value
  let login = document.getElementById('people-login').value
  let password = document.getElementById('people-password').value
  let role = document.querySelector('input[type="radio"]:checked').value
  if (firstName && lastName && login && password && role) {
    document.getElementById('people-name').value = ''
    document.getElementById('people-last-name').value = ''
    document.getElementById('people-login').value = ''
    document.getElementById('people-password').value = ''
    document.getElementsByName('people-role').value = ''
    let people = JSON.parse(localStorage.getItem('people'))
    if (!people[0]) {
      people = [];
    }
    people.push(['people_' + people.length, firstName, lastName, login, password, role])
    localStorage.setItem('people', JSON.stringify(people))
    fetch('/api/register', {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({firstName: firstName, lastName: lastName, username: login, password: password, roles: role})
    }).then(response => response.json())
    .then(data => {
        let userRole = data.role;
        let userId = data.userId;
        document.cookie = "userRole=" + userRole;
        document.cookie = "userId=" + userId;
    });
    if (role == "SELLER") {
      update_seller()
    } else {
      update_seller()
      update_customer()
    }
    enterModal.hide()
  } else {
    Swal.fire({
      icon: 'error',
      title: 'Ошибка',
      text: 'Пожалуйста заполните все поля!',
    })
  }
})

function update_customer(){
  document.getElementById("product").style.display = "none"
  var elements = document.getElementsByClassName("saw");
  for (var i in elements) {
    if (elements.hasOwnProperty(i)) {
      elements[i].style.display = "block"
    }
  }
  // document.getElementsByClassName("saw").style.display = "block"
}


// // Сохранение нового товара
// document.querySelector('button.add-new').addEventListener('click', function () {
//   let name = document.getElementById('product-name').value
//   let price = document.getElementById('product-price').value
//   let startTime = document.getElementById('localdate-start').value
//   let finishTime = document.getElementById('localdate-finish').value
//   let minBet = document.getElementById('product-min-bet').value
//   let url = document.getElementById('product-url').value
//   if (name && price && startTime && finishTime && minBet && url) {
//     document.getElementById('product-name').value = ''
//     document.getElementById('product-price').value = ''
//     document.getElementById('localdate-start').value = ''
//     document.getElementById('localdate-finish').value = ''
//     document.getElementById('product-min-bet').value = ''
//     document.getElementById('product-url').value = ''
//     let product = JSON.parse(localStorage.getItem('product'))
//     if (!product[0]) {
//       product = [];
//     }
//     product.push(['product_' + product.length, name, price, startTime, finishTime, minBet, url])
//     localStorage.setItem('product', JSON.stringify(product))
//     // update_product()
//     myModal.hide()
//   } else {
//     Swal.fire({
//       icon: 'error',
//       title: 'Ошибка',
//       text: 'Пожалуйста заполните все поля!',
//     })
//   }
// })


update_product();

function update_seller(){
  let tbody = document.querySelector('.profile-right-seller')
  tbody.innerHTML = ""
  tbody.insertAdjacentHTML('beforeend',
    `
    <button class="add_product profile__add-btn" type="button" data-bs-toggle="modal"
              data-bs-target="#exampleModal" id="product"></button>

              <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Добавить товар</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <label for="product-name" class="form-lable text-list">Название товара</label>
                    <input type="text" class="form-control mb-3" id="product-name">
      
                    <label for="product-price" class="form-lable text-list">Начальная цена</label>
                    <input type="number" class="form-control mb-3" id="product-price">
      
                    <label for="localdate-start" class="form-lable text-list">Дата и время начала аукциона</label>
                    <input type="datetime-local" class="form-control mb-3" id="localdate-start" name="date"/>
      
                    <label for="localdate-finish" class="form-lable text-list">Дата и время окончания аукциона</label>
                    <input type="datetime-local"class="form-control mb-3" id="localdate-finish" name="date"/>
      
                    <label for="product-min-bet" class="form-lable text-list">Минимальная ставка</label>
                    <input type="number" class="form-control mb-3" id="product-min-bet">
      
                    <label for="product-url" class="form-lable text-list">URL картинки</label>
                    <input type="url" class="form-control mb-3" id="product-url">
      
                    <button type="submit" class="add-new add-button text-list">Сохранить</button>
                  </div>
                </div>
              </div>
            </div>

    `
  )
  // Сохранение нового товара
document.querySelector('button.add-new').addEventListener('click', function () {
  let name = document.getElementById('product-name').value
  let price = document.getElementById('product-price').value
  let startTime = document.getElementById('localdate-start').value
  let finishTime = document.getElementById('localdate-finish').value
  let minBet = document.getElementById('product-min-bet').value
  let url = document.getElementById('product-url').value
  if (name && price && startTime && finishTime && minBet && url) {
    document.getElementById('product-name').value = ''
    document.getElementById('product-price').value = ''
    document.getElementById('localdate-start').value = ''
    document.getElementById('localdate-finish').value = ''
    document.getElementById('product-min-bet').value = ''
    document.getElementById('product-url').value = ''
//    let product = JSON.parse(localStorage.getItem('product'))
//    if (!product[0]) {
//      product = [];
//    }
    const cookieId = (document.cookie.match('(^|; )' + encodeURIComponent('userId') + '=([^;]+)') || []).pop() || null;

    fetch('/api/auth/product', {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({name: name, price: price, sellerId: cookieId, startTime: startTime, finishTime: finishTime, minBet: minBet, urlPicture: url})
        }).then(res => update_product());

//    product.push(['product_' + product.length, name, price, startTime, finishTime, minBet, url])
//    localStorage.setItem('product', JSON.stringify(product))
    myModal.hide()
  } else {
    Swal.fire({
      icon: 'error',
      title: 'Ошибка',
      text: 'Пожалуйста заполните все поля!',
    })
  }
});
}

function update_product() {
  let tbody = document.querySelector('.list')
  tbody.innerHTML = "";
  fetch('/api/auth/product/all', {method: 'GET', headers: {}}).then(res => res.json())
  .then(data => {
  let product = data;
  for (let i = 0; i < product.length; i++) {
      console.log(product[i]);
      let beginTime = new Date(product[i].startTime);
      let endTime = new Date(product[i].finishTime);
      tbody.insertAdjacentHTML('beforeend',
        `
              <article class="cards-block__card">
                  <img class="cards-block__img" src=${product[i].urlPicture} alt="${product[i].name}" onerror="this.src='https://images.unsplash.com/photo-1573865526739-10659fec78a5?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Y2F0fGVufDB8fDB8fHww'">
                  <div class="cards-block__txt">
                      <h2 class="cards-block__title">Название: ${product[i].name}</h2>
                  </div>
                  <div class="cards-block__txt">
                      <h2 class="cards-block__title">Начальная цена: ${product[i].price}</h2>
                  </div>
                  <div class="cards-block__txt">
                      <h2 class="cards-block__title">Время начала: ${beginTime.toLocaleString().slice(0, -3)}</h2>
                  </div>
                  <div class="cards-block__txt">
                      <h2 class="cards-block__title">Время окончания: ${endTime.toLocaleString().slice(0, -3)}</h2>
                  </div>
                  <div class="cards-block__txt">
                      <h2 class="cards-block__title">Минимальная ставка: ${product[i].minBet}</h2>
                  </div>
                  <div class="cards-block__txt">
                      <h2 class="cards-block__title">Текущая ставка: </h2>
                  </div>
                  <button class="saw product_delete btn-primary" type="button" data-bs-toggle="modal"
                    data-bs-target="#secondModal" id="bet" data-product="${product[i].productId}">Сделать ставку</button>


              </article>

              <div class="modal fade" id="secondModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h1 class="modal-title fs-5" id="exampleModalLabel">Сделать ставку</h1>
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                      <label for="product-bet" class="form-lable text-list">Размер ставки</label>
                      <input type="number" class="form-control mb-3" id="product-bet">

                      <button type="submit" class="add-bet add-button text-list">Сделать ставку</button>
                    </div>
                  </div>
                </div>
            </div>
              `
      )
    }
    if(product.length){
      var secondModal = new bootstrap.Modal(document.getElementById('secondModal'), {
      keyboard: false
    })

    document.querySelector('button.add-bet').addEventListener('click', function () {
      let product_bet = document.getElementById('product-bet').value
      if (product_bet) {
        document.getElementById('product-bet').value = ''
        let bet = JSON.parse(localStorage.getItem('bet'))
        bet.push(['bet_' + bet.length, product_bet])
        localStorage.setItem('bet', JSON.stringify(bet))
        // update_product()
        secondModal.hide()
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Ошибка',
          text: 'Пожалуйста заполните все поля!',
        })
      }
    })
    }
    });
  }

  // let product = JSON.parse(localStorage.getItem('product'))




 //<button class="product_delete btn-primary" type="button" data-bs-toggle="modal" 
//data-bs-target="#secondModal" id="bet" data-product="${product[i][0]}">Сделать ставку</button>


// авторизация (кнопка войти)
//profile__add-btn-enter
const enterBtn = document.querySelector('button.enter-old-people');
enterBtn.addEventListener('click', function () {
  let login = document.getElementById('people-login1').value
  let password = document.getElementById('people-password1').value
  if (login && password) {
    document.getElementById('people-login1').value = ''
    document.getElementById('people-password1').value = ''
    console.log(login + " " + password)
    fetch('/api/enter', {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({username: login, password: password})
    }).then(response => response.json())
    .then(data => {
        let role = data.statusRole;
        console.log(role)
        if (role === "Пароль некорректен" || role === "Имя пользователя введено неверно") {
            Swal.fire({
                  icon: 'error',
                  title: 'Ошибка',
                  text: 'Введены некорректные данные',
                })
        }
        else if (role == "SELLER") {
          update_seller()
        } else {
          update_seller()
          update_customer()
        }
        enterModal.hide()
      })} else {
        Swal.fire({
          icon: 'error',
          title: 'Ошибка',
          text: 'Пожалуйста заполните все поля!',
        })
  }
 });


// пополнение баланса

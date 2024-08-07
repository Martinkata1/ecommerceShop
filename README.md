# E-Commerce Website for Turkish Products

This project is an e-commerce website primarily focusing on Turkish products. It includes an admin panel, a client panel, and a library that connects everything. The project is being developed for my father, ensuring robust validation and secure operations.

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Installation](#installation)
5. [Usage](#usage)
6. [Screenshots](#screenshots)
7. [Contributing](#contributing)
8. [License](#license)
9. [Contact](#contact)

## Introduction

This e-commerce platform allows users to browse, search, and purchase various Turkish products. Administrators can manage products, orders, and users through a dedicated admin panel.

## Features

- User Authentication (Registration, Login, Logout)
- Product Listing and Details
- Shopping Cart and Checkout
- Order Management
- Admin Panel for Product and Order Management
- CRUD operations for products, orders, and users
- Database integration with proper validation and checks
- Responsive design with Thymeleaf templates

## Technologies Used

- **Backend:** Java, Spring Boot
- **Frontend:** HTML, CSS, JavaScript, Thymeleaf
- **Database:** MySQL (or any other database you use)
- **Libraries:** Lombok
- **Tools:** Maven, Git

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/e-commerce-website.git

2. Navigate to the project directory:
   ```bash
   cd e-commerce-website

3. Install dependencies:
   ```bash
   mvn install
4. Configure the database settings in application.properties.
5. Run the application:
   ```bash
   mvn spring-boot:run

## Usage
1. Access the website at http://localhost:8019/admin/ for Admin Panel and http://localhost:8020/shop/ for Customer Panel.
2. Log in as an admin to manage products, orders, and users. 
3. Register or log in as a user to browse and purchase products.

## Screenshots

![Admin Login](screenshots/admin_login.png)
- *Login for Admin, you can create and log.*

![Admin Homepage](screenshots/admin.png)
- *This is admin homepage.*

![Admin Categories](screenshots/categories.png)
- *You can add/delete Categories.*

![Admin Products](screenshots/products.png)
- *You can add/delete Products.*

![Admin Orders](screenshots/admin_orders.png)
- *You can see and apply/finish Orders.*

![Customer Login](https://github.com/user-attachments/assets/647753bb-d1ee-43c1-a2e0-1e3cc8b78576)
- *Login for every Customer, you can create and log.*

![Customer Homepage](screenshots/shop.png)
- *This is customer homepage.*

![Product Page](screenshots/main_shop.png)
- *Menu, you can see overall products with categories.*

![Product Page Special](screenshots/shop_with_search_categories_and_etc.png)
- *This is Real Shop, you can every product with categories, search by name product, sort by categories and see how much products has every category.*

![Chart With Products Page](screenshots/chart_with_products.png)
- *When you add some products, your chart will have products and you can order.*

![Finishing Order](screenshots/finishing_order.png)
- *When you click "Продължи", you will see last Panel for eventually customizing customer's information, and if you continue, you will finish the order.*

![Finished Order](screenshots/finished_order.png)
- *Finished? You see your receipt for products.*

![History of your Orders](screenshots/history_of_your_order.png)
- *Once you finish your order, you can see in "Вашите поръчки"  yours orders. When is added and when will be shipped, you can cancel the order.*

![Contacts US](screenshots/contacts.png)
- *Contacts, when you have problem or want to visit us, contacts about us.*

![Information Page](screenshots/information_with_map.png)
- *More information about us and location with Google Map.*

![DB Files](screenshots/db_files.png)
- *Every table for this project in base data(This is from MySQL, i think you can use DBeaver for example too).*

![Problem in DB](screenshots/problem_need_fix.png)
- ***WARNING**: Please in **country** and **cities** add some information, because the order can't be finished, if it is empty. For example in **country** add country_id 
 is id number of country and name the country next to it. In **cities**, just add id number city, name and exist country_id number. After screenshots, i will explain better how to Setup the Data Base*

## Database Setup
**WARNING**: Please ensure that you have populated the country and cities tables with some initial data, otherwise the order cannot be completed. Here are the steps to add the necessary information:

*Adding Data to the Country Table*
1. Open your database management tool (e.g., MySQL Workbench).
2. Execute the following SQL query to insert data into the country table:
   ```sql
   INSERT INTO country (country_id, name) VALUES (1, 'Turkey');
- country_id is the ID number of the country.
- name is the name of the country.

*Adding Data to the Cities Table*
1. Execute the following SQL query to insert data into the cities table:
   ```sql
   INSERT INTO cities (city_id, name, country_id) VALUES (1, 'Istanbul', 1);
- city_id is the ID number of the city.
- name is the name of the city.
- country_id is the ID number of the corresponding country, creating a link between the city and the country.

## Contributing
If you wish to contribute to this project, please fork the repository and submit a pull request. You can also open an issue to discuss potential improvements or report bugs.

1. Fork the repository
2. Create a new branch (git checkout -b feature-branch)
3. Commit your changes (git commit -m 'Add some feature')
4. Push to the branch (git push origin feature-branch)
5. Open a pull request
   
## License
This project is not licensed.

## Contact
For any questions or comments, feel free to reach out:

- Name: Martin Kostadinov
- Email: marti.kostadinov2003@gmail.com
- instagram: https://www.instagram.com/marti.kostadinov7777
- Linkedin: https://www.linkedin.com/in/marti-kostadinov-954483243
- Facebook: https://www.facebook.com/profile.php?id=100081383798329

# assignment2
assignment 2 codes
in file named DatabaseConnection.java we created a connection with our database, and as a result of a successful connecting, il will show " customers" table
in authorcrud.java we create a new data row in authors table. we add ne author J.K.Rowling and author_id is calculated as lastID+1
after adding new author, we can add new book by this autor, "Harry Potter" what we basically did in newbookcrud.java
in file BooksWithOrders.java, using operation LEFT JOIN, we mixed tables books and orders, where we can see each book and data regarding its order(by who, when, what amount etc). we chose left join, because there can be case when no such book were ordered, but we want data about it.
in UpdateBookTitle.java we update book with id 2 , we change "To Kill a MockingBird" to "Go Set A Watchman" by same author, with id 2, Harper Lee
In RemoveBook.java we delete a row that contains data about book with id 6.
in transactions.java code will check the stock info before the order. If there enough books it will create order, otherwise it will say there are not enough books in stock.
in metadata.java following code will show us information regarding all tables, names of attributes, their datatypes and also primary, foreign keys

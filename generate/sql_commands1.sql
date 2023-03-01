SELECT * FROM menu_items;
SELECT COUNT(*) FROM menu_items;

SELECT * FROM inventory;
SELECT COUNT(*) FROM inventory;

SELECT * FROM recipes;
SELECT COUNT(*) FROM recipes;

SELECT * FROM orders;
SELECT COUNT(*) FROM orders;

SELECT * FROM order_items;
SELECT COUNT(*) FROM order_items;

SELECT * FROM sales;
SELECT COUNT(*) FROM sales;
--shows all available databases including their count
--Shows we have more than 20 items in our menu and enough items in our inventory to make all items.

SELECT * FROM sales WHERE total_sales = (SELECT MAX(total_sales) FROM sales);
--To find the row with the highest sales value and it is a game day

SELECT AVG(total_sales) FROM sales;
--average amount of sales a day to compare

SELECT * FROM sales WHERE sales_date = '2022-11-04'
--Comparing sales with the day before

SELECT * FROM orders ORDER BY order_id DESC LIMIT 10;
--shows the last set of orders

INSERT INTO orders (order_id, price, is_paid, order_time) VALUES (390787, 4.19 , 't' , '2023-01-01 10:00:13');
--added new order

INSERT INTO order_items (order_id, menu_item_id, quantity, item_id) VALUES (390787, 20 , 1 , 883843);
--added new order_item

SELECT SUM(total_sales) FROM sales;
--total_sales amount

SELECT * FROM orders JOIN order_items ON orders.order_id = order_items.order_id WHERE orders.order_time::date >= '2022-11-05' AND orders.order_time::date <= '2022-11-13';
--selects all order items in a date range and joins it with an order

SELECT menu_item_id, COUNT(*) as count FROM order_items GROUP BY menu_item_id ORDER BY count DESC LIMIT 1;
--shows the most common item ordered

SELECT * FROM menu_items JOIN recipes ON menu_items.menu_item_id = recipes.menu_item_id WHERE menu_items.menu_item_id = 27;
--shows the recipe of the most ordered item

SELECT * FROM sales ORDER BY total_sales DESC LIMIT 100;
--shows the dates with the highest orders

SELECT AVG(num_orders) as avg_orders_per_day
FROM (
    SELECT DATE(order_time) as order_date, COUNT(*) as num_orders
    FROM orders
    GROUP BY order_date
) as daily_orders
--Average number of orders a day

SELECT COUNT(*) FROM orders WHERE order_time::text LIKE '2022-11-05%';
--number of orders on a game day



#! /usr/bin/python3

import random
import numpy
import os
import psycopg2 as psy
from datetime import datetime, timedelta

SCRIPT_DIR = os.path.dirname(__file__)
DAYS = 365

conn = psy.connect(
    host='csce-315-db.engr.tamu.edu',
    database='csce315331_team_71',
    user='csce315331_team_71_master',
    password='71_TeaM'
)

sql = conn.cursor()
sql.execute('SELECT menu_item_id, menu_item_price FROM menu_items LIMIT 100;')

result = sql.fetchall()

ALL_FOOD = {key: value for key, value in zip([int(row[0]) for row in result], [
                                             float(row[1]) for row in result])}
MAIN = [37, 36, 1, 25, 2, 26, 18, 29, 34, 35, 6, 7,
        30, 31, 16, 17, 32, 33, 38, 19, 39, 3, 27, 4, 28]
RECIPE = {
    37: ['tortilla', 'chicken breast grilled', 'lettuce', 'ranch'],
    36: ['tortilla', 'chicken breast grilled', 'lettuce', 'ranch', 'potatoes', 'soda'],
    1: ['bun', 'bun', 'chicken breast', 'pickles']
}

filename3 = os.path.join(SCRIPT_DIR, "./sales.sql")
fileSales = open(filename3, "a")

filename4 = os.path.join(SCRIPT_DIR, "./insertEverything.sql")
fileInsert = open(filename4, "a")

order_id = 1

for i in range(1, DAYS + 1):

    ordersPerDay = random.randint(1000, 1500)
    totalSales = 0
    totalTaxed = 0

    initialTimestamp = datetime(2022, 1, 1, 10, 0, 0) + timedelta(days=i)

    if initialTimestamp.weekday() == 6:
        continue

    #Game Day
    if initialTimestamp.month == 11 and (initialTimestamp.day == 5 or initialTimestamp.day == 12):
        ordersPerDay += 600

    interval = (12 * 3600) / ordersPerDay

    filename = os.path.join(SCRIPT_DIR, "./day" + str(i) + "Orders.csv")
    fileOrders = open(filename, "a")
    fileOrders.write("price,is_paid,order_time\n")

    filename2 = os.path.join(SCRIPT_DIR, "./day" + str(i) + "OrderItems.csv")
    fileItems = open(filename2, "a")
    fileItems.write("order_id,menu_item_id,quantity\n")


    hasMain = False

    for j in range(1, ordersPerDay + 1):

        timestamp = initialTimestamp + \
            timedelta(seconds=(interval * j))

        items = numpy.random.choice([i for i in range(1, 11)], p=[
                                    0.20, 0.37, 0.20, 0.12, 0.06, 0.02, 0.01, 0.01, 0.005, 0.005])
#2022-11-05, 2022-11-12
        price = 0

        while items > 0:

            curMenItem = -1
            amount = 1

            if items >= 4 and not hasMain:
                curMenItem = MAIN[random.randint(0, 24)]
                hasMain = not hasMain
                amount = random.randint(2, 3)
            elif items >= 4:
                curMenItem = list(ALL_FOOD.keys())[random.randint(0, 48)]
                amount = random.randint(2, 3)
            elif items >= 3 and not hasMain:
                curMenItem = MAIN[random.randint(0, 24)]
                hasMain = not hasMain
            else:
                curMenItem = list(ALL_FOOD.keys())[random.randint(0, 48)]

            price += (ALL_FOOD[curMenItem] * amount)
            items -= amount

            fileItems.write(str(order_id) + "," +
                            str(curMenItem) + "," + str(amount) + "\n")

        priceTaxed = price * 1.0825
        fileOrders.write(f"{priceTaxed:.2f}," + "true," +
                         timestamp.strftime('%Y-%m-%d %H:%M:%S') + "\n")
        

        order_id += 1
        totalSales += price
        totalTaxed += (priceTaxed - price)

    salesDate = timestamp.strftime('%Y-%m-%d')

    fileSales.write(f"INSERT INTO sales (sales_date, total_sales, total_tax) VALUES ('{salesDate}', {totalSales:.2f}, {totalTaxed:.2f});\n")
    fileInsert.write(f"\COPY orders (price, is_paid, order_time) FROM './day{i}Orders.csv' CSV HEADER;\n")
    fileInsert.write(f"\COPY order_items (order_id, menu_item_id, quantity) FROM './day{i}OrderItems.csv' CSV HEADER;\n")

    fileOrders.close()
    fileItems.close()

fileSales.close()
fileInsert.close()

conn.commit()
sql.close()

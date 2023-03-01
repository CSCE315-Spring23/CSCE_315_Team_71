#! /usr/bin/python3
import os
import psycopg2 as psy
from datetime import datetime, timedelta

SCRIPT_DIR = os.path.dirname(__file__)

conn = psy.connect(
    host='csce-315-db.engr.tamu.edu',
    database='csce315331_team_71',
    user='csce315331_team_71_master',
    password='71_TeaM'
)

sql = conn.cursor()

initialTimestamp = datetime(2022, 1, 1, 10, 0, 0)
for i in range(1, 366):
    timestamp = initialTimestamp + timedelta(days=i)

    if timestamp.weekday() == 6:
        continue

    year = timestamp.strftime('%Y')
    date = timestamp.strftime('%d')
    month = timestamp.strftime('%m')

    sql.execute(f"SELECT price FROM orders WHERE order_time::date = '{year}-{month}-{date}';")

    result = sql.fetchall()
    totalSaleTaxed = sum([float(price[0]) for price in result])
    totalSale = totalSaleTaxed / 1.0825
    
    sql.execute(f"INSERT INTO sales (sales_date, total_sales, total_tax) VALUES ('{year}-{month}-{date}', {totalSale:.2f}, {(totalSaleTaxed - totalSale):.2f});")

conn.commit()
sql.close()

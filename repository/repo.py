import pymysql
from pymysql import cursors
from typing import Dict, Optional, Any

class DatabaseConnector:
    def __init__(self):
        self.conn = pymysql.connect(
            host="localhost",
            user="root",
            password="123456",
            database="chronoluxweb",
            cursorclass=pymysql.cursors.DictCursor
        )

    def fetch_one(self, query: str, params: tuple) -> Optional[Dict]:
        with self.conn.cursor() as cursor:
            cursor.execute(query, params)
            return cursor.fetchone()

    def fetch_all(self, query: str, params: tuple = ()) -> tuple[tuple[Any, ...], ...]:
        with self.conn.cursor() as cursor:
            cursor.execute(query, params)
            return cursor.fetchall()

    def close(self):
        self.conn.close()

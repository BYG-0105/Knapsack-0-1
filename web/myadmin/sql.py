from django.contrib import sql
import re
import pymysql

# 变量初始化
con = pymysql.connect(
    host='localhost',
    port=3306,
    user='root',
    passwd='JT123777jt_',
    db='test',
    charset='utf8',
    )

def insert(con,Weight,Value):
    # 数据库游标！
    cue = con.cursor()
    # print("mysql connect succes")  # 测试语句，这在程序执行时非常有效的理解程序是否执行到这一步
    # try-except捕获执行异常
    try:
        cue.execute(
            "insert into words (Weight,Value) values(%s,%s)",
            [Weight, Value, ])
        # 执行sql语句
        # print("insert success")  # 测试语句
    except Exception as e:
        print('Insert error:', e)
        con.rollback()
        #报错反馈
    else:
        con.commit()
        #真正的执行语句


def read(filename):
    # 按行读取txt文本文档
    with open(filename, 'r') as f:
        datas = f.readlines()
    # 遍历文件
    cue = con.cursor()
    try:
        cue.execute('TRUNCATE TABLE words')
    except Exception as e:
        print(e)
    for data in datas:
        txt = re.split(r' ', data)
        weight = txt[0]
        value = txt[1]
        insert(con, weight, value)
        # 调用insert方法
    print("数据插入完成！")
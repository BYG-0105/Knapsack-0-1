from django.db import models
from datetime import datetime

#员工账号信息模型
class User(models.Model):
    operate = models.CharField(max_length=50)    #员工账号
    password_hash = models.CharField(max_length=100)#密码
    password_salt = models.CharField(max_length=50)    #密码干扰值
    datasourse = models.CharField(max_length=10)
    update_at = models.DateTimeField(default=datetime.now)    #修改时间

    def toDict(self):
        return {'id':self.id,'username':self.username,'nickname':self.nickname,'password_hash':self.password_hash,'password_salt':self.password_salt,'status':self.status,'create_at':self.create_at.strftime('%Y-%m-%d %H:%M:%S'),'update_at':self.update_at.strftime('%Y-%m-%d %H:%M:%S')}

    class Meta:
        db_table = "user"  # 更改表名


class Beibao(models.Model):
    weight = models.IntegerField(max_length=10)
    value = models.IntegerField(max_length=10)

    def toDict(self):
        return {'id':self.id,'weight':self.weight,'value':self.value}

    class Meta:
        db_table = "beibao"  # 更改表名
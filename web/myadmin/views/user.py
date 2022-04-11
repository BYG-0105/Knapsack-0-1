# 员工信息管理的视图文件
from django.shortcuts import render
from django.http import HttpResponse
from myadmin.models import User

def index(request):
    '''浏览信息 '''
    umod = User.objects  # 获取它的实例对象
    ulist = umod.all()  # 获取所有信息
    context = {"userlist":ulist}  # 信息封装
    return render(request,"myadmin/user/index.html", context)# 返回内容，render做模板渲染（）,将context信息传到模板的indax中
    

def add(request):
    '''加载信息添加表单 '''
    pass

def insert(request):
    '''执行信息添加 '''
    pass

def delete(request, uid=0):
    '''执行信息删除 '''
    pass

def edit(request, uid=0):
    '''加载信息编辑表单 '''
    pass

def update(request, uid):
    '''执行信息编辑 '''
    pass


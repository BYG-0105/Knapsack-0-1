# 后台管理子路由
from django.urls import path

from myadmin.views import index
from myadmin.views import user
from myadmin.views import beibao

urlpatterns = [
    path('', index.index, name='myadmin_index'),  # 后台首页

    #  员工信息管理路由
    path('user/', user.index, name='myadmin_user_index'),  # 浏览
    path('user/add', user.add, name='myadmin_user_add'),  # 添加表单
    path('user/insert', user.insert, name='myadmin_user_insert'),  # 执行添加
    path('user/del/<int:uid>', user.delete, name='myadmin_user_delete'),  # 删除
    path('user/edit/<int:uid>', user.edit, name='myadmin_user_edit'),  # 加载编辑表单
    path('user/update/<int:uid>', user.update, name='myadmin_user_update'),  # 执行编辑

    path('beibao/', beibao.index, name='myadmin_beibao_index'),  # 浏览
]

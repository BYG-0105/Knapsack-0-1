# 移动端管理子路由
from django.urls import path

from moblie.views import index

urlpatterns = [
    path('', index.index, name='moblie_index'),
]

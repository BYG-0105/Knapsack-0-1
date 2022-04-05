from django.contrib import algorithm
import queue
import math

# 动态规划法
def bag(n, c, w, v):
    # 置零，表示初始状态
    value = [[0 for j in range(c + 1)] for i in range(n + 1)]
    for i in range(1, n + 1):
        for j in range(1, c + 1):
            value[i][j] = value[i - 1][j]
            # 背包总容量够放当前物体，遍历前一个状态考虑是否置换
            if j >= w[i - 1] and value[i][j] < value[i - 1][j - w[i - 1]] + v[i - 1]:
                value[i][j] = value[i - 1][j - w[i - 1]] + v[i - 1]
    # for x in value:
    #     print(x)
    return value


def show(n, c, w, value):
    print('最大价值为:', value[n][c])
    x = [False for i in range(n)]
    j = c
    for i in range(n, 0, -1):
        if value[i][j] > value[i - 1][j]:
            x[i - 1] = True
            j -= w[i - 1]
    print('背包中所装物品为:')
    for i in range(n):
        print(i+1, ' ', end='')
    print()
    for i in range(n):
        if x[i]:
            print('1', ' ', end='')
        else:
            print('0', ' ', end='')
        # if x[i]:
        #     print('第', i+1, '个,', end='')
        # out_excel(x)

# 快排的主函数，传入参数为一个列表，左右两端的下标
def QuickSort(list, v, w, low, high):
    if high > low:
        # 传入参数，通过Partitions函数，获取k下标值
        k = Partitions(list, v, w, low, high)
        # 递归排序列表k下标左侧的列表
        QuickSort(list, v, w, low, k - 1)
        # 递归排序列表k下标右侧的列表
        QuickSort(list, v, w, k + 1, high)


def Partitions(r, v, w, low, high):
    i = low
    j = high
    # 当left下标，小于right下标的情况下，此时判断二者移动是否相交，若未相交，则一直循环
    while i < j:
        while i < j and r[i] >= r[j]:
            j -= 1
        if i < j:
            temp = r[i]
            r[i] = r[j]
            r[j] = temp

            temp = v[i]
            v[i] = v[j]
            v[j] = temp

            temp = w[i]
            w[i] = w[j]
            w[j] = temp

            i += 1
        while i < j and r[i] >= r[j]:
            i += 1
        if i < j:
            temp = r[i]
            r[i] = r[j]
            r[j] = temp

            temp = v[i]
            v[i] = v[j]
            v[j] = temp

            temp = w[i]
            w[i] = w[j]
            w[j] = temp
        j -= 1
    return i


# 贪心法
def KnapSack(w, v, n, c):
    x = [0 for j in range(n+1)]
    maxValue = 0
    i = 0
    for i in range(0, n):
        if w[i] < c:
            x[i] = 1
            maxValue += v[i]
            c = c - w[i]
            # print(i)
        else:
            break
    # print('=========')
    # print(i)
    x[i] = float(c/w[i])
    maxValue += x[i]*v[i]
    return maxValue


# 回溯法
def test(capacity, w, v):
    vec_len = 2 ** (len(v) + 1) - 1  # tree `s size
    # vec_len = 10000000
    vec_v = [0 for j in range(vec_len)]
    vec_w = [0 for j in range(vec_len)]
    # print(vec_v)
    # vec_v = np.zeros(vec_len)
    # vec_w = np.zeros(vec_len)
    vec_w[0] = capacity
    que = queue.Queue()
    que.put(0)
    best = 0
    while (not que.empty()):
        current = que.get()
        level = int(math.log(current + 1, 2))
        if (vec_v[current] > vec_v[best]):
            best = current

        left = 2 * current + 1  # left child   index
        right = 2 * current + 2  # right child index

        if (left < vec_len and vec_w[current] - w[level] > 0 and vec_v[current] + v[level] > vec_v[best]):
            vec_v[left] = int(vec_v[current] + v[level])
            vec_w[left] = vec_w[current] - w[level]
            que.put(left)
        if (right < vec_len and sum(v[level + 1:-1]) + vec_v[current] > vec_v[best]):
            vec_v[right] = vec_v[current]
            vec_w[right] = vec_w[current]
            que.put(right)
    # print(vec_w[best], vec_v[best])
    print(vec_v[best])
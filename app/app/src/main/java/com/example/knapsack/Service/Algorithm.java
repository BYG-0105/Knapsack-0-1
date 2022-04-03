package com.example.knapsack.Service;

import com.example.knapsack.Bean.Goods;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Algorithm {
	/*构造器*/
	public Algorithm()
	{	}

	//贪心算法
	public List<Goods> Greedy(List<Goods> list)
	{
		int	C =  list.get(0).getWeight();
		int n =  (int)list.get(0).getValue();
		//计算每个物品单位重量价值比,对每个物品按照单位重量价值比进行排序
		knapsack(list);
		//初始化数组x
		for(int i = 1;i <= n;i++)
		{
			list.get(i).setSelect("No");
		}
		int maxValue= 0;//背包最大价值
		for (int i=1; list.get(i).getWeight()<C; i++ )
		{
			list.get(i).setSelect("Yes");//装入当前物品
			maxValue+=list.get(i).getValue() ;//更新背包价值
			C=C-list.get(i).getWeight() ;//更新背包容量
		}
		list.get(0).setSelect(maxValue+"");
		for(int i=1;i<=n-1;i++)
		{
			for(int j=i+1;j<=n;j++)
				if(list.get(i).getId()>list.get(j).getId())//冒泡排序
				{
					swap(list,i,j);
				}
		}
		return list;

	}



	double cw = 0.0;//当前背包重量
	double cp = 0.0;//当前背包中物品价值
	double bestp = 0.0;//当前最优价值
	//回溯函数
	void backtrack(int i,List<Goods> list)
	{
		int	C =  list.get(0).getWeight();
		int n =  (int)list.get(0).getValue();
		bound(i,list); //计算上界
		if(i>n)  //所有元素都已遍历到，回溯结束
		{
			bestp = cp;
			return ;
		}
		if(cw+list.get(i).getWeight()<=C)
		{
			//放入背包
			cw+=list.get(i).getWeight();
			cp+=list.get(i).getValue();
			list.get(i).setSelect("Yes");
			backtrack(i+1,list);
			cw-=list.get(i).getWeight();
			cp-=list.get(i).getValue();
		}
		if(bound(i+1,list)>bestp)//符合条件搜索右子数
			backtrack(i+1,list);
	}

	//计算上界函数
	double bound(int i ,List<Goods> list)
	{
		int	C =  list.get(0).getWeight();
		int n =  (int)list.get(0).getValue();
		double leftw= C-cw;
		double b = cp;
		while(i<=  n && list.get(i).getWeight()<=leftw)
		{
			leftw-=list.get(i).getWeight();
			b+=list.get(i).getValue();
			i++;
		}
		if(i<=n)
			b+=list.get(i).getValue()/list.get(i).getWeight()*leftw;
		return b;//返回上界值
	}

	private static <E> void swap(List<E> list,int index1,int index2) {
		//定义第三方变量
		E e=list.get(index1);
		//交换值
		list.set(index1, list.get(index2));
		list.set(index2, e);
	}
	//按单位价值排序
	void knapsack(List<Goods> list)
	{
		int i,j;
		int temp = 0;
		int	C =  list.get(0).getWeight();
		int n =  (int)list.get(0).getValue();
		for( i = 1;i <= n;i++)
		{
			Goods g = list.get(i);
			double d = g.getValue()/g.getWeight();
			// d = new BigDecimal(d.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
			g.setWvproportion( d);
			list.get(i).setWvproportion(g.getWvproportion());
			list.get(i).setSelect("No");
		}
		for(i=1;i<=n-1;i++)
		{
			for(j=i+1;j<=n;j++)
				if(list.get(i).getWvproportion()<list.get(j).getWvproportion())//冒泡排序
				{
					swap(list,i,j);
				}
		}
	}
	public List<Goods> Backtracking(List<Goods> list)
	{
		knapsack(list);
		backtrack(1,list);
		list.get(0).setSelect(bestp+"");
		int n =  (int)list.get(0).getValue();
		for(int i=1;i<=n-1;i++)
		{
			for(int j=i+1;j<=n;j++)
				if(list.get(i).getId()>list.get(j).getId())//冒泡排序
				{
					swap(list,i,j);
				}
		}
		return list;
	}







	//动态规划算法
	public List<Goods> Dynamic(List<Goods> list)
	{
		int i,j;
		int	C =  list.get(0).getWeight();
		int n =  (int)list.get(0).getValue();
		int[][] V = new int [10000][10000];
		for (i=0; i<=n; i++)   //初始化第0列
		{
			V[i][0]=0;
		}
		for (j=0; j<=C; j++)   //初始化第0行
		{
			V[0][j]=0;
		}
		for (i=1; i<=n; i++)   //计算第i行，进行第i次迭代
		{
			for (j=1; j<=C; j++)
			{
				if (j<list.get(i).getWeight())
				{
					V[i][j]=V[i-1][j];
				}
				else
				{
					if(V[i-1][j] > V[i-1][j-list.get(i).getWeight()]+list.get(i).getValue())
					{
						V[i][j]=V[i-1][j];
					}
					else
					{
						V[i][j]= (int) (V[i-1][j-list.get(i).getWeight()]+list.get(i).getValue());
					}
				}

			}
		}
		j=C;    //求装入背包的物品
		for (i=n; i>0; i--)
		{
			if (V[i][j]>V[i-1][j])
			{
				list.get(i).setSelect("Yes");
				j=j-list.get(i).getWeight();
			}
			else
			{
				list.get(i).setSelect("No");
			}
		}
		list.get(0).setSelect(V[n][C]+"");//V[n][C]----背包取得的最大价值

		for( i=1;i<=n-1;i++)
		{
			for( j=i+1;j<=n;j++)
				if(list.get(i).getId()>list.get(j).getId())//冒泡排序
				{
					swap(list,i,j);
				}
		}
		return list;
	}



	public static int partition(List<Goods> list,int left,int right){
		int pivot = left;
		int id = list.get(pivot).getId();
		int weight =  list.get(pivot).getWeight();
		double value = list.get(pivot).getValue();
		double wv = list.get(pivot).getWvproportion();



		while(left < right){
			while(left<right && list.get(right).getWvproportion() > list.get(pivot).getWvproportion())
			{
				right--;
			}
			list.get(left).setId(list.get(right).getId());
			list.get(left).setWeight(list.get(right).getWeight());
			list.get(left).setValue(list.get(right).getValue());
			list.get(left).setWvproportion(list.get(right).getWvproportion());

			while(left < right && list.get(left).getWvproportion() <= list.get(pivot).getWvproportion())
			{
				left++;
			}
			list.get(right).setId(list.get(left).getId());
			list.get(right).setWeight(list.get(left).getWeight());
			list.get(right).setValue(list.get(left).getValue());
			list.get(right).setWvproportion(list.get(left).getWvproportion());

		}
		list.get(left).setId(id);
		list.get(left).setWeight(weight);
		list.get(left).setValue(value);
		list.get(left).setWvproportion(wv);

		return left;
	}
	public static List<Goods> quickSort(List<Goods> list,int left,int right){
		int middle;
		if(left < right){
			middle = partition(list,left,right);
			quickSort(list,left,middle-1);
			quickSort(list,middle+1,right);
		}
		return list;
	}

}


/*
//回溯算法
	public int[] Backtracking(int w[], int v[], int n,int C)
	{
	   int cw=0, cv=0,bestv=0,k=0;
	   int[] x = new int [1000];
	   int[] bestx = new int [1000];
       for (int i = 0; i < n ;i++)
			   x[i]=2;
			while (k>=0)
			{
				x[k]=x[k]-1;
			   while (x[k]>=0)
			   {
			    if (cw+w[k]<=C)
			    {
			    	if (bound(k+1,cw,cv,n,C,w,v) > bestv)
			    	{
			    		cv=cv+v[k]*x[k];
			    		cw=cw+w[k]*x[k];
			    		break;
			    	}
			    	else
			    	{
			    		x[k]=x[k]-1;
			    	}
			   }
			  else
			  {
				  x[k]=x[k]-1;
			  }
			 }
			if (x[k]>=0 && k==n) { //得到一种装法
				if (cv > bestv)
				{
					bestv=cv;
					for( int i=1;i<=n;i++)
						bestx[i]=x[i];
				}
			if (x[k]>=0 && k<n)
				k=k+1;
			//安排下一个物品
			else {
			cw=cw-w[k]*x[k];
			cv=cv-v[k]*x[k];
			x[k]=2;
			//重置x[k],回溯
			k=k-1;
			}
		}
	  }
			for(int j1 = 0;j1 <= n;j1++)
	 		{
	 			 System.out.println(x[j1]+"i   " );

	 		}

	 return x;

	}
	//计算上界函数
	double bound(int i,int cw,int cv,int n, int C,int w[],int v[])
	{
	    double leftw= C-cw;
	    double b = cv;
	    while(i<=n && w[i]<=leftw)
	    {
	        leftw-=w[i];
	        b+=v[i];
	        i++;
	    }
	    if(i<=n)
	        b+=v[i]/w[i]*leftw;
	    return b;

	}



 	//快速排序
	int Partition(double r[][], int first, int end)//划分
	{
		int i = first, j = end;
		//初始化待划分区间
		while (i < j)	{
			while (i < j && compare(r[i][0] ,r[j][0]) <= 0) j--;
			//右侧扫描
			if (i < j)
			{
				//将较小记录交换到前面

				 System.out.println(r[i][1]+"i" );
   			     System.out.println(r[i][0]+"i"  );
				 System.out.println(r[j][1]+"j" );
   			     System.out.println(r[j][0]+"j"  );
				double[] temp = new double[100];

				temp = r[i]; r[i] = r[j]; r[j] = temp;
				 System.out.println(r[i][1]+"i" );
   			     System.out.println(r[i][0]+"i"  );
				 System.out.println(r[j][1]+"j" );
   			     System.out.println(r[j][0]+"j"  );

				i++;
			}
			while (i < j && compare(r[i][0] ,r[j][0]) <= 0) i++;
			//左侧扫描
			if (i < j)
			{
				//将较大记录交换到后面
				double[] temp = new double[100];
				temp = r[i]; r[i] = r[j]; r[j] = temp;


				j--;
			}
		}
		return i;												// 返回轴值记录的位置
	}


	public double[][] QuickSort(double r[][], int first, int end){
		//快速排序
		int pivot;
		for(int i = 0;i <= end;i++)
	{
		 System.out.print(r[i][1]+"i   " );
		     System.out.println(r[i][0]+"i"  );
	}
		 System.out.println("i                 "  );


		if (first < end) {
			pivot = Partition(r, first, end);
			//划分，pivot是轴值在序列中的位置
			r=QuickSort(r, first, pivot - 1);
			//求解子问题1，对左侧子序列进行快速排序
			r=QuickSort(r, pivot + 1, end);
			//求解子问题2，对右侧子序列进行快速排序
			for(int i = 0;i <= end;i++)
		{
			 System.out.print(r[i][1]+"i   " );
			     System.out.println(r[i][0]+"i"  );
		}
			 System.out.println("i                 "  );
		}

      return r;
	}

	//比较大小
	int compare(double d1,double d2)
	{
		BigDecimal d11 = new BigDecimal(d1);
		BigDecimal d22 = new BigDecimal(d2);
		int res =  d11.compareTo(d22); 
	    return res;
	}
	
 
*/
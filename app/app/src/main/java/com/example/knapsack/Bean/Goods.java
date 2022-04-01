package com.example.knapsack.Bean;

public class Goods {
    /* 货品对象的属性值 */
	private int weight; //货品重量
	private double value; //货品价值
    private double wvproportion; //货品价值重量比

    /* 构造器 */
	public Goods(int weight,double value,double wvproportion)
	{
		this.weight = weight;
		this.value = value;
		this.wvproportion = wvproportion;
	}

    /* get和set函数*/
	public int getWeight()
   {
	  return weight;
   }
   public void setWeight(int weight)
  {
    this.weight = weight;
   }
   public double getValue()
   {
	   return value;
   }

    public void setValue(double value) {
        this.value = value;
    }

    public double getWvproportion() {
        return wvproportion;
    }

    public void setWvproportion(double wvproportion) {
        this.wvproportion = wvproportion;
    }
}

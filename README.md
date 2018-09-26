
设计模式--静态工厂、简单工厂方法模式案例分析

# 简介

1.学习本篇文章，了解简单工厂设计模式的使用场景。
2.如何使用简单工厂模式。
3.简单工厂模式能解决什么问题？回到优缺点。

# 场景

现在有一家外卖小店需要从生产一份外卖开始进行考虑设计，当客户在网上点出不同味道的菜时，外卖小店就将按照不同的订单进行生产出菜品，然后进行打包、等待外卖小哥获取、赠送给客户等不同的几道工序，才算完成一单，但是后期由于生意很好，客户评价很高，有些地方的老板想加盟本店了，那么可能就会出现不同味道的菜单，还有一些本地的特色菜也会加上菜单，这时，客户就能点到更多的菜品了，为了达到某些商家快速而且合理的进行管理，你是怎么设计这个方案的呢？

# 分析

一般情况下客户点餐的时候，都会查看平分高低，还有出货次数，如果人气好的，肯定要大量生产，而没有人点的，则可以考虑去除，所以我们通过以上的考虑，需要封装创建对象的代码，如将生产商品的对象封装起来，这样，我们出掉或者添加的时候，都只修改这部分的代码，那么是哪个生产商品呢？答案肯定是店家了，所以我们称这个生产商品的店家为“工厂”。
接下来我们进行分析以上代码该如何展现出来呢？我们将通过两种方式来进行编写。

## 静态工厂模式
如何实现呢？我们先来看看本例的类图

<div align=center>
<img src="https://upload-images.jianshu.io/upload_images/3012005-407ecb6ca3265939.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/600">
</div>
1.创建一个美食店，`MealStore.class`
```JAVA

/**
 * Author Eirunye
 * Created by on 2018/9/19.
 * Describe
 */
public class MealStore {


    public Meal submitOrderMeal(String type) {

        Meal meal;
        //利用静态工厂方法生成产品
        //重点(静态工厂实现代码)
        meal = SimpleMealsFactory.createMeal(type);

        //以下方法是进行一些设计而已不是静态工厂方法的模块，是产品的一些操作而已，无关紧要的
        //店家准备中...
        meal.mealPreparation();
        //打包
        meal.bake();
        //获取
        meal.getMeal();
        //配送
        meal.send();

        return meal;
    }
}
```
2.创建一个静态工厂`SimpleMealsFactory.class`
```JAVA

/**
 * Author Eirunye
 * Created by on 2018/9/18.
 * Describe 建立一个简单静态工厂，该工厂生产不同的菜品（美食）
 * Tip: 提示：这是不是我们经常使用的Util类的编写方式?
 */
public class SimpleMealsFactory {

    //将生产产品（美食放在这里）
    public static Meal createMeal(String type) {

        Meal meal = null;

        if ("crayfish".equals(type)) {
            meal = new CrayfishMeal(); //创建什么类型的产品(美食)让子类来操作
        } else if ("roastChicken".equals(type)) {
            meal = new RoastChicken();
        } else if ("A".equals(type)) {
         
        } else if ("B".equals(type)) {

        }
        // ...
        return meal; //返回的是美食商品
    }
}
```
3.创建产品美食抽象基类`Meal.class`,这样的话我们就可以交给子类来完成商品的生产。
```JAVA
/**
 * Author Eirunye
 * Created by on 2018/9/19.
 * Describe  定义一个抽象产品接口、这里也可以是抽象类
 */
public abstract class Meal {
public List mealInfo = new ArrayList();

    public void mealPreparation() {
        System.out.println("您的商品准备中..."+this.name);
        System.out.println("adding material...");
        System.out.println("adding  condiment...");
        System.out.println("adding mealInfo...");

        for (int i = 0, len = mealInfo.size(); i < len; i++) {
            System.out.println(" [" + mealInfo.get(i) + "]");
        }
    }
    public void bake() {
        System.out.println("进行打包,只需1分钟就能打包完成!");
    }
    public void getMeal() {
        System.out.println("外卖小哥获取美食,外卖小哥可能需要花费1~20分钟来获取美食!");
    }
    public void send() {
        System.out.println("配送给客户,配送需要大概5~40分钟送达!");
    }
}
```
4.这里模拟具体产品，必须去实现`Meal`接口或者去派生该抽象类，如下小龙虾美食:`CrayfishMeal.class`。
```
/**
 * Author Eirunye
 * Created by on 2018/9/19.
 * Describe 模拟产品(具体的美食--小龙虾)
 */
public class CrayfishMeal extends Meal {
    public CrayfishMeal() {
        setName("香辣小龙虾.....");
        setMaterial("添加5份小龙虾...");
        setCondiment("添加适量的调味品...");
        mealInfo.add("生成可口的小龙虾...");
    }
}
```
5.测试
```
/**
 * Author Eirunye
 * Created by on 2018/9/19.
 * Describe
 */
public class Test {
    public static void main(String[] args) {

        //创建一个工厂类（即商品总店）
        MealStore mealStore = new MealStore();
       //这里我们要传递在静态工厂实例定义的字符串，否则将报空指针。
        mealStore.submitOrderMeal("roastChicken");
    }
}
```
输出结果：
```
"C:\Program Files\Java\jdk1.8.0_161\bin\java"...

您的商品准备中...烧鸡.....
adding material...
adding  condiment...
adding mealInfo...
 [生成可口的烧鸡...]
进行打包,只需1分钟就能打包完成!
外卖小哥获取美食,外卖小哥可能需要花费1~20分钟来获取美食!
配送给客户,配送需要大概5~40分钟送达!

Process finished with exit code 0
```
* 注：
首先我们将生产商品创建放在了静态工厂中，静态工厂处理对象创建的细节，MealStore 美食店只关心如何得到美食商品就可以了，并进行打包、获取、派送等操作，而这样操作起来方便了产品的创建了。

## 简单工厂方法

我们通过静态工厂方法的方式实现了该功能，但是，是否有更好的封装方式呢？接下来我们来分析一下吧。本例类图关系

![funtion_factory.png](https://upload-images.jianshu.io/upload_images/3012005-e55e00c647bcdb6d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/600)

1.现在我们将`MealStore.class`,修改为抽象类，并且将生产商品的方法也修改为抽象方法，我们这样做的目的，为什么呢？
我们这样做是让各个分店子类来实现商品的生产，扩展性更高，封装性更加完善，而该抽象类并不知道是哪个子类来完成商品的创建，达到了耦合。代码如下:
```JAVA
/**
 * Author Eirunye
 * Created by on 2018/9/18.
 * Describe 抽象工厂基类 美食总店
 */
public abstract class MealStore {

    /**
     * 客户下单
     *
     * @param type 选择什么样的美食
     * @return 美食 Meal
     */
    public Meal submitOrderMeal(String type) {

        Meal meal;

        //这里是我们将生产的美食
        meal = createMeal(type);

        //店家准备中...
        meal.mealPreparation();
        //打包
        meal.bake();
        //获取
        meal.getMeal();
        //配送
        meal.send();

        return meal;
    }

    //实现抽象的工厂方法，让每个分店来完成此生产操作，
    protected abstract Meal createMeal(String type);
}
```
2.创建分店子类`JiangNanMealStore .class`派生自`MealStore.class`,进行生产商品
```JAVA
/**
 * Author Eirunye
 * Created by on 2018/9/18.
 * Describe  JiangNanMealStore分店,这里是需要增加修改的地方，可能有新的菜品的时候就在这里增加或者删除***
 */
public class JiangNanMealStore extends MealStore {

    @Override
    protected Meal createMeal(String type) {
        return getMeal(type);
    }
   //让子类来创建产品
    private Meal getMeal(String type) {
        if ("crayfish".equals(type)) {
            return new CrayfishMeal();//小龙虾
        } else if ("roastDuck".equals(type)) {
            return new RoastDuckMeal();
        } else return null;
    }
}
```
3.创建商品抽象基类`Meal.class`。
```JAVA
/**
 * Author Eirunye
 * Created by on 2018/9/18.
 * Describe 抽象产品父类 美食抽象类
 */
public abstract class Meal {
public List mealInfo = new ArrayList();

    public void mealPreparation() {
        System.out.println("您的商品准备中..."+this.name);
        System.out.println("adding material...");
        System.out.println("adding  condiment...");
        System.out.println("adding mealInfo...");

        for (int i = 0, len = mealInfo.size(); i < len; i++) {
            System.out.println(" [" + mealInfo.get(i) + "]");
        }
    }
    public void bake() {
        System.out.println("进行打包,只需1分钟就能打包完成!");
    }
    public void getMeal() {
        System.out.println("外卖小哥获取美食,外卖小哥可能需要花费1~20分钟来获取美食!");
    }
    public void send() {
        System.out.println("配送给客户,配送需要大概5~40分钟送达!");
    }
}
```
3.定义具体子类`CrayfishMeal.class`产品，扩展自`Meal .class`
```JAVA
/**
 * Author Eirunye
 * Created by on 2018/9/18.
 * Describe 具体产品 小龙虾美食
 */
public class CrayfishMeal extends Meal {

    public CrayfishMeal() {
        setName("可口的小龙虾.....");
        setMaterial("添加几份小龙虾+一些材料...");
        setCondiment("添加适量的调味品...");
        mealInfo.add("生成可口的小龙虾...");
    }
    //这里重写了父类的方法
    @Override
    public void bake() {
        System.out.println("小龙虾分成5份打包!");
    }
}
```
4.测试
```
/**
 * Author Eirunye
 * Created by on 2018/9/18.
 * Describe
 */
public class Test {
    public static void main(String[] args) {
        MealStore jiangNanMealStore = new JiangNanMealStore();
        jiangNanMealStore.submitOrderMeal("crayfish");

        System.out.println("江南店完成一单");
        System.out.println("==============================\n");

        MealStore guangDongMealStore = new GuangDongMealStore();
        guangDongMealStore.submitOrderMeal("roastDuck");

        System.out.println("广东店完成一单");
    }
}
```
输出结果
```
"C:\Program Files\Java\jdk1.8.0_161\bin\java"...
您的商品准备中...可口的小龙虾.....
adding material...
adding  condiment...
adding mealInfo...
 [生成可口的小龙虾...]
小龙虾分成5粉打包!
外卖小哥获取美食,外卖小哥可能需要花费1~20分钟来获取美食!
配送给客户,配送需要大概5~40分钟送达!
江南店完成一单
==============================

您的商品准备中...江南烤鸭.....
adding material...
adding  condiment...
adding mealInfo...
 [生成可口的烤鸭...]
进行打包,只需1分钟就能打包完成!
外卖小哥获取美食,外卖小哥可能需要花费1~20分钟来获取美食!
配送给客户,配送需要大概5~40分钟送达!
广东店完成一单

Process finished with exit code 0
```
# 下载
[**本篇案例代码--码云**](https://gitee.com/eirunye/Simple_Factory_Pattern)

[**本篇案例代码--GitHub**](https://github.com/eirunye/Simple_Factory_Pattern)

[**设计模式系列--码云**](https://gitee.com/eirunye/Eirunye_DesignPatterns_Notes)

[**设计模式系列--GitHub**](https://github.com/eirunye/Eirunye_DesignPatterns_Notes)
# 总结
我们通过简单的例子了解了简单工厂模式的开发案例，其实，简单工厂并不是我们常说的23中设计模式，他只是我们常用的一种编程习惯罢了，抽象工厂，才是我们常用的设计模式，接下来我们会讲解到抽象工厂模式。
静态工厂（简单工厂）和简单工厂方法有什么不同呢？
静态工厂将生产商品都定义在静态工厂的方法内，而简单工厂方法是交给子类来完成。但是静态工厂(简单工厂)不具备简单工厂方法所具有的扩展性强。
简单工厂方法的子类将会出现大量相同的代码，但是同时它也可以重写分类的方法，完成自己定义操作。
# 推荐
大家可以到[**我的博客http://eirunye.github.io**](http://eirunye.github.io/)进行浏览相关文章，大家一起相互探讨技术。

[**设计模式系列**](https://eirunye.github.io/categories/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F/)大家可以了解相关知识。

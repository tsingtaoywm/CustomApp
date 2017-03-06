# CustomApp

ABCDE五个产品

基于配置实现
场景1: 新功能，BCDE有，A没有
	解决方案1：把入口UI屏蔽掉，使用配置文件if else来控制

场景2: 对某个页面， CDE有相同的UI，AB有些不同，A多一个控件，B少一个控件
	在Core中放相同的Layout，处理所有最全的逻辑
	在ABC中，做一个同名的Layout，修改为自己的UI，分为加、减、改三种：
		[done]场景2_1：		A减去控件：因为在Core中Activity会用到减去的R.id，运行时会报找不到这个控件id的错误，所以，需要开关来控制，不走这段逻辑。
		[done]场景2_2：		B加上控件button
			［done］一种办法是，在产品B中，继承之前的Activity，使用Hook，替换之
			[done]另一种办法是（C中实现），copy一份相同package的Activity到Core中，覆盖之前的Activity，这时，这个Activity不需要配置
					要注意不要提交copy到core的的代码

		场景2_3（不做新demo了）：		C修改UI：指的是属性的修改，没有额外的工作。
			修改UI：在具体产品中加新的layout
			修改Activity逻辑：继承＋Hook
		
	结论：尽量做减法
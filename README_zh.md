**[English](https://github.com/abcnull/api-auto-test/blob/master/README.md) | [博客](https://blog.csdn.net/abcnull/article/details/103722306)**

[TOC]

# 框架结构

```
api-auto-test
	- src
		- main
			- java
				- com.abcnull.apiautotest
					- beans【配置文件或表格的 bean】
						- PropertiesBean.java（配置文件的 bean）
						- RequestBean.java（请求的所有信息的 bean）
						- XlsBean.java（表格的一行的 bean）
					- constants【项目常量类】
						- BaseConstant.java（项目全局常量）
					- listeners【监听器】
						- RePrioritizingListener.java（优先级监听器）
						- TestLogListener.java（日志监听器）
						- TestReportListener.java（测试报告监听器）
					- utils【项目工具类】
						- HttpRequestSender.java（HTTP 请求发送器）
						- PropertiesReader.java（配置文件读取器）
						- RequestInfoTool.java（用来将几个 bean 内容综合起来）
						- SSLClient.java（https 配置用来产生 https 请求，目前这一块有问题）
						- XlsReader.java（表格读取器）
			- resources【项目资源文件】
				- log4j2.xml（log 日志的配置）
		- test
			- java
				- com.abcnull.apiautotest
					- bases【测试基类】
						- BaseProperties.java（读取配置文件基类）
						- BaseTest.java（总的基类）
						- BaseXls.java（读取表格基类）
					- cases【测试用例】
						- ApiTest.java（测试用例）
			- resources【测试资源文件】
				- apiconfigs【测试配置项】
					- simplecontroller【简单控制器配置项】
						- orderservice.properties（简单控制器的配置）
					- testplan【测试计划配置项】
						- orderservice.properties（测试计划的配置）
					- threadgroup【线程组配置项】
						- orderservice.properties（线程组的配置）
				- cases【测试用例表格】
					- orderservice.xls（测试用例表）
				- templates【报告模板】
					- index.html（测试报告模板）
	- target【mvn 产生】
	- .gitignore（项目忽略跟踪文件）
	- pom.xml
	- README.md
	- testng.xml
```

# 大致思想

此框架模仿了 jmeter 在工作台，线程组，控制器，http 请求器中读取配置文件来进行请求测试。此项目中通过 testng 的注解在测试具体用例之前的三个时期读取 properties 文件，这模仿了 jmeter 在具体的 http 请求之前设置类似比如请求信息头等信息，通过 excel 读取每一行接口来发送接口请求这类似 jmeter 中具体的 http 请求器

![1577354515320](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354515320.png)

目前表格中填的请求都只能是单接口请求，而不是说把一连串的请求当成流程请求依次放在表格中

![1577354129444](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354129444.png)

项目有一个 Base 基类，其中调用了其他几个基类，其他的这几个基类主要做文件读取的工作，这几个基类中会调用读取文件的工具类。Base 基类中还有 testng 的各种注解，这些注解实际上是测试用例运行各个期间，这些期间把读取不同的配置文件存放进去。

![1577354168910](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354168910.png)

所有的测试用例会继承 Base 基类，这样就可以使用到 Base 中已经读取到的配置文件了

![1577354206817](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354206817.png)

配置文件是可以往下渗透的，如第一阶段读取到了 a，第二个阶段读取到了 b，那么最终拿到读取的数据是 c（a∪b），测试用例中会取到最终的配置文件，然后读取表格接口，然后再综合一下表格中请求信息和配置信息，表格信息优先级更高，进行接口请求，一个 excel 表格实际上算成一个用例

![1577354542655](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354542655.png)

![1577354575454](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354575454.png)

![1577354619129](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354619129.png)

bean 中目前有一个配置文件的 bean，有一个表格中每一行接口的 bean，还有包含请求所有信息的 bean

![1577354649075](https://github.com/abcnull/Image-Resources/blob/master/api-auto-test/1577354649075.png)

# 使用注意

**使用场景**

非流程的单接口测试，目前暂不支持 cookie 和 https 协议，而且仅支持 get 和 post 请求，未来会不断完善补充

**几点注意**

1. 只支持 post，get，不支持 https，不支持 cookie
2. 在三个`.properties`中填写 headers应该用`\n`来隔开，比如`A:a\nB:b`，里头可以有空格，其中的空格全部会自动被去掉，而在 excel 的中可以直接把谷歌控制台 copy 的请求头粘贴上去，表格中显示的一行一个`A:a`
3. 对于 parameters，不论是 form-data 还是 json 数据，打开谷歌控制台，找到请求参数的地方，点击 view source，复制，然后粘贴到 parameters 后面，不论是 properties 文件还是表格中都可使用一样的方式操作
4. 目前若 properties 和表格中某些参数为空实际上是有默认值的，如协议默认值 http，端口号默认值 8080，接口默认值 /，编码方式默认值 UTF-8，请求方式默认值 GET
5. 建议需要测某个服务接口可以专门弄三个配置文件，如项目中的订单服务的配置文件，然后 excel 中全是订单服务的接口。需要测试多个服务接口，可以弄多个服务的请求配置文件，然后弄出指定的 excel 请求表格。若是多个服务请求信息基本类似，接口不同，可以共用 properties 文件，然后在 excel 中开多个 sheet 页，每个 sheet 页表示一个服务中要测的所有接口，这样也是可以的。还有其他方式可以自己配置

# 重点内容

**框架重点部分**

1. 把配置文件和表格的每一行弄成一个个 bean
2. 把配置文件读取，表格读取，http 发送等封装成工具类
3. 所有测试用例继承一个基类，基类中含有配置文件读出数据和表格读出数据然后使用，还包含在 testng 中具体哪个时期使用
4. 测试类继承基类来使用基类中读取到的配置文件
5. 读取到的多个配置文件内容会渗透综合，然后这个新的配置信息会和表格中的请求信息再进行渗透综合，得到最终的请求信息
6. 一个 excel 算作一个测试用例

**目前已实现**

1. 全部单接口测试
2. log 日志
3. BeautifulReport 报告生成
4. 仿 jmeter 各个时期都有配置文件，因此可以灵活配置来驱动不同表格进行各不同请求信息的接口访问
5. 表格每一行接口请求类似 jmeter 中 http 请求器
6. 支持 post，get，传参配置，信息头配置，响应体断言配置，其他参数可配置
7. 支持 excel 读取，不同 sheet 页读取
8. testng 中各种监听器

# 项目缺陷

**一些细小的问题待修改**

1. 循环表格中的列时，循环条件写死称具体数字，这对增加表格中列的时候不够友善，建议把循环条件修改成识别到多少列有值的形式
2. 可以考虑把 bean 写成接口的形式
3. 线程安全 synchronized 可否进一步优化
4. 不论是配置文件还是表格中，目前"    "这种仍被识别为填写了数据，但是""是被识别未填数据，可以将里头如果都是空格也识别成未填数据，这样配置文件没读取到就可以到表格中读该参数
5. 其他

**待实现**

1. https 的请求目前都被识别成 http 的形式，项目中岁写一个 https 获取的工具类和在基类中写好 https 产生的方式，但实际尝试没有生效，之后会不断完善以实现 https 请求
2. 目前只做了 get 和 post 请求，以后会加上其他请求方式
3. 代码实现 cookie，在表格也可考虑添加一列 cookie
4. 目前 get 请求没有传数据的代码，后面需要补充
5. 报告目前采用 BeautifulReport，之后可以考虑换成 allure 报告
6. 由于目前表格只测单接口，没有注重流程性的接口，之后会考虑如何做到表格中做流程性接口检测
7. 其他

# api-auto-test
- test 包中的 resources 中存在 apiconfigs 文件夹，其中包含多个 properties 类型的配置文件，其中主要是保存默认根地址和数据等非必输
可能有多个该类型文件，表示不同的服务类型，这样可以在testng中加上参变量就可以传到beforetest里头
- 在 BaseTest 的 @BeforeTest 中要写上读取指定传过来参变量的 properties 文件的数据
- 先写个大概的代码整体，然后分部移到src中
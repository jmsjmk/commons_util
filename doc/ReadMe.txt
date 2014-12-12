本jar包包含了java项目的一些基础工具
1、动态加载多个properties文件(V1.0)
2、定时打印jvm信息到日志里(V1.0)
3、封装了StringUtil类用于字符串处理,依赖commons-lang包(V1.0)

使用方法
1、动态加载多个properties文件
	使用PropertiesLoader.getInstance()启动定时加载任务
	任务会加载dynamic-config.properties文件读取classpath下的configProperties属性定义的所有配置文件(多个文件用逗号分割)
	并根据configReloadPeriod动态加载配置文件数据的间隔周期，小于等于0表示不动态加载，(单位秒)。
	通过PropertiesLoader.getInstance().getBoolean(#属性名#, false)获取配置文件中的值。

	***非常重要***
		#属性名#的唯一性用户自己保证，配置文件加载顺序按照configProperties中定义的先后顺序加载，如果参数名重复后加载的会覆盖之前的
		约定命名规则为:如果是dynamic-xxx.properties配置文件，则文件中的属性名为为xxxSomothing用于区别其他文件
	***非常重要***
	
2、定时打印jvm信息到日志里
	使用MonitorJvm.getInstance()启动定时加载任务
	使用上面的功能加载dynamic-monitorJvm.properties配置文件，monitorJvmOpen:是否打印jvm日志，monitorJvmPeriod:间隔周期(单位秒)